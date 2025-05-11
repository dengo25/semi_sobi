package sobi.action.mypage;

import sobi.action.common.SobiAction;
import sobi.dao.mypage.MemberDAO;
import sobi.vo.member.MemberVO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class WithdrawAction implements SobiAction {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        MemberVO loginMember = (MemberVO) session.getAttribute("member");

        if (loginMember == null) {
            return "redirect:login.do"; 
        }

        String memberId = loginMember.getMemberId();
        MemberDAO dao = new MemberDAO();
        int result = dao.deactivateMember(memberId);

        if (result > 0) {
            // 세션무효화
            session.invalidate();

            // 성공시 알림 띄우고 메인으로 이동
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('회원 탈퇴가 완료되었습니다.'); location.href='main.do';</script>");
            out.close();
            return null;
        } else {
            // 실패 시 다시 프로필 페이지
            request.setAttribute("errorMsg", "회원 탈퇴에 실패했습니다.");
            return "/v1/views/mypage/profile.jsp";
        }
    }
}
