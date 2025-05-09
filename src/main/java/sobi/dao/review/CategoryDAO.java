package sobi.dao.review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import sobi.db.ConnectionProvider;
import sobi.vo.review.CategoryVO;


public class CategoryDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    // 전체 카테고리 조회
    public List<CategoryVO> getAllCategories() {
        List<CategoryVO> list = new ArrayList<>();
        String sql = "SELECT * FROM CATEGORY ORDER BY REVIEW_CATEGORY_ID";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CategoryVO vo = new CategoryVO();
                vo.setReviewCategoryId(rs.getInt("REVIEW_CATEGORY_ID"));
                vo.setReviewCategoryName(rs.getString("REVIEW_CATEGORY_NAME"));
                list.add(vo);
            }
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }
        return list;
    }

    // 카테고리 ID로 조회
    public CategoryVO getCategoryById(int reviewCategoryId) {
        CategoryVO vo = null;
        String sql = "SELECT * FROM CATEGORY WHERE REVIEW_CATEGORY_ID = ?";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewCategoryId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                vo = new CategoryVO();
                vo.setReviewCategoryId(rs.getInt("REVIEW_CATEGORY_ID"));
                vo.setReviewCategoryName(rs.getString("REVIEW_CATEGORY_NAME"));
            }
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return vo;
    }

    // 카테고리 추가 (MySQL: REVIEW_CATEGORY_ID는 AUTO_INCREMENT)
    public int insertCategory(CategoryVO vo) {
        int result = 0;
        String sql = "INSERT INTO CATEGORY(REVIEW_CATEGORY_NAME) VALUES (?)";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, vo.getReviewCategoryName());
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return result;
    }

    // 카테고리 수정
    public int updateCategory(CategoryVO vo) {
        int result = 0;
        String sql = "UPDATE CATEGORY SET REVIEW_CATEGORY_NAME = ? WHERE REVIEW_CATEGORY_ID = ?";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, vo.getReviewCategoryName());
            pstmt.setInt(2, vo.getReviewCategoryId());
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return result;
    }

    // 카테고리 삭제
    public int deleteCategory(int reviewCategoryId) {
        int result = 0;
        String sql = "DELETE FROM CATEGORY WHERE REVIEW_CATEGORY_ID = ?";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewCategoryId);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return result;
    }
}
