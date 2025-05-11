package sobi.dao.member;

import sobi.db.ConnectionProvider;
import sobi.vo.member.MemberSocialVO;
import sobi.vo.member.MemberVO;

import java.sql.*;
import java.util.Date;

public class MemberSocialDAO {
  
  /** 1. 소셜 ID로 MEMBER_ID 조회 */
  public String findMemberIdBySocial(String socialType, String socialId) {
    String sql = "SELECT MEMBER_ID FROM MEMBER_SOCIAL WHERE SOCIAL_TYPE = ? AND SOCIAL_ID = ?";
    try (Connection conn = ConnectionProvider.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, socialType);
      pstmt.setString(2, socialId);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) return rs.getString("member_id");
    } catch (SQLException e) {
      System.out.println("[findMemberIdBySocial] 오류: " + e.getMessage());
    }
    return null;
  }
  
  /** 2. 이메일로 MEMBER_ID 조회 */
  public String findMemberIdByEmail(String email) {
    String sql = "SELECT MEMBER_ID FROM MEMBER WHERE MEMBER_EMAIL = ?";
    try (Connection conn = ConnectionProvider.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, email);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) return rs.getString("member_id");
    } catch (SQLException e) {
      System.out.println("[findMemberIdByEmail] 오류: " + e.getMessage());
    }
    return null;
  }
  
  /** 3. 소셜 로그인 처리 공통 메서드 (KAKAO / NAVER 등) */
  public String handleSocialLogin(String socialType, String socialId, String email) {
    String memberId = findMemberIdBySocial(socialType, socialId);
    if (memberId != null) {
      System.out.println("[" + socialType + "] 기존 소셜 계정 연동됨");
      return memberId;
    }
    
    memberId = findMemberIdByEmail(email);
    if (memberId != null) {
      System.out.println("[" + socialType + "] 이메일로 기존 회원 존재");
      return memberId;
    }
    
    System.out.println("[" + socialType + "] 신규 회원입니다.");
    return null;
  }
  
  /** 4. 소셜 테이블에 연결 정보 INSERT (VO 기반으로 통일) */
  public void insertSocialVO(MemberSocialVO vo, String socialType) {
    String sql = "INSERT INTO MEMBER_SOCIAL (MEMBER_ID, SOCIAL_TYPE, SOCIAL_ID, LINKED_AT) VALUES (?, ?, ?, ?)";
    try (Connection conn = ConnectionProvider.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, vo.getMemberId());
      pstmt.setString(2, socialType);
      pstmt.setString(3, vo.getSocialId());
      pstmt.setTimestamp(4, new Timestamp(vo.getLinkedAt().getTime()));
      pstmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println("[insertSocialVO] 오류: " + e.getMessage());
    }
  }
  
  /** 5. 신규 소셜 회원 등록 (MEMBER + MEMBER_SOCIAL) */
  public MemberVO registerNewSocialMember(String socialType, String socialId, String email, String name, String birthday) {
    String memberId = socialType.toLowerCase() + "_" + socialId;
    
    MemberVO newMember = new MemberVO();
    newMember.setMemberId(memberId);
    newMember.setMemberPassword(""); // 소셜 계정은 비번 없음
    newMember.setMemberName(name);
    newMember.setMemberEmail(email);
    newMember.setMemberBirth(birthday);
    newMember.setIsActive("Y");
    newMember.setRole("M");
    
    new MemberDAO().createMember(newMember);
    
    MemberSocialVO socialVO = new MemberSocialVO();
    socialVO.setMemberId(memberId);
    socialVO.setSocialId(socialId);
    socialVO.setLinkedAt(new Date());
    
    insertSocialVO(socialVO, socialType);
    
    return newMember;
  }
}
