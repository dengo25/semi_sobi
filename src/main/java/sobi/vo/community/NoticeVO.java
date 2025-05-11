package sobi.vo.community;

import java.sql.Date;

public class NoticeVO {
	private	int rowNo;
	private	int noticeNo;
	private	String noticeTitle;
	private	String noticeContent;	
	private	int count;
	private	Date noticeCreateDate;	
	private	Date noticeEditDate;
	private	Date notieceDeleteDate;
	private	String isDeleted;
	private	String isVisible;
	private	int noticeImageNumber;
	
	public NoticeVO() {
		super();
	}

	public NoticeVO(int rowNo, int noticeNo, String noticeTitle, String noticeContent, int count,
			Date noticeCreateDate, Date noticeEditDate, Date notieceDeleteDate, String isDeleted, String isVisible,
			int noticeImageNumber) {
		super();
		this.rowNo = rowNo;
		this.noticeNo = noticeNo;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.count = count;
		this.noticeCreateDate = noticeCreateDate;
		this.noticeEditDate = noticeEditDate;
		this.notieceDeleteDate = notieceDeleteDate;
		this.isDeleted = isDeleted;
		this.isVisible = isVisible;
		this.noticeImageNumber = noticeImageNumber;
	}

	public int getRowNo() {
		return rowNo;
	}

	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}

	public int getnoticeNo() {
		return noticeNo;
	}

	public void setnoticeNo(int noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Date getNoticeCreateDate() {
		return noticeCreateDate;
	}

	public void setNoticeCreateDate(Date noticeCreateDate) {
		this.noticeCreateDate = noticeCreateDate;
	}

	public Date getNoticeEditDate() {
		return noticeEditDate;
	}

	public void setNoticeEditDate(Date noticeEditDate) {
		this.noticeEditDate = noticeEditDate;
	}

	public Date getNotieceDeleteDate() {
		return notieceDeleteDate;
	}

	public void setNotieceDeleteDate(Date notieceDeleteDate) {
		this.notieceDeleteDate = notieceDeleteDate;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}

	public int getNoticeImageNumber() {
		return noticeImageNumber;
	}

	public void setNoticeImageNumber(int noticeImageNumber) {
		this.noticeImageNumber = noticeImageNumber;
	}
}
