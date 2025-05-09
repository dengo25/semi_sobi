<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<main>
	<h2>👤 내 정보 보기</h2>

	<table border="1" cellpadding="10" cellspacing="0" width="100%" style="border-collapse: collapse; margin-top: 20px;">
		<tr>
			<th style="width: 150px;">아이디</th>
			<td>${member.memberId}</td>
		</tr>
		<tr>
			<th>이름</th>
			<td>${member.memberName}</td>
		</tr>
		<tr>
			<th>성별</th>
			<td>
				<c:choose>
					<c:when test="${member.memberGender eq 'M'}">남자</c:when>
					<c:when test="${member.memberGender eq 'F'}">여자</c:when>
					<c:otherwise>기타</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<th>이메일</th>
			<td>${member.memberEmail}</td>
		</tr>
		<tr>
			<th>생년월일</th>
			<td>${member.memberBirth}</td>
		</tr>
		<tr>
			<th>주소</th>
			<td>${member.memberAddr}</td>
		</tr>
		<tr>
			<th>우편번호</th>
			<td>${member.memberZip}</td>
		</tr>
		<tr>
			<th>가입일</th>
			<td>${member.memberReg}</td>
		</tr>
	</table>

	<!-- 수정하기 버튼 -->
	<div style="margin-top: 20px;">
		<form action="editProfile.do" method="get">
			<button type="submit">✏ 내 정보 수정하기</button>
		</form>
	</div>
</main>
