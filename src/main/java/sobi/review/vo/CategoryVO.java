package sobi.review.vo;

public class CategoryVO {
	private int reviewCategoryId;
	private String reviewCategoryName;
	
	public CategoryVO(int reviewCategoryId, String reviewCategoryName) {
		this.reviewCategoryId = reviewCategoryId;
		this.reviewCategoryName = reviewCategoryName;
	}

	public CategoryVO() {
		
	}

	public int getReviewCategoryId() {
		return reviewCategoryId;
	}

	public void setReviewCategoryId(int reviewCategoryId) {
		this.reviewCategoryId = reviewCategoryId;
	}

	public String getReviewCategoryName() {
		return reviewCategoryName;
	}

	public void setReviewCategoryName(String reviewCategoryName) {
		this.reviewCategoryName = reviewCategoryName;
	}
	
}
