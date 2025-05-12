package sobi.vo.admin;

import java.util.Date;

public class MemberLogVO{
	private int logNo;
	private String memberId;
	private String accessedMenu;
	private String memberActionType;
	private Date memberLogCreated;
	
	public MemberLogVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public MemberLogVO(String memberId, String accessedMenu, String memberActionType, Date memberLogCreated) {
		super();
		this.memberId = memberId;
		this.accessedMenu = accessedMenu;
		this.memberActionType = memberActionType;
		this.memberLogCreated = memberLogCreated;
	}

	public MemberLogVO(int logNo, String memberId, String accessedMenu, String memberActionType, Date memberLogCreated) {
		super();
		this.logNo = logNo;
		this.memberId = memberId;
		this.accessedMenu = accessedMenu;
		this.memberActionType = memberActionType;
		this.memberLogCreated = memberLogCreated;
	}
	public int getLogNo() {
		return logNo;
	}
	public void setLogNo(int logNo) {
		this.logNo = logNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getaccessedMenu() {
		return accessedMenu;
	}
	public void setaccessedMenu(String accessedMenu) {
		this.accessedMenu = accessedMenu;
	}
	public String getMemberActionType() {
		return memberActionType;
	}
	public void setMemberActionType(String memberActionType) {
		this.memberActionType = memberActionType;
	}
	public Date getMemberLogCreated() {
		return memberLogCreated;
	}
	public void setMemberLogCreated(Date memberLogCreated) {
		this.memberLogCreated = memberLogCreated;
	}
}
