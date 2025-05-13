package sobi.action.review;

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
		request.setAttribute("title", "리뷰 상세");
		
//		response.setContentType("text/html;charset=UTF-8");
//		response.getWriter().println("<script>");
//		response.getWriter().println("alert('인증되었습니다.');");
//		response.getWriter().println("location.href='reviewDetail.do?reviewId=" + reviewId + "';");
//		response.getWriter().println("</script>");
//
		
		return "/v1/views/review/reviewDetail.jsp";
	}

}
