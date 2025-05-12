package sobi.dao.review;

import sobi.db.ConnectionProvider;
import sobi.vo.review.ReviewImageVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
  
  
  ///  edit에 사용
  public ArrayList<ReviewImageVO> getImagesByReviewId(int reviewId) {
    ArrayList<ReviewImageVO> list = new ArrayList<>();
    String sql = "SELECT * FROM REVIEW_IMAGE WHERE REVIEW_ID = ? ORDER BY IMAGE_NUMBER ASC";
    
    try (
        Connection conn = ConnectionProvider.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)
    ) {
      pstmt.setInt(1, reviewId);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        ReviewImageVO vo = new ReviewImageVO();
        vo.setReviewId(rs.getInt("REVIEW_ID"));
        vo.setImageNumber(rs.getInt("IMAGE_NUMBER"));
        vo.setFileUrl(rs.getString("FILE_URL"));
        vo.setUploadTime(rs.getTimestamp("UPLOAD_TIME").toString());
        vo.setOriginalFileName(rs.getString("ORIGINAL_FILE_NAME"));
        vo.setFileType(rs.getString("FILE_TYPE"));
        list.add(vo);
      }
    } catch (Exception e) {
      System.out.println("getImagesByReviewId 오류: " + e.getMessage());
      e.printStackTrace();
    }
    
    return list;
  }
  
  
  
  public int deleteImages(List<String> fileUrls) {
    if (fileUrls == null || fileUrls.isEmpty()) return 0;
    
    StringBuilder sql = new StringBuilder("DELETE FROM REVIEW_IMAGE WHERE FILE_URL IN (");
    for (int i = 0; i < fileUrls.size(); i++) {
      sql.append("?");
      if (i < fileUrls.size() - 1) sql.append(", ");
    }
    sql.append(")");
    
    int result = 0;
    try (
        Connection conn = ConnectionProvider.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql.toString())
    ) {
      for (int i = 0; i < fileUrls.size(); i++) {
        pstmt.setString(i + 1, fileUrls.get(i));
      }
      result = pstmt.executeUpdate();
    } catch (Exception e) {
      System.out.println("deleteImages 오류: " + e.getMessage());
      e.printStackTrace();
    }
    
    return result;
  }
}