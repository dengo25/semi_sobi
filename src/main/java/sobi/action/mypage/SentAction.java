package sobi.action.mypage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sobi.action.common.SobiAction;
import sobi.dao.mypage.MessageDAO;
import sobi.vo.mypage.MessageVO;
import sobi.vo.mypage.PagingVO;
import sobi.vo.member.MemberVO;

public class SentAction implements SobiAction {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        MemberVO member = (MemberVO) session.getAttribute("member");

        if (member == null) {
            return "redirect:login.do";
        }

        String memberId = member.getMemberId();
        
        int nowPage = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null) nowPage = Integer.parseInt(pageParam);

        try {
            MessageDAO dao = new MessageDAO();
            int totalCount = dao.countSentMessages(memberId);
            PagingVO paging = new PagingVO(nowPage, totalCount);

            List<MessageVO> messageList = dao.getSentMessages(memberId, paging.getStartNo(), paging.getPageSize());
            
            request.setAttribute("messageList", messageList);
            request.setAttribute("paging", paging);
            request.setAttribute("contentPage", "/v1/views/mypage/sent.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "보낸 쪽지를 불러오는 중 오류가 발생했습니다.");
            return "/v1/views/common/error.jsp";
        }

        return "/v1/views/mypage/sent.jsp";
    }
}
