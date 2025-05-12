<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<main>
	<h2>후기</h2>
	<div class="content-area">
		<div class="cont-top-area">
			<h3>${review.title}</h3>
			<div class="info-wrap">
				<span class="date">작성일 : <fmt:formatDate
						value="${review.createdAt}" pattern="yyyy-MM-dd" /></span> <span
					class="writer">작성자 : ${review.memberId}</span> <span class="count">평점
					: ${review.rating}점</span>
			</div>
		</div>

		<div class="content">
			<c:out value="${review.content}" escapeXml="false" />
		</div>

		<!--
		<c:if test="${not empty review.imageUrl}">
			<img src="${review.imageUrl}" alt="리뷰 이미지" class="image-preview">
		</c:if>
		-->
	</div>


	<div class="btn-area">
		<a href="review.do" class="btn btn-deepgrey">목록</a>
		
		<c:if test="${not empty member && member.memberId == review.memberId}">
			<a href="reviewEdit.do?reviewId=${review.reviewId}"
				class="btn btn-primary">수정</a> 
			<a href="reviewDelete.do?reviewId=${review.reviewId}"
			    class="btn btn-grey" onclick="return confirm('정말로 삭제하시겠습니까?');">
				삭제 </a>
		</c:if>
	</div>
	<c:if test="${member.role == 'A'}">
		<div class="btn-area-right">		
			<form action="confirmReview.do" method="post" >
				<input type="hidden" name="reviewId" value="${review.reviewId}" />
				<button type="submit" class="btn btn-primary">인증</button>
			</form>
		</div>
	</c:if>
</main>


<script>
	function confirmDelete(reviewId) {
		if (confirm("정말로 삭제하시겠습니까?")) {
			location.href = 'reviewDelete.do?reviewId=' + reviewId;
		}
	}
</script>