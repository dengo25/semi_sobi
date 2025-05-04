package sobi.review.vo;

import java.sql.Timestamp;

public class ReviewCommentVO {
	private int commentId;
	private int reviewId;
	private String memberId;
	private String commentContent;
	private Timestamp createdAt;
	
	public ReviewCommentVO(int commentId, int reviewId, String memberId, String commentContent, Timestamp createdAt) {
		this.commentId = commentId;
		this.reviewId = reviewId;
		this.memberId = memberId;
		this.commentContent = commentContent;
		this.createdAt = createdAt;
	}
	public ReviewCommentVO() {
		
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		commentContent = commentContent;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
}
