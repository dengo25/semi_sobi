package sobi.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;

import sobi.db.ConnectionProvider;

public class BlackListHistoryDAO {
	public int insertBlackListHistory(String memberId, int blackListNo, String detail) {
		int re = -1;
		String sql = "insert into BLACKLIST_HISTORY(blacklist_no, member_id, detail) values(?,?,?)";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, blackListNo);
			pstmt.setString(2, memberId);
			pstmt.setString(3, detail);
			
			re = pstmt.executeUpdate();
			
			ConnectionProvider.close(conn, pstmt);
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		}
		return re;
	}
}
