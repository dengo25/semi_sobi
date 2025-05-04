package sobi.review.vo;

import java.sql.Timestamp;

public class ReviewVO {
	private int reviewId;
	private String memberId;
	private String productName;
	private String title;
	private int rating;
	private int categoryId;
	private String content;
	private String imageUrl;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private Timestamp noticeDeleteDate;
	private String isDeleted;
	private String confirmed;

	private int likeCount; // 좋아요 수

	public ReviewVO() {
		super();
	}

	public ReviewVO(int reviewId, String memberId, String productName, String title, int rating, int categoryId,
	                String content, String imageUrl, Timestamp createdAt, Timestamp updatedAt,
	                Timestamp noticeDeleteDate, String isDeleted, String confirmed) {
		this.reviewId = reviewId;
		this.memberId = memberId;
		this.productName = productName;
		this.title = title;
		this.rating = rating;
		this.categoryId = categoryId;
		this.content = content;
		this.imageUrl = imageUrl;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.noticeDeleteDate = noticeDeleteDate;
		this.isDeleted = isDeleted;
		this.confirmed = confirmed;
	}

	// Getter & Setter

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

	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Timestamp getNoticeDeleteDate() {
		return noticeDeleteDate;
	}
	public void setNoticeDeleteDate(Timestamp noticeDeleteDate) {
		this.noticeDeleteDate = noticeDeleteDate;
	}

	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}

	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
}