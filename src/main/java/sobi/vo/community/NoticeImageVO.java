package sobi.vo.community;

import java.sql.Date;

public class NoticeImageVO {
	private	int imageNumber;
	private	String fileUrl;
	private	Date uploadTime;
	private	String originalFileName;
	private	String fileType;
	
	public NoticeImageVO() {
		super();
	}

	public NoticeImageVO(int imageNumber, String fileUrl, Date uploadTime, String originalFileName, String fileType) {
		super();
		this.imageNumber = imageNumber;
		this.fileUrl = fileUrl;
		this.uploadTime = uploadTime;
		this.originalFileName = originalFileName;
		this.fileType = fileType;
	}
	
	public int getImageNumber() {
		return imageNumber;
	}
	public void setImageNumber(int imageNumber) {
		this.imageNumber = imageNumber;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
