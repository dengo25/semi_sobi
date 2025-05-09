<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<main>
	<h2>📨 쪽지 내용</h2>

	<table border="1" cellpadding="10" cellspacing="0" width="100%">
		<tr>
			<th>보낸 사람</th>
			<td>${msg.senderId}</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${msg.messageTitle}</td>
		</tr>
		<tr>
			<th>내용</th>
			<td style="min-height:150px;">${msg.messageContent}</td>
		</tr>
		<tr>
			<th>보낸 날짜</th>
			<td>${msg.messageSendDate}</td>
		</tr>
	</table>

	<!-- 답장하기 버튼 -->
	<div style="margin-top: 20px;">
		<button type="button" onclick="openReplyPopup('${msg.senderId}')"> 답장하기</button>
	</div>

	<script>
		function openReplyPopup(senderId) {
			const width = 800;
			const height = 600;
			const left = (window.screen.width - width) / 2;
			const top = (window.screen.height - height) / 2;

			const url = `composeForm.jsp?receiverId=${encodeURIComponent(senderId)}`;

			const popup = window.open(
				url,
				'replyPopup',
				`width=${width},height=${height},left=${left},top=${top},scrollbars=yes,resizable=yes`
			);

			if (popup) {
				popup.focus();
			}
		}
	</script>
</main>
