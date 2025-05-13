<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 
<style>
  main {
    padding: 20px;
    font-family: 'Segoe UI', sans-serif;
  }

  table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
    font-size: 14px;
  }

  th, td {
    border: 1px solid #ddd;
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

<main class="admin">
<div class="admin-header">
	<h3 class="main-header">회원 상세</h3>
	<div><%@ include file="/v1/views/admin/adminSidebar.jsp" %></div>
</div>
  <table>
    <tr>
      <th>ID</th>
      <th>유형</th>
      <th>일시</th>
      <th>현재 상태</th>
      <th>변경</th>
    </tr>
    <c:forEach var="b" items="${list}">
      <tr>
        <td>${b.memberId}</td>
        <td>${b.reportType}</td>
        <td>${b.updateAt}</td>
        <c:if test="${b.status eq 'blocked'}">
       		<td>접속 차단</td>
        </c:if>
		<c:if test="${b.status eq 'blocked'}">
       		<td><a href="updateBlacKList.do?memberId=${b.memberId}">해제</a></td>
        </c:if>
      </tr>
    </c:forEach>
  </table>
</main>
