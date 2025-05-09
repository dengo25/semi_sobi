package sobi.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import sobi.db.ConnectionProvider;
import sobi.vo.admin.ReviewVO;

public class ReviewDAO {
    public List<ReviewVO> getAllReviews() {
        List<ReviewVO> list = new ArrayList<ReviewVO>();
        String sql = "SELECT * FROM REVIEW ORDER BY REVIEW_ID DESC";

        try {
            Connection conn = ConnectionProvider.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
				list.add(new ReviewVO(rs.getInt("review_id"), rs.getString("member_id"), rs.getString("product_name"),
						rs.getString("review_title"), rs.getInt("rating"), rs.getInt("review_category_id"),
						rs.getString("content"), rs.getString("image_url"), rs.getTimestamp("created_at"),
						rs.getTimestamp("updated_at"), rs.getTimestamp("notice_delete_date"),
						rs.getString("is_deleted"), rs.getString("confirmed")));
            }
            ConnectionProvider.close(conn, pstmt, rs);
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        }
        return list;
    }
}
