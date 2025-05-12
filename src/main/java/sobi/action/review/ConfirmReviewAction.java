package sobi.action.review;

import sobi.action.common.SobiAction;
import sobi.dao.review.ReviewDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfirmReviewAction implements SobiAction {
  @Override
  public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int reviewId = Integer.parseInt(request.getParameter("reviewId"));
    
    ReviewDAO dao = new ReviewDAO();
    dao.updateConfirmed(reviewId);
    
    return "redirect:reviewDetail.do?reviewId=" + reviewId;  }
}
