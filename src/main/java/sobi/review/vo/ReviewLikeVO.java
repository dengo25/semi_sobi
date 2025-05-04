package sobi.review.vo;

public class ReviewLikeVO {
	private int reviewLikeId;
	private int reviewId;
	private String memberId;
	
	public ReviewLikeVO(int reviewLikeId, int reviewId, String memberId) {
		this.reviewLikeId = reviewLikeId;
		this.reviewId = reviewId;
		this.memberId = memberId;
	}
	public ReviewLikeVO() {
		
	}
	public int getReviewLikeId() {
		return reviewLikeId;
	}
	public void setReviewLikeId(int reviewLikeId) {
		this.reviewLikeId = reviewLikeId;
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
	
	
}
