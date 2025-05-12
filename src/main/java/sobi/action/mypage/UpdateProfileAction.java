package sobi.action.mypage;

import sobi.action.common.SobiAction;
import sobi.dao.mypage.MemberDAO;
import sobi.vo.member.MemberVO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class UpdateProfileAction implements SobiAction {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        MemberVO loginMember = (MemberVO) session.getAttribute("member");
        if (loginMember == null) return "redirect:login.do";

        String newPwd = request.getParameter("member_password");
        String zip = request.getParameter("member_zip");
        String addr = request.getParameter("member_addr");

        MemberDAO dao = new MemberDAO();
        int result = dao.updateMemberInfo(loginMember.getMemberId(), newPwd, zip, addr);

        if (result > 0) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('수정에 성공했습니다.'); location.href='mypage.do';</script>");
            out.close();
            return null; // 직접 응답 완료
        } else {
            request.setAttribute("errorMsg", "수정에 실패했습니다.");
            return "/v1/views/mypage/profile.jsp";
        }
    }
}
