package sobi.action.member;

import sobi.action.common.SobiAction;
import sobi.dao.member.MemberDAO;
import sobi.vo.member.MemberVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateMemberAction implements SobiAction {
  
  @Override
  public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("utf-8");
    
    MemberVO u = new MemberVO();
    u.setMemberId(request.getParameter("member_id"));
    u.setMemberPassword(request.getParameter("member_password"));
    u.setMemberName(request.getParameter("member_name"));
    u.setMemberEmail(request.getParameter("member_email"));
    u.setMemberBirth(request.getParameter("member_birth"));
    u.setMemberGender(request.getParameter("member_gender"));
    u.setMemberZip(request.getParameter("member_zip"));
    u.setMemberAddr(request.getParameter("member_addr"));
    
    MemberDAO dao = new MemberDAO();
    int result = dao.createMember(u);
    
    if (result == 1) {
      //  FrontController에서 redirect 시킬 수 있게 "redirect:" 접두어 사용
      return "redirect:/v1/views/member/login.do";
    } else {
      request.setAttribute("errorMsg", "회원가입에 실패했습니다.");
      return "/v1/views/member/join.jsp"; // forward는 그냥 경로만 반환
    }
  }
}
