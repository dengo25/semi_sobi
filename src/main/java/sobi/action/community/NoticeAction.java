package sobi.action.community;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sobi.action.common.SobiAction;
import sobi.dao.community.NoticeDAO;

public class NoticeAction implements SobiAction {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String keyword = "";
		String sort = "";
		int pageNum = 1;
		
		keyword = request.getParameter("keyword");
		if(keyword == null) {
			keyword = "";
		}
		
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("keyword", keyword);
		map.put("sort", sort);
		map.put("pageNum", pageNum);
		
		NoticeDAO dao = new NoticeDAO();
		request.setAttribute("list", dao.getAll(map));
		request.setAttribute("count", dao.getCount(keyword));
		request.setAttribute("totalPaging", dao.totalPage);
		request.setAttribute("pageNum", pageNum);
			
		System.out.println("keyword : "+keyword);
		System.out.println("pageNum : "+pageNum);
		System.out.println("totalPaging : "+dao.totalPage);
		System.out.println("NoticeAction의 process() 동작함.");
		return "/v1/views/community/notice.jsp";
	}

}
