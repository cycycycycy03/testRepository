<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.board.model.vo.Attachment, com.kh.board.model.vo.Board"%>
<%
	Board b = (Board)request.getAttribute("board");
	Attachment at = (Attachment)request.getAttribute("attachment");
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
    
    #detail-area {
		border: 1px solid black;
	}
</style>
</head>

<body>
<%@include file="../common/menubar.jsp" %>
	<div class="outer">
	<br>
	<h2 align="center">일반게시판 상세보기</h2>
	<br>
		<table id="detail-area" align="center">
			<tr>
				<th width="70">카테고리</th>
				<td width="70"><%=b.getCategory() %></td>
				<th width="70">제목</th>
				<td width="350"><%=b.getBoardTitle() %></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><%=b.getBoardWriter() %></td>
				<th>작성일</th>
				<td><%=b.getCreateDate() %></td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3"><p style="height:200px"><%=b.getBoardContent() %></p></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<%if(at!=null) {%>
					<td colspan="3"><a href="<%=contextPath %>/<%=at.getFilePath()%>/<%=at.getChangeName() %>" download="<%=at.getOriginName()%>"><%=at.getOriginName() %></a></td>
				<%} else { %>
					<td colspan="3">첨부파일이 없습니다.</td>
				<%} %>
			</tr>
		</table>
		<br><br>
		
		<%if(loginUser!=null && loginUser.getUserId().equals(b.getBoardWriter())) {%>
		<div align="center">
			<a href="<%=contextPath %>/update.bo?bno=<%=b.getBoardNo() %>" class="btn btn-warning">수정하기</a>
			<a class="btn btn-danger" id="delete">삭제하기</a>
		</div>
		
		<script>
			$(function() {
				$("#delete").click(function() {
					var check = confirm("정말 삭제하시겠습니까?");
					
					if(check==true) {
						location.href = "<%=contextPath%>/delete.bo?bno="+<%=b.getBoardNo()%>;
					}
				});
			});
		</script>
		<%} %>
	</div>
</body>
</html>