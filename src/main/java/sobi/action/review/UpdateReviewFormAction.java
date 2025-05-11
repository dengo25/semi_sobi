package sobi.action.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.dao.review.ReviewDAO;
import sobi.vo.review.ReviewVO;

public class UpdateReviewFormAction implements Action {
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int reviewId = Integer.parseInt(request.getParameter("reviewId"));
        ReviewVO review = new ReviewDAO().getReviewById(reviewId);
        request.setAttribute("review", review);
        request.setAttribute("contentPage", "/v1/views/review/review_update_form.jsp");

        ActionForward forward = new ActionForward();
        forward.setPath("/v1/views/common/layout.jsp");
        forward.setRedirect(false);
        return forward;
    }
}
