package sobi.dao.admin;

import sobi.db.ConnectionProvider;
import sobi.vo.admin.MemberVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
	  public MemberVO findById(String memberId) {
		    MemberVO m = new MemberVO();
		    
		    String sql = "select * from MEMBER where member_id = ?";
		    try {
		      Connection conn = ConnectionProvider.getConnection();
		      PreparedStatement pstmt = conn.prepareStatement(sql);
		      pstmt.setString(1, memberId);
		      ResultSet rs = pstmt.executeQuery();
		      if (rs.next()) {
		        m.setMemberId(rs.getString("member_id"));
		        m.setMemberName(rs.getString("member_name"));
		        m.setMemberGender(rs.getString("member_gender"));
		        m.setMemberEmail(rs.getString("member_email"));
		        m.setMemberBirth(rs.getString("member_birth"));
		        m.setMemberAddr(rs.getString("member_addr"));
		        m.setMemberReg(rs.getTimestamp("member_reg"));

		      }
		      ConnectionProvider.close(conn, pstmt, rs);
		    } catch (SQLException e) {
		      System.out.println("예외 발생" + e.getMessage());
		    }
		    return m;
		  }
	public List<MemberVO> findAllMember() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		String sql = "select * from MEMBER where role='M'";
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				list.add(new MemberVO(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getDate(10),
						rs.getString(11), rs.getString(12)));
			}
			ConnectionProvider.close(conn, stmt, rs);
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		}
		return list;
	}

	public List<MemberVO> getReviewandCommentCount() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		String sql = "SELECT "
				+ "    m.MEMBER_ID, "
				+ "    m.MEMBER_NAME, "
				+ "    m.MEMBER_EMAIL, "
				+ "    m.MEMBER_REG, "
				+ "    IFNULL(r.review_count, 0) AS review_count, "
				+ "    IFNULL(c.comment_count, 0) AS comment_count "
				+ "FROM MEMBER m "
				+ "LEFT JOIN ( "
				+ "    SELECT MEMBER_ID, COUNT(*) AS review_count "
				+ "    FROM REVIEW "
				+ "    WHERE IS_DELETED = 'N' "
				+ "    GROUP BY MEMBER_ID "
				+ ") r ON m.MEMBER_ID = r.MEMBER_ID "
				+ "LEFT JOIN ( "
				+ "    SELECT MEMBER_ID, COUNT(*) AS comment_count "
				+ "    FROM REVIEW_COMMENT "
				+ "    GROUP BY MEMBER_ID "
				+ ") c ON m.MEMBER_ID = c.MEMBER_ID "
				+ "WHERE m.ROLE = 'M'";
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				list.add(new MemberVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5),
						rs.getInt(6)));
			}
			ConnectionProvider.close(conn, stmt, rs);
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		}
		return list;
	}
}