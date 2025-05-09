<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<main>
	<h2>
	📥 받은 쪽지함
	<button onclick="openComposePopup()" style="float: right;">✏ 쪽지 쓰기</button>
	</h2>

	<c:if test="${empty messageList}">
		<p>받은 쪽지가 없습니다.</p>
	</c:if>

	<c:if test="${not empty messageList}">
	<table border="1" width="100%" cellpadding="8" style="border-collapse: collapse; margin-top: 20px;">
		<thead>
			<tr>
				<th>보낸 사람</th>
				<th>제목</th>
				<th>보낸 날짜</th>
				<th>읽음 여부</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="msg" items="${messageList}">
				<tr>
					<td>${msg.senderId}</td>
					<td><a href="viewMessage.do?messageId=${msg.messageId}">${msg.messageTitle}</a></td>
					<td><fmt:formatDate value="${msg.messageSendDate}" pattern="yyyy-MM-dd HH:mm" /></td>
					<td>
						<c:choose>
							<c:when test="${msg.messageIsRead eq 'Y'}">읽음</c:when>
							<c:otherwise>안읽음</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</c:if>

	<script>
	function openComposePopup() {
		const width = 800;
		const height = 600;
		const left = (window.screen.width - width) / 2;
		const top = (window.screen.height - height) / 2;

		const popup = window.open(
			'composeForm.jsp',
			'composeWindow',
			`width=${width},height=${height},top=${top},left=${left},scrollbars=yes,resizable=yes`
		);
		if (popup) {
			popup.focus();
		}
	}
	</script>
</main>
