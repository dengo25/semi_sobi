package sobi.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import sobi.db.ConnectionProvider;
import sobi.vo.admin.MemberLogVO;

public class MemberLogDAO{
	public List<MemberLogVO> getPagedMemberLogByMemberId(String memberId, int page, int pageSize) {
	    List<MemberLogVO> list = new ArrayList<>();
	    String sql = "SELECT member_id, accessed_menu, member_action_type, member_log_created " +
	                 "FROM MEMBER_LOG " +
	                 "WHERE member_id = ? " +
	                 "ORDER BY member_log_created DESC " +
	                 "LIMIT ? OFFSET ?";
	    try {
	        Connection conn = ConnectionProvider.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, memberId);
	        pstmt.setInt(2, pageSize);
	        pstmt.setInt(3, (page - 1) * pageSize);

	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            list.add(new MemberLogVO(
	            		rs.getString(1),
	            		rs.getString(2),
	            		rs.getString(3),
	            		rs.getDate(4)
	            ));
	        }
	        ConnectionProvider.close(conn, pstmt, rs);
	    } catch (Exception e) {
	        System.out.println("예외발생: " + e.getMessage());
	    }
	    return list;
	}
	public int getMemberLogCount(String memberId) {
		int count=0;
		String sql = "select IFNULL(count(*),0) from MEMBER_LOG where member_id = ?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			ConnectionProvider.close(conn, pstmt, rs);
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		}
		return count;
	}
	public List<MemberLogVO> getRecentLog(){
		List<MemberLogVO> list = new ArrayList<MemberLogVO>();
	    String sql = "SELECT * FROM MEMBER_LOG "
	               + "ORDER BY member_log_created DESC "
	               + "LIMIT 5";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(new MemberLogVO(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getDate(5)
						));
			}
			ConnectionProvider.close(conn, pstmt, rs);
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		}
		return list;
	}
	public int insertMemberLog(String memberId, String accessedMenu, String actionType) {
		int re = -1;
		String sql = "insert MEMBER_LOG(member_id, accessed_menu, member_action_type) values(?,?,?)";
		 
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberId);
			pstmt.setString(2, accessedMenu);
			pstmt.setString(3, actionType);
			
			re = pstmt.executeUpdate();
			
			ConnectionProvider.close(conn, pstmt);
			
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		}
		return re;
	}
	public int getFilteredMemberLogCount(String level, String menu, String type) {
	    int count = 0;
	    StringBuilder sql = new StringBuilder(
	        "SELECT COUNT(*) FROM MEMBER_LOG ml JOIN MEMBER m ON m.member_id = ml.member_id WHERE 1=1 "
	    );
	    List<String> setStr = new ArrayList<>();

	    if (level != null && !level.equals("") && !level.equals("all")) {
	        if (level.equals("admin")) {
	            sql.append("AND m.role = 'A' ");
	        } else if (level.equals("member")) {
	            sql.append("AND m.role = 'M' ");
	        }
	    }

	    if (menu != null && !menu.equals("")) {
	        sql.append("AND ml.accessed_menu = ? ");
	        setStr.add(menu);
	    }

	    if (type != null && !type.equals("")) {
	        sql.append("AND ml.member_action_type = ? ");
	        setStr.add(type);
	    }

	    try {
	        Connection conn = ConnectionProvider.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql.toString());

	        for (int i = 0; i < setStr.size(); i++) {
	            pstmt.setString(i + 1, setStr.get(i));
	        }

	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            count = rs.getInt(1);
	        }
	        ConnectionProvider.close(conn, pstmt, rs);
	    } catch (Exception e) {
	        System.out.println("예외발생: " + e.getMessage());
	    }

	    return count;
	}
	public List<MemberLogVO> getAllMemberLog(String level, String menu, String type, int page, int pageSize) {
		List<MemberLogVO> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder(
			"SELECT ml.log_no, ml.member_id, ml.accessed_menu, ml.member_action_type, ml.member_log_created " +
			"FROM MEMBER_LOG ml " +
			"JOIN MEMBER m ON m.member_id = ml.member_id " +
			"WHERE 1=1 "
		);
		List<String> setStr = new ArrayList<>();

		if (level != null && !level.equals("") && !level.equals("all")) {
			if (level.equals("admin")) {
				sql.append("AND m.role = 'A' ");
			} else if (level.equals("member")) {
				sql.append("AND m.role = 'M' ");
			}
		}
		if (menu != null && !menu.equals("")) {
			sql.append("AND ml.accessed_menu = ? ");
			setStr.add(menu);
		}
		if (type != null && !type.equals("")) {
			sql.append("AND ml.member_action_type = ? ");
			setStr.add(type);
		}

		sql.append("ORDER BY ml.member_log_created DESC ");
		sql.append("LIMIT ? OFFSET ?");

		try {
			Connection conn = ConnectionProvider.getConnection();
		    PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			int idx = 1;
			for (String param : setStr) {
				pstmt.setString(idx++, param);
			}
			pstmt.setInt(idx++, pageSize);              
			pstmt.setInt(idx, (page - 1) * pageSize);     

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(new MemberLogVO(
					rs.getInt(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getTimestamp(5)
				));
			}
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		}
		return list;
	}
}
