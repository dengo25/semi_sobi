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
        String sql = "SELECT * FROM REVIEW WHERE IS_DELETED = 'N' ORDER BY REVIEW_ID DESC";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ReviewVO vo = extractReviewVO(rs);
                reviewList.add(vo);
            }
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return reviewList;
    }

    // 후기 등록
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

    // 카테고리별 리뷰 조회
    public List<ReviewVO> getReviewByCategory(int categoryId) {
        List<ReviewVO> list = new ArrayList<>();
        String sql = "SELECT * FROM REVIEW WHERE REVIEW_CATEGORY_ID = ? AND IS_DELETED = 'N' ORDER BY REVIEW_ID DESC";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, categoryId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ReviewVO vo = extractReviewVO(rs);
                list.add(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }
        return list;
    }

    // 좋아요 순으로 리뷰 조회
    public List<ReviewVO> getReviewOrderByLikes() {
        List<ReviewVO> list = new ArrayList<>();
        String sql = "SELECT R.*, COUNT(L.REVIEW_ID) AS LIKE_COUNT " +
                     "FROM REVIEW R LEFT JOIN REVIEW_LIKE L ON R.REVIEW_ID = L.REVIEW_ID " +
                     "WHERE R.IS_DELETED = 'N' " +
                     "GROUP BY R.REVIEW_ID " +
                     "ORDER BY LIKE_COUNT DESC";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ReviewVO vo = extractReviewVO(rs);
                vo.setLikeCount(rs.getInt("LIKE_COUNT"));
                list.add(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return list;
    }

    // 키워드 검색 (제목, 작성자 ID, 내용)
    public List<ReviewVO> searchReview(String keyword) {
        List<ReviewVO> list = new ArrayList<>();
        String sql = "SELECT * FROM REVIEW WHERE " +
                     "(REVIEW_TITLE LIKE ? OR MEMBER_ID LIKE ? OR CONTENT LIKE ?) " +
                     "AND IS_DELETED = 'N' ORDER BY REVIEW_ID DESC";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            String like = "%" + keyword + "%";
            pstmt.setString(1, like);
            pstmt.setString(2, like);
            pstmt.setString(3, like);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ReviewVO vo = extractReviewVO(rs);
                list.add(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return list;
    }
    
    // 리뷰번호로 후기를 불러오는 메소드
    public ReviewVO getReviewById(int reviewId) {
        ReviewVO vo = null;
        String sql = "SELECT * FROM REVIEW WHERE REVIEW_ID = ? AND IS_DELETED = 'N'";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                vo = extractReviewVO(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return vo;
    }
    
    // 운영자가 블라인드 처리할 수 있는 메서드
    public int setReviewBlindStatus(int reviewId, boolean blind) {
        int result = 0;
        String sql;

        if (blind) {
            sql = "UPDATE REVIEW SET IS_DELETED = 'Y', NOTICE_DELETE_DATE = NOW() WHERE REVIEW_ID = ?";
        } else {
            sql = "UPDATE REVIEW SET IS_DELETED = 'N', NOTICE_DELETE_DATE = NULL WHERE REVIEW_ID = ?";
        }

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewId);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return result;
    }

    // 공통 ReviewVO 추출 메서드
    private ReviewVO extractReviewVO(ResultSet rs) throws SQLException {
        ReviewVO vo = new ReviewVO();
        vo.setReviewId(rs.getInt("REVIEW_ID"));
        vo.setMemberId(rs.getString("MEMBER_ID"));
        vo.setProductName(rs.getString("PRODUCT_NAME"));
        vo.setTitle(rs.getString("REVIEW_TITLE"));
        vo.setRating(rs.getInt("RATING"));
        vo.setCategoryId(rs.getInt("REVIEW_CATEGORY_ID"));
        vo.setContent(rs.getString("CONTENT"));
        vo.setImageUrl(rs.getString("IMAGE_URL"));
        vo.setCreatedAt(rs.getTimestamp("CREATED_AT"));
        vo.setUpdatedAt(rs.getTimestamp("UPDATED_AT"));
        vo.setNoticeDeleteDate(rs.getTimestamp("NOTICE_DELETE_DATE"));
        vo.setIsDeleted(rs.getString("IS_DELETED"));
        vo.setConfirmed(rs.getString("CONFIRMED"));
        return vo;
    }
}