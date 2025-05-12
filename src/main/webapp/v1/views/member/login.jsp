<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="sobi.db.ConnectionProvider" %>
<main>
<%-- 
  <style>
    .login-wrap {
      max-width: 520px;
      margin: 80px auto;
      padding: 50px 40px;
      border: 1px solid #ccc;
      border-radius: 16px;
      font-family: 'Helvetica', sans-serif;
      background: #fff;
      box-shadow: 0 0 20px rgba(0,0,0,0.05);
    }

    .login-wrap h1 {
      text-align: center;
      font-size: 48px;
      color: #b2956b;
      margin-bottom: 40px;
      font-weight: bold;
    }

    .login-wrap input[type="text"],
    .login-wrap input[type="password"] {
      width: 100%;
      padding: 18px;
      margin-bottom: 16px;
      border: 1px solid #ccc;
      border-radius: 12px;
      font-size: 18px;
      box-sizing: border-box;
    }

    .login-wrap .checkbox-area {
      display: flex;
      justify-content: space-between;
      font-size: 16px;
      color: #555;
      margin: 20px 0;
    }

    .login-wrap .login-btn {
      width: 100%;
      background-color: #b2956b;
      color: #fff;
      padding: 18px;
      font-size: 20px;
      border: none;
      border-radius: 12px;
      font-weight: bold;
    }

    .login-wrap .login-btn:hover {
      background-color: #a3845c;
      cursor: pointer;
    }

    .login-wrap .link-line {
      text-align: center;
      margin: 25px 0;
      font-size: 16px;
      color: #777;
    }

    .sns-btns a,
    .sns-btns button {
      width: 100%;
      height: 50px;
      margin: 8px 0;
      font-size: 17px;
      border: 1px solid #ccc;
      border-radius: 12px;
      background-color: #fff;
      display: flex;
      align-items: center;
      justify-content: center;
      text-decoration: none;
      box-sizing: border-box;
    }

    .sns-btns img {
      height: 24px;
      max-height: 80%;
      object-fit: contain;
    }

    .sns-naver img {
      height: 24px;
    }

    .sns-kakao {
      background-color: #FEE500;
      border: none;
      color: #000000;
      font-weight: bold;
    }

    .sns-facebook {
      color: #1877f2;
      font-weight: bold;
    }
  </style>
--%>
  <div class="login-wrap">
    <h1>SOBI</h1>

    <form action="login_process.do" method="post">
      <input type="text" name="memberId" placeholder="아이디" required />
      <input type="password" name="password" placeholder="비밀번호" required />

	<%--
      <div class="checkbox-area">
        <label class="input-check"><input type="checkbox" name="keepLogin" /><span class="checkMark"></span> 로그인 상태 유지</label>
        <label class="input-check"><input type="checkbox" name="saveId" /><span class="checkMark"></span> 아이디 저장</label>
      </div>
		--%>
      <button type="submit" class="login-btn">로그인</button>

      <div class="link-line">
        <a href="findMemberId.do">아이디찾기</a> | <a href="findMemberPassword.do">비밀번호 찾기</a>
      </div>

      <button type="button" class="login-btn" onclick="location.href='${pageContext.request.contextPath}/v1/views/member/createMember.do'">
        회원가입
      </button>
    </form>

    <div class="sns-btns">
      <!-- 네이버 로그인 -->
      <a href="${pageContext.request.contextPath}/naverLogin.do" class="sns-naver">
        <img src="${pageContext.request.contextPath}/v1/static/images/logo-naver.png" alt="네이버 로그인" />
      </a>

      <!-- 카카오 로그인 -->
      <button type="button" class="sns-kakao" onclick="loginWithKakao()">
        <img src="${pageContext.request.contextPath}/v1/static/images/logo-kakao.png"
             alt="카카오 로그인"
             style="width: 100%; height: 100%; object-fit: contain;" />
      </button>

      <!-- 페이스북  -->
      <%-- 
      <button class="sns-facebook" disabled>페이스북 아이디로 로그인</button> --%>
    </div>
  </div>

  <c:if test="${not empty error}">
    <script>
      alert('${error}');
    </script>
  </c:if>
</main>

<!-- 카카오 로그인 호출부분 -->
<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.7.5/kakao.min.js"
        integrity="sha384-dok87au0gKqJdxs7msEdBPNnKSRT+/mhTVzq+qOhcL464zXwvcrpjeWvyj1kCdq6"
        crossorigin="anonymous"></script>

<script>
  const KAKAO_REDIRECT_URI = '<%= ConnectionProvider.KAKAO_REDIRECT_URI %>';

  Kakao.init('e68b9631d90143101d14aecdc86c2140');

  function loginWithKakao() {
    Kakao.Auth.authorize({
      redirectUri: KAKAO_REDIRECT_URI
    });
  }
</script>

