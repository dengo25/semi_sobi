<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

        .login-wrap input[type="text"],
        .login-wrap input[type="password"] {
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
            background-color: #ac9876;
            color: #fff;
            padding: 16px;
            font-size: 18px;
            border: none;
            border-radius: 30px;
            font-weight: bold;
            margin-top: 12px;
        }

        .login-wrap .login-btn:hover {
            background-color: #998763;
            cursor: pointer;
        }
    </style>

    <div class="login-wrap">
        <h1>비밀번호 변경하기</h1>

        <form action="memberChangePwOK.do" method="post">
            <input type="text" name="authCode" placeholder="인증번호를 입력하세요" required />
            <input type="password" name="newPassword" placeholder="새 비밀번호를 입력하세요" required />
            <input type="password" name="confirmPassword" placeholder="새 비밀번호를 한 번 더 입력하세요" required />
            <button type="submit" class="login-btn">비밀번호 변경</button>
        </form>
    </div>
</main>
