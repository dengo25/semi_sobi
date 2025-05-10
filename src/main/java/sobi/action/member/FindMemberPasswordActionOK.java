package sobi.action.member;

import sobi.action.common.SobiAction;
import sobi.dao.member.MemberDAO;
import sobi.util.NaverMailSend;
import sobi.vo.member.MemberVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class FindMemberPasswordActionOK implements SobiAction {
  @Override
  public String process(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    
    request.setCharacterEncoding("utf-8");
    response.setContentType("text/html;charset=utf-8");
    
    HttpSession session = request.getSession(true);
    
    // 로그인 상태면 접근 차단
    if (session.getAttribute("loginUser") != null) {
      session.invalidate();
      PrintWriter out = response.getWriter();
      out.println("<script>");
      out.println("alert('접근 권한이 없습니다.');");
      out.println("location.href='main.do';");
      out.println("</script>");
      return null;
    }
    
    // 입력 파라미터
    String memberId = request.getParameter("memberId");
    String memberEmail = request.getParameter("memberEmail");
    
    MemberDAO dao = new MemberDAO();
    MemberVO member = dao.findMemberByIdAndEmail(memberId, memberEmail);
    
    if (member == null || !member.getMemberEmail().equals(memberEmail)) {
      // 정보 불일치
      PrintWriter out = response.getWriter();
      out.println("<script>");
      out.println("alert('회원 정보가 존재하지 않습니다.');");
      out.println("history.back();");
      out.println("</script>");
      return null;
    }
    
    try {
      // 인증 코드 전송
      NaverMailSend mail = new NaverMailSend();
      String authCode = mail.sendEmail(memberEmail);
      
      // 인증코드와 아이디를 세션에 저장
      session.setAttribute("authenticationCode", authCode);
      session.setAttribute("memberId", memberId);
      
      // 비밀번호 변경 화면으로 리다이렉트
      return "redirect:memberChangePw.do";
      
    } catch (Exception e) {
      e.printStackTrace();
      PrintWriter out = response.getWriter();
      out.println("<script>");
      out.println("alert('메일 전송 중 오류가 발생했습니다.');");
      out.println("history.back();");
      out.println("</script>");
      return null;
    }
  }
}
