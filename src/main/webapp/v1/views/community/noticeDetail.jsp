<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<main>
	<h2>공지사항 - 상세보기</h2>
	
	<div class="content-area">
		<p>${noticeDetail.noticeCreateDate} <b>관리자</b></p>
		<h3>${noticeDetail.noticeTitle}</h3>
		<div class="content">
			${noticeDetail.noticeContent}
			<%-- 이미지 호출 미사용 
			<c:if test="${not empty noticeImg}">
				<c:forEach var="img" items="${noticeImg}">
					<img src="${img.fileUrl}" alt="이미지" />
				</c:forEach>
			</c:if>
			--%>
		</div>
	</div>

	<div class="btn-area">
		<c:if test="${member.role == 'A'}">
			<a href="noticeEdit.do?noticeNo=${noticeDetail.noticeNo}" class="btn btn-primary">수정</a>
		</c:if>
		<a href="notice.do" class="btn btn-deepgrey">목록</a>
    </div>

</main>
