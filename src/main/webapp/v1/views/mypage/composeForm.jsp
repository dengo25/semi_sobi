<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
  <title>쪽지 쓰기</title>
</head>
<body>
<main>
  <h2>쪽지 쓰기</h2>
  <form action="sendMessage.do" method="post">
    <label>받는 사람 ID: <input type="text" name="receiverId" value="${param.receiverId}" /></label><br/><br/>
    <label>제목: <input type="text" name="messageTitle" /></label><br/><br/>
    <label>내용:<br/>
      <textarea name="messageContent" rows="10" cols="50"></textarea>
    </label><br/><br/>
    <input type="submit" value="보내기" />
  </form>
</main>
</body>
</html>
