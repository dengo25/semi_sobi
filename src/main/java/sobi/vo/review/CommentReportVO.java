package sobi.vo.review;

public class CommentReportVO {
	private int commentReportId;
	private int commentId;
	private String reportingUserId; // 신고하는 User Id
	private String reportedUserId;  // 신고 당하는 User Id
	private String reportCategory;
	private String reportDetail;
	
	public CommentReportVO(int commentReportId, int commentId, String reportingUserId, String reportedUserId,
			String reportCategory, String reportDetail) {
		this.commentReportId = commentReportId;
		this.commentId = commentId;
		this.reportingUserId = reportingUserId;
		this.reportedUserId = reportedUserId;
		this.reportCategory = reportCategory;
		this.reportDetail = reportDetail;
	}

	public CommentReportVO() {
		
	}

	public int getCommentReportId() {
		return commentReportId;
	}

	public void setCommentReportId(int commentReportId) {
		this.commentReportId = commentReportId;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getReportingUserId() {
		return reportingUserId;
	}

	public void setReportingUserId(String reportingUserId) {
		this.reportingUserId = reportingUserId;
	}

	public String getReportedUserId() {
		return reportedUserId;
	}

	public void setReportedUserId(String reportedUserId) {
		this.reportedUserId = reportedUserId;
	}

	public String getReportCategory() {
		return reportCategory;
	}

	public void setReportCategory(String reportCategory) {
		this.reportCategory = reportCategory;
	}

	public String getReportDetail() {
		return reportDetail;
	}

	public void setReportDetail(String reportDetail) {
		this.reportDetail = reportDetail;
	}

	@Override
	public String toString() {
		return "CommentReportVO [commentReportId=" + commentReportId + ", commentId=" + commentId + ", reportingUserId="
				+ reportingUserId + ", reportedUserId=" + reportedUserId + ", reportCategory=" + reportCategory
				+ ", reportDetail=" + reportDetail + "]";
	}
	
	
}
