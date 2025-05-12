package sobi.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sobi.db.ConnectionProvider;
import sobi.vo.admin.ReviewVO;

public class ReviewDAO {
	
	public List<ReviewVO> getReviewsByMemberId(String memberId) {
    List<ReviewVO> list = new ArrayList<ReviewVO>();
    String sql = "SELECT r.review_id, r.review_title, r.created_at, "
    		+ "(SELECT COUNT(*) FROM REVIEW_COMMENT c WHERE c.review_id = r.review_id) AS comment_count, "
    		+ "(SELECT COUNT(*) FROM REVIEW_REPORT rr WHERE rr.review_id = r.review_id) AS report_count "
    		+ "FROM REVIEW r "
    		+ "WHERE r.member_id = ?";
    try {
        Connection conn = ConnectionProvider.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, memberId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
			list.add(
					new ReviewVO(
							rs.getInt(1),
							rs.getString(2),
							rs.getDate(3),
							rs.getInt(4),
							rs.getInt(5)));
        }
        ConnectionProvider.close(conn, pstmt, rs);
    } catch (Exception e) {
        System.out.println("예외발생: " + e.getMessage());
    }
    return list;
}
    public ReviewVO getReviewsByReviewId(int reviewId) {
        ReviewVO review = new ReviewVO();
        String sql = "SELECT * FROM REVIEW WHERE review_id = ?";

        try {
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
				review = new ReviewVO(rs.getInt("review_id"), rs.getString("member_id"), rs.getString("product_name"),
						rs.getString("review_title"), rs.getInt("rating"), rs.getInt("review_category_id"),
						rs.getString("content"), rs.getString("image_url"), rs.getTimestamp("created_at"),
						rs.getTimestamp("updated_at"), rs.getTimestamp("notice_delete_date"),
						rs.getString("is_deleted"), rs.getString("confirmed"));
            }
            ConnectionProvider.close(conn, pstmt, rs);
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        }
        return review;
    }
	public int reviewCount() {
		int reviewCount = 0;
		String sql = "select IFNULL(count(*),0) from REVIEW";
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if(rs.next()) {
				reviewCount = rs.getInt(1);
			}
			ConnectionProvider.close(conn, stmt, rs);
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		}
		return reviewCount;
	}
    public List<ReviewVO> getAllReviews() {
        List<ReviewVO> list = new ArrayList<ReviewVO>();
        String sql = "SELECT * FROM REVIEW ORDER BY REVIEW_ID DESC";

        try {
            Connection conn = ConnectionProvider.getConnection();
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
				list.add(new ReviewVO(rs.getInt("review_id"), rs.getString("member_id"), rs.getString("product_name"),
						rs.getString("review_title"), rs.getInt("rating"), rs.getInt("review_category_id"),
						rs.getString("content"), rs.getString("image_url"), rs.getTimestamp("created_at"),
						rs.getTimestamp("updated_at"), rs.getTimestamp("notice_delete_date"),
						rs.getString("is_deleted"), rs.getString("confirmed")));
            }
            ConnectionProvider.close(conn, stmt, rs);
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        }
        return list;
    }
}
