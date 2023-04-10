<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    	width: 760px;
    	margin: auto;
    }
    
    .thumbnail {
    	border: 1px solid black;
    	width: 220px;
    	display: inline-block;
    	margin: 14px;
    }
    
    .thumbnail>img:hover {
    	cursor: pointer;
    	opacity: 0.7;
    }
</style>
</head>

<body>
	<%@include file="../common/menubar.jsp" %>
	<div class="outer">
		<br>
		<h2 align="center">사진 게시판</h2>
		<br><br>
		
		<%if(loginUser != null) {%>
			<div align="right">
				<a href="<%=contextPath%>/insert.ph" class="btn btn-primary" style="margin-right: 30px">글 작성</a>
				<br><br>
			</div>
		<%} %>
		
		<div class="list-area">
			<div class="thumbnail" align="center">
				<img width="200px" height="150px">
				<p>
					No.5<br>
					조회수: 14 <br>
				</p>
			</div>
		</div>
	</div>
</body>
</html>