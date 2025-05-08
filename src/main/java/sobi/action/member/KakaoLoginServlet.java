package sobi.action.member;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sobi.db.ConnectionProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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

@WebServlet("/v1/view/member/kakao_login_callback.do")
public class KakaoLoginServlet extends HttpServlet {

  private static final String REST_API_KEY = ConnectionProvider.KAKAO_REST_API_KEY;
  private static final String REDIRECT_URI = ConnectionProvider.KAKAO_REDIRECT_URI;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String code = request.getParameter("code");

    if (code == null || code.isEmpty()) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "인가 코드(code)가 없습니다.");
      return;
    }

    try {
      // 1. Access Token 요청
      String tokenRequest = "grant_type=authorization_code"  //표준 OAuth 방식으로
          + "&client_id=" + URLEncoder.encode(REST_API_KEY, "UTF-8")
          + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8")
          + "&code=" + URLEncoder.encode(code, "UTF-8");  //나머지는 카카오에 등록함 앱 정보와 방금 받은 code를 담음

      URL tokenUrl = new URL("https://kauth.kakao.com/oauth/token");  //카카오에 액세스 토큰을 받기위해서 주소지정하고
      HttpURLConnection conn = (HttpURLConnection) tokenUrl.openConnection();
      conn.setRequestMethod("POST"); //post방식으로 아래 타입지정
      conn.setDoOutput(true);
      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

      //위에 만들어진 데이터를 전송함.
      try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())) {
        writer.write(tokenRequest);
        writer.flush();
      }

      // 응답을 여기서 받아서 tokenResult에 저장.
      BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      StringBuilder tokenResult = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        tokenResult.append(line);
      }

      // 저장된 토큰을 gson라이브러르를 사용해서 파씽함.  -> 여기서 access_token을 꺼냄.
      JsonObject tokenJson = JsonParser.parseString(tokenResult.toString()).getAsJsonObject();
      String accessToken = tokenJson.get("access_token").getAsString();

      // 2. 사용자 정보 요청
      URL userInfoUrl = new URL("https://kapi.kakao.com/v2/user/me");
      HttpURLConnection userConn = (HttpURLConnection) userInfoUrl.openConnection();
      userConn.setRequestMethod("GET");
      userConn.setRequestProperty("Authorization", "Bearer " + accessToken);  //액세세스 토큰을 헤더에 넣고 get요청


      //응답을 json으로 받아 userResult에 저장
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



      // 3. 세션에 저장
      HttpSession session = request.getSession();

      System.out.println("kakaoId = " + kakaoId);
      System.out.println("email = " + email);

      session.setAttribute("kakaoId", kakaoId);
      session.setAttribute("email", email);

      // 4. 로그인 성공 후 리다이렉트
      response.sendRedirect(request.getContextPath() + "/v1/views/main/main.do");

    } catch (Exception e) {
      e.printStackTrace();
      throw new ServletException("카카오 로그인 처리 중 오류", e);
    }
  }
}