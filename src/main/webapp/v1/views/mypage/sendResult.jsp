<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<main>
	<h2>📨 전송 결과</h2>

	<c:choose>
		<c:when test="${sendSuccess}">
			<p>쪽지가 성공적으로 전송되었습니다.</p>
		</c:when>
		<c:otherwise>
			<p style="color:red;">쪽지 전송에 실패했습니다.</p>
		</c:otherwise>
	</c:choose>

	<script>
		// 일정 시간 후 자동 닫기 or 수동 확인 버튼도 가능
		setTimeout(() => {
			window.close();
		}, 2000);
	</script>
</main>