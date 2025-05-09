<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<header>
	<div class="wrap">
		<nav>
			<h1>
				<a href="${ctx}${main.link}">${main.name}</a>
			</h1>
			<ul class="menu-list">
				<c:forEach var="menu" items="${applicationScope.menuList}">
					<li>
						<a href="${ctx}${menu.link}">${menu.name}</a>
					</li>
				</c:forEach>

				<c:if test="${member.role == 'A'}">
					<li>
						<a href="${ctx}${admin.link}">${admin.name}</a>
					</li>
				</c:if>
			</ul>
			<ul class="util-box">
				<c:choose>
					<c:when test="${empty member}"> <%-- empty -> EL // memeber -> session  --%>
						<li>
							<a href="${ctx}${login.link}"><span>${login.name}</span></a>
						</li>
					</c:when>
					<c:otherwise>
						<li>
							<a href="${ctx}${logout.link}"><span>${logout.name}</span></a>
						</li>
						<li>
							<a href="${ctx}${mypage.link}"><span>${mypage.name}</span></a>
						</li>
					</c:otherwise>
				</c:choose>
			</ul>
		</nav>
	</div>
</header>