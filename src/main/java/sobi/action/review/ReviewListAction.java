package sobi.action.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import sobi.action.review.Action;
import sobi.action.review.ActionForward;
import sobi.dao.review.CategoryDAO;
import sobi.dao.review.ReviewDAO;
import sobi.vo.review.CategoryVO;
import sobi.vo.review.ReviewVO;

public class ReviewListAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ReviewDAO reviewDao = new ReviewDAO();
        CategoryDAO categoryDao = new CategoryDAO();

        String categoryParam = request.getParameter("categoryId");
        int selectedCategoryId = (categoryParam != null && !categoryParam.isEmpty()) ? Integer.parseInt(categoryParam) : 0;

        List<ReviewVO> list = (selectedCategoryId > 0) ?
                reviewDao.getReviewByCategory(selectedCategoryId) : reviewDao.getAllReviews();

        List<CategoryVO> categoryList = categoryDao.getAllCategories();

        // 콘솔 로그 출력
        System.out.println("선택된 카테고리 ID: " + selectedCategoryId);
        System.out.println("불러온 리뷰 개수: " + list.size());

        request.setAttribute("reviewList", list);
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("selectedCategoryId", selectedCategoryId);

        request.setAttribute("contentPage", "/v1/views/review/review_list.jsp");

        ActionForward forward = new ActionForward();
        forward.setPath("/v1/views/common/layout.jsp");  // layout 사용!
        forward.setRedirect(false);
        return forward;
    }
}
