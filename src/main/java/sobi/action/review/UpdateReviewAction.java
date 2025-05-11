package sobi.action.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.dao.review.ReviewDAO;
import sobi.vo.review.ReviewVO;

public class UpdateReviewAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        int reviewId = Integer.parseInt(request.getParameter("reviewId"));
        String title = request.getParameter("title");
        String productName = request.getParameter("productName");
        int rating = Integer.parseInt(request.getParameter("rating"));
        String content = request.getParameter("content");
        String imageUrl = request.getParameter("imageUrl");

        ReviewVO vo = new ReviewVO();
        vo.setReviewId(reviewId);
        vo.setTitle(title);
        vo.setProductName(productName);
        vo.setRating(rating);
        vo.setContent(content);
        vo.setImageUrl(imageUrl);

        ReviewDAO dao = new ReviewDAO();
        int result = dao.updateReview(vo);
        if (result > 0) {
            System.out.println("리뷰 수정 성공");
        } else {
            System.out.println("리뷰 수정 실패");
        }
        ActionForward forward = new ActionForward();
        if (result >0 ) {
            forward.setPath("reviewDetail.do?reviewId=" + reviewId); // 수정 후 상세페이지로
        } else {
            forward.setPath("/v1/views/error.jsp");
        }
        forward.setRedirect(true);
        return forward;
    }
}
