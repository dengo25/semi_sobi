package sobi.dao;


import sobi.db.ConnectionProvider;
import sobi.vo.UserVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
  
  public boolean isIdExist(String id) { //아이디 중복확인
    boolean result = false;
    String sql = "SELECT COUNT(*) FROM MEMBER WHERE MEMBER_ID = ?";
    
    try (Connection conn = ConnectionProvider.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      
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
  
  
  public UserVO findByName(String name) {
    UserVO u = new UserVO();
    
    String sql = "select * from MEMBER where member_id = ";
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
  
  
  
  public UserVO findById(int no) {
    UserVO u = new UserVO();
    
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
        
      }
      ConnectionProvider.close(conn, pstmt, rs);
    } catch (SQLException e) {
      System.out.println("예외 발생" + e.getMessage());
    }
    return u;
  }
  
  public int insert(UserVO u) {
    String sql = "insert into MEMBER (member_id, member_password, member_name, member_email, member_birth, member_zip, member_addr) " +
        " values (?, ?, ?, ?, ?, ?, ?)";
    
    try {
      Connection conn = ConnectionProvider.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql);
      
      pstmt.setString(1, u.getMemberId());
      pstmt.setString(2, u.getMemberPassword());
      pstmt.setString(3, u.getMemberName());
      pstmt.setString(4, u.getMemberEmail());
      pstmt.setString(5, u.getMemberBirth());
      pstmt.setString(6, u.getMemberZip());
      pstmt.setString(7, u.getMemberAddr());
      
      return pstmt.executeUpdate(); // 성공하면 1을 반환
      
    } catch (Exception e) {
      System.out.println("예외 발생 " + e.getMessage());
      return 0;
    }
  }
  
  public UserVO loginConfirm(String id, String password) {
    UserVO u = new UserVO();
    String sql = "select * from MEMBER where member_id =? and member_password = ?";
    
    try {
      Connection conn = ConnectionProvider.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql);
      
      pstmt.setString(1, id);
      pstmt.setString(2, password);
      
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
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
      }
      
    } catch (Exception e) {
      System.out.println("예외 발생 " + e.getMessage());
    }
    return u;
  }
  
}
