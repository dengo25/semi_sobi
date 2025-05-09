
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- /content.jsp --%>
<main>
    <h2> common - 컨텐츠 </h2>
    <p>여기는 컴포넌트 컨텐츠입니다.</p>

    <div class="tab-classify type-round">
        <ul>
            <li class="current"><a href="#rond6-1"><span>Menu 1</span></a></li>
            <li><a href="#rond6-2"><span>Menu 2</span></a></li>
            <li><a href="#rond6-3"><span>Menu 3</span></a></li>
            <li><a href="#rond6-4"><span>Menu 4</span></a></li>
            <li><a href="#rond6-5"><span>Menu 5</span></a></li>
            <li><a href="#rond6-6"><span>Menu 6</span></a></li>
        </ul>
    </div><!--// tab-classify -->
    <br><hr><br>

    <div class="thum-item gallery-list reverse">
        <figure class="img">
            <a href="javascript:void(0);">
                <img class="" src="../../static/images/@img-test.png" alt="test img">
            </a>
        </figure>
        <div class="desc">
            <div class="category-badge-area">
                <a href="javascript:void(0);" class="txtbadge">카테고리명</a>
            </div>
            <p class="tit">
                <a href="javascript:void(0);">타이틀을 블라블라</a>
            </p>
            <p class="date">2025-05-08</p>
            <p class="cont">
                <a href="javascript:void(0);">내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!내용을 블라블라, 3줄까지만 읽어!</a>
            </p>
            <p class="count">좋아요 <span>22</span>건 / 댓글 <span>10</span>건</p>
        </div>
    </div>
    <br><hr><br>

    <%--
    <c:forEach var="test" items="${list}">
            test : ${test}<br>
            list : ${list}<br>
    </c:forEach>
    폼양식도 넣 --%>
    list : ${list}<br>
</main>
