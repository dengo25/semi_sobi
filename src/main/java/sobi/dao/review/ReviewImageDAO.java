package sobi.dao.review;

import sobi.db.ConnectionProvider;
import sobi.vo.review.ReviewImageVO;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ReviewImageDAO {
  
  public void insertReviewImage(ReviewImageVO image) {
    String sql = "INSERT INTO REVIEW_IMAGE (REVIEW_ID, FILE_URL, ORIGINAL_FILE_NAME, FILE_TYPE) " +
        "VALUES (?, ?, ?, ?)";
    
    try (
        Connection conn = ConnectionProvider.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
    ) {
      pstmt.setInt(1, image.getReviewId());
      pstmt.setString(2, image.getFileUrl());
      pstmt.setString(3, image.getOriginalFileName());
      pstmt.setString(4, image.getFileType());
      
      pstmt.executeUpdate();
    } catch (Exception e) {
      System.out.println("insertReviewImage() 오류: " + e.getMessage());
      e.printStackTrace();
    }
  }
}