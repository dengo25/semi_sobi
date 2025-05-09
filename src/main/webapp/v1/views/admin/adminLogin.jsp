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
    </style>
    <div class="login-wrap">
        <h1>sobi</h1>

        <form action="adminLogin.do" method="post">
            <input type="text" name="adminId" placeholder="아이디" required />
            <input type="password" name="adminPassword" placeholder="비밀번호" required />

            <div class="checkbox-area">
                <label><input type="checkbox" name="keepLogin" /> 로그인 상태 유지</label>
                <label><input type="checkbox" name="saveId" /> 아이디 저장</label>
            </div>

            <button type="submit" class="login-btn">로그인</button>

            <div class="link-line">
                <a href="#">아이디찾기</a> | <a href="#">비밀번호 찾기</a>
            </div>
        </form>
    </div>
</main>
<!-- ✅ JS SDK + 로그인 호출 -->
<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.7.5/kakao.min.js"
        integrity="sha384-dok87au0gKqJdxs7msEdBPNnKSRT+/mhTVzq+qOhcL464zXwvcrpjeWvyj1kCdq6"
        crossorigin="anonymous"></script>