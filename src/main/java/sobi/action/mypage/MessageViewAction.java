package sobi.action.mypage;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import sobi.action.common.SobiAction;
import sobi.dao.mypage.MessageDAO;
import sobi.vo.mypage.MessageVO;

public class MessageViewAction implements SobiAction {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String messageId = request.getParameter("messageId");
        HttpSession session = request.getSession();
        if (session.getAttribute("member") == null) {
            return "redirect:login.do";
        }

        try {
            MessageDAO dao = new MessageDAO();
            MessageVO message = dao.getMessageById(Integer.parseInt(messageId));
            request.setAttribute("message", message);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "쪽지 상세 조회 중 오류 발생");
            return "/v1/views/common/error.jsp";
        }

        return "/v1/views/mypage/viewMessage.jsp";
    }
}
