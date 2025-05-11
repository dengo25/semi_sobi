package sobi.action.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.dao.review.ReviewDAO;
import sobi.vo.review.ReviewVO;

public class ReviewDetailAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String param = request.getParameter("reviewId");
        if (param == null || param.trim().isEmpty()) {
            throw new IllegalArgumentException("reviewId 파라미터가 없습니다.");
        }

        int reviewId = Integer.parseInt(param);

        ReviewDAO dao = new ReviewDAO();
        ReviewVO review = dao.getReviewById(reviewId);

        if (review == null) {
            throw new RuntimeException("리뷰를 찾을 수 없습니다. reviewId=" + reviewId);
        }

        request.setAttribute("review", review);

        // ✅ layout.jsp와 contentPage 설정
        request.setAttribute("contentPage", "/v1/views/review/review_detail.jsp");

        ActionForward forward = new ActionForward();
        forward.setPath("/v1/views/common/layout.jsp");
        forward.setRedirect(false);
        return forward;
    }
}
