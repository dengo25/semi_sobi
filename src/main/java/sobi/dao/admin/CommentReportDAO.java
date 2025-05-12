package sobi.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sobi.db.ConnectionProvider;
import sobi.vo.admin.CommentReportVO;

public class CommentReportDAO {
	public List<CommentReportVO> getAllCommentReport(){
		List<CommentReportVO> list = new ArrayList<CommentReportVO>();
		String sql = "select * from Comment_REPORT";
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list.add(new CommentReportVO(
						rs.getInt(1),
						rs.getInt(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6)						
						));
			}
			ConnectionProvider.close(conn, stmt, rs);
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		}
		return list;
	}
	public int commentReportCount(String memberId) {
		int cnt = 0;
		String sql = "select count(*) from REVIEW_COMMENT "
				+ "where comment_id "
				+ "in (select comment_id from REVIEW_COMMENT"
				+ "WHERE member_id = ?) ";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		}
		return cnt;
	}
}
