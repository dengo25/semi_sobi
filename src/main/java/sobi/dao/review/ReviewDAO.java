package sobi.dao.review;

import sobi.db.ConnectionProvider;
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
    
    String sql = "INSERT INTO REVIEW (MEMBER_ID, PRODUCT_NAME, REVIEW_TITLE, RATING, REVIEW_CATEGORY_ID, CONTENT, IS_DELETED, CONFIRMED) " +
        "VALUES (?, ?, ?, ?, ?, ?, 'N', 'N')";
    
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
}
