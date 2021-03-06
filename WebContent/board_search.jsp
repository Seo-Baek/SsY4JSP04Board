<%@page import="com.sist.model.BoardDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	List<BoardDTO> search = (List<BoardDTO>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div align="center">
		<hr width="500" color="tomato" />
			<h3>BOARD1 테이블 게시물 검색 목록</h3>
		<hr width="500" color="tomato" />
		<br /><br />
		<table border="1" cellspacing="0" width="400">
			<tr>
				<th>글번호</th>
				<th>글제목</th>
				<th>조회수</th>
				<th>작성일자</th>
			</tr>
			<%
				if(search.size() != 0) { // 검색된 레코드가 있으면
					// 검색된 레코드 수만큼 반복해서 웹 브라우저에 출력
					for(int i = 0; i < search.size(); i++){
						BoardDTO dto = search.get(i);
			%>		
			<tr>
				<td> <%= dto.getBoard_no() %></td>
				<td> 
					<%-- get방식으로 (위에 정보가 나오게), no는 변수, 
					              변수에 값을 저장해서 같이 넘어간다. 
					         인자를 두 개 이상 넘기려면, ...&변수명=값으로 작성 --%>
					<a href="cont.do?no=<%=dto.getBoard_no() %>">
					<%-- = <a href="<%=request.getContextPath() %>
					            cont.do?no=<%=dto.getBoard_no() %>"> --%> 
				<%= dto.getBoard_title() %></a></td>
				<td> <%= dto.getBoard_hit() %></td>				
				<td> <%= dto.getBoard_regdate() %></td>
			</tr>	
			<%		} //for문 end
					
				} else { // 검색된 레코드가 없는 경우 %>
				<tr>
					<td colspan="4" align="center">
						<h3>검색할 레코드가 없습니다.</h3>
					</td>
				</tr>		
			<%	}
			%>
		</table>
		<br />
		<input type="button" value="전체목록" onclick="location.href='select.do" />
	</div>

</body>
</html>