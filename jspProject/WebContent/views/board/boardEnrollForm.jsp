<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.board.model.vo.Category"%>
<%
	ArrayList<Category> clist = (ArrayList<Category>)request.getAttribute("clist");
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
    <br>
    <h2 align="center">글 작성 페이지</h2>

    <!--카테고리, 제목, 내용, 첨부파일, 작성자번호-->
    <form action="<%=contextPath%>/insert.bo" method="post" id="enroll-form" enctype="multipart/form-data">
        <input type="hidden" name="userNo" value="<%=loginUser.getUserNo()%>">
        <!--카테고리 테이블에서 조회해 온 카테고리 목록 선택상자 만들기-->
        <table align="center">
            <tr>
                <th width="100">카테고리</th>
                <td width="500">
                    <select name="category">
                        <%for(Category c : clist) {%>
                        	<option value="<%=c.getCategoryNo()%>"><%=c.getCategoryName() %></option>
                        <%} %>
                    </select>
                </td>
            </tr>
            <tr>
                <th>제목</th>
                <td><input type="text" name="title" required></td>
            </tr>
            <tr>
                <th>내용</th>
                <td><textarea name="content" cols="30" rows="10" style="resize: none;" required></textarea></td>
            </tr>
            <tr>
                <th>첨부파일</th>
                <td><input type="file" name="upfile"></td>
            </tr>
        </table>
        <br>
        <div align="center">
            <button type="submit">게시글 등록</button>
            <button type="reset">초기화</button>
        </div>
    </form>
</div>
</body>
</html>