<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 
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
</style> --%>
	<%@ include file="/v1/views/admin/adminSidebar.jsp" %>
<main class="admin">
	<form action="logList.do">
		<label>
			<select name="level">
	    		<option value="" ${empty level ? 'selected' : ''}>--선택 안 함--</option>
				<option value="admin" ${level == 'admin' ? 'selected' : ''}>관리자</option>
				<option value="member" ${level == 'member' ? 'selected' : ''}>사용자</option>
			</select>
		</label>
	    <label>
	    	<select name="menu">
	    		<option value="" ${empty menu ? 'selected' : ''}>--선택 안 함--</option>
	    		<option value="로그인" ${menu == '로그인' ? 'selected' : ''}>로그인</option>
	    		<option value="회원정보" ${menu == '회원정보' ? 'selected' : ''}>회원정보</option>
	    		<option value="후기" ${menu == '후기' ? 'selected' : ''}>후기</option>
	    		<option value="공지사항" ${menu == '공지사항' ? 'selected' : ''}>공지사항</option>
	    		<option value="FAQ" ${menu == 'FAQ' ? 'selected' : ''}>FAQ</option>
	    	</select>
	    </label>
	   	<label>
	    	<select name="type">
	    		<option value="" ${empty type ? 'selected' : ''}>--선택 안 함--</option>
	    		<option value="로그인" ${type == '로그인' ? 'selected' : ''}>로그인</option>
	    		<option value="로그아웃" ${type == '로그아웃' ? 'selected' : ''}>로그아웃</option>	    		
	    		<option value="회원가입" ${type == '회원가입' ? 'selected' : ''}>회원가입</option>	    		
	    		<option value="회원탈퇴" ${type == '회원탈퇴' ? 'selected' : ''}>회원탈퇴</option>	    		
	    		<option value="작성" ${type == '작성' ? 'selected' : ''}>작성</option>
	    		<option value="조회" ${type == '조회' ? 'selected' : ''}>조회</option>
	    		<option value="삭제" ${type == '삭제' ? 'selected' : ''}>삭제</option>
	    		<option value="수정" ${type == '수정' ? 'selected' : ''}>수정</option>
	    	</select>
	    </label>
	    <button type="submit" name="search">조회</button>
	</form>
	<table>
		<tr>
			<th>ID</th>
			<th>메뉴</th>
			<th>내용</th>
			<th>접근시간</th>
			<th></th>
			<th></th>
		</tr>
		
		<c:forEach var="l" items="${list}">
			<tr>
				<td>${l.memberId }</td>
				<td>${l.accessedMenu }</td>
				<td>${l.memberActionType }</td>
				<td>${l.memberLogCreated }</td>
				<td><button type="submit" name="actionType" value="수정">수정</button></td>
			    <td><button type="submit" name="actionType" value="삭제">삭제</button></td>
			</tr>
		</c:forEach>
	</table>
	<div style="text-align: center; margin-top: 15px;">
		<c:forEach var="i" begin="1" end="${totalPage}">
			<c:choose>
				<c:when test="${i == currentPage}">
					<strong>[${i}]</strong>
				</c:when>
				<c:otherwise>
					<a
						href="logList.do?page=${i}&level=${level}&menu=${menu}&type=${type}">[${i}]</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>

</main>