<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- /content.jsp --%>
<main>
	<h2>공지사항</h2>
	
	<div class="search-area">
		<form class="input-group" action="notice.do" method="post">
	        <input type="search" name="keyword" class="form-control">
	        <button type="submit" class="btn btn-deepgrey">검색</button>
	    </form>
	</div>	
	
	<p>총 ${count}건</p>
	<table class="table-basic">
	    <caption>공지사항 테이블 목록 - no, 제목, 작성일, 조회수로 구성</caption>
	    <colgroup>
	        <col style="width:8%">
	        <col style="width:52%">
	        <col style="width:30%">
	        <col style="width:10%">
	    </colgroup>
        <thead>
            <tr>
                <th scope="col">no</th>
                <th scope="col">제목</th>
                <th scope="col">작성일</th>
                <th scope="col">조회수</th>
            </tr>
        </thead>
        <tbody>
        	<c:forEach var="notice" items="${list}">
        		<tr>
	        		<td class="num">${notice.rowNo}</td>
	                <td><a href="javascript();"><span>${notice.noticeTitle}</span></a></td>
	                <td>${notice.noticeCreateDate}</td>
	                <td>${notice.count}</td>
                <tr>
        	</c:forEach>
     	    <c:if test="${empty list}">
	            <tr class="table-is-empty">
	            	<td colspan="4">검색된 결과가 없습니다.</td>
	            </tr>
            </c:if>
        </tbody>
    </table>


	<div class="paging-area">
		<%-- 
	    <button type="button" class="btn btn-paging">처음</button>
	    <button type="button" class="btn btn-paging">이전</button> --%>
	    <ul class="pagination">
	        <c:forEach var="num" begin="1" end="${totalPaging}">
		    	<li class="${ pageNum == num ? 'on' : '' }">
		    		<a href="notice.do?pageNum=${num}" aria-current="${ pageNum == num ? 'page' : '' }"><span>${num}</span></a>
		    	</li>
	        </c:forEach>
	    </ul>
	    <%--
	    <button type="button" class="btn btn-paging">다음</button>
	    <button type="button" class="btn btn-paging">마지막</button> --%>
	</div>
	
	
	
</main>
