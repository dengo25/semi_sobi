<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<main>
  <style>
    .join-wrap {
      max-width: 600px;
      margin: 50px auto;
      padding: 30px;
      border: 1px solid #ccc;
      border-radius: 12px;
      font-family: 'Helvetica', sans-serif;
    }
    .join-wrap h2 {
      text-align: center;
      margin-bottom: 20px;
      font-size: 28px;
      color: #333;
    }
    .join-wrap input {
      width: 100%;
      padding: 12px;
      margin-bottom: 12px;
      border: 1px solid #ccc;
      border-radius: 8px;
      font-size: 16px;
    }
    .join-wrap button {
      width: 100%;
      padding: 14px;
      background-color: #b2956b;
      color: #fff;
      font-size: 18px;
      border: none;
      border-radius: 10px;
    }
  </style>

  <div class="join-wrap">
    <h2>회원가입</h2>
    <form action="/createMemberProcess.v1" method="post" onsubmit="return prepareAddress();">
      <div style="display: flex; gap: 8px; margin-bottom: 12px;">
        <input type="text" name="member_id" id="member_id" placeholder="아이디" required style="flex: 1;">
        <button type="button" onclick="checkId()" style="width: 130px;">중복확인</button>
      </div>
      <span id="idCheckMsg" style="font-size: 14px;"></span>
      <input type="password" name="member_password" placeholder="비밀번호" required />
     <!-- <input type="text" name="member_name" placeholder="이름" required /> -->
      <div style="display: flex; gap: 10px; align-items: center; margin-bottom: 12px;">
        <input type="text" name="member_name" placeholder="이름" required style="flex: 1;" />

        <div style="display: flex; gap: 10px;">
          <label>
            <input type="radio" name="member_gender" value="M" required /> 남
          </label>
          <label>
            <input type="radio" name="member_gender" value="F" /> 여
          </label>
        </div>
      </div>
      <input type="text" name="member_email" placeholder="이메일" required />
      <input type="text" name="member_birth" placeholder="생년월일 (예: 990101)" required />

      <!-- 주소 영역 -->
      <div style="display: flex; gap: 8px; margin-bottom: 12px;">
        <input type="text" name="member_zip" id="sample4_postcode" placeholder="우편번호" readonly style="flex: 1;">
        <input type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기" style="width: 130px;">
      </div>

      <input type="text" id="sample4_roadAddress" placeholder="도로명주소" readonly>
      <input type="text" id="sample4_jibunAddress" style="display:none;" placeholder="지번주소" readonly>
      <span id="guide" style="color:#999; display:none"></span><br>

      <input type="text" id="sample4_detailAddress" placeholder="상세주소">
      <input type="text" id="sample4_extraAddress" style="display:none;" placeholder="참고항목" readonly>

      <!-- 서버로 전송할 최종 주소 값 -->
      <input type="hidden" name="member_addr" id="member_addr_final">

      <button type="submit">회원가입 완료</button>
    </form>
  </div>

  <!-- 다음 주소 API -->
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script>
    function sample4_execDaumPostcode() {
      new daum.Postcode({
        oncomplete: function(data) {
          var roadAddr = data.roadAddress;
          var extraRoadAddr = '';

          if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
            extraRoadAddr += data.bname;
          }
          if (data.buildingName !== '' && data.apartment === 'Y') {
            extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
          }
          if (extraRoadAddr !== '') {
            extraRoadAddr = ' (' + extraRoadAddr + ')';
          }

          document.getElementById('sample4_postcode').value = data.zonecode;
          document.getElementById("sample4_roadAddress").value = roadAddr;
          document.getElementById("sample4_jibunAddress").value = data.jibunAddress;
          document.getElementById("sample4_extraAddress").value = extraRoadAddr;
          document.getElementById("guide").style.display = 'none';

          document.getElementById("sample4_detailAddress").focus();
        }
      }).open();
    }

    function prepareAddress() {
      const road = document.getElementById("sample4_roadAddress").value;
      const detail = document.getElementById("sample4_detailAddress").value;
      const fullAddr = (road + ' ' + detail).trim();
      document.getElementById("member_addr_final").value = fullAddr;
      return true; // submit 진행
    }

    // 중복 id체크
    function checkId() {
      const id = document.getElementById("member_id").value.trim();
      if (!id) {
        alert("아이디를 입력하세요");
        return;
      }

      fetch("/checkId.json?member_id=" + encodeURIComponent(id))
              .then(res => res.json())
              .then(data => {
                const msg = document.getElementById("idCheckMsg");
                if (data.exists) {
                  msg.style.color = "red";
                  msg.textContent = "이미 존재하는 아이디입니다.";
                } else {
                  msg.style.color = "green";
                  msg.textContent = "사용 가능한 아이디입니다.";
                }
              })
              .catch(err => {
                console.error("중복확인 실패", err);
              });
    }
  </script>
</main>