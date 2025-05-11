<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="sobi.vo.review.ReviewVO"%>

<%
ReviewVO review = (ReviewVO) request.getAttribute("review");
if (review == null) {
	out.println("<h2>리뷰를 찾을 수 없습니다.</h2>");
	return;
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리뷰 상세보기</title>
<style>
body {
	font-family: 'Segoe UI', sans-serif;
	background-color: #f9f9f9;
	margin: 0;
	padding: 0;
}

.container {
	max-width: 800px;
	margin: 40px auto;
	padding: 20px;
	background: #fff;
	border-radius: 10px;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

h1 {
	font-size: 24px;
	margin-bottom: 10px;
}

.meta {
	font-size: 14px;
	color: #666;
	margin-bottom: 20px;
}

.image-preview {
	max-width: 100%;
	height: auto;
	border-radius: 8px;
	margin-bottom: 20px;
}

.content {
	font-size: 16px;
	line-height: 1.7;
}

.back-link {
	margin-top: 30px;
	display: inline-block;
	color: #333;
	text-decoration: underline;
}
</style>
</head>
<body>
	<div class="container">
		<%
		// 현재 로그인한 사용자 ID (예: 세션에서 가져옴)
		String loggedInUser = (String) session.getAttribute("loginId");
		boolean isOwner = loggedInUser != null && loggedInUser.equals(review.getMemberId());
		request.setAttribute("isOwner", isOwner);
		%>

		<c:if test="${isOwner}">
			<div style="margin-top: 20px;">
				<form method="get"
					action="${pageContext.request.contextPath}/updateReviewForm.do"
					style="display: inline;">
					<input type="hidden" name="reviewId" value="${review.reviewId}" />
					<button type="submit">수정</button>
				</form>

				<form method="post"
					action="${pageContext.request.contextPath}/deleteReview.do"
					style="display: inline;" onsubmit="return confirm('정말 삭제하시겠습니까?');">
					<input type="hidden" name="reviewId" value="${review.reviewId}" />
					<button type="submit">삭제</button>
				</form>
			</div>
		</c:if>

		<h1>${review.title}</h1>
		<div class="meta">
			작성자: ${review.memberId} | 평점: ${review.rating}점 | 작성일:
			<fmt:formatDate value="${review.createdAt}" pattern="yyyy-MM-dd" />
		</div>

		<c:if test="${not empty review.imageUrl}">
			<img src="${review.imageUrl}" alt="리뷰 이미지" class="image-preview">
		</c:if>

		<div class="content">
			<c:out value="${review.content}" escapeXml="false" />
		</div>

		<a href="${pageContext.request.contextPath}/review_list_page.jsp"
			class="back-link">← 목록으로 돌아가기</a>
	</div>
</body>
</html>
