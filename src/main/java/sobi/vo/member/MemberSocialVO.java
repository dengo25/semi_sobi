package sobi.vo.member;

import java.util.Date;

public class MemberSocialVO {
  
  private String MemberSocialId;
  private String MemberId;
  private String SocialType;
  private String SocialId;
  private Date LinkedAt;
  
  public MemberSocialVO(String memberSocialId, String memberId, String socialType, String socialId, Date linkedAt) {
    MemberSocialId = memberSocialId;
    MemberId = memberId;
    SocialType = socialType;
    SocialId = socialId;
    LinkedAt = linkedAt;
  }
}
