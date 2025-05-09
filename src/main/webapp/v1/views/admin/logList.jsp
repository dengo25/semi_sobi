<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<main>
	<h3>로그 기록 조회</h3>
	<form method="get" action="logList.do?level=${level }&menu=?${menu }&type=${type }">
		<label>
			<select name="level">
				<option value="all">전체</option>
				<option value="admin">사용자</option>
				<option value="member">관리자</option>
			</select>
		</label>
	    <label>
	    	<select name="menu">
	    		<option disabled selected>--선택해주세요--</option>
	    		<option value="로그인">로그인</option>
	    		<option value="후기">후기</option>
	    		<option value="댓글">댓글</option>
	    		<option value="공지사항">공지사항</option>
	    		<option value="FAQ">FAQ</option>
	    	</select>
	    </label>
	   	<label>
	    	<select name="type">
	    		<option disabled selected>--선택해주세요--</option>
	    		<option value="로그인">로그인</option>
	    		<option value="작성">작성</option>
	    		<option value="조회">조회</option>
	    		<option value="삭제">삭제</option>
	    		<option value="수정">수정</option>
	    	</select>
	    </label>
	    <button type="submit" name="search">조회</button>
	</form>
	<table>
		<tr>
			<th>번호</th>
			<th>ID</th>
			<th>메뉴</th>
			<th>내용</th>
			<th>접근시간</th>
		</tr>
		
		<c:forEach var="l" items="${list}">
			<tr>
				<td>${l.logNo }</td>
				<td>${l.memberId }</td>
				<td>${l.accessedMenu }</td>
				<td>${l.memberActionType }</td>
				<td>${l.memberLogCreated }</td>
				<td><button type="submit" name="actionType" value="수정">수정</button></td>
			    <td><button type="submit" name="actionType" value="삭제">삭제</button></td>
			</tr>
		</c:forEach>
	</table>

</main>