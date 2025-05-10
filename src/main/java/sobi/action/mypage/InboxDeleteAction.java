package sobi.action.mypage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import sobi.action.common.SobiAction;
import sobi.dao.mypage.MessageDAO;
import sobi.vo.member.MemberVO;

public class InboxDeleteAction implements SobiAction {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        MemberVO member = (MemberVO) session.getAttribute("member");
        if (member == null) return "redirect:login.do";

        String[] messageIdParams = request.getParameterValues("messageId");
        String box = request.getParameter("box"); 

        if (messageIdParams != null) {
            List<Integer> messageIds = Arrays.stream(messageIdParams)
                                             .map(Integer::parseInt)
                                             .collect(Collectors.toList());
            try {
                MessageDAO dao = new MessageDAO();
                dao.deleteReceivedMessages(messageIds, member.getMemberId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return "mypage.do?tab=" + (box != null ? box : "inbox");
    }
}
