package sobi.vo;

import java.util.Date;

public class UserVO {
  
  private String memberId;
  private int blackList;
  private String memberPassword;
  private String memberName;
  private String memberGender;
  private String memberEmail;
  private String memberBirth;
  private String memberAddr;
  private String memberZip;
  private Date memberReg;
  private String isActive;
  
  public UserVO() {
  }
  
  public UserVO(String memberId, int blackList, String memberPassword, String memberName, String memberGender,
                String memberEmail, String memberBirth, String memberAddr, String memberZip, Date memberReg, String isActive) {
    this.memberId = memberId;
    this.blackList = blackList;
    this.memberPassword = memberPassword;
    this.memberName = memberName;
    this.memberGender = memberGender;
    this.memberEmail = memberEmail;
    this.memberBirth = memberBirth;
    this.memberAddr = memberAddr;
    this.memberZip = memberZip;
    this.memberReg = memberReg;
    this.isActive = isActive;
  }
  
  public String getMemberId() {
    return memberId;
  }
  
  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }
  
  public int getBlackList() {
    return blackList;
  }
  
  public void setBlackList(int blackList) {
    this.blackList = blackList;
  }
  
  public String getMemberPassword() {
    return memberPassword;
  }
  
  public void setMemberPassword(String memberPassword) {
    this.memberPassword = memberPassword;
  }
  
  public String getMemberName() {
    return memberName;
  }
  
  public void setMemberName(String memberName) {
    this.memberName = memberName;
  }
  
  public String getMemberGender() {
    return memberGender;
  }
  
  public void setMemberGender(String memberGender) {
    this.memberGender = memberGender;
  }
  
  public String getMemberEmail() {
    return memberEmail;
  }
  
  public void setMemberEmail(String memberEmail) {
    this.memberEmail = memberEmail;
  }
  
  public String getMemberBirth() {
    return memberBirth;
  }
  
  public void setMemberBirth(String memberBirth) {
    this.memberBirth = memberBirth;
  }
  
  public String getMemberAddr() {
    return memberAddr;
  }
  
  public void setMemberAddr(String memberAddr) {
    this.memberAddr = memberAddr;
  }
  
  public String getMemberZip() {
    return memberZip;
  }
  
  public void setMemberZip(String memberZip) {
    this.memberZip = memberZip;
  }
  
  public String getIsActive() {
    return isActive;
  }
  
  public void setIsActive(String isActive) {
    this.isActive = isActive;
  }
  
  public Date getMemberReg() {
    return memberReg;
  }
  
  public void setMemberReg(Date memberReg) {
    this.memberReg = memberReg;
  }
}
