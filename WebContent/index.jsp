<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<div align="center">
		<hr width="50%" color="red" />
		<h3>BOARD1 메인 페이지</h3>
		<hr width="50%" color="red" />
		<br /><br /><br />
		<%-- request.getContextPath() : 현재 프로젝트명을 반환하는 메소드 --%>
		<a href="<%= request.getContextPath() %>/select.do">[레코드 검색]</a>
		<%-- /select  서블릿의 별칭. 그러니까, select라는 이름의 서블릿을 생성하면 됨! --%>
		<%-- 스크립트릿/없이 select만 해도 이동이 가능하나, 명확한 코드를 위하여 사용한다. --%>
	</div>

</body>
</html>