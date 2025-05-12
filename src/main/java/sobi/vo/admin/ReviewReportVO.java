package sobi.vo.admin;

public class ReviewReportVO {
	private int reviewReportId;
	private int reviewId;
	private String reviewReportingUserId;
	private String reviewReportedUserId;
	private String reportCategory;
	private String reviewReportDetail;	
	
	public ReviewReportVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReviewReportVO(int reviewReportId, int reviewId, String reviewReportingUserId, String reviewReportedUserId,
			String reportCategory, String reviewReportDetail) {
		super();
		this.reviewReportId = reviewReportId;
		this.reviewId = reviewId;
		this.reviewReportingUserId = reviewReportingUserId;
		this.reviewReportedUserId = reviewReportedUserId;
		this.reportCategory = reportCategory;
		this.reviewReportDetail = reviewReportDetail;
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
	public String getReviewReportingUserId() {
		return reviewReportingUserId;
	}
	public void setReviewReportingUserId(String reviewReportingUserId) {
		this.reviewReportingUserId = reviewReportingUserId;
	}
	public String getReviewReportedUserId() {
		return reviewReportedUserId;
	}
	public void setReviewReportedUserId(String reviewReportedUserId) {
		this.reviewReportedUserId = reviewReportedUserId;
	}
	public String getReportCategory() {
		return reportCategory;
	}
	public void setReportCategory(String reportCategory) {
		this.reportCategory = reportCategory;
	}
	public String getreviewReportDetail() {
		return reviewReportDetail;
	}
	public void setreviewReportDetail(String reviewReportDetail) {
		this.reviewReportDetail = reviewReportDetail;
	}
	
	
}
