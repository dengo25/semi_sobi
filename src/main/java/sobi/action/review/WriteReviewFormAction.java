package sobi.action.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import sobi.dao.review.CategoryDAO;
import sobi.vo.review.CategoryVO;

public class WriteReviewFormAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 카테고리 목록 가져오기
        CategoryDAO categoryDao = new CategoryDAO();
        List<CategoryVO> categoryList = categoryDao.getAllCategories();

        request.setAttribute("categoryList", categoryList);

        // contentPage로 사용할 JSP 지정
        request.setAttribute("contentPage", "/v1/views/review/review_write.jsp");

        ActionForward forward = new ActionForward();
        forward.setPath("/v1/views/common/layout.jsp"); // layout을 통해 렌더링
        forward.setRedirect(false);
        return forward;
    }
}
