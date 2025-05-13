<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<meta charset="UTF-8">
	<main class="admin">
<div class="admin-header">
	<h3 class="main-header">메인</h3>
	<div><%@ include file="/v1/views/admin/adminSidebar.jsp" %></div>
</div>
		<!-- 요약 통계 -->
		<div class="stats">
			<div class="card" onclick="location.href='memberList.do'">
				<h3>전체 회원 수</h3>
				<p>${memberCount}</p>
			</div>
			<div class="card" onclick="location.href='todayJoinMember.do'">
				<h3>오늘 가입자</h3>
				<p>${todayJoinCount}</p>
			</div>
			<div class="card" onclick="location.href='getBlackList.do'">
				<h3>블랙리스트</h3>
				<p>${blackListCount}</p>
			</div>
			<div class="card" onclick="location.href='review.do'">
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