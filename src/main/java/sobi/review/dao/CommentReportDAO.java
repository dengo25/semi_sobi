package sobi.review.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import sobi.db.ConnectionProvider;
import sobi.review.vo.CommentReportVO;

public class CommentReportDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
		
	// 댓글 신고 등록
	public int insertReport(CommentReportVO vo) {
	    int result = 0;
	    String sql = "INSERT INTO COMMENT_REPORT "
	            + "(COMMENT_REPORT_ID, COMMENT_ID, COMMENT_REPORTING_USER_ID, "
	            + "COMMENT_REPORTED_USER_ID, REPORT_CATEGORY, COMMENT_REPORT_DETAIL) "
	            + "VALUES (COMMENT_REPORT_SEQ.NEXTVAL, ?, ?, ?, ?, ?)";

	    try {
	        conn = ConnectionProvider.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, vo.getCommentId());
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
	
	
	// 특정 댓글에 대한 신고 목록 조회
	public List<CommentReportVO> getReportsByCommentId(int commentId){
		List<CommentReportVO> list = new ArrayList<>();
		String sql = "SELECT * FROM COMMENT_REPORT WHERE COMMENT_ID =?";
				
		try {
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, commentId);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				CommentReportVO vo = new CommentReportVO();
				vo.setCommentReportId(rs.getInt("COMMENT_REPORT_ID"));
				vo.setCommentId(rs.getInt("COMMENT_ID"));
				vo.setReportingUserId(rs.getString("COMMENT_REPORTING_USER_ID"));
				vo.setReportedUserId(rs.getString("COMMENT_REPORTED_USER_ID"));
				vo.setReportCategory(rs.getString("REPORT_CATEGORY"));
				vo.setReportDetail(rs.getString("COMMENT_REPORT_DETAIL"));
				
				list.add(vo);
			}
			
		} catch (Exception e) {
			System.out.println("예외발생: "+e.getMessage());
		} finally {
			ConnectionProvider.close(conn, pstmt);
		}
		return list;
	}
		
	// 신고 삭제 (관리자 기능 또는 테스트용)
    public int deleteReport(int reportId) {
        int result = 0;
        String sql = "DELETE FROM COMMENT_REPORT WHERE COMMENT_REPORT_ID = ?";

        try {
            conn = ConnectionProvider.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reportId);
            result = pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("예외 발생: " + e.getMessage());
        } finally {
        	ConnectionProvider.close(conn, pstmt);
        }

        return result;
    }
}
