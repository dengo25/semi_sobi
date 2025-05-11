package sobi.action.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.dao.review.ReviewDAO;

public class DeleteReviewAction implements Action {
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int reviewId = Integer.parseInt(request.getParameter("reviewId"));
        new ReviewDAO().deleteReview(reviewId);

        ActionForward forward = new ActionForward();
        forward.setPath("reviewList.do"); // 목록으로 리디렉트
        forward.setRedirect(true);
        return forward;
    }
}