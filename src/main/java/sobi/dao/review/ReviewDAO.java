package sobi.dao.review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import sobi.db.ConnectionProvider;
import sobi.vo.review.ReviewVO;

public class ReviewDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    // 후기 전체 조회
    public List<ReviewVO> getAllReviews() {
        List<ReviewVO> reviewList = new ArrayList<>();
        String sql = "SELECT * FROM REVIEW ORDER BY REVIEW_ID DESC";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ReviewVO vo = new ReviewVO();
                vo.setReviewId(rs.getInt("review_id"));
                vo.setMemberId(rs.getString("member_id"));
                vo.setProductName(rs.getString("product_name"));
                vo.setTitle(rs.getString("review_title"));
                vo.setRating(rs.getInt("rating"));
                vo.setCategoryId(rs.getInt("review_category_id"));
                vo.setContent(rs.getString("content"));
                vo.setImageUrl(rs.getString("image_url"));
                vo.setCreatedAt(rs.getTimestamp("created_at"));
                vo.setUpdatedAt(rs.getTimestamp("updated_at"));
                vo.setNoticeDeleteDate(rs.getTimestamp("notice_delete_date"));
                vo.setIsDeleted(rs.getString("is_deleted"));
                vo.setConfirmed(rs.getString("confirmed"));
                reviewList.add(vo);
            }
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return reviewList;
    }

    // 후기 등록 후 생성된 리뷰 ID 반환
    public int insertReview(ReviewVO vo) {
        int generatedId = 0;
        String sql = "INSERT INTO REVIEW(MEMBER_ID, PRODUCT_NAME, REVIEW_TITLE, RATING, REVIEW_CATEGORY_ID, CONTENT, IMAGE_URL, CREATED_AT, UPDATED_AT, IS_DELETED, CONFIRMED) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, NOW(), NOW(), 'N', 'N')";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, vo.getMemberId());
            pstmt.setString(2, vo.getProductName());
            pstmt.setString(3, vo.getTitle());
            pstmt.setInt(4, vo.getRating());
            pstmt.setInt(5, vo.getCategoryId());
            pstmt.setString(6, vo.getContent());
            pstmt.setString(7, vo.getImageUrl());

            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return generatedId;
    }

    // 후기 수정
    public int updateReview(ReviewVO vo) {
        int result = 0;
        String sql = "UPDATE REVIEW SET PRODUCT_NAME=?, REVIEW_TITLE=?, RATING=?, REVIEW_CATEGORY_ID=?, CONTENT=?, IMAGE_URL=?, UPDATED_AT=NOW() WHERE REVIEW_ID=?";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, vo.getProductName());
            pstmt.setString(2, vo.getTitle());
            pstmt.setInt(3, vo.getRating());
            pstmt.setInt(4, vo.getCategoryId());
            pstmt.setString(5, vo.getContent());
            pstmt.setString(6, vo.getImageUrl());
            pstmt.setInt(7, vo.getReviewId());

            result = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return result;
    }

    // 후기 삭제
    public int deleteReview(int reviewId) {
        int result = 0;
        String sql = "DELETE FROM REVIEW WHERE REVIEW_ID = ?";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewId);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("예외처리: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return result;
    }
}
