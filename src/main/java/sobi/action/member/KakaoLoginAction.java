package sobi.action.member;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sobi.action.common.SobiAction;
import sobi.dao.member.MemberDAO;
import sobi.dao.member.MemberSocialDAO;
import sobi.db.ConnectionProvider;
import sobi.vo.member.MemberSocialVO;
import sobi.vo.member.MemberVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

public class KakaoLoginAction implements SobiAction {
  
  private static final String REST_API_KEY = ConnectionProvider.KAKAO_REST_API_KEY;
  private static final String REDIRECT_URI = ConnectionProvider.KAKAO_REDIRECT_URI;
  
  @Override
  public String process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String code = request.getParameter("code");
    
    if (code == null || code.isEmpty()) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "인가 코드(code)가 없습니다.");
      return null;
    }
    
    try {
      // 1. Access Token 요청
      String tokenRequest = "grant_type=authorization_code" //여기서 인가 코드를 받아서 담는다
          + "&client_id=" + URLEncoder.encode(REST_API_KEY, "UTF-8")
          + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8")
          + "&code=" + URLEncoder.encode(code, "UTF-8");
      
      URL tokenUrl = new URL("https://kauth.kakao.com/oauth/token"); //clientid를 받아서 담는다.
      HttpURLConnection conn = (HttpURLConnection) tokenUrl.openConnection();
      conn.setRequestMethod("POST");
      conn.setDoOutput(true);
      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
      
      try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())) {
        writer.write(tokenRequest);
        writer.flush();
      }
      
      BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      
      // 1. Kakao로부터 access token을 응답받은 JSON 문자열을 StringBuilder로 누적 저장한 결과
      // 예시: {"access_token":"xxxxxx", "token_type":"bearer", ...}
      StringBuilder tokenResult = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) tokenResult.append(line);  // JSON 형태의 문자열을 한 줄씩 읽어와 누적
      
      // 2. StringBuilder → 문자열 → JsonObject 형태로 파싱
      // 이제부터는 JSON 객체로부터 값을 추출할 수 있음
      JsonObject tokenJson = JsonParser.parseString(tokenResult.toString()).getAsJsonObject(); //여기서 엑세스 토큰을 담는다
      
      // 3. access_token 항목 추출
      // JSON 객체 안에서 "access_token" 키에 해당하는 값을 꺼내 문자열로 저장
      String accessToken = tokenJson.get("access_token").getAsString();
      
      // 사용자 정보 요청
      // 여기서 {"id:xx"} 이런 방식으로 닉네임등을 받아옴
      URL userInfoUrl = new URL("https://kapi.kakao.com/v2/user/me");
      HttpURLConnection userConn = (HttpURLConnection) userInfoUrl.openConnection();
      userConn.setRequestMethod("GET");
      userConn.setRequestProperty("Authorization", "Bearer " + accessToken);
      
      BufferedReader userReader = new BufferedReader(new InputStreamReader(userConn.getInputStream()));
      
      //userResult는 {id:고유번호} 닉네임등 들어간다.
      StringBuilder userResult = new StringBuilder();
      while ((line = userReader.readLine()) != null) userResult.append(line);
      
      //userREsult에서 값을 가져와서 각각 객체에 담는다.
      JsonObject userJson = JsonParser.parseString(userResult.toString()).getAsJsonObject();
      String kakaoId = userJson.get("id").getAsString();
      String email = "";
      String nickName = "";
      
      if (userJson.has("kakao_account")) {
        JsonObject account = userJson.getAsJsonObject("kakao_account");
        email = account.has("email") ? account.get("email").getAsString() : "";
        
        if (account.has("profile")) {
          JsonObject profile = account.getAsJsonObject("profile");
          nickName = profile.has("nickname") ? profile.get("nickname").getAsString() : "";
        }
      }
      
      // 3. DB 처리
      MemberSocialDAO socialDAO = new MemberSocialDAO();
      MemberDAO memberDAO = new MemberDAO();
      
      String memberId = socialDAO.handleSocialLogin("KAKAO", kakaoId, email);
      MemberVO loginMember;
      
      if (memberId == null) {
        // 신규 → 등록까지 완료
        loginMember = socialDAO.registerNewSocialMember("KAKAO", kakaoId, email, nickName, null);
      } else {
        // 기존 회원
        loginMember = memberDAO.findById(memberId);
        
        // 소셜 연동 누락되었으면 여기서 insert!
        if (socialDAO.findMemberIdBySocial("KAKAO", kakaoId) == null) {
          MemberSocialVO vo = new MemberSocialVO();
          vo.setMemberId(memberId);
          vo.setSocialId(kakaoId);
          vo.setLinkedAt(new Date());
          socialDAO.insertSocialVO(vo, "KAKAO");
          System.out.println("[KAKAO] 기존 이메일 회원, 소셜 연동 추가 완료");
        }
      }
      
      // 4. 세션 저장
      HttpSession session = request.getSession();
      session.setAttribute("member", loginMember);
      
      // 5. 메인 페이지로 이동
      response.sendRedirect(request.getContextPath() + "/v1/views/main/main.do");
      return null;
      
    } catch (Exception e) {
      e.printStackTrace();
      throw new ServletException("카카오 로그인 처리 중 오류", e);
    }
  }
}
