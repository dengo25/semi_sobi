package s3.util;

import java.sql.Timestamp;


public class ReviewVO {
  private int reviewId;
  private String memberId;
  private String productName;
  private String reviewTitle;
  private int rating;
  private int reviewCategoryId;
  private String content;
  private String imageUrl;
  private Timestamp createdAt;
  private Timestamp updatedAt;
  private Timestamp noticeDeleteDate;
  private String isDeleted;
  private String confirmed;
  
  // --- Getters and Setters ---
  
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
  
  public String getReviewTitle() {
    return reviewTitle;
  }
  
  public void setReviewTitle(String reviewTitle) {
    this.reviewTitle = reviewTitle;
  }
  
  public int getRating() {
    return rating;
  }
  
  public void setRating(int rating) {
    this.rating = rating;
  }
  
  public int getReviewCategoryId() {
    return reviewCategoryId;
  }
  
  public void setReviewCategoryId(int reviewCategoryId) {
    this.reviewCategoryId = reviewCategoryId;
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
}