package sobi.action.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.dao.review.ReviewDAO;
import sobi.vo.review.ReviewVO;

public class InsertReviewAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        // 파라미터 수집
        String memberId = request.getParameter("member_id");
        String productName = request.getParameter("product_name");
        String title = request.getParameter("review_title");
        int categoryId = Integer.parseInt(request.getParameter("review_category_id"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String content = request.getParameter("content");

        // ReviewVO 생성
        ReviewVO vo = new ReviewVO();
        vo.setMemberId(memberId);
        vo.setProductName(productName);
        vo.setTitle(title);
        vo.setCategoryId(categoryId);
        vo.setRating(rating);
        vo.setContent(content);

        // DAO 처리
        ReviewDAO dao = new ReviewDAO();
        int generatedId = dao.insertReview(vo);

        System.out.println("✅ 리뷰 등록 완료. ID = " + generatedId);

        // 등록 후 상세 페이지로 리다이렉트
        ActionForward forward = new ActionForward();
        forward.setPath("reviewDetail.do?reviewId=" + generatedId); // 등록한 리뷰 상세로 이동
        forward.setRedirect(true); // 리다이렉트
        return forward;
    }
}
