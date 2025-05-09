package sobi.vo.community;

import java.sql.Date;

public class FaqVO {
	private	int faqNo;
	private	String faqCategory;
	private	String faqQuestion;
	private	String faqAnswer;
	private	Date faqCreateDate;
	private	Date faqEditDate;
	private	Date faqDelete;
	private	String isDeleted;
	private	String isVisible;
	
	public FaqVO() {
		super();
	}

	public FaqVO(int faqNo, String faqCategory, String faqQuestion, String faqAnswer, Date faqCreateDate,
			Date faqEditDate, Date faqDelete, String isDeleted, String isVisible) {
		super();
		this.faqNo = faqNo;
		this.faqCategory = faqCategory;
		this.faqQuestion = faqQuestion;
		this.faqAnswer = faqAnswer;
		this.faqCreateDate = faqCreateDate;
		this.faqEditDate = faqEditDate;
		this.faqDelete = faqDelete;
		this.isDeleted = isDeleted;
		this.isVisible = isVisible;
	}
	
	public int getFaqNo() {
		return faqNo;
	}
	public void setFaqNo(int faqNo) {
		this.faqNo = faqNo;
	}
	public String getFaqCategory() {
		return faqCategory;
	}
	public void setFaqCategory(String faqCategory) {
		this.faqCategory = faqCategory;
	}
	public String getFaqQuestion() {
		return faqQuestion;
	}
	public void setFaqQuestion(String faqQuestion) {
		this.faqQuestion = faqQuestion;
	}
	public String getFaqAnswer() {
		return faqAnswer;
	}
	public void setFaqAnswer(String faqAnswer) {
		this.faqAnswer = faqAnswer;
	}
	public Date getFaqCreateDate() {
		return faqCreateDate;
	}
	public void setFaqCreateDate(Date faqCreateDate) {
		this.faqCreateDate = faqCreateDate;
	}
	public Date getFaqEditDate() {
		return faqEditDate;
	}
	public void setFaqEditDate(Date faqEditDate) {
		this.faqEditDate = faqEditDate;
	}
	public Date getFaqDelete() {
		return faqDelete;
	}
	public void setFaqDelete(Date faqDelete) {
		this.faqDelete = faqDelete;
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

	

}
