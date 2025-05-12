<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
  main {
    padding: 20px;
    font-family: 'Segoe UI', sans-serif;
  }

  h3 {
    margin-bottom: 20px;
    color: #333;
  }

  form {
    display: flex;
    gap: 10px;
    align-items: center;
    margin-bottom: 20px;
  }

  select {
    padding: 6px 10px;
    font-size: 14px;
  }

  button[type="submit"] {
    padding: 6px 14px;
    font-size: 14px;
    background-color: #3a7afe;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }

  button[type="submit"]:hover {
    background-color: #2d64d6;
  }

  table {
    width: 100%;
    border-collapse: collapse;
    font-size: 14px;
    margin-top: 10px;
  }

  th, td {
    border: 1px solid #ddd;
    padding: 10px;
    text-align: center;
  }

  th {
    background-color: #f5f7fa;
    color: #333;
  }

  tr:nth-child(even) {
    background-color: #fafafa;
  }

  tr:hover {
    background-color: #f0f6ff;
  }

  td button {
    background-color: #f08a5d;
    color: white;
    border: none;
    padding: 5px 10px;
    border-radius: 3px;
    cursor: pointer;
  }

  td button:hover {
    background-color: #d76d3d;
  }
</style>
    
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
			<th></th>
			<th></th>
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