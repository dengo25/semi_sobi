package sobi.action.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.action.common.SobiAction;
import sobi.dao.admin.MemberDAO;
import sobi.dao.admin.MemberLogDAO;
import sobi.dao.admin.ReviewDAO;
import sobi.vo.admin.MemberLogVO;
import sobi.vo.admin.MemberVO;
import sobi.vo.admin.ReviewVO;



public class MemberDetailAction implements SobiAction {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String memberId = request.getParameter("memberId");
		
		MemberDAO memberdao = new MemberDAO();
		MemberVO memberDetail = memberdao.findById(memberId);
		request.setAttribute("memberDetail", memberDetail);

		ReviewDAO reviewdao = new ReviewDAO();
		List<ReviewVO> list = reviewdao.getReviewsByMemberId(memberId);
		request.setAttribute("memberReviewList", list);
		
		MemberLogDAO memberLogdao = new MemberLogDAO();
		List<MemberLogVO> memberlogList = memberLogdao.getMemberLog(memberId);
		request.setAttribute("logList", memberlogList);
		return "/v1/views/admin/memberDetail.jsp";
	}

}
