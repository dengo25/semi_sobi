<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<main>
<div class="mypage-wrapper" style="display: flex; padding: 20px;">
    
    <!-- 좌측 서브 메뉴 -->
    <jsp:include page="mypage_submenu.jsp" />
     <section class="mypage-content" style="flex-grow: 1; margin-left: 40px;">
	<h2>💰 포인트 내역</h2>

	<table border="1" width="100%" cellpadding="8" style="border-collapse: collapse; margin-top: 20px;">
		<thead>
			<tr>
				<th>활동 종류</th>
				<th>설명</th>
				<th>변동 포인트</th>
				<th>총 보유 포인트</th>
				<th>날짜</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="ph" items="${pointList}">
				<tr>
					<td>${ph.activityType}</td>
					<td>${ph.description}</td>
					<td>
						<c:choose>
							<c:when test="${ph.pointChange > 0}">
								<span style="color:green;">+${ph.pointChange}</span>
							</c:when>
							<c:otherwise>
								<span style="color:red;">${ph.pointChange}</span>
							</c:otherwise>
						</c:choose>
					</td>
					<td>${ph.totalPoint}</td>
					<td><fmt:formatDate value="${ph.createdAt}" pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<c:if test="${empty pointList}">
		<p>포인트 내역이 없습니다.</p>
	</c:if>
	  </section>
    
  </div>
</main>
