<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>성공</h1>
<%
  sobi.vo.member.MemberVO loginUser = (sobi.vo.member.MemberVO) session.getAttribute("loginUser");
  if (loginUser != null) {
    out.print("ROLE: " + loginUser.getRole());
  } else {
    out.print("로그인 유저 없음");
  }
%>
</body>
</html>
