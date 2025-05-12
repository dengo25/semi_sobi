<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<style>
  main {
    padding: 20px;
    font-family: 'Segoe UI', sans-serif;
  }

  .dashboard {
    display: flex;
    gap: 20px;
    margin-top: 20px;
    margin-bottom: 30px;
  }

  .card {
    flex: 1;
    background-color: #f4f9ff;
    padding: 20px;
    border-radius: 12px;
    box-shadow: 0 0 8px rgba(0, 0, 0, 0.05);
    text-align: center;
  }

  .card h3 {
    margin: 0;
    font-size: 28px;
    color: #007acc;
  }

  .card p {
    margin-top: 8px;
    font-size: 14px;
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

<main>
	<h2>회원 정보 관리</h2>
	<div class="dashboard">
		<div class="card">
			<h3>${memberReviewList.size()}</h3>
			<p>작성한 후기</p>
		</div>
		<div class="card">
			<h3>${loglist.size()}</h3>
			<p>활동 로그 수</p>
		</div>
		<div class="card">
			<h3>
				<c:set var="reportCount" value="0" />
				<c:forEach var="r" items="${memberReviewList}">
					<c:set var="reportCount" value="${reportCount + r.reportCount}" />
				</c:forEach>
				${reportCount}
			</h3>
			<p>총 신고 수</p>
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
				<%-- 성별 값이 M 또는 F일 경우 텍스트 변환 --%>
				<c:choose>
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
			<td><fmt:formatDate value="${memberDetail.memberReg }" pattern="yyyy-MM-dd"/></td>
		</tr>		
		<tr>
			<th>블랙리스트</th>
			<td><a href="blackListDetail.do?memberId=${memberDetail.memberId }">보기</a></td>		
		</tr>		
	</table>
	<table>
		<tr>
			<th>제목</th>
			<th>작성일자</th>
			<th>댓글수</th>
			<th>신고수</th>
		</tr>
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
	<table>
		<tr>
			<th>번호</th>
			<th>ID</th>
			<th>메뉴</th>
			<th>내용</th>
			<th>접근시간</th>
		</tr>
		
		<c:forEach var="l" items="${loglist}">
			<tr>
				<td>${l.logNo }</td>
				<td>${l.memberId }</td>
				<td>${l.accessedMenu }</td>
				<td>${l.memberActionType }</td>
				<td>${l.memberLogCreated }</td>
			</tr>
		</c:forEach>
	</table>
</main>