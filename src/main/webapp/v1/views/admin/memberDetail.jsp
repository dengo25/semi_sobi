<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
  table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
    font-family: 'Segoe UI', sans-serif;
    font-size: 14px;
  }

  th, td {
    border: 1px solid #ccc;
    padding: 10px;
    text-align: center;
  }

  th {
    background-color: #f0f4f8;
    color: #333;
  }

  tr:nth-child(even) {
    background-color: #fafafa;
  }

  tr:hover {
    background-color: #eef6ff;
  }
</style>
<main>
	<h2>회원 정보 관리</h2>
	<table>
		<tr>
			<th>ID</th>
			<td>${member.memberId }</td>
		</tr>
		<tr>
			<th>이름</th>
			<td>${member.memberName }</td>
		</tr>
		<tr>
			<th>성별</th>
			<td>${member.memberGender }</td>
		</tr>
		<tr>
			<th>이메일</th>
			<td>${member.memberEmail }</td>
		</tr>
		<tr>
			<th>생년월일</th>			
			<td>${member.memberBirth }</td>
		</tr>
		<tr>
			<th>주소</th>
			<td>${member.memberAddr }</td>
		</tr>
		<tr>
			<th>가입일자</th>
			<td>${member.memberReg }</td>		
		</tr>		
		<tr>
			<th>블랙리스트</th>
			<td><a href="blackListDetail.do">보기</a></td>		
		</tr>		
	</table>
</main>