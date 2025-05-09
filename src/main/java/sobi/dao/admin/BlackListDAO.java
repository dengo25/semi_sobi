package sobi.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sobi.db.ConnectionProvider;
import sobi.vo.admin.BlackListVO;

public class BlackListDAO {
	public int insertBlackList(String memberId, String reportType, String status) {
		int re = -1;
		String sql = "insert into BLACK_LIST(member_id, report_type, status) values(?, ?, ?)";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberId);
			pstmt.setString(2, reportType);
			pstmt.setString(3, status);
			
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		}
		return re;
	}
	public List<BlackListVO> getAllBlackList() {
		List<BlackListVO> list = new ArrayList<BlackListVO>();
		
		String sql = "select * from BLACK_LIST";
		
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				list.add(new BlackListVO(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getDate(4),
						rs.getString(5)
						));
			}
			ConnectionProvider.close(conn, stmt, rs);
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		}
		return list;
	}
	public List<BlackListVO> getAllBlockedList() {
		List<BlackListVO> list = new ArrayList<BlackListVO>();
		
		String sql = "select * from BLACK_LIST where status='blocked'";
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				list.add(new BlackListVO(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getDate(4),
						rs.getString(5)
						));
			}
			ConnectionProvider.close(conn, stmt, rs);
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		}
		return list;
	}
}
