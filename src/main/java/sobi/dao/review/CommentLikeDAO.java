package sobi.dao.review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import sobi.db.ConnectionProvider;
import sobi.vo.review.CommentLikeVO;


public class CommentLikeDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    // 댓글 좋아요 추가 (MySQL: COMMENT_LIKE_ID는 AUTO_INCREMENT)
    public int insertLike(CommentLikeVO vo) {
        int result = 0;
        String sql = "INSERT INTO COMMENT_LIKE(COMMENT_ID, MEMBER_ID) VALUES (?, ?)";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, vo.getCommentId());
            pstmt.setString(2, vo.getMemberId());
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return result;
    }

    // 댓글 좋아요 취소
    public int deleteLike(int commentId, String memberId) {
        int result = 0;
        String sql = "DELETE FROM COMMENT_LIKE WHERE COMMENT_ID = ? AND MEMBER_ID = ?";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, commentId);
            pstmt.setString(2, memberId);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return result;
    }

    // 댓글 좋아요 수 카운트
    public int countLikeByCommentId(int commentId) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM COMMENT_LIKE WHERE COMMENT_ID = ?";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, commentId);
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

    // 특정 사용자가 특정 댓글에 좋아요 눌렀는지 확인
    public boolean hasUserLiked(int commentId, String memberId) {
        boolean liked = false;
        String sql = "SELECT COUNT(*) FROM COMMENT_LIKE WHERE COMMENT_ID = ? AND MEMBER_ID = ?";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, commentId);
            pstmt.setString(2, memberId);
            rs = pstmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                liked = true;
            }
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return liked;
    }
} 