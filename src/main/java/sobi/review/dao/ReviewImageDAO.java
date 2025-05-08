package sobi.review.dao;

import sobi.db.ConnectionProvider;
import sobi.review.vo.ReviewImageVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReviewImageDAO {

    // 리뷰 이미지 삽입 (MySQL: IMAGE_NUMBER는 AUTO_INCREMENT)
    public void insertImage(ReviewImageVO vo) {
        String sql = "INSERT INTO REVIEW_IMAGE (REVIEW_ID, FILE_URL, UPLOAD_TIME, ORIGINAL_FILE_NAME, FILE_TYPE) " +
                     "VALUES (?, ?, NOW(), ?, ?)";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, vo.getReviewId());
            ps.setString(2, vo.getFileUrl());
            ps.setString(3, vo.getOriginalFileName());
            ps.setString(4, vo.getFileType());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 특정 리뷰에 연결된 이미지 목록 조회
    public List<ReviewImageVO> getImagesByReviewId(int reviewId) {
        List<ReviewImageVO> list = new ArrayList<>();
        String sql = "SELECT * FROM REVIEW_IMAGE WHERE REVIEW_ID = ? ORDER BY IMAGE_NUMBER ASC";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reviewId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ReviewImageVO vo = new ReviewImageVO();
                    vo.setImageNumber(rs.getInt("IMAGE_NUMBER"));
                    vo.setReviewId(rs.getInt("REVIEW_ID"));
                    vo.setFileUrl(rs.getString("FILE_URL"));
                    vo.setUploadTime(rs.getTimestamp("UPLOAD_TIME"));
                    vo.setOriginalFileName(rs.getString("ORIGINAL_FILE_NAME"));
                    vo.setFileType(rs.getString("FILE_TYPE"));
                    list.add(vo);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}