<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <script>
    window.onload = function () {
      <c:choose>
        <c:when test="${sendSuccess eq true}">
          alert("쪽지가 성공적으로 전송되었습니다.");
          if (window.opener && !window.opener.closed) {
            window.opener.location.reload(); // 부모창 새로고침
          }
        </c:when>
        <c:otherwise>
          alert("쪽지 전송에 실패했습니다.");
        </c:otherwise>
      </c:choose>
      window.close();
    };
  </script>
</head>
<body></body>
</html>
