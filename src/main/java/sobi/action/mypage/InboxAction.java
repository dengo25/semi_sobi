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

public class InboxAction implements SobiAction {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String memberId = (String) session.getAttribute("memberId");

        if (memberId == null) {
            return "login.do"; 
        }

        try {
            MessageDAO dao = new MessageDAO();
            List<MessageVO> messageList = dao.getInboxMessages(memberId);
            request.setAttribute("messageList", messageList);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "받은 쪽지를 불러오는 중 오류가 발생했습니다.");
            return "/v1/views/common/error.jsp";
        }

        return "/v1/views/mypage/inbox.jsp";
    }
}
