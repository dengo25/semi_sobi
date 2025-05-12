
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- <h2>서브메뉴</h2> --%>
<nav class="mypage-sidebar">
	<ul>
		<li><a href="inbox.do"><span>받은 쪽지함</span></a></li>
		<li><a href="sent.do"><span>보낸 쪽지함</span></a></li>
		<li><a href="javascript:void(0);" onclick="openComposePopup()"><span>쪽지 쓰기</span></a></li>
		<li><a href="history.do"><span>포인트 내역</span></a></li>
		<%-- 미개발 메뉴 주석처리  
		<li><a href="grade.do"><span>내 등급</span></a></li>--%>
		<li><a href="checkPassword.do"><span>내 정보 보기</span></a></li>
	</ul>
</nav>
<script>
   function openComposePopup() {
     const width = 600;
     const height = 500;

     // 가운데 정렬 계산
     const left = (window.screen.width - width) / 2;
     const top = (window.screen.height - height) / 2;

     window.open('composeForm.jsp', 'composeWindow',`width=${width},height=${height},top=${top},left=${left},scrollbars=yes`);
   }
 </script>
