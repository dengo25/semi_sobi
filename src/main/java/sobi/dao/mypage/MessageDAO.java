package sobi.dao.mypage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sobi.db.ConnectionProvider;
import sobi.vo.mypage.MessageVO;

public class MessageDAO {
    // 받은쪽지목록 
	public List<MessageVO> getInboxMessages(String memberId) throws SQLException {
        List<MessageVO> list = new ArrayList<>();

        String sql =
            "SELECT M.MESSAGE_ID, M.SENDER_ID, M.MESSAGE_TITLE, M.MESSAGE_SEND_DATE, M.MESSAGE_IS_READ, " +
            "       U.MEMBER_NAME AS SENDER_NAME " +
            "FROM MESSAGE M " +
            "JOIN MEMBER U ON M.SENDER_ID = U.MEMBER_ID " +
            "WHERE M.RECEIVER_ID = ? AND M.DELETED_BY_RECEIVER != 'Y' " +
            "ORDER BY M.MESSAGE_SEND_DATE DESC";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    MessageVO vo = new MessageVO();
                    vo.setMessageId(rs.getInt("MESSAGE_ID"));

                    // 닉네임(아이디) 형태로 
                    String senderId = rs.getString("SENDER_ID");
                    String senderName = rs.getString("SENDER_NAME");
                    vo.setSenderId(senderName + " (" + senderId + ")");

                    vo.setMessageTitle(rs.getString("MESSAGE_TITLE"));
                    vo.setMessageSendDate(rs.getTimestamp("MESSAGE_SEND_DATE"));
                    vo.setMessageIsRead(rs.getString("MESSAGE_IS_READ"));

                    list.add(vo);
                }
            }
        }

        return list;
    }

    // 보낸 쪽지목록
	public List<MessageVO> getSentMessages(String memberId) throws SQLException {
        List<MessageVO> list = new ArrayList<>();

        String sql =
            "SELECT M.MESSAGE_ID, M.RECEIVER_ID, M.MESSAGE_TITLE, M.MESSAGE_SEND_DATE, " +
            "       U.MEMBER_NAME AS RECEIVER_NAME " +
            "FROM MESSAGE M " +
            "JOIN MEMBER U ON M.RECEIVER_ID = U.MEMBER_ID " +
            "WHERE M.SENDER_ID = ? AND M.DELETED_BY_SENDER != 'Y' " +
            "ORDER BY M.MESSAGE_SEND_DATE DESC";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    MessageVO vo = new MessageVO();
                    vo.setMessageId(rs.getInt("MESSAGE_ID"));

                    
                    String receiverId = rs.getString("RECEIVER_ID");
                    String receiverName = rs.getString("RECEIVER_NAME");
                    vo.setReceiverId(receiverName + " (" + receiverId + ")");

                    vo.setMessageTitle(rs.getString("MESSAGE_TITLE"));
                    vo.setMessageSendDate(rs.getTimestamp("MESSAGE_SEND_DATE"));

                    list.add(vo);
                }
            }
        }

        return list;
    }
}
