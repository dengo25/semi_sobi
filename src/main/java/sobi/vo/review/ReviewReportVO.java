package sobi.vo.review;

public class ReviewReportVO {
	private int reviewReportId;
	private int reviewId;
	private String reportingUserId; // 신고하는 유저 ID
	private String reportedUserId;  // 신고당하는 유저 ID
	private String reportCategory;
	private String reportDetail;
	
	public ReviewReportVO(int reviewReportId, int reviewId, String reportingUserId, String reportedUserId,
			String reportCategory, String reportDetail) {
		this.reviewReportId = reviewReportId;
		this.reviewId = reviewId;
		this.reportingUserId = reportingUserId;
		this.reportedUserId = reportedUserId;
		this.reportCategory = reportCategory;
		this.reportDetail = reportDetail;
	}

	public ReviewReportVO() {

	}

	public int getReviewReportId() {
		return reviewReportId;
	}

	public void setReviewReportId(int reviewReportId) {
		this.reviewReportId = reviewReportId;
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
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
		return "ReviewReportVO [reviewReportId=" + reviewReportId + ", reviewId=" + reviewId + ", reportingUserId="
				+ reportingUserId + ", reportedUserId=" + reportedUserId + ", reportCategory=" + reportCategory
				+ ", reportDetail=" + reportDetail + "]";
	}
	
	
}
