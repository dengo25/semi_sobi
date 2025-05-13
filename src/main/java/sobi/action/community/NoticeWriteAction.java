package sobi.action.community;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sobi.action.common.SobiAction;
import sobi.vo.member.MemberVO;

public class NoticeWriteAction implements SobiAction {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();

		MemberVO memberInfo = (MemberVO) session.getAttribute("member");
		if (memberInfo == null || !"A".equals(memberInfo.getRole())) {
		    request.setAttribute("errorMsg", "관리자만 접근 가능합니다.");
		    return "/v1/views/community/notice.jsp";
		}
		
		String memberId = memberInfo.getMemberId();
		request.setAttribute("memberId",memberId);
		
		System.out.println("session : "+ memberId);
		request.setAttribute("title", "공지사항");
		return "/v1/views/community/noticeWrite.jsp";
	}

}
