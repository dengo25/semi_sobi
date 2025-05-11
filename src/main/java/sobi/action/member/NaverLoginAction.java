package sobi.action.member;

import sobi.action.common.SobiAction;
import sobi.db.ConnectionProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;

public class NaverLoginAction implements SobiAction {
  @Override
  public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String clientId = ConnectionProvider.NAVER_CLIENT_ID;
    String redirectURI = URLEncoder.encode(ConnectionProvider.NAVER_REDIRECT_URI, "UTF-8");
    SecureRandom random = new SecureRandom();
    String state = new BigInteger(130, random).toString();
    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code"
        + "&client_id=" + clientId
        + "&redirect_uri=" + redirectURI
        + "&state=" + state
        + "&auth_type=reauthenticate";
    
    request.getSession().setAttribute("state", state);
    response.sendRedirect(apiURL);  // 바로 네이버로 이동
    return null;
  }
}
