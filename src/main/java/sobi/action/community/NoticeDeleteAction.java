package sobi.action.community;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sobi.action.common.SobiAction;
import sobi.dao.community.NoticeDAO;
import sobi.vo.community.NoticeVO;
import sobi.vo.member.MemberVO;

public class NoticeDeleteAction implements SobiAction {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("member");

		if (member == null || !"A".equals(member.getRole())) {
		    request.setAttribute("errorMsg", "관리자만 글을 삭제할 수 있습니다.");
		    return "/v1/views/community/notice.jsp";
		}
		
		int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));
		NoticeDAO dao = new NoticeDAO();
		
		int result = dao.setDelete(noticeNo);
		
		
		if(result > 0) { // 삭제 성공 
			response.sendRedirect("notice.do");
			return null;
		}else { // 삭제 실패 
			request.setAttribute("errorMsg", "공지 삭제에 실패했습니다.");
			
			// 현재 공지 데이터 다시 조회해서 화면에 유지
			NoticeVO noticeDetail = dao.getDetail(noticeNo);
			request.setAttribute("noticeDetail", noticeDetail);
			request.setAttribute("noticeImg", dao.getImgByNoticeNo(noticeNo));
			
			return "/v1/views/community/noticeDetail.jsp";
		}
	}

}
