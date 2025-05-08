package sobi.review.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import sobi.db.ConnectionProvider;
import sobi.review.vo.ReviewReportVO;

public class ReviewReportDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    // 후기 신고 등록 (MySQL: REVIEW_REPORT_ID는 AUTO_INCREMENT)
    public int insertReport(ReviewReportVO vo) {
        int result = 0;
        String sql = "INSERT INTO REVIEW_REPORT(REVIEW_ID, REVIEW_REPORTING_USER_ID, REVIEW_REPORTED_USER_ID, REPORT_CATEGORY, REVIEW_REPORT_DETAIL) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, vo.getReviewId());
            pstmt.setString(2, vo.getReportingUserId());
            pstmt.setString(3, vo.getReportedUserId());
            pstmt.setString(4, vo.getReportCategory());
            pstmt.setString(5, vo.getReportDetail());

            result = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return result;
    }

    // 특정 리뷰에 대한 신고 목록 조회
    public List<ReviewReportVO> getReportsByReviewId(int reviewId) {
        List<ReviewReportVO> list = new ArrayList<>();
        String sql = "SELECT * FROM REVIEW_REPORT WHERE REVIEW_ID = ? ORDER BY REVIEW_REPORT_ID DESC";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ReviewReportVO vo = new ReviewReportVO();
                vo.setReviewReportId(rs.getInt("REVIEW_REPORT_ID"));
                vo.setReviewId(rs.getInt("REVIEW_ID"));
                vo.setReportingUserId(rs.getString("REVIEW_REPORTING_USER_ID"));
                vo.setReportedUserId(rs.getString("REVIEW_REPORTED_USER_ID"));
                vo.setReportCategory(rs.getString("REPORT_CATEGORY"));
                vo.setReportDetail(rs.getString("REVIEW_REPORT_DETAIL"));
                list.add(vo);
            }
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return list;
    }

    // 전체 신고 목록 조회
    public List<ReviewReportVO> getAllReports() {
        List<ReviewReportVO> list = new ArrayList<>();
        String sql = "SELECT * FROM REVIEW_REPORT ORDER BY REVIEW_REPORT_ID DESC";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ReviewReportVO vo = new ReviewReportVO();
                vo.setReviewReportId(rs.getInt("REVIEW_REPORT_ID"));
                vo.setReviewId(rs.getInt("REVIEW_ID"));
                vo.setReportingUserId(rs.getString("REVIEW_REPORTING_USER_ID"));
                vo.setReportedUserId(rs.getString("REVIEW_REPORTED_USER_ID"));
                vo.setReportCategory(rs.getString("REPORT_CATEGORY"));
                vo.setReportDetail(rs.getString("REVIEW_REPORT_DETAIL"));
                list.add(vo);
            }
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return list;
    }

    // 신고 삭제
    public int deleteReport(int reportId) {
        int result = 0;
        String sql = "DELETE FROM REVIEW_REPORT WHERE REVIEW_REPORT_ID = ?";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reportId);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }

        return result;
    }

    // 중복 신고 여부 확인
    public boolean hasUserReported(int reviewId, String reportingUserId) {
        boolean exists = false;
        String sql = "SELECT COUNT(*) FROM REVIEW_REPORT WHERE REVIEW_ID = ? AND REVIEW_REPORTING_USER_ID = ?";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewId);
            pstmt.setString(2, reportingUserId);
            rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                exists = true;
            }
        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        } finally {
            ConnectionProvider.close(conn, pstmt);
        }
        return exists;
    }
} 
