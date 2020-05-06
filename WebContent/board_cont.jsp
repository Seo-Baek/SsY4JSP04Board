<%@page import="com.sist.model.BoardDTO"%>
<%@page import="com.sist.model.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%-- 20/03/11 여기까지 --%>
<%
	BoardDTO cont =(BoardDTO)request.getAttribute("Cont");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div align="center">
		<table border="1" cellspacing="0" width="400">
			<%
				if(cont != null){	// 검색된 레코드가 있는 경우
			%>
				<tr>
					<th colspan="2" align="center">
						<h3><%=cont.getBoard_writer() %>님 게시물 상세 내역</h3>
					</th>
				</tr>	
				<tr>
					<th>작성자</th>
					<td> <%= cont.getBoard_writer() %></td>
				</tr>
				<tr>
					<th>글제목</th>
					<td><%= cont.getBoard_title() %></td>
				</tr>
				<tr>
					<th>글내용</th>
					<td>
						<textarea rows="8" cols="30" readonly><%=
						/* <%= */cont.getBoard_cont() %>
						</textarea>
					</td>
				</tr>
				<tr>
					<th>조회수</th>
					<td><%= cont.getBoard_hit() %></td>
				</tr>
				<tr>
					<th>작성일자</th>
					<td><%= cont.getBoard_regdate() %></td>
				</tr>
			
			<%} else {%>
				<tr>
					<td colspan="2" align="center">
						<h3>검색된 레코드가 없습니다.</h3>
					</td>
				</tr>
			<% } %>
			<tr>
				<td colspan="2" align="right">&nbsp;비밀번호 : 
					<input type="password" name="pwd" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="수정"
				onclick="location.href='<%=request.getContextPath()%>/update.do?no=<%=cont.getBoard_no() %>&userpwd=<%=cont.getBoard_pwd() %>'" />
					<input type="button" value="삭제"
				onclick="location.href='delete.do?no=<%=cont.getBoard_no() %>'" />
				&nbsp;
					<input type="button" value="전체목록"
				onclick="location.href='select.do'" />
				&nbsp;
					<%-- 한번 해봤당..ㅋㅋ --%>
					<%-- <a href="select.do">[전체 목록]</a> --%>
					<%-- 늘어난 조회수 값을 다시 가져오기 위해
						 뒤로 가지 않고 새로운 dao를 불러주는 것 --%>
				</td>
			</tr>
		</table>
	</div>

</body>
</html>