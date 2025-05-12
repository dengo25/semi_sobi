package sobi.vo.admin;

import java.util.Date;

public class BlackListHistoryVO {
	private int blackListHistoryNo;
	private int blackListNo;
	private String memberId;
	private String detail;
	private Date historyCreatedAt;
	
	public BlackListHistoryVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BlackListHistoryVO(int blackListHistoryNo, int blackListNo, String memberId, String detail,
			Date historyCreatedAt) {
		super();
		this.blackListHistoryNo = blackListHistoryNo;
		this.blackListNo = blackListNo;
		this.memberId = memberId;
		this.detail = detail;
		this.historyCreatedAt = historyCreatedAt;
	}
	public int getBlackListHistoryNo() {
		return blackListHistoryNo;
	}
	public void setBlackListHistoryNo(int blackListHistoryNo) {
		this.blackListHistoryNo = blackListHistoryNo;
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
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Date getHistoryCreatedAt() {
		return historyCreatedAt;
	}
	public void setHistoryCreatedAt(Date historyCreatedAt) {
		this.historyCreatedAt = historyCreatedAt;
	}
	
	
}
