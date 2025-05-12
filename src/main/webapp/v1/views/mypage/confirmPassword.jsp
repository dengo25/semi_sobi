<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /main/main.jsp --%>
<main>
  <div class="join-wrap">
    <h2>비밀번호 확인</h2>
    <form action="checkPassword.do" method="post">
      <input type="password" name="member_password" placeholder="비밀번호를 입력하세요" required />
      <button type="submit">확인</button>
    </form>
    <c:if test="${not empty errorMsg}">
      <p style="color: red">${errorMsg}</p>
    </c:if>
  </div>
</main>