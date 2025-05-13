<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<main>
    <h2>후기</h2>

    <!-- 🔍 검색 영역 -->
    <div class="search-area">
        <form class="input-group" action="review.do" method="get">
            <input type="search" name="keyword" class="form-control" placeholder="제목 또는 작성자를 입력하세요" value="${param.keyword}">
            <button type="submit" class="btn btn-deepgrey">검색</button>
        </form>
    </div>

    <p class="txt-count">총 ${count}건</p>

    <!-- 📋 리뷰 테이블 -->
    <table class="table-basic">
        <caption>리뷰 테이블 목록 - 번호, 제목, 작성자, 평점, 작성일, 승인 여부</caption> 
        <colgroup>
            <col style="width:8%">
            <col style="width:30%">
            <col style="width:16%">
            <col style="width:8%">
            <col style="width:18%">
            <col style="width:10%">
        </colgroup>
        <thead>
        <tr>
            <th scope="col">no</th>
            <th scope="col">제목</th>
            <th scope="col">작성자</th>
            <th scope="col">평점</th>
            <th scope="col">작성일</th>
            <th scope="col">승인</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="review" items="${list}">
            <tr>
                <td class="num">${review.rowNo}</td>
                <td>
                    <a href="reviewDetail.do?reviewId=${review.reviewId}">
                        <span>${review.reviewTitle}</span>
                    </a>
                </td>
                <td>${review.memberId}</td>
                <td>${review.rating}</td>
                <td>${review.createdAt}</td>
                <td> <!-- ✅ [5] 승인 여부 값 표시 -->
                    <c:choose>
                        <c:when test="${review.confirmed == 'Y'}">✅</c:when>
                        <c:otherwise>❌</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>

        <c:if test="${empty list}">
            <tr class="table-is-empty">
                <td colspan="6">검색된 리뷰가 없습니다.</td>
            </tr>
        </c:if>
        </tbody>
    </table>

    <!-- 📄 페이징 영역 -->
    <div class="paging-area">
        <ul class="pagination">
            <c:forEach var="num" begin="1" end="${totalPaging}">
                <li class="${ pageNum == num ? 'on' : '' }">
                    <a href="review.do?pageNum=${num}&keyword=${param.keyword}" aria-current="${ pageNum == num ? 'page' : '' }">
                        <span>${num}</span>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>

    <!-- ✏ 관리자/작성자 버튼 영역 -->
    <c:if test="${not empty member}">
        <div class="btn-area-right">
            <a href="reviewWrite.do" class="btn btn-primary">리뷰 작성</a>
        </div>
    </c:if>
</main>
