package sobi.review.vo;

public class CommentLikeVO {
	private int commentLikeId;
	private int commentId;
	private String memberId;
	
	
	public CommentLikeVO() {
	}

	public CommentLikeVO(int commentLikeId, int commentId, String memberId) {
		this.commentLikeId = commentLikeId;
		this.commentId = commentId;
		this.memberId = memberId;
	}

	public int getCommentLikeId() {
		return commentLikeId;
	}

	public void setCommentLikeId(int commentLikeId) {
		this.commentLikeId = commentLikeId;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

}
