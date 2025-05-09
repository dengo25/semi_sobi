<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- /board.jsp --%>
<main>
	<h2>mypage - 컨텐츠</h2>
	<div class="mypage-wrapper" style="display: flex; padding: 20px;">
		<!-- 좌측 메뉴 영역 -->
		<jsp:include page="mypage_submenu.jsp" />

		<!-- 우측 콘텐츠 영역 -->
		<section class="mypage-content"
			style="flex-grow: 1; margin-left: 40px;">
			<h2>마이페이지</h2>
			<p>
				<strong>${sessionScope.loginUser.memberName}</strong>님, 환영합니다 ^.^
			</p>
			<p>원하는 메뉴를 선택해 정보를 확인하세요.</p>
		</section>
	</div>
</main>