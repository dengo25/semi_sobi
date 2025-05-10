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
	        "       M.MESSAGE_IS_READ, " + // 읽음 여부 추가
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

	                // 읽음 여부
	                vo.setMessageIsRead(rs.getString("MESSAGE_IS_READ"));

	                list.add(vo);
	            }
	        }
	    }

	    return list;
	}

	public MessageVO getMessageById(int messageId) throws SQLException {
        MessageVO message = null;

        String sql = "SELECT * FROM MESSAGE WHERE MESSAGE_ID = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, messageId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                message = new MessageVO();
                message.setMessageId(rs.getInt("MESSAGE_ID"));
                message.setSenderId(rs.getString("SENDER_ID"));
                message.setReceiverId(rs.getString("RECEIVER_ID"));
                message.setMessageTitle(rs.getString("MESSAGE_TITLE"));
                message.setMessageContent(rs.getString("MESSAGE_CONTENT"));
                message.setMessageSendDate(rs.getTimestamp("MESSAGE_SEND_DATE"));
                message.setMessageIsRead(rs.getString("MESSAGE_IS_READ"));
                message.setDeletedBySender(rs.getString("DELETED_BY_SENDER"));
                message.setDeletedByReceiver(rs.getString("DELETED_BY_RECEIVER"));
            }
        } finally {
            ConnectionProvider.close(conn, pstmt, rs);
        }

        return message;
    }
	//쪽지 읽음
	public void updateMessageRead(int messageId) throws SQLException {
	    String sql = "UPDATE MESSAGE SET MESSAGE_IS_READ = 'Y' WHERE MESSAGE_ID = ?";
	    
	    try (Connection conn = ConnectionProvider.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, messageId);
	        pstmt.executeUpdate();
	    }
	}
}
