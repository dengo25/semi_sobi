<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- /main/main.jsp --%>
<main class="main">
	<h2 class="blind">SOBI 메인</h2>

	<%-- 후기 검색으로 이
	<div class="search-area">
		<form class="input-group" action="review.do" method="get">
			<input type="search" name="keyword" class="form-control"
				placeholder="검색어를 입력하세요" value="${param.keyword}">
			<button type="submit" class="btn btn-deepgrey">검색</button>
		</form>
	</div>
--%>
	<div class="cont-wrap">
		<div class="cont-item banner">
			<h3>SOBI PICK! 최신 리뷰</h3>
			<div class="swiper">
				<div class="swiper-wrapper">
					<c:forEach var="review" items="${list}">
						<div class="swiper-slide">
							<div class="thum-item gallery-list reverse">
								<figure class="img">
									<a href="javascript:void(0);"> <img class=""
										src="${review.imageURL}">
									</a>
								</figure>
								<div class="desc">
									<p class="tit">
										<a href="javascript:void(0);">${review.reviewTitle}</a>
									</p>
									<p class="date">${review.createdAt}</p>
									<p class="cont">
										<a href="javascript:void(0);">${review.plainTextContent}</a>
									</p>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
				<div class="swiper-scrollbar"></div>
			</div>
		</div>
		<div class="cont-item thm">
			<h3>SOBI 공지사항</h3>
			<c:forEach var="notice" items="${noticeList}">
				<div class="thum-item gallery-list">
					<figure class="img">
						<a href="javascript:void(0);"> <img class=""
							src="${notice.noticeImageVO.fileUrl}">
						</a>
					</figure>
					<div class="desc">
						<p class="tit">
							<a href="javascript:void(0);">${notice.noticeTitle}</a>
						</p>
						<p class="cont">
							<a href="javascript:void(0);">${notice.plainTextContent}</a>
						</p>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<div class="cont-list">
		<h3 class="blind">SOBI 리뷰</h3>
		<c:forEach var="review" items="${list}">
			<div class="thum-item gallery-list reverse">
				<figure class="img">
					<a href="javascript:void(0);"> <img class=""
						src="${review.imageURL}">
					</a>
				</figure>
				<div class="desc">
					<p class="tit">
						<a href="javascript:void(0);">${review.reviewTitle}</a>
					</p>
					<p class="date">2025-05-08</p>
					<p class="cont">
						<a href="javascript:void(0);">${review.plainTextContent}</a>
					</p>
				</div>
			</div>
		</c:forEach>
	</div>


</main>
