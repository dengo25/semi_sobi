<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%-- 
<style>
main {
	padding: 20px;
	font-family: 'Segoe UI', sans-serif;
}

.dashboard {
	display: flex;
	flex-wrap: wrap;
	gap: 20px;
	margin: 30px 0;
}

.card {
	flex: 1 1 30%;
	background: linear-gradient(to right, #e0f7fa, #f1f8ff);
	padding: 20px;
	border-radius: 12px;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.06);
	text-align: center;
	transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.card:hover {
	transform: translateY(-5px);
	box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
}

.card h3 {
	margin: 0;
	font-size: 32px;
	color: #0288d1;
}

.card p {
	margin-top: 10px;
	font-size: 15px;
	font-weight: 500;
	color: #555;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 30px;
	font-size: 14px;
}

th, td {
	border: 1px solid #ccc;
	padding: 10px;
	text-align: center;
}

th {
	background-color: #f0f4f8;
	color: #333;
}

tr:nth-child(even) {
	background-color: #fafafa;
}

tr:hover {
	background-color: #eef6ff;
}
</style>
--%>
		
<main class="admin">
<div class="admin-header">
	<h3 class="main-header">회원 상세</h3>
	<div><%@ include file="/v1/views/admin/adminSidebar.jsp" %></div>
</div>
	<div class="dashboard">
		<div class="card">
			<p>작성한 후기</p>
			<p>${totalReviewCount }</p>
		</div>
		<div class="card">
			<p>활동 로그 수</p>
			<p>${totalMemberLogCount}</p>
		</div>
		<div class="card">
			<p>총 신고 수</p>
			<p>${totalReportCount}</p>
		</div>
	</div>

	<table>
		<tr>
			<th>ID</th>
			<td>${memberDetail.memberId }</td>
		</tr>
		<tr>
			<th>이름</th>
			<td>${memberDetail.memberName }</td>
		</tr>
		<tr>
			<th>성별</th>
			<td>
				<%-- 성별 값이 M 또는 F일 경우 텍스트 변환 --%> <c:choose>
					<c:when test="${memberDetail.memberGender eq 'M'}">남</c:when>
					<c:when test="${memberDetail.memberGender eq 'F'}">여</c:when>
					<c:otherwise>정보 없음</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<th>이메일</th>
			<td>${memberDetail.memberEmail }</td>
		</tr>
		<tr>
			<th>생년월일</th>
			<td>${memberDetail.memberBirth }</td>
		</tr>
		<tr>
			<th>주소</th>
			<td>${memberDetail.memberAddr }</td>
		</tr>
		<tr>
			<th>가입일자</th>
			<td><fmt:formatDate value="${memberDetail.memberReg }"
					pattern="yyyy-MM-dd" /></td>
		</tr>
		<tr>
			<th>블랙리스트</th>
			<td><a
				href="blackListDetail.do?memberId=${memberDetail.memberId }">보기</a></td>
		</tr>
	</table>
	<table>
		<tr>
			<th>제목</th>
			<th>작성일자</th>
			<th>댓글수</th>
			<th>신고수</th>
		</tr>
		<c:if test="${empty memberReviewList}">
			<tr class="table-is-empty">
				<td colspan="4">검색된 결과가 없습니다.</td>
			</tr>
		</c:if>
		<c:forEach var="r" items="${memberReviewList }">
			<tr>
				<td><a href="reviewDetail.do?reviewId=${r.reviewId}">
						${r.title} </a></td>
				<td>${r.createdAt }</td>
				<td>${r.commentCount }</td>
				<td>${r.reportCount }</td>
			</tr>
		</c:forEach>
	</table>
	<div class="paging" style="margin-top: 20px; text-align: center;">
		<c:forEach var="i" begin="1" end="${totalReviewPage}">
			<c:choose>
				<c:when test="${i == currentReviewPage}">
					<strong>[${i}]</strong>
				</c:when>
				<c:otherwise>
					<a href="memberDetail.do?memberId=${memberId}&reviewPage=${i}">[${i}]</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>
	<table>
		<tr>
			<th>ID</th>
			<th>메뉴</th>
			<th>내용</th>
			<th>접근시간</th>
		</tr>
		<c:if test="${empty memberLogList}">
			<tr class="table-is-empty">
				<td colspan="4">검색된 결과가 없습니다.</td>
			</tr>
		</c:if>
		<c:forEach var="l" items="${memberLogList}">
			<tr>
				<td>${l.memberId }</td>
				<td>${l.accessedMenu }</td>
				<td>${l.memberActionType }</td>
				<td>${l.memberLogCreated }</td>
			</tr>
		</c:forEach>

	</table>
	<div class="paging" style="margin-top: 15px; text-align: center;">
		<c:forEach var="i" begin="1" end="${totalMemberLogPage}">
			<c:choose>
				<c:when test="${i == currentMemberLogPage}">
					<strong>[${i}]</strong>
				</c:when>
				<c:otherwise>
					<a
						href="memberDetail.do?memberId=${memberId}&reviewPage=${currentReviewPage}&memberLogPage=${i}">[${i}]</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>


	<br>
	<div class="btn-area">
		<a href="memberList.do" class="btn btn-deepgrey">회원 목록</a>
	</div>
	
	
	
</main>