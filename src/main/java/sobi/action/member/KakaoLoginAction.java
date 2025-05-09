package sobi.action.member;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sobi.action.common.SobiAction;
import sobi.dao.member.MemberDAO;
import sobi.dao.member.MemberSocialDAO;
import sobi.db.ConnectionProvider;
import sobi.vo.member.MemberVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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
      String tokenRequest = "grant_type=authorization_code"
          + "&client_id=" + URLEncoder.encode(REST_API_KEY, "UTF-8")
          + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8")
          + "&code=" + URLEncoder.encode(code, "UTF-8");
      
      URL tokenUrl = new URL("https://kauth.kakao.com/oauth/token");
      HttpURLConnection conn = (HttpURLConnection) tokenUrl.openConnection();
      conn.setRequestMethod("POST");
      conn.setDoOutput(true);
      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
      
      try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())) {
        writer.write(tokenRequest);
        writer.flush();
      }
      
      BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      StringBuilder tokenResult = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        tokenResult.append(line);
      }
      
      JsonObject tokenJson = JsonParser.parseString(tokenResult.toString()).getAsJsonObject();
      String accessToken = tokenJson.get("access_token").getAsString();
      
      // 2. 사용자 정보 요청
      URL userInfoUrl = new URL("https://kapi.kakao.com/v2/user/me");
      HttpURLConnection userConn = (HttpURLConnection) userInfoUrl.openConnection();
      userConn.setRequestMethod("GET");
      userConn.setRequestProperty("Authorization", "Bearer " + accessToken);
      
      BufferedReader userReader = new BufferedReader(new InputStreamReader(userConn.getInputStream()));
      StringBuilder userResult = new StringBuilder();
      while ((line = userReader.readLine()) != null) {
        userResult.append(line);
      }
      
      JsonObject userJson = JsonParser.parseString(userResult.toString()).getAsJsonObject();
      String kakaoId = userJson.get("id").getAsString();
      String email = "";
      if (userJson.has("kakao_account")) {
        JsonObject account = userJson.getAsJsonObject("kakao_account");
        email = account.has("email") ? account.get("email").getAsString() : "";
      }
      
      //닉네임 추출
      String nickName = "";
      
      if (userJson.has("properties")) {
        JsonObject props = userJson.getAsJsonObject("properties");
        if (props.has("nickname")) {
          nickName = props.get("nickname").getAsString();
        }
      }
      
      if (nickName.isEmpty() && userJson.has("kakao_account")) {
        JsonObject account = userJson.getAsJsonObject("kakao_account");
        if (account.has("profile")) {
          JsonObject profile = account.getAsJsonObject("profile");
          if (profile.has("nickname")) {
            nickName = profile.get("nickname").getAsString();
          }
        }
      }
      
// 3. DB 확인 및 자동가입 처리
      MemberSocialDAO socialDAO = new MemberSocialDAO();
      MemberDAO memberDAO = new MemberDAO();
      
      String memberId = socialDAO.handleKakaoLogin("kakao", kakaoId, email);
      
      if (memberId == null) {
        // 완전 신규 → 통합 메서드 호출
        memberId = socialDAO.registerNewSocialMember("kakao", kakaoId, email,nickName);
      } else {
        // 기존 회원 → 소셜 연동만 확인해서 추가
        socialDAO.insertSocial(memberId, "kakao", kakaoId);
      }

// 4. 로그인 처리 (세션에 MemberVO 저장)
      MemberVO loginMember = memberDAO.findByName(memberId);
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