package sobi.action.member;

import sobi.action.common.SobiAction;
import sobi.dao.member.MemberDAO;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

public class FindMemberPasswordChangeOK implements SobiAction {
  
  @Override
  public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setContentType("text/html;charset=utf-8");
    
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("authenticationCode") == null || session.getAttribute("memberId") == null) {
      PrintWriter out = response.getWriter();
      out.println("<script>");
      out.println("alert('접근 권한이 없습니다.');");
      out.println("location.href='main.do';");
      out.println("</script>");
      return null;
    }
    
    // 세션에서 인증코드와 회원 ID 가져오기
    String sessionAuthCode = (String) session.getAttribute("authenticationCode");
    String sessionMemberId = (String) session.getAttribute("memberId");
    
    // 폼에서 입력된 값 가져오기
    String inputAuthCode = request.getParameter("authCode");
    String newPassword = request.getParameter("newPassword");
    String confirmPassword = request.getParameter("confirmPassword");
    
    PrintWriter out = response.getWriter();
    
    // 비밀번호 불일치 검사
    if (!newPassword.equals(confirmPassword)) {
      out.println("<script>");
      out.println("alert('비밀번호가 일치하지 않습니다.');");
      out.println("history.back();");
      out.println("</script>");
      return null;
    }
    
    // 인증코드 불일치 검사
    if (!sessionAuthCode.equals(inputAuthCode)) {
      out.println("<script>");
      out.println("alert('인증번호가 일치하지 않습니다.');");
      out.println("history.back();");
      out.println("</script>");
      return null;
    }
    
    // 비밀번호 변경 처리
    MemberDAO dao = new MemberDAO();
    boolean updateResult = false;
    int result = dao.updateMemberPassword(newPassword, sessionMemberId);
    
    if (result > 0) {
      updateResult = true;
    }
    
    if (updateResult) {
      session.invalidate(); // 인증 정보 제거
      out.println("<script>");
      out.println("alert('비밀번호가 변경되었습니다. 로그인 페이지로 이동합니다.');");
      out.println("location.href='login.do';");
      out.println("</script>");
    } else {
      out.println("<script>");
      out.println("alert('비밀번호 변경에 실패했습니다. 다시 시도해주세요.');");
      out.println("history.back();");
      out.println("</script>");
    }
    
    return null;
  }
}