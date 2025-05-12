package sobi.action.member;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sobi.action.common.SobiAction;
import sobi.dao.member.MemberDAO;
import sobi.dao.member.MemberSocialDAO;
import sobi.db.ConnectionProvider;
import sobi.vo.member.MemberSocialVO;
import sobi.vo.member.MemberVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

public class NaverLoginCallbackAction implements SobiAction {
  
  @Override
  public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String code = request.getParameter("code");
    String state = request.getParameter("state");
    String sessionState = (String) request.getSession().getAttribute("state");
    
    if (code == null || code.isBlank()) {
      request.setAttribute("error", "정상적인 네이버 인증이 필요합니다.");
      return "v1/views/member/login.jsp";
    }
    
    if (!state.equals(sessionState)) {
      request.setAttribute("error", "잘못된 접근입니다.");
      return "v1/views/member/login.jsp";
    }
    
    // 1. Access Token 요청
    String clientId = ConnectionProvider.NAVER_CLIENT_ID;
    String clientSecret = ConnectionProvider.NAVER_CLIENT_SECRET;
    String redirectUri = URLEncoder.encode(ConnectionProvider.NAVER_REDIRECT_URI, "UTF-8");
    
    //아래 주소로 사전에 등록된 정보를 담아서 보냄 ex) 네이버로 요청한 인가코드, 내 redirectURI등
    String tokenUrl = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code"
        + "&client_id=" + clientId
        + "&client_secret=" + clientSecret
        + "&redirect_uri=" + redirectUri
        + "&code=" + code
        + "&state=" + state;
    
    //아래코드에 응답이 옴 cliend_id, secret등
    URL url = new URL(tokenUrl);
    HttpURLConnection tokenCon = (HttpURLConnection) url.openConnection();
    tokenCon.setRequestMethod("GET");
    
    BufferedReader tokenReader = new BufferedReader(new InputStreamReader(tokenCon.getInputStream()));
    //tokenResult가 아래에 담김
    StringBuilder tokenResult = new StringBuilder();
    String line;
    while ((line = tokenReader.readLine()) != null) tokenResult.append(line);
    tokenReader.close();

    //아래에 엑세스, 리프레시 토큰, 유효기간이 담김
    JsonObject tokenJson = JsonParser.parseString(tokenResult.toString()).getAsJsonObject();
    if (!tokenJson.has("access_token")) {
      request.setAttribute("error", "토큰 발급 실패");
      return "v1/views/member/login.jsp";
    }
    
    //엑세스 토큰만 따로 꺼내서 저장
    String accessToken = tokenJson.get("access_token").getAsString();
    
    // 2. 사용자 정보 요청
    URL userUrl = new URL("https://openapi.naver.com/v1/nid/me");
    HttpURLConnection userCon = (HttpURLConnection) userUrl.openConnection();
    userCon.setRequestMethod("GET");
    
    //여기서 엑세스토큰을 형식에 맞춤
    userCon.setRequestProperty("Authorization", "Bearer " + accessToken);
    
    BufferedReader userReader = new BufferedReader(new InputStreamReader(userCon.getInputStream()));
    
    //아래 success 메세지와 함게 response로 고유 id가 넘어옴
    StringBuilder userResult = new StringBuilder();
    while ((line = userReader.readLine()) != null) userResult.append(line);
    userReader.close();
    
    JsonObject userJson = JsonParser.parseString(userResult.toString()).getAsJsonObject();
    JsonObject userInfo = userJson.getAsJsonObject("response");
    if (userInfo == null) {
      request.setAttribute("error", "사용자 정보 조회 실패");
      return "v1/views/member/login.jsp";
    }
    
    // 3. 사용자 정보 추출
    String naverId = userInfo.get("id").getAsString();
    String email = userInfo.has("email") ? userInfo.get("email").getAsString() : "";
    String name = userInfo.has("name") ? userInfo.get("name").getAsString() : "";
    String birthday = userInfo.has("birthday") ? userInfo.get("birthday").getAsString() : "";
    
    // 4. DB 처리
    MemberSocialDAO socialDAO = new MemberSocialDAO();
    MemberDAO memberDAO = new MemberDAO();
    String memberId = socialDAO.handleSocialLogin("NAVER", naverId, email);
    MemberVO memberVO;
    
    if (memberId == null) {
      // 신규 회원 등록
      memberVO = socialDAO.registerNewSocialMember("NAVER", naverId, email, name, birthday);
    } else {
      // 기존 회원
      memberVO = memberDAO.findById(memberId);
      
      // 소셜 연동이 안 되어있다면 추가
      if (socialDAO.findMemberIdBySocial("NAVER", naverId) == null) {
        MemberSocialVO socialVO = new MemberSocialVO();
        socialVO.setMemberId(memberId);
        socialVO.setSocialId(naverId);
        socialVO.setLinkedAt(new Date());
        socialDAO.insertSocialVO(socialVO, "NAVER");
        System.out.println("[NAVER] 기존 회원 소셜 연동 추가 완료");
      }
    }
    
    // 5. 세션 저장 및 리다이렉트
    HttpSession session = request.getSession();
    session.setAttribute("member", memberVO);
    
    return "redirect:v1/views/main/main.do";
  }
}
