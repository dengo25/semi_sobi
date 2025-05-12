package sobi.action.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.action.common.SobiAction;
import sobi.dao.admin.MemberLogDAO;
import sobi.vo.admin.MemberLogVO;

public class LogListAction implements SobiAction {

	@Override
	public  String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		MemberLogDAO dao = new MemberLogDAO();
		request.setCharacterEncoding("utf-8");
		String level = request.getParameter("level");
		if (level == null) level = "all";

		String menu = request.getParameter("menu");
		if (menu == null) menu = "";

		String type = request.getParameter("type");
		if (type == null) type = "";

		int page = 1;
		int pageSize = 10;
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		int totalCount = dao.getFilteredMemberLogCount(level, menu, type);
		int totalPage = (int)Math.ceil((double)totalCount / pageSize);

		
		List<MemberLogVO> list = dao.getAllMemberLog(level, menu, type, page, pageSize);
		request.setAttribute("list", list);
		request.setAttribute("currentPage", page);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("level", level);
		request.setAttribute("menu", menu);
		request.setAttribute("type", type);

		return "/v1/views/admin/logList.jsp";
	}

}
