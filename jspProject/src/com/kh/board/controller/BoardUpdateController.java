package com.kh.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.common.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardUpdateController
 */
@WebServlet("/update.bo")
public class BoardUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardNo = Integer.parseInt(request.getParameter("bno"));
		Board b = new BoardService().selectBoard(boardNo);
		Attachment at = new BoardService().selectAttachment(boardNo);
		ArrayList<Category> list = new BoardService().categoryList();
		request.setAttribute("clist", list);
		request.setAttribute("board", b);
		request.setAttribute("attachment", at);
		request.getRequestDispatcher("views/board/boardUpdateForm.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// enctype이 multipart/form-data 형식인지 확인
		if(ServletFileUpload.isMultipartContent(request)) {
			// 전송 파일 용량 제한
			int maxSize = 10 * 1024 * 1024;
			// 저장시킬 서버 저장경로 찾기 (물리적인 서버 폴더 경로)
			String savePath = request.getSession().getServletContext().getRealPath("/resources/board_files/");
			// 파일명 수정 작업 객체 추가하기
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
			
			// 수정 작업에 필요한 기존 데이터 추출하기
			int boardNo = Integer.parseInt(multiRequest.getParameter("boardNo"));
			String category = multiRequest.getParameter("category");
			String boardTitle = multiRequest.getParameter("title");
			String boardContent = multiRequest.getParameter("content");
			
			Board b = new Board();
			b.setBoardNo(boardNo);
			b.setCategory(category);
			b.setBoardTitle(boardTitle);
			b.setBoardContent(boardContent);
			
			// 새롭게 전달된 첨부파일이 있다면 처리하기
			Attachment at = null;
			
			if(multiRequest.getOriginalFileName("reUpfile") != null) {
				// 조회가 된 경우 (첨부파일이 있음)
				at = new Attachment();
				at.setOriginName(multiRequest.getOriginalFileName("reUpfile")); // 원본명
				at.setChangeName(multiRequest.getFilesystemName("reUpfile")); // 수정명 (실제 서버에 업로드된 파일명)
				at.setFilePath("resources/board_files");
				
				// 기존에 첨부파일이 있었을 경우 해당 데이터에서 수정 작업
				// form에서 hidden으로 파일번호와 변경된 이름(서버에 저장된 이름)을 전달했기 때문에
				// 해당 정보가 있는지 없는지 판단하면 된다.
				if(multiRequest.getParameter("fileNo") != null) {
					// 새로운 첨부파일이 있고, 기존에도 첨부파일이 있는 경우
					// updateAttachment
					// 기존 파일번호(식별자)를 이용하여 데이터 변경하기
					at.setFileNo(Integer.parseInt(multiRequest.getParameter("fileNo")));
					
					// 기존 첨부파일 삭제하기
					new File(savePath + "/" + multiRequest.getParameter("originFileName")).delete();
				}
				else {
					// 원래 첨부파일이 없었고, 새롭게 들어온 경우
					// 현재 게시글 번호를 참조하게 INSERT하기
					at.setRefBno(boardNo);
				}
			}
			else {
				// 새로운 업로드없이 기존파일 삭제만 했을 경우
				// removeAttachment
				at = new Attachment();
				at.setFileNo(Integer.parseInt(multiRequest.getParameter("fileNo")));
				new File(savePath + "/" + multiRequest.getParameter("originFileName")).delete();
			}
			
			// 서비스에게 준비된 객체들 전달하며 서비스 요청하기
			BoardService service = new BoardService();
			int result = service.updateBoard(b, at);
			// 새로운 첨부파일 없고, 기존 첨부파일도 없는 경우: Board-update
			// 새로운 첨부파일 있고, 기존 첨부파일 없는 경우: Board-update & Attachment-insert
			// 새로운 첨부파일 있고, 기존 첨부파일 있는 경우: Board-update & Attachment-update
			b = service.selectBoard(boardNo);
			
			if(result > 0) {
				b = service.selectBoard(boardNo);
				at = service.selectAttachment(boardNo);
				request.setAttribute("savePath", savePath);
				request.setAttribute("board", b);
				request.setAttribute("attachment", at);
				request.getSession().setAttribute("alertMsg", "게시글 수정 성공");
				response.sendRedirect(request.getContextPath()+"/detail.bo?bno="+boardNo);
			}
			else {
				// 실패시 업로드된 파일 지워주는 작업이 필요하다 (게시글은 없는데 업로드 파일이 자원을 쓰고있으니)
				if(at != null) { // 넘어온 파일이 있어서 객체가 생성됐다면
					// 해당 파일 경로 잡아서 파일 객체 생성 후 delete메소드로 파일 삭제 작업
					new File(savePath+at.getChangeName()).delete();
				}
				request.setAttribute("errorMsg", "게시글 수정 실패");
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			}
		}
	}

}
