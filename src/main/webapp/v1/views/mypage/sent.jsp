<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<main>
	<h2>
		📤 보낸 쪽지함
		<button onclick="openComposePopup()" style="float: right;">✏
			쪽지 쓰기</button>
	</h2>

	<table border="1" width="100%" cellpadding="8"
		style="border-collapse: collapse; margin-top: 20px;">
		<thead>
			<tr>
				<th>받는 사람</th>
				<th>제목</th>
				<th>보낸 날짜</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="msg" items="${sentMessageList}">
				<tr>
					<td>${msg.receiverId}</td>
					<td><a href="viewSentMessage.do?messageId=${msg.messageId}">
							${msg.messageTitle} </a></td>
					<td><fmt:formatDate value="${msg.messageSendDate}"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<c:if test="${empty sentMessageList}">
		<p>보낸 쪽지가 없습니다.</p>
	</c:if>
<script>
	function openComposePopup() {
		const width = 600;
		const height = 500;

		// 가운데 정렬 계산
		const left = (window.screen.width - width) / 2;
		const top = (window.screen.height - height) / 2;

		window
				.open('composeForm.jsp', 'composeWindow',
						`width=${width},height=${height},top=${top},left=${left},scrollbars=yes`);
	}
</script>

</main>

