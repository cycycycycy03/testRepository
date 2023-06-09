package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberUpdatePwdController
 */
@WebServlet("/updatePwd.me")
public class MemberUpdatePwdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdatePwdController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// hidden타입으로 숨겨서 보낸 id값 꺼내보기
//		String userId = request.getParameter("userId");
//		System.out.println("솜겨왔나요: " + userId);
		
		// session에 담겨있는 loginUser에서 id값 꺼내보기
		HttpSession session = request.getSession();
		String userId = ((Member)session.getAttribute("loginUser")).getUserId();
//		System.out.println("세션에 있나요: " + userId);
		
		String updatePwd = request.getParameter("updatePwd");
		
		Member updateMem = new MemberService().updatePwd(userId, updatePwd);
		
		if(updateMem != null) {
			session.setAttribute("alertMsg", "비밀번호 수정 완료. 변경된 비밀번호로 재로그인해주세요.");
			session.setAttribute("loginUser", updateMem);
			response.sendRedirect(request.getContextPath()+"/logout.me");
		}
		else {
			session.setAttribute("errorMsg", "비밀번호 수정을 실패하였습니다.");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
		}
	}

}
