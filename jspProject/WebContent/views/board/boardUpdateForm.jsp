<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.board.model.vo.Category, com.kh.board.model.vo.Attachment, com.kh.board.model.vo.Board, java.io.File"%>
<%
	Board b = (Board)request.getAttribute("board");
	Attachment at = (Attachment)request.getAttribute("attachment");
	ArrayList<Category> clist = (ArrayList<Category>)request.getAttribute("clist");
	String savePath = (String)request.getAttribute("savePath");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
    .outer {
        background-color: rgb(233, 231, 231);
        width: 1000px;
        height: 500px;
        margin: auto;
        margin-top: 50px;
    }
    
    #update-form>table {
    	border: 1px solid black;
    }
    
    #update-form input, textarea {
    	width: 100%;
    	box-sizing: border-box;
    }
</style>
<body>
<%@include file="../common/menubar.jsp" %>
<div class="outer">
    <br>
    <h2 align="center">글 수정 페이지</h2>
    <br>

    <!--카테고리, 제목, 내용, 첨부파일, 작성자번호-->
    <form action="<%=contextPath%>/update.bo" method="post" id="update-form" enctype="multipart/form-data">
        <input type="hidden" name="boardNo" value="<%=b.getBoardNo()%>">
        <!--카테고리 테이블에서 조회해 온 카테고리 목록 선택상자 만들기-->
        <table align="center">
            <tr>
                <th width="100">카테고리</th>
                <td width="500">
                    <select name="category">
                        <%for(Category c : clist) {%>
                        	<!-- 사용자 원래 선택했던거 if문 돌려서 알아내고 selected -->
                        	<%if(b.getCategory().equals(c.getCategoryName())) {%>
                        		<option value="<%=c.getCategoryNo()%>" selected><%=c.getCategoryName() %></option>
                        	<%} else { %>
                        		<option value="<%=c.getCategoryNo()%>"><%=c.getCategoryName() %></option>
                        	<%} %>
                        <%} %>
                    </select>
                    <!--  
                    <script>
                    	$(function() {
                    		// option에 있는 text와 조회해 온 게시글 카테고리가 일치하는지 찾아내어 선택되어있게 작업하기
                    		$("#update-form option").each(function() {
                    			// 현재 접근된 요소객체의 text와 조회해 온 카테고리가 같다면
                    			if($(this).text() == "b.getCategory()") {
                    				// 해당 요소를 선택되어있게 만들기
                    				$(this).attr("selected", true);
                    			}
                    		})
                    	})
                    </script>
                    -->
                </td>
            </tr>
            <tr>
                <th>제목</th>
                <td><input type="text" name="title" value="<%=b.getBoardTitle() %>" required></td>
            </tr>
            <tr>
                <th>내용</th>
                <td><textarea name="content" cols="30" rows="10" style="resize: none;" required><%=b.getBoardContent() %></textarea></td>
            </tr>
            <tr>
                <th>첨부파일</th>
                <!-- 첨부파일 유무 확인 -->
                <%if(at==null) {%>
                	<td><input type="file" name="reUpfile"></td>
                <%} else { %>
                	<td>
                		<!-- 기존 첨부파일이 있었을 경우 수정할 때 첨부파일 정보를 보내야 한다. -->
                		<!-- 파일번호, 변경된 파일명 전달하기 -->
                		<input type=hidden name="fileNo" value="<%=at.getFileNo()%>">
                		<input type=hidden name="originFileName" value="<%=at.getChangeName()%>">
                		<a href="<%=contextPath %>/<%=at.getFilePath()%>/<%=at.getChangeName() %>"><%=at.getOriginName() %></a>
                		<button type="button">삭제</button>
                	</td>
                	
                <%} %>
            </tr>
        </table>
        <br>
        
        <script>
				$(function() {
					$("td>button").click(function() {
						var str = '<input type="file" name="upfile">';
						$(this).parent("td").html(str);
					});
				})
			</script>
        <div align="center">
            <button type="submit">수정하기</button>
            <button type="button" onclick="history.back();">뒤로가기</button>
        </div>
    </form>
</div>
</body>
</html>