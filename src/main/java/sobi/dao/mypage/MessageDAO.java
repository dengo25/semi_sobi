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
	//페이징
	public List<MessageVO> getInboxMessages(String memberId, int start, int size) throws SQLException {
	    List<MessageVO> list = new ArrayList<>();
	    String sql = 
	        "SELECT M.MESSAGE_ID, M.SENDER_ID, M.MESSAGE_TITLE, M.MESSAGE_SEND_DATE, M.MESSAGE_IS_READ, " +
	        "       U.MEMBER_NAME AS SENDER_NAME " +
	        "FROM MESSAGE M " +
	        "JOIN MEMBER U ON M.SENDER_ID = U.MEMBER_ID " +
	        "WHERE M.RECEIVER_ID = ? AND M.DELETED_BY_RECEIVER != 'Y' " +
	        "ORDER BY M.MESSAGE_SEND_DATE DESC LIMIT ?, ?";
	    
	    try (Connection conn = ConnectionProvider.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, memberId);
	        pstmt.setInt(2, start);
	        pstmt.setInt(3, size);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                MessageVO msg = new MessageVO();
	                msg.setMessageId(rs.getInt("MESSAGE_ID"));

	                String senderId = rs.getString("SENDER_ID");
	                String senderName = rs.getString("SENDER_NAME");
	                msg.setSenderId(senderName + " (" + senderId + ")");

	                msg.setMessageTitle(rs.getString("MESSAGE_TITLE"));
	                msg.setMessageSendDate(rs.getTimestamp("MESSAGE_SEND_DATE"));
	                msg.setMessageIsRead(rs.getString("MESSAGE_IS_READ")); // ✅ 요게 없었음!

	                list.add(msg);
	            }
	        }
	    }
	    return list;
	}
	public List<MessageVO> getSentMessages(String memberId, int start, int size) throws SQLException {
	    List<MessageVO> list = new ArrayList<>();

	    String sql =
	        "SELECT M.MESSAGE_ID, M.RECEIVER_ID, M.MESSAGE_TITLE, M.MESSAGE_SEND_DATE, M.MESSAGE_IS_READ, " +
	        "       U.MEMBER_NAME AS RECEIVER_NAME " +
	        "FROM MESSAGE M " +
	        "JOIN MEMBER U ON M.RECEIVER_ID = U.MEMBER_ID " +
	        "WHERE M.SENDER_ID = ? AND M.DELETED_BY_SENDER != 'Y' " +
	        "ORDER BY M.MESSAGE_SEND_DATE DESC LIMIT ?, ?";

	    try (Connection conn = ConnectionProvider.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, memberId);
	        pstmt.setInt(2, start);
	        pstmt.setInt(3, size);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                MessageVO vo = new MessageVO();
	                vo.setMessageId(rs.getInt("MESSAGE_ID"));

	                String receiverId = rs.getString("RECEIVER_ID");
	                String receiverName = rs.getString("RECEIVER_NAME");
	                vo.setReceiverId(receiverName + " (" + receiverId + ")");

	                vo.setMessageTitle(rs.getString("MESSAGE_TITLE"));
	                vo.setMessageSendDate(rs.getTimestamp("MESSAGE_SEND_DATE"));
	                vo.setMessageIsRead(rs.getString("MESSAGE_IS_READ"));

	                list.add(vo);
	            }
	        }
	    }

	    return list;
	}


	
	//받은 쪽지 개수
	public int countInboxMessages(String memberId) throws SQLException {
	    String sql = "SELECT COUNT(*) FROM MESSAGE WHERE RECEIVER_ID = ? AND DELETED_BY_RECEIVER != 'Y'";
	    try (Connection conn = ConnectionProvider.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, memberId);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) return rs.getInt(1);
	        }
	    }
	    return 0;
	}
	public int countSentMessages(String memberId) throws SQLException {
	    String sql = "SELECT COUNT(*) FROM MESSAGE WHERE SENDER_ID = ? AND DELETED_BY_SENDER != 'Y'";
	    try (Connection conn = ConnectionProvider.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, memberId);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) return rs.getInt(1);
	        }
	    }
	    return 0;
	}


	
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
	//받은쪽지 삭제 
	 public void deleteReceivedMessages(List<Integer> messageIds, String receiverId) throws SQLException {
	        String sql = "UPDATE MESSAGE SET DELETED_BY_RECEIVER = 'Y' "
	                   + "WHERE MESSAGE_ID = ? AND RECEIVER_ID = ?";

	        try (Connection conn = ConnectionProvider.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            for (int messageId : messageIds) {
	                pstmt.setInt(1, messageId);
	                pstmt.setString(2, receiverId);
	                pstmt.addBatch(); // 일괄처리
	            }

	            pstmt.executeBatch(); // 한번에실행
	        }
	    }
	//보낸쪽지 삭제(논리)
	  public void deleteSentMessages(List<Integer> messageIds, String senderId) throws SQLException {
	        String sql = "UPDATE MESSAGE SET DELETED_BY_SENDER = 'Y' "
	                   + "WHERE MESSAGE_ID = ? AND SENDER_ID = ?";

	        try (Connection conn = ConnectionProvider.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            for (int messageId : messageIds) {
	                pstmt.setInt(1, messageId);
	                pstmt.setString(2, senderId);
	                pstmt.addBatch();
	            }

	            pstmt.executeBatch();
	        }
	    }

	// 쪽지 쓰기
	  public boolean insertMessage(MessageVO message) {
	      String sql = "INSERT INTO MESSAGE (SENDER_ID, RECEIVER_ID, MESSAGE_TITLE, MESSAGE_CONTENT, MESSAGE_SEND_DATE, MESSAGE_IS_READ, DELETED_BY_SENDER, DELETED_BY_RECEIVER) "
	                 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	      try (Connection conn = ConnectionProvider.getConnection();
	           PreparedStatement pstmt = conn.prepareStatement(sql)) {

	          pstmt.setString(1, message.getSenderId());
	          pstmt.setString(2, message.getReceiverId());
	          pstmt.setString(3, message.getMessageTitle());
	          pstmt.setString(4, message.getMessageContent());
	          pstmt.setTimestamp(5, message.getMessageSendDate());
	          pstmt.setString(6, message.getMessageIsRead());
	          pstmt.setString(7, message.getDeletedBySender());
	          pstmt.setString(8, message.getDeletedByReceiver());

	          int result = pstmt.executeUpdate();
	          return result == 1;

	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	      return false;
	  }
}
