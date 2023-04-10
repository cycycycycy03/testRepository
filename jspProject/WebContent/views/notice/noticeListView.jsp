<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.notice.model.vo.Notice"%>
<%
	ArrayList<Notice> list = (ArrayList<Notice>)request.getAttribute("noticeList");
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
    <h2 align="center">공지사항</h2>
    <br>

	<!-- 
		공지사항 작성은 관리자만 가능하게 조건 부여하기
		null인지 확인하는 작업을 먼저 작성하여야 한다. 접근전에 확인 후 벗어나기 위해 (논리추론)
	-->
	<%if(loginUser!=null && loginUser.getUserId().equals("admin")) {%>
    <div align="right">
        <a href="<%=contextPath %>/insert.no" class="btn btn-primary" style="margin-right: 30px">공지사항 작성</a>
        <br><br>
    </div>
    <%} %>

    <table class="list-area" align="center">
        <thead>
            <tr style="border:1px solid black">
                <th>글번호</th>
                <th width="400">글제목</th>
                <th width="100">작성자</th>
                <th>조회수</th>
                <th width="100">작성일</th>
            </tr>
        </thead>
        <tbody>
        	<%if(list.isEmpty()) {%>
        		<tr>
        			<td>존재하는 공지사항이 없습니다. </td>
        		</tr>
        	<%} else { %>
	            <%for(Notice n : list) {%>
	            	<tr>
	                	<td><%=n.getNoticeNo()%></td>
	                	<td><%=n.getNoticeTitle()%></td>
	                	<td><%=n.getNoticeWriter()%></td>
	                	<td><%=n.getCount()%></td>
	                	<td><%=n.getCreateDate()%></td>
	            	</tr>
	            <%} %>
	         <%} %>
        </tbody>
    </table>
    
    <script>
        $(function() {
        	// list-area클래스의 자손 tbody의 자손 tr이 클릭됐을 때
        	$(".list-area>tbody>tr").click(function() {
        		// 글을 클릭했을 때 해당 글번호가 있어야 그 글에 대해서 상세조회를 할 수 있으니 글번호 추출하여 서버에 넘기기
                // console.log($(this).children().eq(0).text());
        		var nno = $(this).children().eq(0).text();
        		
        		// 요청할 url?key1=value1&key2=value2...
        		// 물음표 뒤 내용들을 쿼리 스트링이라고 한다. - 직접 기술하여 넘기기
        		location.href = "<%=contextPath%>/detail.no?nno=" + nno;
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
</body>
</html>