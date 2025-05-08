package sobi.action.member;

import sobi.dao.member.MemberDAO;
import sobi.vo.member.MemberVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/createMemberProcess.v1") //나중에 frontController로 받을 수 있게 수정필요 임시적 서블릿
public class CreateMemberServlet extends HttpServlet {
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    
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
      response.sendRedirect("member/login.do"); // 회원가입 성공 시 로그인 화면으로
    } else {
      request.setAttribute("errorMsg", "회원가입에 실패했습니다.");
      request.getRequestDispatcher("/v1/views/member/join.jsp").forward(request, response);
    }
  }
}