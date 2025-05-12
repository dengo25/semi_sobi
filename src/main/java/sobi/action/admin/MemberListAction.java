package sobi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.action.common.SobiAction;
import sobi.dao.admin.MemberDAO;
import sobi.vo.admin.MemberVO;


public class MemberListAction implements SobiAction {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MemberDAO dao = new MemberDAO();
		List<MemberVO> list = new ArrayList<MemberVO>();
		
		int page = 1;
		int totalPageSize = 10;
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		int totalCount = dao.memberCount();
		int totalPage = (int)Math.ceil((double)totalCount/totalPageSize);
		
		list = dao.getReviewandCommentCount(page, totalPageSize);
		request.setAttribute("list", list);		
		request.setAttribute("currentPage", page);		
		request.setAttribute("totalPage", totalPage);		
		
		return "/v1/views/admin/memberList.jsp";
	}
}
