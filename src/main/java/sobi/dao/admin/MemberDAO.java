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
	public List<MemberVO> getPagedTodayJoinMemberList(int page, int pageSize) {
		List<MemberVO> list = new ArrayList<MemberVO>();
		String sql = "SELECT MEMBER_ID, MEMBER_NAME, MEMBER_EMAIL, MEMBER_REG "
				+ "FROM MEMBER "
				+ "WHERE DATE(member_reg) = CURDATE() AND ROLE = 'M' "
				+ "ORDER BY MEMBER_REG DESC LIMIT ? OFFSET ?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pageSize);
			pstmt.setInt(2, (page-1) * pageSize);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(new MemberVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4)));
			}
			ConnectionProvider.close(conn, pstmt, rs);
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		}
		return list;
	}
	public int getTodayJoinMember() {
		int count = 0;
		String sql = "select IFNULL(count(*), 0) from MEMBER where DATE(member_reg) = CURDATE() and role = 'M'";
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {
				count = rs.getInt(1);
			}
			ConnectionProvider.close(conn, stmt, rs);
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		}
		return count;
	}
	public int memberCount() {
		int memberCount = 0;
		String sql = "select IFNULL(count(*),0) from MEMBER where role='M'";
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if(rs.next()) {
				memberCount = rs.getInt(1);
			}
			ConnectionProvider.close(conn, stmt, rs);
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		}
		return memberCount;
	}
	public MemberVO findById(String memberId) {
		MemberVO m = new MemberVO();

		String sql = "select * from MEMBER where member_id = ? ";
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

	public List<MemberVO> getPagedMemberList(int page, int pageSize) {
		List<MemberVO> list = new ArrayList<MemberVO>();
		String sql = "SELECT MEMBER_ID, MEMBER_NAME, MEMBER_EMAIL, MEMBER_REG "
				+ "FROM MEMBER "
				+ "WHERE ROLE = 'M' "
				+ "ORDER BY MEMBER_REG DESC LIMIT ? OFFSET ?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pageSize);
			pstmt.setInt(2, (page-1) * pageSize);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(new MemberVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4)));
			}
			ConnectionProvider.close(conn, pstmt, rs);
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		}
		return list;
	}
}