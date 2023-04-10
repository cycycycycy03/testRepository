package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Board;
import com.kh.common.model.vo.PageInfo;

/**
 * Servlet implementation class BoardListController
 */
@WebServlet("/list.bo")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ----------------------페이징 처리----------------------
		int listCount; // 현재 게시글의 총 개수
		int currentPage; // 현재 페이지
		int pageLimit; // 페이지 하단에 보여질 페이징바의 최대 페이지 개수
		int boardLimit; // 한 페이지에서 보여질 최대 게시글 개수
		
		int maxPage; // 가장 마지막 페이지가 몇인지 (총 페이지 개수)
		int startPage; // 페이징 하단에 보여질 페이징바의 시작 수
		int endPage; // 페이징 하단에 보여질 페이징바의 끝 수
		
		// listCount: 총 게시글 개수 구하기
		listCount = new BoardService().selectListCount();
		
		// currentPage: 현재 페이지
		currentPage = Integer.parseInt(request.getParameter("cPage"));
		
		// pageLimit: 페이지 하단에 보여질 페이징바의 페이지 최대 개수 (목록 단위)
		pageLimit = 10;
		// boardLimit: 한 페이지에 보여질 게시글 개수 (게시글 단위)
		boardLimit = 10;
		
		/*
		 * maxPage: listCount와 boardLimit의 영향을 받는 수
		 * 
		 * - 공식 찾기
		 * 총 게시글 개수		boardLimit		maxPage
		 * 100		/	10		= 10.0		10
		 * 101		/	10		= 10.1		11
		 * 111		/	10		= 11.2		12
		 * 나눗셈 후 올림처리를 통해 maxPage 구하기
		 * 
		 * 1) listCount를 double 자료형을 바꾸기
		 * 2) listCount/boardLimit
		 * 3) 결과를 올림 처리(Math.ceil() 메소드)
		 * 4) 결과값을 int로 마무리
		 * 
		 * */
		maxPage = (int)Math.ceil((double)listCount/boardLimit);
		
		/*
		 * startPage: 페이징바의 시작 수
		 * 
		 * - 공식찾기
		 * startPage: 1, 11, 21, 31, 41, ..., n*pageLimit+1
		 * currentPage		startPage		pageLimit=10
		 * 1	0/10 0				1
		 * 5	4/10 0				1
		 * 10	9/10 1				1
		 * 11	10/10 1				11
		 * 15	14/10 1				11
		 * 20	19/10 1				11
		 * 21	20/10 2				21
		 * 
		 * */
		startPage = 1 + pageLimit*((currentPage-1)/pageLimit);
		// endPage: 페이징바 끝 수
		endPage = startPage + (pageLimit-1); // 1-10  11-20  21-30
		
		// 총 페이지 수(maxPage)가 13이라면?
		// startPage: 11  endPage: 20
		if(endPage > maxPage) { // 끝 수가 페이지 수보다 크다면 해당 수를 총 페이지 수로 바꿔주기
			endPage = maxPage;
		}
		
		// 페이지 정보들을 하나의 객체에 담아보내기
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
		
		// 현재 사용자가 요청한 페이지(currentPage)에 보여질 게시글 리스트 조회
		ArrayList<Board> list = new BoardService().selectList(pi);
		
		// 조회된 리스트와 페이징정보 request로 보내기
		request.setAttribute("boardList", list);
		request.setAttribute("pageInfo", pi);
		request.getRequestDispatcher("views/board/boardListView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
