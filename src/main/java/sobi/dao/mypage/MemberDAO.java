package sobi.dao.mypage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sobi.db.ConnectionProvider;
import sobi.vo.member.MemberVO;

public class MemberDAO {
	
	public int updateMemberInfo(String id, String newPwd, String zip, String addr) {
	    String sql = "UPDATE MEMBER SET "
	               + (newPwd != null && !newPwd.isBlank() ? "member_password = ?, " : "")
	               + "member_zip = ?, member_addr = ? WHERE member_id = ?";
	    try (Connection conn = ConnectionProvider.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        int idx = 1;
	        if (newPwd != null && !newPwd.isBlank()) {
	            pstmt.setString(idx++, newPwd);
	        }
	        pstmt.setString(idx++, zip);
	        pstmt.setString(idx++, addr);
	        pstmt.setString(idx, id);
	        return pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return 0;
	    }
	}

	public MemberVO loginConfirm(String id, String password) {
	    String sql = "SELECT * FROM MEMBER WHERE member_id = ? AND member_password = ? AND IS_ACTIVE = 'Y'";
	    MemberVO u = null;
	    try (Connection conn = ConnectionProvider.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, id);
	        pstmt.setString(2, password);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            u = new MemberVO();
	            u.setMemberId(rs.getString("member_id"));
	            u.setMemberPassword(rs.getString("member_password"));
	            u.setMemberName(rs.getString("member_name"));
	            u.setMemberGender(rs.getString("member_gender"));
	            u.setMemberEmail(rs.getString("member_email"));
	            u.setMemberBirth(rs.getString("member_birth"));
	            u.setMemberAddr(rs.getString("member_addr"));
	            u.setMemberZip(rs.getString("member_zip"));
	            u.setMemberReg(rs.getTimestamp("member_reg"));
	            u.setIsActive(rs.getString("is_active"));
	            u.setRole(rs.getString("role"));
	        }
	    } catch (Exception e) {
	        System.out.println("예외 발생 " + e.getMessage());
	    }
	    return u;
	}

}
