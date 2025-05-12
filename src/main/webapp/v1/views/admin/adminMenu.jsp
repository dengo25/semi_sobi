<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<meta charset="UTF-8">
	<main class="admin">
	<%@ include file="/v1/views/admin/adminSidebar.jsp" %>

		<!-- 요약 통계 -->
		<div class="stats">
			<div class="card">
				<h3>전체 회원 수</h3>
				<p><c:out value="${memberCount}" /></p>
			</div>
			<div class="card">
				<h3>오늘 가입자</h3>
				<p><c:out value="${todayJoinCount}" /></p>
			</div>
			<div class="card">
				<h3>블랙리스트</h3>
				<p><c:out value="${blackListCount}" /></p>
			</div>
			<div class="card">
				<h3>총 후기 수</h3>
				<p>${reviewCount}</p>
			</div>
		</div>

		<!-- 미처리 신고 -->
		<div class="alert-box">
			🚨 미처리 신고가 <strong><c:out value="${unresolvedReports}" /></strong>건 있습니다. 빠르게 확인해주세요!
		</div>

		<!-- 최근 활동 -->
		<div class="section">
			<h4>🕓 최근 활동 로그</h4>
			<table class="log-table">
				<thead>
					<tr>
						<th>ID</th>
						<th>메뉴</th>
						<th>활동</th>
						<th>접근일자</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="log" items="${memberLogList}">
						<tr>
							<td>${log.memberId}</td>
							<td>${log.accessedMenu}</td>
							<td>${log.memberActionType}</td>
							<td>${log.memberLogCreated}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

	</main>
</body>
</html>