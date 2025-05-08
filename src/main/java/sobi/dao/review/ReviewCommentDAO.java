package sobi.dao.review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import sobi.db.ConnectionProvider;
import sobi.vo.review.ReviewCommentVO;


public class ReviewCommentDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    // 후기에 달린 모든 댓글 조회
    public List<ReviewCommentVO> getCommentsByReviewId(int reviewId) {
        List<ReviewCommentVO> list = new ArrayList<>();
        String sql = "SELECT * FROM REVIEW_COMMENT WHERE REVIEW_ID = ? ORDER BY CREATED_AT ASC";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ReviewCommentVO vo = new ReviewCommentVO();
                vo.setCommentId(rs.getInt("comment_id"));
                vo.setReviewId(rs.getInt("review_id"));
                vo.setMemberId(rs.getString("member_id"));
                vo.setCommentContent(rs.getString("comment_content"));
                vo.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(vo);
            }
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return list;
    }

    // 댓글 등록 (MySQL: COMMENT_ID는 AUTO_INCREMENT)
    public int insertComment(ReviewCommentVO vo) {
        int result = 0;
        String sql = "INSERT INTO REVIEW_COMMENT(REVIEW_ID, MEMBER_ID, COMMENT_CONTENT, CREATED_AT) VALUES (?, ?, ?, NOW())";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, vo.getReviewId());
            pstmt.setString(2, vo.getMemberId());
            pstmt.setString(3, vo.getCommentContent());
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return result;
    }

    // 댓글 삭제
    public int deleteComment(int commentId) {
        int result = 0;
        String sql = "DELETE FROM REVIEW_COMMENT WHERE COMMENT_ID = ?";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, commentId);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return result;
    }

    // 후기에 달린 댓글 갯수 조회
    public int countCommentsByReviewId(int reviewId) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM REVIEW_COMMENT WHERE REVIEW_ID = ?";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return count;
    }
} 
