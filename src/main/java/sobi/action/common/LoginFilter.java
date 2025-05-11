package sobi.action.common;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("*.do")
public class LoginFilter extends HttpFilter implements Filter {
  
  // 로그인 없이 허용할 URL 목록
  private static final String[] EXCLUDE_LIST = {
      "login.do",               // 로그인 페이지
      "login_process.do",       // 로그인 처리
      "logout.do",              // 로그아웃
      "join.do",                // 회원가입 페이지
      "createMemberProcess.do", // 회원가입 처리
      "createMember.do",
      "checkId.do",             // 아이디 중복 확인
      "kakao_login_callback.do",// 카카오 로그인 콜백
      "naver_login_callback.do", // 네이버 로그인 콜백
      "main.do", "faq.do", "notice.do", "reviewList.do","review_list.do", //상단 헤더 메뉴
      "findMemberId.do", //아이디 찾기
      "findMemberIdOK.do",
      "findMemberPassword.do",//비밀번호 찾기
      "findMemberPasswordOK.do",
      "memberChangePw.do", //비밀번호 변경 페이지
      "memberChangePwOK.do",
      "naverLogin.do"  //네이버 로그인
      
      
  };
  
  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    
    String uri = request.getRequestURI();
    String cmd = uri.substring(uri.lastIndexOf("/") + 1);
    
    // 인증이 필요 없는 요청이면 바로 통과
    for (String exclude : EXCLUDE_LIST) {
      if (exclude.equals(cmd)) {
        chain.doFilter(request, response);
        return;
      }
    }
    
    // 로그인 여부 확인
    HttpSession session = request.getSession(false);
    Object loginUser = (session != null) ? session.getAttribute("member") : null;
    
    if (loginUser == null) {
      System.out.println("[LoginFilter] 인증되지 않은 요청 차단됨 ");
      response.sendRedirect("login.do");
    } else {
      chain.doFilter(request, response);
    }
  }
}
