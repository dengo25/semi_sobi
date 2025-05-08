package sobi.vo.review;

import java.sql.Timestamp;

public class ReviewImageVO {
  private int imageNumber;
  private int reviewId;
  private String fileUrl;
  private Timestamp uploadTime;
  private String originalFileName;
  private String fileType;
  
  public int getImageNumber() { return imageNumber; }
  public void setImageNumber(int imageNumber) { this.imageNumber = imageNumber; }
  
  public int getReviewId() { return reviewId; }
  public void setReviewId(int reviewId) { this.reviewId = reviewId; }
  
  public String getFileUrl() { return fileUrl; }
  public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
  
  public Timestamp getUploadTime() { return uploadTime; }
  public void setUploadTime(Timestamp uploadTime) { this.uploadTime = uploadTime; }
  
  public String getOriginalFileName() { return originalFileName; }
  public void setOriginalFileName(String originalFileName) { this.originalFileName = originalFileName; }
  
  public String getFileType() { return fileType; }
  public void setFileType(String fileType) { this.fileType = fileType; }
}