<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%-- 
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
</style>--%>
	<%@ include file="/v1/views/admin/adminSidebar.jsp" %>
<main class="admin">
	<table>
		<tr>
			<th>ID</th>
			<th>이름</th>
			<th>이메일</th>
			<th>가입일</th>
			<th>상세보기</th>
		</tr>
		<c:forEach var="t" items="${todayJoinMemberList }">
				<tr>
					<td>${t.memberId }</td>
					<td>${t.memberName }</td>
					<td>${t.memberEmail }</td>
					<td><fmt:formatDate value="${t.memberReg }" pattern="yyyy-MM-dd" /></td>
					<td><a href="memberDetail.do?memberId=${t.memberId }">클릭</a></td>
				</tr>
		</c:forEach>
	</table>
	<div style="text-align: center; margin-top: 20px;">
		<c:forEach var="i" begin="1" end="${totalPage}">
			<c:choose>
				<c:when test="${i == currentPage}">
					<strong>[${i}]</strong>
				</c:when>
				<c:otherwise>
					<a href="todayJoinMemberList.do?page=${i}">${i}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>
</main>
