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
		String menu = request.getParameter("menu");
		String type = request.getParameter("type");
		List<MemberLogVO> list = dao.getAllMemberLog(level, menu, type); 
		request.setAttribute("list", list);

		return "/v1/views/admin/logList.jsp";
	}

}
