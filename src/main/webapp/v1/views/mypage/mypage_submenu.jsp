<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- /main/main.jsp --%>
<main>
	<h2>서브메뉴</h2>
	<nav class="mypage-sidebar" style="width: 200px;">
		<ul style="list-style: none; padding: 0; margin: 0;">
			<li style="margin-bottom: 10px;"><a href="inbox.do"> 받은 쪽지함</a>
			</li>
			<li style="margin-bottom: 10px;"><a href="sent.do"> 보낸 쪽지함</a></li>
			<li><a href="javascript:void(0);" onclick="openComposePopup()">
					쪽지 쓰기</a></li>
			<li style="margin-bottom: 10px;"><a href="history.do"> 포인트
					내역</a></li>
			<li style="margin-bottom: 10px;"><a href="grade.do"> 내 등급</a></li>
			<li style="margin-bottom: 10px;"><a href="profile.do"> 내 정보
					보기</a></li>
			<li style="margin-bottom: 10px;"><a href="editProfile.do"> 내
					정보 수정</a></li>
			<li style="margin-bottom: 10px;"><a href="withdrawForm.do"
				style="color: red;">🚫 회원 탈퇴</a></li>
		</ul>
	</nav>
	<script>
	function openComposePopup() {
		const width = 600;
		const height = 500;

		// 가운데 정렬 계산
		const left = (window.screen.width - width) / 2;
		const top = (window.screen.height - height) / 2;

		window
				.open('composeForm.jsp', 'composeWindow',
						`width=${width},height=${height},top=${top},left=${left},scrollbars=yes`);
	}
</script>
</main>
