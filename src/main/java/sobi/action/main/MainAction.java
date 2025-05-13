package sobi.action.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.action.common.SobiAction;
import sobi.dao.community.NoticeDAO;
import sobi.dao.review.ReviewDAO;
import sobi.vo.review.ReviewVO;

public class MainAction implements SobiAction {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		ReviewDAO rdao = new ReviewDAO();
		NoticeDAO ndao = new NoticeDAO();
		
		request.setAttribute("list", rdao.getLatestReviews());
		request.setAttribute("noticeList", ndao.getNewNotice());
		request.setAttribute("title", "메인");
		return "/v1/views/main/main.jsp";
	}

}
