package sobi.vo.review;

public class ReviewVO {
  
  private int reviewId;
  private String memberId;
  private String productName;
  private String reviewTitle;
  private int rating;
  private int reviewCategoryId;
  private String content;
  private String imageURL;
  private String createdAt;
  private String updatedAt;
  private String noticeDeleteDate;
  private String isDeleted;
  private String confirmed;
  
  //순번 표시용으로. JSP 번호 출력시 사용
  private int rowNo;
  
  public ReviewVO() {
  }
  
  public ReviewVO(int reviewId, String memberId, String productName, String reviewTitle, int rating, int reviewCategoryId, String content, String imageURL, String createdAt,
                  String updatedAt, String noticeDeleteDate, String isDeleted, String confirmed, int rowNo) {
    this.reviewId = reviewId;
    this.memberId = memberId;
    this.productName = productName;
    this.reviewTitle = reviewTitle;
    this.rating = rating;
    this.reviewCategoryId = reviewCategoryId;
    this.content = content;
    this.imageURL = imageURL;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.noticeDeleteDate = noticeDeleteDate;
    this.isDeleted = isDeleted;
    this.confirmed = confirmed;
    this.rowNo = rowNo;
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
  
  public String getImageURL() {
    return imageURL;
  }
  
  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }
  
  public String getCreatedAt() {
    return createdAt;
  }
  
  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }
  
  public String getUpdatedAt() {
    return updatedAt;
  }
  
  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }
  
  public String getNoticeDeleteDate() {
    return noticeDeleteDate;
  }
  
  public void setNoticeDeleteDate(String noticeDeleteDate) {
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
  
  public int getRowNo() {
    return rowNo;
  }
  
  public void setRowNo(int rowNo) {
    this.rowNo = rowNo;
  }
  
  // 2025-05-12 왕 : html 태그 붙은 텍스트 가공 메서드 
  public String getPlainTextContent() {
      return content != null ? content.replaceAll("<[^>]*>", "") : "";
  }
}
