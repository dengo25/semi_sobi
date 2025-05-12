package sobi.action.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.action.common.SobiAction;
import sobi.dao.admin.CommentReportDAO;
import sobi.dao.admin.MemberDAO;
import sobi.dao.admin.MemberLogDAO;
import sobi.dao.admin.ReviewDAO;
import sobi.dao.admin.ReviewReportDAO;
import sobi.vo.admin.MemberLogVO;
import sobi.vo.admin.MemberVO;
import sobi.vo.admin.ReviewVO;



public class MemberDetailAction implements SobiAction {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String memberId = request.getParameter("memberId");
		
		int reviewPage = 1;
		int reviewPageSize = 5;
		int memberLogPage = 1;
		int memberLogPageSize = 5;
		if(request.getParameter("reviewPage") != null) {
			reviewPage = Integer.parseInt(request.getParameter("reviewPage"));
		}
		if(request.getParameter("memberLogPage") != null) {
			memberLogPage = Integer.parseInt(request.getParameter("memberLogPage"));
		}
		MemberDAO memberdao = new MemberDAO();
		ReviewDAO reviewdao = new ReviewDAO();	
		MemberLogDAO memberLogdao = new MemberLogDAO();
		ReviewReportDAO reportdao = new ReviewReportDAO();
		CommentReportDAO commentdao = new CommentReportDAO();
		
		MemberVO memberDetail = memberdao.findById(memberId);
		
		int totalReviewCount = reviewdao.memberReviewCount(memberId);
		int totalReviewPage = (int)Math.ceil((double)totalReviewCount/reviewPageSize);
		int totalMemberLogCount = memberLogdao.getMemberLogCount(memberId);
		int totalMemberLogPage = (int)Math.ceil((double)totalMemberLogCount/memberLogPageSize);
		
		List<ReviewVO> reviewList = reviewdao.getPagedReviewsByMemberId(memberId,reviewPage, reviewPageSize);
		List<MemberLogVO> memberLogList = memberLogdao.getPagedMemberLogByMemberId(memberId, memberLogPage, memberLogPageSize);
		
		int totalReportCount = reportdao.reviewReportCount(memberId) + commentdao.commentReportCount(memberId);

		
		request.setAttribute("memberDetail", memberDetail);
		request.setAttribute("memberReviewList", reviewList);
		request.setAttribute("memberLogList", memberLogList);
		request.setAttribute("totalMemberLogCount", totalMemberLogCount);
		request.setAttribute("totalReportCount", totalReportCount);
		request.setAttribute("totalReviewCount", totalReviewCount);
		
		//리뷰 페이징
		request.setAttribute("currentReviewPage", reviewPage);
		request.setAttribute("totalReviewPage", totalReviewPage);

		//로그 페이징
		request.setAttribute("currentMemberLogPage", memberLogPage);
		request.setAttribute("totalMemberLogPage", totalMemberLogPage);
		request.setAttribute("memberId", memberId);


		return "/v1/views/admin/memberDetail.jsp";
	}

}
