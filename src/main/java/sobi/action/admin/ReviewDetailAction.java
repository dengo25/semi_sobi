package sobi.action.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.action.common.SobiAction;
import sobi.dao.admin.ReviewDAO;
import sobi.vo.admin.ReviewVO;

public class ReviewDetailAction implements SobiAction {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int reviewId = Integer.parseInt(request.getParameter("reviewId"));
		ReviewDAO reviewdao= new ReviewDAO();
		ReviewVO review = reviewdao.getReviewsByReviewId(reviewId);
		request.setAttribute("review", review);
		return "/v1/views/admin/reviewDetail.jsp";
	}

}
