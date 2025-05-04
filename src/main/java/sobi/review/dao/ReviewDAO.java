package sobi.review.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import sobi.review.vo.ReviewVO;

public class ReviewDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public void disconnect() {
		try {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (conn != null) conn.close();
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		}
	}

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
			disconnect();
		}

		return reviewList;
	}

	// 후기 등록
	public int insertReview(ReviewVO vo) {
		String sql = "INSERT INTO REVIEW(REVIEW_ID, MEMBER_ID, PRODUCT_NAME, REVIEW_TITLE, RATING, REVIEW_CATEGORY_ID, CONTENT, IMAGE_URL, CREATED_AT, UPDATED_AT, IS_DELETED, CONFIRMED) "
				   + "VALUES(REVIEW_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, SYSDATE, NULL, 'N', 'N')";

		int result = 0;

		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getMemberId());
			pstmt.setString(2, vo.getProductName());
			pstmt.setString(3, vo.getTitle());
			pstmt.setInt(4, vo.getRating());
			pstmt.setInt(5, vo.getCategoryId());
			pstmt.setString(6, vo.getContent());
			pstmt.setString(7, vo.getImageUrl());

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		} finally {
			disconnect();
		}

		return result;
	}

	// 후기 수정
	public int updateReview(ReviewVO vo) {
		int result = 0;
		String sql = "UPDATE REVIEW SET PRODUCT_NAME=?, REVIEW_TITLE=?, RATING=?, REVIEW_CATEGORY_ID=?, CONTENT=?, IMAGE_URL=?, UPDATED_AT=SYSDATE WHERE REVIEW_ID=?";

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
			disconnect();
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
			disconnect();
		}

		return result;
	}

	// 카테고리에 따른 후기 검색
	public List<ReviewVO> getReviewByCategory(int categoryId) {
		List<ReviewVO> reviewList = new ArrayList<>();
		String sql = "SELECT * FROM REVIEW WHERE REVIEW_CATEGORY_ID = ? ORDER BY REVIEW_ID DESC";

		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, categoryId);
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
			disconnect();
		}

		return reviewList;
	}

	// 좋아요 수 기준 정렬된 후기 검색
	public List<ReviewVO> getReviewOrderByLikes() {
		List<ReviewVO> reviewList = new ArrayList<>();
		String sql = "SELECT R.*, COUNT(RL.REVIEW_ID) AS LIKE_COUNT "
		           + "FROM REVIEW R "
		           + "LEFT JOIN REVIEW_LIKE RL ON R.REVIEW_ID = RL.REVIEW_ID "
		           + "GROUP BY R.REVIEW_ID, R.MEMBER_ID, R.PRODUCT_NAME, R.REVIEW_TITLE, R.RATING, R.REVIEW_CATEGORY_ID, R.CONTENT, R.IMAGE_URL, R.CREATED_AT, R.UPDATED_AT, R.NOTICE_DELETE_DATE, R.IS_DELETED, R.CONFIRMED "
		           + "ORDER BY LIKE_COUNT DESC";

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
				vo.setLikeCount(rs.getInt("like_count"));
				reviewList.add(vo);
			}
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		} finally {
			disconnect();
		}

		return reviewList;
	}

	// 검색어 기반 후기 검색
	public List<ReviewVO> searchReview(String keyword) {
		List<ReviewVO> list = new ArrayList<>();
		String sql = "SELECT * FROM REVIEW "
				   + "WHERE LOWER(REVIEW_TITLE) LIKE ? "
				   + "OR LOWER(MEMBER_ID) LIKE ? "
				   + "OR TO_CHAR(REVIEW_CATEGORY_ID) LIKE ? "
				   + "OR LOWER(CONTENT) LIKE ? "
				   + "ORDER BY REVIEW_ID DESC";

		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			String searchPattern = "%" + keyword.toLowerCase() + "%";
			for (int i = 1; i <= 4; i++) {
				pstmt.setString(i, searchPattern);
			}

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
				list.add(vo);
			}
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		} finally {
			disconnect();
		}

		return list;
	}
}
