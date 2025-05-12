<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>관리자 메인</title>
	<style>
		body {
			font-family: 'Noto Sans KR', sans-serif;
			margin: 0;
			padding: 0;
			background-color: #f5f5f5;
		}

		main {
			max-width: 1000px;
			margin: 50px auto;
			background-color: #fff;
			padding: 40px;
			box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
			border-radius: 12px;
		}

		h2 {
			text-align: center;
			margin-bottom: 30px;
		}

		.stats {
			display: grid;
			grid-template-columns: repeat(4, 1fr);
			gap: 20px;
			margin-bottom: 40px;
		}

		.card {
			background-color: #f0f8ff;
			padding: 20px;
			border-radius: 10px;
			text-align: center;
			box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
		}

		.card h3 {
			margin: 0;
			font-size: 16px;
			color: #333;
		}

		.card p {
			margin-top: 10px;
			font-size: 24px;
			font-weight: bold;
			color: #007acc;
		}

		.section {
			margin-bottom: 30px;
		}

		.section h4 {
			margin-bottom: 10px;
			border-bottom: 2px solid #007acc;
			padding-bottom: 5px;
		}

		.log-list {
			list-style: none;
			padding-left: 0;
			color: #555;
		}

		.log-list li {
			padding: 6px 0;
			border-bottom: 1px solid #e0e0e0;
		}

		.alert-box {
			background-color: #ffe0e0;
			border-left: 5px solid red;
			padding: 15px;
			color: #b30000;
			border-radius: 6px;
			margin-bottom: 30px;
		}

		.shortcuts {
			display: flex;
			justify-content: space-around;
			flex-wrap: wrap;
			gap: 10px;
		}

		.shortcuts a {
			background-color: #007acc;
			color: white;
			padding: 12px 20px;
			border-radius: 8px;
			text-decoration: none;
			font-weight: bold;
			transition: background-color 0.2s;
		}

		.shortcuts a:hover {
			background-color: #005c99;
		}
	</style>
</head>
<body>
	<main>
		<div class="section">
			<h4>관리자 메뉴</h4>
			<div class="shortcuts">
				<a href="memberList.do">회원관리</a>
				<a href="logList.do">로그기록</a>
				<a href="getBlackList.do">블랙리스트</a>
				<a href="noticeWrite.do">공지사항 작성</a>
			</div>
		</div>

		<!-- 요약 통계 -->
		<div class="stats">
			<div class="card">
				<h3>전체 회원 수</h3>
				<p><c:out value="${memberCount}" /></p>
			</div>
			<div class="card">
				<h3>오늘 가입자</h3>
				<p><c:out value="${todayJoin}" /></p>
			</div>
			<div class="card">
				<h3>블랙리스트</h3>
				<p><c:out value="${blackListCount}" /></p>
			</div>
			<div class="card">
				<h3>총 후기 수</h3>
				<p><c:out value="${reviewCount}" /></p>
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

		<!-- 바로가기 메뉴 -->

	</main>
</body>
</html>
