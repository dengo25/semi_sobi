package sobi.dao.member;

import sobi.db.ConnectionProvider;
import sobi.vo.member.MemberVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {
  
  public MemberVO findMemberByIdAndEmail(String memberId, String memberEmail) {
    MemberVO member = null;
    String sql = "select * from MEMBER where MEMBER_ID = ? and MEMBER_EMAIL = ?";
    try {
      Connection conn = ConnectionProvider.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, memberId);
      pstmt.setString(2, memberEmail);
      
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        member = new MemberVO();
        member.setMemberId(rs.getString("member_id"));
        member.setMemberEmail(rs.getString("member_email"));
        
      }
      ConnectionProvider.close(conn, pstmt, rs);
    } catch (Exception e) {
      System.out.println("예외발생 " + e.getMessage());
    }
    return member;
  }
  
  public String  findMemberId(String memberName, String memberEmail) {
    String sql = "select MEMBER_ID from MEMBER where MEMBER_NAME = ? and MEMBER_EMAIL = ?";
    String memberId = null;
    try {
      Connection conn = ConnectionProvider.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, memberName);
      pstmt.setString(2, memberEmail);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        memberId = rs.getString("MEMBER_ID");
      }
      ConnectionProvider.close(conn, pstmt, rs);
    } catch (Exception e) {
      System.out.println("예외발생 + "+e.getMessage());
    }
    return memberId;
  }

  
  public boolean isIdExist(String id) { //아이디 중복확인
    boolean result = false;
    String sql = "SELECT COUNT(*) FROM MEMBER WHERE MEMBER_ID = ?";
    
    try {
      Connection conn = ConnectionProvider.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, id);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        result = rs.getInt(1) > 0;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return result;
  }
  
  
  public MemberVO findByName(String name) {
    MemberVO u = new MemberVO();
    
    String sql = "select * from MEMBER where member_id = ?";
    try {
      Connection conn = ConnectionProvider.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, name);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        u.setMemberId(rs.getString("member_id"));
        u.setBlackList(rs.getInt("blacklist"));
        u.setMemberName(rs.getString("member_name"));
        u.setMemberAddr(rs.getString("member_addr"));
        u.setMemberZip(rs.getString("member_zip"));
        u.setMemberGender(rs.getString("member_gender"));
        u.setMemberEmail(rs.getString("member_email"));
        u.setMemberReg(rs.getTimestamp("member_reg"));
        u.setIsActive(rs.getString("is_active"));
        
      }
      ConnectionProvider.close(conn, pstmt, rs);
    } catch (SQLException e) {
      System.out.println("예외 발생" + e.getMessage());
    }
    return u;
  }
  
  
  
  public MemberVO findById(int no) {
    MemberVO u = new MemberVO();
    
    String sql = "select * from MEMBER where member_id = ?";
    try {
      Connection conn = ConnectionProvider.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, no);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        u.setMemberId(rs.getString("member_id"));
        u.setBlackList(rs.getInt("blacklist"));
        u.setMemberName(rs.getString("member_name"));
        u.setMemberAddr(rs.getString("member_addr"));
        u.setMemberZip(rs.getString("member_zip"));
        u.setMemberGender(rs.getString("member_gender"));
        u.setMemberEmail(rs.getString("member_email"));
        u.setMemberReg(rs.getTimestamp("member_reg"));
        u.setIsActive(rs.getString("is_active"));
        u.setRole(rs.getString("role"));
        
      }
      ConnectionProvider.close(conn, pstmt, rs);
    } catch (SQLException e) {
      System.out.println("예외 발생" + e.getMessage());
    }
    return u;
  }
  
  public int createMember(MemberVO u) {
    String sql = "INSERT INTO MEMBER (member_id, member_password, member_name, member_gender, member_email, member_birth, member_zip, member_addr) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = ConnectionProvider.getConnection();
      pstmt = conn.prepareStatement(sql);
      
      pstmt.setString(1, u.getMemberId());
      pstmt.setString(2, u.getMemberPassword());
      pstmt.setString(3, u.getMemberName());
      pstmt.setString(4, u.getMemberGender());
      pstmt.setString(5, u.getMemberEmail());
      pstmt.setString(6, u.getMemberBirth());
      pstmt.setString(7, u.getMemberZip());
      pstmt.setString(8, u.getMemberAddr());
      
      return pstmt.executeUpdate(); // 성공하면 1을 반환
      
      
    } catch (Exception e) {
      System.out.println("예외 발생 " + e.getMessage());
      return 0;
      
    } finally {
      ConnectionProvider.close(conn, pstmt); // ✅ 항상 호출됨
    }
  }
  
  public MemberVO loginConfirm(String id, String password) {
    
    
    String sql = "SELECT * FROM MEMBER WHERE member_id = ? AND member_password = ? AND IS_ACTIVE = 'Y'";
    MemberVO u =null;
    try {
      Connection conn = ConnectionProvider.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql);
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
        u.setRole(rs.getString("ROLE"));
      }
      ConnectionProvider.close(conn, pstmt, rs);
    } catch (Exception e) {
      System.out.println("예외 발생 " + e.getMessage());
    }
    return u;
  }
}