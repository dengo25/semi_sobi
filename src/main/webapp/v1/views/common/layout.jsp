<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
 <%@ include file="head_common.jsp" %>
 <title>SOBI : <c:out value="${title}"/></title>
</head>
<body>
<%@ include file="header.jsp" %>

<jsp:include page="${contentPage}" />
<%-- 
http://localhost:내 포트번호/sobi_test/v1/views/common/content.do
http://localhost:내 포트번호/sobi_test/v1/views/board/review_list.do
<c:import url="${contentPage} "/> 
컨텐츠영역<br>
- 컴포넌트 만들기<br><br>
- 테이블, 페이징<br>
- 폼관련<br>
- 버튼<br>
- 레이아웃 만들기<br><br>
- 메뉴
--%>
<%@ include file="footer.jsp" %>
</body>
</html>