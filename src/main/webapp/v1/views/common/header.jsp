<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
	<div class="wrap">
		<nav>
			<h1>
				<a href="${pageContext.request.contextPath}/v1/views/main/main.do">메인바로가기</a>
			</h1>
			<ul class="menu-list">
				<!-- 메뉴체계 포문으로 넣기  -->
				<li>
					<a href="${pageContext.request.contextPath}/v1/views/board/review_list.do">후기(내돈내산)</a>
				</li>
				<li>
					<a href="javascript();">공지사항</a>
				</li>
				<li>
					<a href="javascript();">FAQ</a>
				</li>
				<li>
					<a href="javascript();">쇼핑몰</a>
				</li>
				<li>
					<a href="javascript();">관리자메뉴</a>
				</li>
			</ul>
			<ul class="util-box">
				<li>
					<a href="${pageContext.request.contextPath}/v1/views/member/login.do"><span>로그인</span></a>
				</li>
				<li>
					<a href="javascript();"><span>로그아웃</span></a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/v1/views/mypage/test.do"><span>마이페이지</span></a>
				</li>
			</ul>
		</nav>
	</div>
</header>
