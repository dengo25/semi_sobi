package sobi.dao.member;

import sobi.db.ConnectionProvider;
import sobi.vo.member.MemberVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberSocialDAO {
  
  //카카오 로그인이 들어오면 1차적으로 소셜 테이블에서 조회
  public String findMemberIdBySocial(String socialType, String socialId) {
    
    Connection conn = null;
    String sql = "select MEMBER_ID from MEMBER_SOCIAL where SOCIAL_TYPE = ? AND SOCIAL_ID = ?";
    try {
      conn = ConnectionProvider.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, socialType);
      pstmt.setString(2, socialId);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        return rs.getString("member_id");
      }
      
    } catch (SQLException e) {
      System.out.println("예외 발생  " + e.getMessage());
    }
    return null; // 소셜 계증으로 연결된 회원이 없다.
  }
  
  
  //카카오 로그인 두 번째 -> 소셜타입, 소셜아이디가 없을 때 카카오에서 받아온 이메일을 기존 멤버테이블에서 대조한다.
  public String findMemberIdByEmail(String email) {
    String sql = "select MEMBER_ID from MEMBER where MEMBER_EMAIL=?";
    Connection conn = null;
    
    try {
      conn = ConnectionProvider.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql);
      
      pstmt.setString(1, email);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        return rs.getString("member_id");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }
  
  //마지막 단계
  public String handleKakaoLogin(String socialType, String socialId, String email) {
    // 1단계에서 소셜 id 먼저확인
    String memberId = findMemberIdBySocial(socialType, socialId);
    if (memberId != null) { //가입이 되어있다면
      System.out.println("이미 카카오 가입이 되어있습니다.");
      return memberId;
    }
    
    //2. 카카오로 가입이 안되어 있다면 이메일을 2차적으로 확인
    memberId = findMemberIdByEmail(email);
    if (memberId != null) {
      System.out.println("기존 이메일이 있습니다. 연동이 필요");
      return memberId;
    }
    System.out.println("신규 회원입니다.");
    return null;
    
  }
  
  // db를 조회했을 떄 member가 없다면 insert
  public void insertSocial(String memberId, String socialType, String socialId) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    String sql = "insert into MEMBER_SOCIAL (MEMBER_ID, SOCIAL_TYPE, SOCIAL_ID) values (?, ?, ?)";
    try {
      conn = ConnectionProvider.getConnection();
      //System.out.println(">>> SQL = " + sql);
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, memberId);
      pstmt.setString(2, socialType);
      pstmt.setString(3, socialId);
      pstmt.executeUpdate();
      
    } catch (SQLException e) {
      System.out.println("소셜 계정 INSERT 중 예외 발생: " + e.getMessage());
    } finally {
      ConnectionProvider.close(conn, pstmt); // 깔끔하게 한 줄로 자원 정리
    }
  }
  
  //member, member_social에도 등록이 없을 경우 새로운 멤버 등록
  public String registerNewSocialMember(String socialType, String socialId, String email,String nickName) {
    System.out.println(">>> [registerNewSocialMember] socialType = " + socialType);
    System.out.println(">>> [registerNewSocialMember] socialId = " + socialId);
    System.out.println(">>> [registerNewSocialMember] email = " + email);
    System.out.println(">>> [registerNewSocialMember] nickName = " + nickName);
    String memberId = "kakao_" + socialId;
    
    MemberVO newMember = new MemberVO();
    newMember.setMemberId(memberId);
    newMember.setMemberPassword("");
    newMember.setMemberName(nickName);
    newMember.setMemberEmail(email);
    newMember.setIsActive("Y");
    newMember.setRole("M");
    
    new MemberDAO().createMember(newMember);
    insertSocial(memberId, socialType, socialId); // 기존 insertSocial 재활용
    
    return memberId;
  }
}