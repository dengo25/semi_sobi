package sobi.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sobi.db.ConnectionProvider;
import sobi.vo.admin.ReviewReportVO;

public class ReviewReportDAO {
	public List<ReviewReportVO> getAllReviewReport(){
		List<ReviewReportVO> list = new ArrayList<ReviewReportVO>();
		String sql = "select * from REVIEW_REPORT";
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list.add(new ReviewReportVO(
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
	public int reviewReportCount(String memberId) {
		int cnt = 0;
		String sql = "select IFNULL(count(*),0) from REVIEW_REPORT "
				+ "where review_id "
				+ "in (select review_id from REVIEW "
				+ "WHERE member_id = ?) ";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
			ConnectionProvider.close(conn, pstmt, rs);
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		}
		return cnt;
	}
}
