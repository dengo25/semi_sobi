package sobi.action.member;


import sobi.action.common.SobiAction;
import sobi.dao.MemberDAO;
import sobi.vo.MemberVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//회원 가입
public class JoinProcessAction implements SobiAction {
  
  @Override
  public String pro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    request.setCharacterEncoding("utf-8");
    
    MemberVO u = new MemberVO();
    MemberDAO dao = new MemberDAO();
    
    u.setMemberId(request.getParameter("member_id"));
    u.setMemberPassword(request.getParameter("member_password"));
    u.setMemberName(request.getParameter("member_name"));
    u.setMemberEmail(request.getParameter("member_email"));
    u.setMemberBirth(request.getParameter("member_birth"));
    u.setMemberZip(request.getParameter("member_zip"));
    u.setMemberAddr(request.getParameter("member_addr"));
    
    int result = dao.insert(u);
    
    if (result == 1) {
      return "redirect:login.do";
    } else {
      request.setAttribute("errorMsg", "회원가입에 실패했습니다.");
      return "/v1/views/user/join.jsp"; // 다시 회원가입 폼 보여주기
    }
  }
}
