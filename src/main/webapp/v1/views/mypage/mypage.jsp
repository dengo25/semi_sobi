<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- /mypage.jsp --%>

<main>
	<div class="mypage-wrapper">
		<!-- 좌측 메뉴 영역  -->
		<jsp:include page="mypage_submenu.jsp" />

		<!-- 우측 콘텐츠 영역 -->
		<section class="mypage-content">
			<h2>마이페이지</h2>
			<p>
				<strong>${sessionScope.member.memberName}</strong>님, 환영합니다 ^.^
			</p>
			<p>원하는 메뉴를 선택해 정보를 확인하세요.</p>
			
		</section>
	</div>
</main>