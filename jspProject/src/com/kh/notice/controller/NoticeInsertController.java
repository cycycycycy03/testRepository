package com.kh.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeInsertController
 */
@WebServlet("/insert.no")
public class NoticeInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("views/notice/noticeEnrollForm.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 데이터베이스에 작성한 내용 등록시키고 (insertNotice)
		request.setCharacterEncoding("UTF-8");
		String noticeTitle = request.getParameter("title");
		String noticeContent = request.getParameter("content");
		String noticeWriter = request.getParameter("writer");
		Notice n = new Notice(noticeTitle, noticeContent, noticeWriter);
		HttpSession session = request.getSession();
//		String noticeWriter = String.valueOf(((Member)(session.getAttribute("loginUser"))).getUserNo());
		int result = new NoticeService().insertNotice(n);
		
		// 성공시 알림메세지로 공지등록 완료 띄우고 공지사항 목록으로 이동 (재요청)
		if(result > 0) {
			session.setAttribute("alertMsg", "공지사항 등록 완료");
			response.sendRedirect(request.getContextPath() + "/list.no");
		}
		// 실패시 에러페이지로 이동 (공지사항 작성 실패) 메세지 (위임)
		else {
			request.setAttribute("errorMsg", "공지사항 작성 실패");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
		}
	}

}
