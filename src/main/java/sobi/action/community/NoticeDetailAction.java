package sobi.action.community;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.action.common.SobiAction;
import sobi.dao.community.NoticeDAO;

public class NoticeDetailAction implements SobiAction {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		int noticeNo = Integer.parseInt(request.getParameter("noticeNo")); // noticeInfo.getNoticeNo();
		NoticeDAO dao = new NoticeDAO();
		
		dao.increaseCount(noticeNo);
		
		request.setAttribute("noticeDetail",dao.getDetail(noticeNo));
		request.setAttribute("noticeImg",dao.getImgByNoticeNo(noticeNo));
		request.setAttribute("title", "공지사항");
		// System.out.println("session : "+ noticeNo);
		
		return "/v1/views/community/noticeDetail.jsp";
	}

}
