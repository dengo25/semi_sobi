package sobi.action.mypage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sobi.action.common.SobiAction;
import sobi.dao.mypage.MessageDAO;
import sobi.vo.member.MemberVO;

public class SentDeleteAction implements SobiAction {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        MemberVO member = (MemberVO) session.getAttribute("member");

        if (member == null) {
            return "redirect:/v1/views/member/login.jsp";
        }

        String[] messageIdParams = request.getParameterValues("messageId");

        if (messageIdParams != null) {
            List<Integer> messageIds = Arrays.stream(messageIdParams)
                                             .map(Integer::parseInt)
                                             .collect(Collectors.toList());
            try {
                MessageDAO dao = new MessageDAO();
                dao.deleteSentMessages(messageIds, member.getMemberId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return "redirect:/v1/views/mypage/sent.jsp"; 
    }
}
