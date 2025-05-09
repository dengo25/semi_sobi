package sobi.dao.review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import sobi.db.ConnectionProvider;
import sobi.vo.review.ReviewLikeVO;

public class ReviewLikeDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// 후기 좋아요 추가
	public int insertLike(ReviewLikeVO vo) {
		int result = 0;
		String sql = "INSERT INTO REVIEW_LIKE(REVIEW_ID, MEMBER_ID) VALUES (?, ?)";
		
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getReviewId());
			pstmt.setString(2, vo.getMemberId());
			result = pstmt.executeUpdate();
						
		} catch (Exception e) {
			System.out.println("예외발생: "+e.getMessage());
		} finally {
			ConnectionProvider.close(conn, pstmt);
		}
		
		return result;
	}
	
	// 후기 좋아요 삭제 (회원이 좋아요를 다시 누르면 취소)
	public int deleteLike(int reviewId, String memberId) {
		int result = 0;
		String sql = "DELETE FROM REVIEW_LIKE WHERE REVIEW_ID = ? AND MEMBER_ID = ?";
		
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reviewId);
			pstmt.setString(2, memberId);
			result = pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			System.out.println("예외발생: "+e.getMessage());
		} finally {
			ConnectionProvider.close(conn, pstmt);
		}
		
		return result;
	}
	
	// 특정 후기의 좋아요 수
	public int countLikesByReviewId(int reviewId) {
		int count = 0;
		String sql = "SELECT COUNT(*) FROM REVIEW_LIKE WHERE REVIEW_ID = ?";
		
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reviewId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println("예외발생: "+e.getMessage());
		} finally {
			ConnectionProvider.close(conn, pstmt);
		}
		
		return count;
	}
	
	// 사용자가 특정 후기에 좋아요 눌렀는지 확인
	public boolean hasUserLiked(int reviewId, String memberId) {
		boolean liked = false;
		String sql = "SELECT COUNT(*) FROM REVIEW_LIKE WHERE REVIEW_ID = ? AND MEMBER_ID = ?";
		
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reviewId);
			pstmt.setString(2, memberId);
			rs = pstmt.executeQuery();
			
			if(rs.next() && rs.getInt(1)>0) {
				liked = true;
			}
			
		} catch (Exception e) {
			System.out.println("예외발생: "+e.getMessage());
			
		} finally {
			ConnectionProvider.close(conn, pstmt);
		}
		
		return liked;
	}
}
