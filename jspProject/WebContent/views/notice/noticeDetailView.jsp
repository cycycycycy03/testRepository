<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.notice.model.vo.Notice"%>
<%
	Notice n = (Notice)request.getAttribute("notice");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#detail-area {
		border: 1px solid black;
	}
	
	.outer {
            background-color: rgb(233, 231, 231);
            width: 1000px;
            height: 500px;
            margin: auto;
            margin-top: 50px;
    }
</style>
</head>

<body>
<%@include file="../common/menubar.jsp" %>
	<div class="outer">
		<br>
		<h2 align="center">공지사항 상세보기</h2>
		<br>
		
		<table id="detail-area" align="center">
			<tr>
				<th width="70">제목</th>
				<td width="350" colspan="3"><%=n.getNoticeTitle() %></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><%=n.getNoticeWriter() %></td>
				<th>작성일</th>
				<td><%=n.getCreateDate() %></td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3"><p style="height:150px"><%=n.getNoticeContent() %></p></td>
			</tr>
		</table>
		<br><br>
		
		<%if(loginUser!=null && loginUser.getUserId().equals("admin")) {%>
		<div align="center">
			<a href="<%=contextPath %>/update.no?nno=<%=n.getNoticeNo() %>" class="btn btn-warning">수정하기</a>
			<a class="btn btn-danger" id="delete">삭제하기</a>
		</div>
		
		<script>
			$(function() {
				$("#delete").click(function() {
					var check = confirm("정말 삭제하시겠습니까?");
					
					if(check==true) {
						location.href = "<%=contextPath%>/delete.no?nno="+<%=n.getNoticeNo()%>;
					}
				});
			});
		</script>
		<%} %>
	</div>
</body>
</html>