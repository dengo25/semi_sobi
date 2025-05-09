package sobi.vo.member;

import java.util.Date;

public class MemberSocialVO {
  
  private String memberSocialId;
  private String memberId;
  private String socialId;
  private Date LinkedAt;
  
  public MemberSocialVO() {
  }
  
  public MemberSocialVO(String memberSocialId, String memberId, String socialId, Date linkedAt) {
    this.memberSocialId = memberSocialId;
    this.memberId = memberId;
    this.socialId = socialId;
    LinkedAt = linkedAt;
  }
  
  public String getMemberSocialId() {
    return memberSocialId;
  }
  
  public void setMemberSocialId(String memberSocialId) {
    this.memberSocialId = memberSocialId;
  }
  
  public String getMemberId() {
    return memberId;
  }
  
  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }
  
  public String getSocialId() {
    return socialId;
  }
  
  public void setSocialId(String socialId) {
    this.socialId = socialId;
  }
  
  public Date getLinkedAt() {
    return LinkedAt;
  }
  
  public void setLinkedAt(Date linkedAt) {
    LinkedAt = linkedAt;
  }
}
