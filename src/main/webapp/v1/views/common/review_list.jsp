<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- /board/review_list.jsp --%>
<main>
	<h2>후기 - 목록</h2>
	
	<%@ include file="left_menu.jsp" %>
	
	<table class="table-basic">
	    <caption>테이블 목록- ~으로 구성</caption>
	    <colgroup>
	        <col style="width:8%">
	        <col style="width:37%">
	        <col style="width:20%">
	        <col style="width:15%">
	        <col style="width:20%">
	    </colgroup>
        <thead>
            <tr>
                <th scope="col">no</th>
                <th scope="col">제목</th>
                <th scope="col">작성일</th>
                <th scope="col">작성자</th>
                <th scope="col">기타</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td class="num">1</td>
                <td><a href="javascript();">후기의 제목입니다.</a></td>
                <td>2025. 02. 13 15:25</td>
                <td>작성자의 아이디</td>
                <td>
                    <button class="btn" type="button">삭제</button>
                    <button class="btn" type="button">등록</button>
                </td>
            </tr>
            <tr>
                <td class="num">1</td>
                <td><a href="javascript();">후기의 제목입니다.</a></td>
                <td>2025. 02. 13 15:25</td>
                <td>작성자의 아이디</td>
                <td>
                    <button class="btn" type="button">삭제</button>
                    <button class="btn" type="button">등록</button>
                </td>
            </tr>
            <tr>
                <td class="num">1</td>
                <td><a href="javascript();">후기의 제목입니다.</a></td>
                <td>2025. 02. 13 15:25</td>
                <td>작성자의 아이디</td>
                <td>
                    <button class="btn" type="button">삭제</button>
                    <button class="btn" type="button">등록</button>
                </td>
            </tr>
        </tbody>
    </table>


	<div class="paging-area">
	    <button type="button" class="btn btn-paging">처음</button>
	    <button type="button" class="btn btn-paging">이전</button>
	    <ul class="pagination">
	        <li class="on"><a href="javascript:;" aria-current="page"><span>1</span></a></li>
	        <li><a href="javascript:;"><span>2</span></a></li>
	        <li><a href="javascript:;"><span>3</span></a></li>
	        <li><a href="javascript:;"><span>4</span></a></li>
	        <li><a href="javascript:;"><span>5</span></a></li>
	    </ul>
	    <button type="button" class="btn btn-paging">다음</i></button>
	    <button type="button" class="btn btn-paging">마지막</button>
	</div>




</main>