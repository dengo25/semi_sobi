package sobi.action.mypage;

import sobi.action.common.SobiAction;
import sobi.dao.member.MemberDAO;
import sobi.vo.member.MemberVO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class CheckPasswordAction implements SobiAction {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        MemberVO loginMember = (MemberVO) session.getAttribute("member");

        if (request.getMethod().equalsIgnoreCase("GET")) {
            return "/v1/views/mypage/confirmPassword.jsp";
        }

        String inputPwd = request.getParameter("member_password");

        if (loginMember == null) return "redirect:login.do";

        MemberDAO dao = new MemberDAO();
        MemberVO verified = dao.loginConfirm(loginMember.getMemberId(), inputPwd);

        if (verified != null) {
            session.setAttribute("memberDetail", verified);
            return "/v1/views/mypage/profile.jsp";
        } else {
            request.setAttribute("errorMsg", "비밀번호가 틀렸습니다.");
            return "/v1/views/mypage/confirmPassword.jsp";
        }
    }
}
