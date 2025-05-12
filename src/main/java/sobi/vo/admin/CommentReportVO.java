package sobi.vo.admin;

public class CommentReportVO {
	private int commentReportId;
	private int commentId;
	private String commentReportingUserId;
	private String commentReportedUserId;
	private String reportCategory;
	private String commentReportDetail;	
	
	public CommentReportVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CommentReportVO(int commentReportId, int commentId, String commentReportingUserId, String commentReportedUserId,
			String reportCategory, String commentReportDetail) {
		super();
		this.commentReportId = commentReportId;
		this.commentId = commentId;
		this.commentReportingUserId = commentReportingUserId;
		this.commentReportedUserId = commentReportedUserId;
		this.reportCategory = reportCategory;
		this.commentReportDetail = commentReportDetail;
	}
	public int getcommentReportId() {
		return commentReportId;
	}
	public void setcommentReportId(int commentReportId) {
		this.commentReportId = commentReportId;
	}
	public int getcommentId() {
		return commentId;
	}
	public void setcommentId(int commentId) {
		this.commentId = commentId;
	}
	public String getcommentReportingUserId() {
		return commentReportingUserId;
	}
	public void setcommentReportingUserId(String commentReportingUserId) {
		this.commentReportingUserId = commentReportingUserId;
	}
	public String getcommentReportedUserId() {
		return commentReportedUserId;
	}
	public void setcommentReportedUserId(String commentReportedUserId) {
		this.commentReportedUserId = commentReportedUserId;
	}
	public String getReportCategory() {
		return reportCategory;
	}
	public void setReportCategory(String reportCategory) {
		this.reportCategory = reportCategory;
	}
	public String getcommentReportDetail() {
		return commentReportDetail;
	}
	public void setcommentReportDetail(String commentReportDetail) {
		this.commentReportDetail = commentReportDetail;
	}
	
	
}
