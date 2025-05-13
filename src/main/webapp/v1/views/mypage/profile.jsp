<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="sobi.vo.member.MemberVO" %>
<%
    MemberVO member = (MemberVO) session.getAttribute("memberDetail");
    if (member == null) {
        response.sendRedirect("login.do");
        return;
    }
%>

<main>
  <div class="join-wrap">
    <h2>내 정보 보기</h2>
    <form action="updateProfile.do" method="post" onsubmit="return prepareAddress();">

      <input type="text" name="member_id" class="form-control" value="<%= member.getMemberId() %>" readonly />
      <input type="password" name="member_password" class="form-control" placeholder="새 비밀번호 (선택)" />

      <input type="text" name="member_name" class="form-control" value="<%= member.getMemberName() %>" readonly />


	<div class="input-group">
	    <label for="radioM" class="input-radio">
	        <input type="radio" id="radioM" class="form-control" value="M" disabled <%= "M".equals(member.getMemberGender()) ? "checked" : "" %>>
	        <span class="radioMark"></span>
	        <em>남</em>
	    </label>
	
	    <label for="radioF" class="input-radio">
	        <input type="radio" id="radioF" class="form-control" value="F" disabled <%= "F".equals(member.getMemberGender()) ? "checked" : "" %>>
	        <span class="radioMark"></span>
	        <em>여</em>
	    </label>
	</div>

      <input type="text" name="member_email" value="<%= member.getMemberEmail() %>" readonly />
      <input type="text" name="member_birth" value="<%= member.getMemberBirth() %>" readonly />

      <!-- 주소 수정 -->
      <div style="display: flex; gap: 8px; margin:10px 0;">
        <input type="text" name="member_zip" class="form-control" id="sample4_postcode" value="<%= member.getMemberZip() %>" readonly />
        <input type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기" />
      </div>

      <input type="text" class="form-control" id="sample4_roadAddress" value="<%= member.getMemberAddr() %>" readonly />
      <input type="text" class="form-control" id="sample4_detailAddress" placeholder="상세주소" />
      <input type="hidden" name="member_addr" id="member_addr_final" />

      <button type="submit" style="margin-top: 12px;">수정</button>
    </form>

    <form action="withdraw.do" method="post" onsubmit="return confirm('정말 탈퇴하시겠습니까?')">
      <button type="submit" style="margin-top: 10px; background-color: darkred; color: white;">회원 탈퇴</button>
    </form>
  </div>

  <!-- 다음 주소 API -->
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script>
    function sample4_execDaumPostcode() {
      new daum.Postcode({
        oncomplete: function(data) {
          document.getElementById('sample4_postcode').value = data.zonecode;
          document.getElementById("sample4_roadAddress").value = data.roadAddress;
          document.getElementById("sample4_detailAddress").focus();
        }
      }).open();
    }

    function prepareAddress() {
      const road = document.getElementById("sample4_roadAddress").value;
      const detail = document.getElementById("sample4_detailAddress").value;
      const fullAddr = (road + ' ' + detail).trim();
      document.getElementById("member_addr_final").value = fullAddr;
      return true;
    }
  </script>
</main>
