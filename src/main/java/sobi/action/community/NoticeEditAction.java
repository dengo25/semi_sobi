package sobi.action.community;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sobi.action.common.SobiAction;
import sobi.dao.community.NoticeDAO;
import sobi.vo.member.MemberVO;

public class NoticeEditAction implements SobiAction {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("member");

		if (member == null || !"A".equals(member.getRole())) {
		    request.setAttribute("errorMsg", "관리자만 글을 수정할 수 있습니다.");
		    return "/v1/views/community/notice.jsp";
		}
		
		int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));
		NoticeDAO dao = new NoticeDAO();
		
		request.setAttribute("noticeDetail",dao.getDetail(noticeNo));
		request.setAttribute("noticeImg",dao.getImgByNoticeNo(noticeNo));
		request.setAttribute("title", "공지사항");
		
		return "/v1/views/community/noticeWrite.jsp";
	}

}
