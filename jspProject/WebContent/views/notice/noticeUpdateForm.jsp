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
    .outer {
        background-color: rgb(233, 231, 231);
        width: 1000px;
        height: 500px;
        margin: auto;
        margin-top: 50px;
    }
    
    #enroll-form>table {
    	border: 1px solid black;
    }
    
    #enroll-form input, textarea {
    	width: 100%;
    	box-sizing: border-box;
    }
</style>
</head>

<body>
<%@include file="../common/menubar.jsp" %>
	<div class="outer">
		<h2 align="center">공지사항 수정</h2>
		<br>
		
		<form id="enroll-form" action="<%=contextPath %>/update.no" method="post">
		<input type="hidden" name="nno" value="<%=n.getNoticeNo()%>">
			<table align="center">
				<tr>
					<td width="50">제목</td>
					<td width="350"><input type="text" name="title" value="<%=n.getNoticeTitle() %>" required></td>
				</tr>
				<tr>
					<td>내용</td>
					<td></td>
				</tr>
				<tr>
					<td colspan="2">
						<textarea rows="10" cols="20" style="resize:none" name="content" required><%=n.getNoticeContent() %></textarea>
					</td>
				</tr>
			</table>
			<br><br>
			
			<div align="center">
				<button type="submit">수정하기</button>
				<button type="button" onclick="history.back();">뒤로가기</button>
				<!-- history.back(): 이전 페이지로 돌아가는 함수 -->
			</div>
		</form>
	</div>
</body>
</html>