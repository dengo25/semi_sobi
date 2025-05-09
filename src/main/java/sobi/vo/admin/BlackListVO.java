package sobi.vo.admin;

import java.util.Date;

public class BlackListVO {
	private int blackListNo;
	private String memberId;
	private String reportType;
	private Date updateAt;
	private String status;
	
	public BlackListVO() {
		super();
	}
	public BlackListVO(int blackListNo, String memberId, String reportType, Date updateAt, String status) {
		super();
		this.blackListNo = blackListNo;
		this.memberId = memberId;
		this.reportType = reportType;
		this.updateAt = updateAt;
		this.status = status;
	}
	public int getBlackListNo() {
		return blackListNo;
	}
	public void setBlackListNo(int blackListNo) {
		this.blackListNo = blackListNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setupdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
}
