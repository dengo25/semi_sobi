package sobi.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.action.common.SobiAction;
import sobi.dao.admin.MemberDAO;
import sobi.vo.admin.MemberVO;

public class TodayJoinMemberAction implements SobiAction {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MemberDAO dao = new MemberDAO();
		int page = 1;
		int totalPageSize = 10;
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		int totalCount = dao.getTodayJoinMember();
		int totalPage = (int)Math.ceil((double)totalCount/totalPageSize);

		List<MemberVO> todayJoinMemberList = dao.getPagedTodayJoinMemberList(page, totalPageSize);
		
		request.setAttribute("todayJoinMemberList", todayJoinMemberList);
		request.setAttribute("currentPage", page);		
		request.setAttribute("totalPage", totalPage);	
		return "/v1/views/admin/todayJoinMemberList.jsp";
	}

}
