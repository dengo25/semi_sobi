<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
--%>
  <div class="login-wrap">
    <h1 class="type1">아이디 찾기</h1>

    <form id="findIdForm">
      <input type="text" name="memberName" placeholder="이름을 입력해 주세요" required />
      <input type="text" name="memberEmail" placeholder="이메일을 입력해 주세요" required />
      <button type="submit" class="login-btn">아이디 찾기</button>
    </form>

    <div class="back-link">
      <a href="login.do">돌아가기</a>
    </div>
  </div>
</main>

<script>
  // form의 submit 이벤트를 가로채기
  document.getElementById('findIdForm').addEventListener('submit', function(e) {
    e.preventDefault(); // 기본 form 제출(페이지 이동) 막기

    // form 데이터를 FormData 객체로 가져오기
    const formData = new FormData(this);

    // FormData를 URLSearchParams로 변환 → 서버에서 x-www-form-urlencoded로 처리하기 위함
    const params = new URLSearchParams();
    formData.forEach((value, key) => params.append(key, value));

    // AJAX 요청 보내기
    fetch('findMemberIdOK.do', {
      method: 'POST', // POST 방식
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        // 서버가 request.getParameter(...)로 받을 수 있게 설정
      },
      body: params // 변환된 form 데이터 전송
    })
            .then(res => res.json()) // 서버로부터 JSON 응답 받기
            .then(data => {
              // 응답 데이터가 정상적으로 왔을 때 처리
              if (data.status === 'success') {
                // 서버가 memberId를 반환한 경우
                alert('회원님의 아이디는 ' + data.memberId + ' 입니다.');
              } else {
                // 서버에서 memberId를 찾지 못한 경우
                alert('일치하는 회원 정보를 찾을 수 없습니다.');
              }
              // 결과 출력 후 로그인 페이지로 이동
              location.href = 'login.do';
            })
            .catch(err => {
              // 네트워크 에러나 서버 오류 발생 시
              alert('오류가 발생했습니다.');
              console.error(err); // 콘솔에 에러 출력
            });
  });
</script>

