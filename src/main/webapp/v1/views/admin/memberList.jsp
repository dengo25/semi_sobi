<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
	<table>
		<tr>
			<th>ID</th>
			<th>이름</th>
			<th>이메일</th>
			<th>가입일</th>
			<th>후기수</th>
			<th>댓글수</th>
			<th>상세보기</th>
		</tr>
		<c:forEach var="m" items="${list }">
				<tr>
					<td>${m.memberId }</td>
					<td>${m.memberName }</td>
					<td>${m.memberEmail }</td>
					<td><fmt:formatDate value="${m.memberReg }" pattern="yyyy-MM-dd" /></td>
					<td><a href="memberReviewList.do?memberId${m.memberId }">${m.reviewCount }</a></td>
					<td><a href="memberCommentList.do?memberId${m.memberId }">${m.commentCount }</a></td>
					<td><a href="memberDetail.do?memberId=${m.memberId }">클릭</a></td>
				</tr>
		</c:forEach>
	</table>
</main>
