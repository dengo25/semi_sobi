package sobi.action.member;

import sobi.action.common.SobiAction;
import sobi.dao.admin.BlackListDAO;
import sobi.dao.member.MemberDAO;
import sobi.vo.member.MemberVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

public class LoginProcessAction implements SobiAction {
  @Override
  public String process(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
    request.setCharacterEncoding("utf-8");
    
    String id = request.getParameter("memberId");
    String password = request.getParameter("password");
    
    
    MemberDAO dao = new MemberDAO();
    MemberVO memberVO = dao.loginConfirm(id, password);
    if (memberVO != null) {
      System.out.println("[LoginProcessAction] 로그인 성공");
      System.out.println("[LoginProcessAction] 로그인 사용자 ROLE: " + memberVO.getRole());
      
      HttpSession session = request.getSession();
      session.setAttribute("member", memberVO);
      BlackListDAO blacklistDao = new BlackListDAO();
      int isBlack = blacklistDao.isBlackList(id); // status = 'blocked' 확인

      if (isBlack == 1) {
          request.setAttribute("error", "차단된 사용자입니다. 관리자에게 문의하세요.");
          return "/v1/views/member/login.jsp";
      }
      return "redirect:/v1/views/main/main.do";
      
    } else {
      System.out.println("[LoginProcessAction] 로그인 실패");
      request.setAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
      return "/v1/views/member/login.jsp";
    }
  }
}