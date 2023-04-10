<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.board.model.vo.Board, com.kh.common.model.vo.PageInfo"%>
<%
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("boardList");
	PageInfo pi = (PageInfo)request.getAttribute("pageInfo");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .outer {
        background-color: rgb(233, 231, 231);
        width: 1000px;
        height: 500px;
        margin: auto;
        margin-top: 50px;
    }

    .list-area {
        border: 1px solid black;
        text-align: center;
    }

    .list-area>tbody>tr:hover {
        background-color: gray;
    }
</style>
</head>

<body>
<%@include file="../common/menubar.jsp" %>

<div class="outer">
    <br>
    <h1 align="center">일반 게시판</h1>
    <br><br>

	<%if(loginUser != null) {%>
	    <div align="right">
	        <a href="<%=contextPath %>/insert.bo" class="btn btn-primary" style="margin-right: 30px">글 작성</a>
	        <br><br>
	    </div>
	<%} %>

    <table align="center" class="list-area">
        <thead>
            <tr style="border:1px solid black">
                <th width="70">글번호</th>
                <th width="80">카테고리</th>
                <th width="400">제목</th>
                <th width="150">작성자</th>
                <th width="80">조회수</th>
                <th width="150">작성일</th>
            </tr>
        </thead>
        <tbody>
        	<%if(list.isEmpty()) {%> <!-- 조회된 목록이 비어있다면 -->
        		<tr>
        			<td colspan="6">조회된 게시글이 없습니다.</td>
        		</tr>
        	<%} else {%>
        		<%for(Board b : list) {%>
	        		<tr>
	                	<td><%=b.getBoardNo() %></td>
	                	<td><%=b.getCategory() %></td>
	                	<td><%=b.getBoardTitle() %></td>
	                	<td><%=b.getBoardWriter() %></td>
	                	<td><%=b.getCount() %></td>
	                	<td><%=b.getCreateDate() %></td>
	            	</tr>
        		<%} %>
        	<%} %>
        </tbody>
    </table>
    <br><br>
    
    <script>
    	$(function() {
    		$(".list-area>tbody>tr").click(function() {
        		// 글 번호 추출
        		var bno = $(this).children().eq(0).text();
        		location.href = "<%=contextPath%>/detail.bo?bno=" + bno;
        	});
        	
    		window.onpageshow = function(event) {
    		    if ( event.persisted || (window.performance && window.performance.navigation.type == 2)) {
    		        // Back Forward Cache로 브라우저가 로딩될 경우 혹은 브라우저 뒤로가기 했을 경우
    		        location.reload();
    		    }
    		}
    	});
    </script>
</div>
<br><br>

<div align="center" class="paging-area">
	<%if(pi.getStartPage() != 1) {%>
		<button class="btn btn-outline-dark" onclick="location.href='<%=contextPath%>/list.bo?cPage=<%=pi.getStartPage()-10 %>';">&lt;&lt;</button>
	<%} %>
	<%if(pi.getCurrentPage() != 1) {%>
		<button class="btn btn-outline-dark" onclick="location.href='<%=contextPath%>/list.bo?cPage=<%=pi.getCurrentPage()-1%>';">&lt;</button>
	<%} %>
    <%for(int i=pi.getStartPage();i<=pi.getEndPage();i++) {%>
    	<!-- 내가 보고있는 페이지 버튼은 비활성화 하기 -->
    	<%if(i != pi.getCurrentPage()) {%>
    		<button class="btn btn-outline-dark" onclick="location.href='<%=contextPath%>/list.bo?cPage=<%=i%>';"><%=i %></button>
    	<%} else { %> <!-- 내가 보고있는 페이지의 버튼을 누른다면 -->
    		<button class="btn btn-outline-dark"><%=i %></button>
    	<%} %>
    <%} %>
    <%if(pi.getCurrentPage() != pi.getMaxPage()) {%>
    	<button class="btn btn-outline-dark" onclick="location.href='<%=contextPath%>/list.bo?cPage=<%=pi.getCurrentPage()+1%>';">&gt;</button>
    <%} %>
    <%if(pi.getEndPage() != pi.getMaxPage()) {%>
		<button class="btn btn-outline-dark" onclick="location.href='<%=contextPath%>/list.bo?cPage=<%=pi.getStartPage()+10 %>';">&gt;&gt;</button>
	<%} %>
</div>
</body>
</html>