<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
</style>

<main>
  <table>
    <tr>
      <th>ID</th>
      <th>유형</th>
      <th>일시</th>
      <th>현재 상태</th>
    </tr>
    <c:forEach var="b" items="${list}">
      <tr>
        <td>${b.memberId}</td>
        <td>${b.reportType}</td>
        <td>${b.updateAt}</td>
        <td>${b.status}</td>
      </tr>
    </c:forEach>
  </table>
</main>
