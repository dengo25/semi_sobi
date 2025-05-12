package sobi.action.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.action.common.SobiAction;
import sobi.dao.admin.ReviewDAO;
import sobi.vo.admin.ReviewVO;

public class MemberReviewListAction implements SobiAction {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String memberId = request.getParameter("memberId");
		ReviewDAO dao = new ReviewDAO();
		
		List<ReviewVO> list = dao.getReviewsByMemberId(memberId);
		request.setAttribute("memberReviewList", list);
		return "/v1/views/admin/memberDetail.jsp";
	}

}
