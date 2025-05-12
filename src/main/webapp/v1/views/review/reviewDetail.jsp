<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


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

		<h1>${review.title}</h1>
		<div class="meta">
			작성자: ${review.memberId} | 평점: ${review.rating}점 | 작성일:
			<fmt:formatDate value="${review.createdAt}" pattern="yyyy-MM-dd" />
		</div>

		<!--
		<c:if test="${not empty review.imageUrl}">
			<img src="${review.imageUrl}" alt="리뷰 이미지" class="image-preview">
		</c:if>
		-->

		<div class="content">
			<c:out value="${review.content}" escapeXml="false" />
		</div>




	</div>

	<c:if test="${not empty member && member.memberId == review.memberId}">
		<div style="text-align: right; margin: 20px 40px 0 0;">
			<a href="reviewEdit.do?reviewId=${review.reviewId}" class="btn btn-primary">수정</a>
		</div>
	</c:if>

	<c:if test="${member.role == 'A'}">
		<div style="text-align: right; margin: 20px 40px 0 0;">
			<form action="confirmReview.do" method="post" style="display: inline;">
				<input type="hidden" name="reviewId" value="${review.reviewId}" />
				<button type="submit" class="btn btn-primary">인증</button>
			</form>
		</div>
	</c:if>
</body>
</html>