package sobi.s3.util;

import sobi.db.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {
  
  // 리뷰 등록 (insert) + 생성된 REVIEW_ID 반환
  public int insertReview(ReviewVO vo) {
    int generatedId = -1;
    String sql = "INSERT INTO REVIEW (MEMBER_ID, PRODUCT_NAME, REVIEW_TITLE, RATING, REVIEW_CATEGORY_ID, CONTENT, IMAGE_URL) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    try (Connection conn = ConnectionProvider.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      
      ps.setString(1, vo.getMemberId());
      ps.setString(2, vo.getProductName());
      ps.setString(3, vo.getReviewTitle());
      ps.setInt(4, vo.getRating());
      ps.setInt(5, vo.getReviewCategoryId());
      ps.setString(6, vo.getContent());
      ps.setString(7, vo.getImageUrl());
      
      ps.executeUpdate();
      
      ResultSet rs = ps.getGeneratedKeys();
      if (rs.next()) {
        generatedId = rs.getInt(1);
      }
      rs.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return generatedId;
  }
  
  // 전체 리뷰 목록 조회
  public List<ReviewVO> getAllReviews() {
    List<ReviewVO> list = new ArrayList<>();
    String sql = "SELECT * FROM REVIEW ORDER BY REVIEW_ID DESC";
    
    try (Connection conn = ConnectionProvider.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
      
      while (rs.next()) {
        ReviewVO vo = new ReviewVO();
        vo.setReviewId(rs.getInt("REVIEW_ID"));
        vo.setMemberId(rs.getString("MEMBER_ID"));
        vo.setProductName(rs.getString("PRODUCT_NAME"));
        vo.setReviewTitle(rs.getString("REVIEW_TITLE"));
        vo.setRating(rs.getInt("RATING"));
        vo.setReviewCategoryId(rs.getInt("REVIEW_CATEGORY_ID"));
        vo.setContent(rs.getString("CONTENT"));
        vo.setImageUrl(rs.getString("IMAGE_URL"));
        vo.setCreatedAt(rs.getTimestamp("CREATED_AT"));
        vo.setUpdatedAt(rs.getTimestamp("UPDATED_AT"));
        vo.setNoticeDeleteDate(rs.getTimestamp("NOTICE_DELETE_DATE"));
        vo.setIsDeleted(rs.getString("IS_DELETED"));
        vo.setConfirmed(rs.getString("CONFIRMED"));
        
        list.add(vo);
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return list;
  }
  
  // 특정 리뷰 상세 조회
  public ReviewVO getReviewById(int reviewId) {
    ReviewVO vo = null;
    String sql = "SELECT * FROM REVIEW WHERE REVIEW_ID = ?";
    
    try (Connection conn = ConnectionProvider.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
      
      ps.setInt(1, reviewId);
      
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          vo = new ReviewVO();
          vo.setReviewId(rs.getInt("REVIEW_ID"));
          vo.setMemberId(rs.getString("MEMBER_ID"));
          vo.setProductName(rs.getString("PRODUCT_NAME"));
          vo.setReviewTitle(rs.getString("REVIEW_TITLE"));
          vo.setRating(rs.getInt("RATING"));
          vo.setReviewCategoryId(rs.getInt("REVIEW_CATEGORY_ID"));
          vo.setContent(rs.getString("CONTENT"));
          vo.setImageUrl(rs.getString("IMAGE_URL"));
          vo.setCreatedAt(rs.getTimestamp("CREATED_AT"));
          vo.setUpdatedAt(rs.getTimestamp("UPDATED_AT"));
          vo.setNoticeDeleteDate(rs.getTimestamp("NOTICE_DELETE_DATE"));
          vo.setIsDeleted(rs.getString("IS_DELETED"));
          vo.setConfirmed(rs.getString("CONFIRMED"));
        }
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return vo;
  }
}