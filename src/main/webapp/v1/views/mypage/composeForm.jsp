<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
  	<%@ include file="../common/head_common.jsp" %>
  	<title>쪽지 쓰기</title>
</head>
<body>

  <h2>쪽지 쓰기</h2>
  <form action="sendMessage.do" method="post">
  	<table class="table-basic type-popup">
		<caption>쪽지쓰기 테이블 - 받는사람아이디,제목,내용으로 구성</caption>
		<colgroup>
			<col style="width: 20%">
			<col style="width: 80%">
		</colgroup>
		<tbody>
			<tr>
				<th>받는 사람 ID</th>
				<td>
					<div class="input-group">
						<input type="text" name="receiverId" class="form-control" value="${param.receiverId}" />
					</div>
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>
					<div class="input-group">
						<input type="text" name="messageTitle" class="form-control"/>
					</div>
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<div class="input-group">
						<textarea name="messageContent" rows="10" cols="50" class="form-control"></textarea>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
    <div class="btn-area type-popup">
		<button type="submit" class="btn btn-primary">보내기</button>
	</div>
  </form>
</body>
</html>
