<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<main>
	<table>
		<tr>
			<th>번호</th>
			<th>ID</th>
			<th>유형</th>
			<th>일시</th>
			<th>현재 상태</th>
		</tr>
		<c:forEach var="b" items="${list }">
			<tr>
				<td>${b.blackListNo }</td>
				<td>${b.memberId }</td>
				<td>${b.reportType }</td>
				<td>${b.updateAt }</td>
				<td>${b.status }</td>
			</tr>
		</c:forEach>
	</table>
</main>