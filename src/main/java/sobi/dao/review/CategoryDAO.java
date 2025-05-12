package sobi.dao.review;

import sobi.db.ConnectionProvider;
import sobi.vo.review.CategoryVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
  
  public List<CategoryVO> getAllCategories() {
    List<CategoryVO> list = new ArrayList<>();
    
    String sql = "SELECT REVIEW_CATEGORY_ID, REVIEW_CATEGORY_NAME FROM CATEGORY ORDER BY REVIEW_CATEGORY_ID ASC";
    
    try (
        Connection conn = ConnectionProvider.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
    ) {
      while (rs.next()) {
        CategoryVO vo = new CategoryVO();
        vo.setReviewCategoryId(rs.getInt("REVIEW_CATEGORY_ID"));
        vo.setReviewCategoryName(rs.getString("REVIEW_CATEGORY_NAME"));
        list.add(vo);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return list;
  }
}
