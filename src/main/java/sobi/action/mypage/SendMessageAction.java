package sobi.action.mypage;

import sobi.action.common.SobiAction;
import sobi.dao.mypage.MessageDAO;
import sobi.vo.mypage.MessageVO;
import sobi.vo.member.MemberVO;

import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;

public class SendMessageAction implements SobiAction {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	 request.setCharacterEncoding("UTF-8");
    	 
        HttpSession session = request.getSession();
        MemberVO sender = (MemberVO) session.getAttribute("member");

        if (sender == null) {
            return "redirect:login.do";
        }

        
        String senderId = sender.getMemberId();
        String receiverId = request.getParameter("receiverId");
        String title = request.getParameter("messageTitle");
        String content = request.getParameter("messageContent");

       
        MessageVO message = new MessageVO();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setMessageTitle(title);
        message.setMessageContent(content);
        message.setMessageSendDate(new Timestamp(System.currentTimeMillis()));
        message.setMessageIsRead("N");
        message.setDeletedBySender("N");
        message.setDeletedByReceiver("N");

        
        MessageDAO dao = new MessageDAO();
        boolean result = dao.insertMessage(message);

       
        request.setAttribute("sendSuccess", result);

       
        return "/v1/views/mypage/sendResult.jsp";
    }
}
