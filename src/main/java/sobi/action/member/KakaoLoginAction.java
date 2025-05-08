//package sobi.action.member;
//
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import sobi.action.common.SobiAction;
//import sobi.db.ConnectionProvider;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//
//public class KakaoLoginAction implements SobiAction {
//
//  private static final String REST_API_KEY = ConnectionProvider.KAKAO_REST_API_KEY;
//  private static final String REDIRECT_URI = ConnectionProvider.KAKAO_REDIRECT_URI;
//
//  @Override
//  public String pro(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//    String code = request.getParameter("code");
//
//    if (code == null || code.isEmpty()) {
//      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "인가 코드(code)가 없습니다.");
//      return null;
//    }
//
//    try {
//      // 1. Access Token 요청
//      String tokenRequest = "grant_type=authorization_code"
//          + "&client_id=" + URLEncoder.encode(REST_API_KEY, "UTF-8")
//          + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8")
//          + "&code=" + URLEncoder.encode(code, "UTF-8");
//
//      URL tokenUrl = new URL("https://kauth.kakao.com/oauth/token");
//      HttpURLConnection conn = (HttpURLConnection) tokenUrl.openConnection();
//      conn.setRequestMethod("POST");
//      conn.setDoOutput(true);
//      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//
//      try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())) {
//        writer.write(tokenRequest);
//        writer.flush();
//      }
//
//      BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//      StringBuilder tokenResult = new StringBuilder();
//      String line;
//      while ((line = reader.readLine()) != null) {
//        tokenResult.append(line);
//      }
//
//      JsonObject tokenJson = JsonParser.parseString(tokenResult.toString()).getAsJsonObject();
//      String accessToken = tokenJson.get("access_token").getAsString();
//
//      // 2. 사용자 정보 요청
//      URL userInfoUrl = new URL("https://kapi.kakao.com/v2/user/me");
//      HttpURLConnection userConn = (HttpURLConnection) userInfoUrl.openConnection();
//      userConn.setRequestMethod("GET");
//      userConn.setRequestProperty("Authorization", "Bearer " + accessToken);
//
//      BufferedReader userReader = new BufferedReader(new InputStreamReader(userConn.getInputStream()));
//      StringBuilder userResult = new StringBuilder();
//      while ((line = userReader.readLine()) != null) {
//        userResult.append(line);
//      }
//
//      JsonObject userJson = JsonParser.parseString(userResult.toString()).getAsJsonObject();
//      String kakaoId = userJson.get("id").getAsString();
//      String email = "";
//      if (userJson.has("kakao_account")) {
//        JsonObject account = userJson.getAsJsonObject("kakao_account");
//        email = account.has("email") ? account.get("email").getAsString() : "";
//      }
//
//      // 3. 세션에 저장
//      HttpSession session = request.getSession();
//      session.setAttribute("kakaoId", kakaoId);
//      session.setAttribute("email", email);
//
//      System.out.println("kakaoId = " + kakaoId);
//      System.out.println("email = " + email);
//
//      // 4. 로그인 성공 후 리다이렉트
//      response.sendRedirect(request.getContextPath() + "/v1/views/main/main.do");
//      return null;
//
//    } catch (Exception e) {
//      e.printStackTrace();
//      throw new ServletException("카카오 로그인 처리 중 오류", e);
//    }
//  }
//}
