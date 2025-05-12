package sobi.action.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.action.common.SobiAction;
import sobi.dao.admin.BlackListDAO;
import sobi.dao.admin.MemberDAO;
import sobi.dao.admin.MemberLogDAO;
import sobi.dao.admin.ReviewDAO;
import sobi.vo.admin.MemberLogVO;

public class AdminMenuAction implements SobiAction {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MemberDAO memberdao = new MemberDAO();
		ReviewDAO reviewdao = new ReviewDAO();
		BlackListDAO blackListdao = new BlackListDAO();
		MemberLogDAO memberLogDAO= new MemberLogDAO();
		
		int memberCount = memberdao.memberCount();
		int reviewCount = reviewdao.reviewCount();
		int blackListCount = blackListdao.blackListCount();
		List<MemberLogVO> memberLogList = memberLogDAO.getRecentLog();
		
		request.setAttribute("memberCount", memberCount);
		request.setAttribute("reviewCount", reviewCount);
		request.setAttribute("blackListCount", blackListCount);
		request.setAttribute("memberLogList", memberLogList);

		return "/v1/views/admin/adminMenu.jsp";
	}

}
