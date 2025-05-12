package sobi.dao.review;

import sobi.db.ConnectionProvider;
import sobi.vo.member.MemberVO;
import sobi.vo.review.ReviewImageVO;
import sobi.vo.review.ReviewVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReviewDAO {
  
  public List<ReviewVO> getAll(Map<String, Object> paramMap) {
    List<ReviewVO> list = new ArrayList<>();
    
    String keyword = "%" + paramMap.get("keyword") + "%";
    int pageNum = (int) paramMap.get("pageNum");
    int pageSize = (int) paramMap.get("pageSize");
    int offset = (pageNum - 1) * pageSize;
    
    String sql = "SELECT R.REVIEW_ID, R.MEMBER_ID, R.PRODUCT_NAME, R.REVIEW_TITLE, " +
        "R.RATING, R.REVIEW_CATEGORY_ID, C.REVIEW_CATEGORY_NAME, " +
        "R.CREATED_AT, R.CONFIRMED " +
        "FROM REVIEW R " +
        "LEFT JOIN CATEGORY C ON R.REVIEW_CATEGORY_ID = C.REVIEW_CATEGORY_ID " +
        "WHERE R.IS_DELETED = 'N' " +
        "AND (R.REVIEW_TITLE LIKE ? OR R.MEMBER_ID LIKE ?) " +
        "ORDER BY R.REVIEW_ID DESC LIMIT ?, ?";
    
    try (
        Connection conn = ConnectionProvider.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)
    ) {
      pstmt.setString(1, keyword);
      pstmt.setString(2, keyword);
      pstmt.setInt(3, offset);
      pstmt.setInt(4, pageSize);
      
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        ReviewVO vo = new ReviewVO();
        vo.setReviewId(rs.getInt("REVIEW_ID"));
        vo.setMemberId(rs.getString("MEMBER_ID"));
        vo.setProductName(rs.getString("PRODUCT_NAME"));
        vo.setReviewTitle(rs.getString("REVIEW_TITLE"));
        vo.setRating(rs.getInt("RATING"));
        vo.setReviewCategoryId(rs.getInt("REVIEW_CATEGORY_ID"));
        // vo.setCategoryName(rs.getString("REVIEW_CATEGORY_NAME"));
        vo.setCreatedAt(rs.getString("CREATED_AT"));
        vo.setConfirmed(rs.getString("CONFIRMED")); // 👈 여기가 중요
        list.add(vo);
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return list;
  }
  
  
  public int insertReview(ReviewVO review) {
    int generatedId = -1;
    
    String sql = "INSERT INTO REVIEW (MEMBER_ID, PRODUCT_NAME, REVIEW_TITLE, RATING, REVIEW_CATEGORY_ID, CONTENT, IMAGE_URL, IS_DELETED, CONFIRMED) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, 'N', 'N')";
    
    try (
        Connection conn = ConnectionProvider.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
    ) {
      pstmt.setString(1, review.getMemberId());
      pstmt.setString(2, review.getProductName());
      pstmt.setString(3, review.getReviewTitle());
      pstmt.setInt(4, review.getRating());
      pstmt.setInt(5, review.getReviewCategoryId());
      pstmt.setString(6, review.getContent());
      pstmt.setString(7, review.getImageURL());
      
      int rows = pstmt.executeUpdate();
      
      if (rows > 0) {
        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
          generatedId = rs.getInt(1); // 생성된 PK 반환
        }
      }
    } catch (Exception e) {
      System.out.println("insertReview() 오류: " + e.getMessage());
      e.printStackTrace();
    }
    
    return generatedId;
  }
  
  public int getCount(String keyword) {
    int count = 0;
    String sql = "SELECT COUNT(*) FROM REVIEW " +
        "WHERE IS_DELETED = 'N' AND (REVIEW_TITLE LIKE ? OR MEMBER_ID LIKE ?)";
    
    try (
        Connection conn = ConnectionProvider.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)
    ) {
      String likeKeyword = "%" + keyword + "%";
      pstmt.setString(1, likeKeyword);
      pstmt.setString(2, likeKeyword);
      
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        count = rs.getInt(1);
      }
    } catch (Exception e) {
      System.out.println("getCount() 오류: " + e.getMessage());
      e.printStackTrace();
    }
    
    return count;
  }
  
  public void updateConfirmed(int reviewId) {
    String sql = "UPDATE REVIEW SET CONFIRMED = 'Y' WHERE REVIEW_ID = ?";
    
    try (
        Connection conn = ConnectionProvider.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)
    ) {
      pstmt.setInt(1, reviewId);
      pstmt.executeUpdate();
    } catch (Exception e) {
      System.out.println("updateConfirmed 오류: " + e.getMessage());
      e.printStackTrace();
    }
  }
  
  
  //main에 던저주는 메서드
  public List<ReviewVO> getLatestReviews() {
    List<ReviewVO> list = new ArrayList<>();
    String sql = "SELECT REVIEW_ID, REVIEW_TITLE, MEMBER_ID, IMAGE_URL, CREATED_AT " +
        "FROM REVIEW " +
        "WHERE IS_DELETED = 'N' " +
        "ORDER BY CREATED_AT DESC " +
        "LIMIT 5";
    
    try (
        Connection conn = ConnectionProvider.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()
    ) {
      while (rs.next()) {
        ReviewVO vo = new ReviewVO();
        vo.setReviewId(rs.getInt("REVIEW_ID"));
        vo.setReviewTitle(rs.getString("REVIEW_TITLE"));
        vo.setMemberId(rs.getString("MEMBER_ID"));
        vo.setImageURL(rs.getString("IMAGE_URL"));  // 썸네일 이미지
        vo.setCreatedAt(rs.getString("CREATED_AT"));
        list.add(vo);
      }
    } catch (Exception e) {
      System.out.println("getLatestReviews 오류: " + e.getMessage());
      e.printStackTrace();
    }
    return list;
  }
  
  // 특정 리뷰 ID로 리뷰 상세 조회
  public ReviewVO getReviewsByReviewId(int reviewId) {
    ReviewVO vo = null;
    String sql = "SELECT REVIEW_ID, MEMBER_ID, PRODUCT_NAME, REVIEW_TITLE, RATING, REVIEW_CATEGORY_ID, CONTENT, IMAGE_URL, CREATED_AT, CONFIRMED " +
        "FROM REVIEW " +
        "WHERE IS_DELETED = 'N' AND REVIEW_ID = ?";
    
    try (
        Connection conn = ConnectionProvider.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)
    ) {
      pstmt.setInt(1, reviewId);
      ResultSet rs = pstmt.executeQuery();
      
      if (rs.next()) {
        vo = new ReviewVO();
        vo.setReviewId(rs.getInt("REVIEW_ID"));
        vo.setMemberId(rs.getString("MEMBER_ID"));
        vo.setProductName(rs.getString("PRODUCT_NAME"));
        vo.setReviewTitle(rs.getString("REVIEW_TITLE"));
        vo.setRating(rs.getInt("RATING"));
        vo.setReviewCategoryId(rs.getInt("REVIEW_CATEGORY_ID"));
        vo.setContent(rs.getString("CONTENT"));
        vo.setImageURL(rs.getString("IMAGE_URL"));
        vo.setCreatedAt(rs.getString("CREATED_AT"));
        vo.setConfirmed(rs.getString("CONFIRMED"));
      }
    } catch (Exception e) {
      System.out.println("getReviewsByReviewId 오류: " + e.getMessage());
      e.printStackTrace();
    }
    
    return vo;
  }
  
  
  // edit에서 사용하는 메서드
  
  public int updateReview(ReviewVO review) {
    int result = 0;
    String sql = "UPDATE REVIEW SET " +
        "PRODUCT_NAME = ?, REVIEW_TITLE = ?, RATING = ?, " +
        "REVIEW_CATEGORY_ID = ?, CONTENT = ?, IMAGE_URL = ?, UPDATED_AT = NOW() " +
        "WHERE REVIEW_ID = ? AND IS_DELETED = 'N'";
    
    try (
        Connection conn = ConnectionProvider.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)
    ) {
      pstmt.setString(1, review.getProductName());
      pstmt.setString(2, review.getReviewTitle());
      pstmt.setInt(3, review.getRating());
      pstmt.setInt(4, review.getReviewCategoryId());
      pstmt.setString(5, review.getContent());
      pstmt.setString(6, review.getImageURL());
      pstmt.setInt(7, review.getReviewId());
      
      result = pstmt.executeUpdate();
    } catch (Exception e) {
      System.out.println("updateReview 오류: " + e.getMessage());
      e.printStackTrace();
    }
    
    return result;
  }
  
  
  /// 논리적 삭제
  public boolean logicalDelete(int reviewId, String memberId) {
    String sql = "UPDATE REVIEW SET IS_DELETED = 'Y' WHERE REVIEW_ID = ? AND MEMBER_ID = ?";
    
    try (
        Connection conn = ConnectionProvider.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)
    ) {
      pstmt.setInt(1, reviewId);
      pstmt.setString(2, memberId);
      int affected = pstmt.executeUpdate();
      return affected > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}