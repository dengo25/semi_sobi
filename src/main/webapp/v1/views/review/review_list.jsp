<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="sobi.dao.review.ReviewDAO"%>
<%@ page import="sobi.dao.review.CategoryDAO"%>
<%@ page import="sobi.vo.review.ReviewVO"%>
<%@ page import="sobi.vo.review.CategoryVO"%>
<%@ page import="java.util.List"%>

<%
ReviewDAO reviewDao = new ReviewDAO();
CategoryDAO categoryDao = new CategoryDAO();

String categoryParam = request.getParameter("categoryId");
int selectedCategoryId = (categoryParam != null && !categoryParam.isEmpty()) ? Integer.parseInt(categoryParam) : 0;

List<ReviewVO> list = (selectedCategoryId > 0)
		? reviewDao.getReviewByCategory(selectedCategoryId)
		: reviewDao.getAllReviews();

List<CategoryVO> categoryList = categoryDao.getAllCategories();

// 콘솔 로그 출력
System.out.println("선택된 카테고리 ID: " + selectedCategoryId);
System.out.println("불러온 리뷰 개수: " + list.size());
for (ReviewVO review : list) {
	System.out.println("▶ 리뷰 제목: " + review.getTitle() + ", 작성자: " + review.getMemberId());
}

request.setAttribute("reviewList", list);
request.setAttribute("categoryList", categoryList);
request.setAttribute("selectedCategoryId", selectedCategoryId);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내돈내산 리뷰 목록</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/common.css">
<style>
body {
	font-family: 'Segoe UI', sans-serif;
	background-color: #f9f9f9;
	margin: 0;
	padding: 0;
}

.container {
	max-width: 1000px;
	margin: 40px auto;
	padding: 0 20px;
}

.category-bar {
	display: flex;
	flex-wrap: wrap;
	gap: 10px;
	margin-bottom: 30px;
}

.category-bar a {
	padding: 8px 16px;
	background-color: #eee;
	color: #333;
	border-radius: 20px;
	text-decoration: none;
	transition: background-color 0.2s;
}

.category-bar a:hover {
	background-color: #ddd;
}

.category-bar .active {
	background-color: #333;
	color: #fff;
}

h2 {
	margin-bottom: 20px;
}

.review-card {
	background: #fff;
	display: flex;
	flex-direction: row-reverse;
	align-items: center;
	gap: 20px;
	padding: 16px;
	border-radius: 12px;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
	margin-bottom: 20px;
	transition: transform 0.2s;
}

.review-card:hover {
	transform: translateY(-3px);
}

.review-card img {
	width: 160px;
	height: 120px;
	object-fit: cover;
	border-radius: 8px;
}

.review-text {
	flex: 1;
}

.review-text h4 {
	margin: 0 0 6px;
	font-size: 18px;
	color: #222;
}

.review-text p {
	margin: 4px 0;
	font-size: 14px;
	color: #555;
}

.load-more {
	text-align: center;
	margin-top: 30px;
}

.load-more button {
	padding: 10px 24px;
	background-color: #444;
	color: white;
	border: none;
	border-radius: 5px;
	font-size: 15px;
	cursor: pointer;
}

.load-more button:hover {
	background-color: #666;
}

.hidden {
	display: none;
}

@media screen and (max-width: 768px) {
	.review-card {
		flex-direction: column;
		align-items: flex-start;
	}
	.review-card img {
		width: 100%;
		height: auto;
	}
	.review-text {
		width: 100%;
	}
}
</style>
</head>
<body>
	<div class="container">
		<h2>📋 내돈내산 리뷰 목록</h2>

		<!-- ✅ 카테고리 버튼 (DispatcherServlet 경유) -->
		<div class="category-bar">
			<a
				href="${pageContext.request.contextPath}/reviewList.do?categoryId=0"
				class="<c:if test='${selectedCategoryId == 0}'>active</c:if>">전체</a>
			<c:forEach var="category" items="${categoryList}">
				<a
					href="${pageContext.request.contextPath}/reviewList.do?categoryId=${category.reviewCategoryId}"
					class="<c:if test='${selectedCategoryId == category.reviewCategoryId}'>active</c:if>">
					${category.reviewCategoryName} </a>
			</c:forEach>
		</div>
		<!-- ✅ 후기 목록 -->
		<div id="reviewList">
			<c:forEach var="review" items="${reviewList}" varStatus="status">
				<div
					class="review-card <c:if test='${status.index >= 10}'>hidden</c:if>">
					<img src="${review.imageUrl}" alt="리뷰 이미지">
					<div class="review-text">
						<h4>
							<a
								href="${pageContext.request.contextPath}/reviewDetail.do?reviewId=${review.reviewId}">${review.title}</a>
						</h4>
						<p>작성자: ${review.memberId}</p>
						<p>평점: ${review.rating}점</p>
						<p>
							<fmt:formatDate value="${review.createdAt}" pattern="yyyy-MM-dd" />
						</p>
					</div>
				</div>
			</c:forEach>
		</div>

		<!-- ✅ 더보기 버튼 -->
		<div class="load-more">
			<button id="loadMoreBtn">더보기</button>
		</div>
	</div>

	<script>
        document.getElementById("loadMoreBtn").addEventListener("click", function () {
            const hidden = document.querySelectorAll(".review-card.hidden");
            let showCount = 0;

            hidden.forEach(card => {
                if (showCount < 10) {
                    card.classList.remove("hidden");
                    showCount++;
                }
            });

            if (document.querySelectorAll(".review-card.hidden").length === 0) {
                this.style.display = "none";
            }
        });
    </script>
</body>
</html>