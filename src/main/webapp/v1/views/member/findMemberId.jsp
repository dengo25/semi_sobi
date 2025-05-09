<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="sobi.db.ConnectionProvider" %>
<main>
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
      font-size: 36px;
      color: #b2956b;
      margin-bottom: 40px;
      font-weight: bold;
    }

    .login-wrap input[type="text"] {
      width: 100%;
      padding: 16px;
      margin-bottom: 16px;
      border: 1px solid #ccc;
      border-radius: 12px;
      font-size: 16px;
      box-sizing: border-box;
    }

    .login-wrap .login-btn {
      width: 100%;
      background-color: #b2956b;
      color: #fff;
      padding: 16px;
      font-size: 18px;
      border: none;
      border-radius: 12px;
      font-weight: bold;
      margin-top: 12px;
    }

    .login-wrap .login-btn:hover {
      background-color: #a3845c;
      cursor: pointer;
    }

    .login-wrap .back-link {
      text-align: center;
      margin-top: 24px;
    }

    .login-wrap .back-link a {
      color: #777;
      text-decoration: underline;
    }
  </style>

  <div class="login-wrap">
    <h1>아이디 찾기</h1>

    <form action="findMemberIdProcess.do" method="post">
      <input type="text" name="memberName" placeholder="이름 입력" required />
      <input type="text" name="memberEmail" placeholder="이메일 입력" required />

      <button type="submit" class="login-btn">아이디 찾기</button>
    </form>

    <div class="back-link">
      <a href="login.do">← 로그인 페이지로 돌아가기</a>
    </div>
  </div>
</main>
