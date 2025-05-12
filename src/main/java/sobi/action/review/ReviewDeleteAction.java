package sobi.action.review;

import sobi.action.common.SobiAction;
import sobi.dao.review.ReviewDAO;
import sobi.vo.member.MemberVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ReviewDeleteAction implements SobiAction {
  @Override
  public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    
    HttpSession session = request.getSession();
    MemberVO member = (MemberVO) session.getAttribute("member"); // ✅ 여기 수정
    String memberId = member != null ? member.getMemberId() : null;
    
    int reviewId = Integer.parseInt(request.getParameter("reviewId"));
    
    ReviewDAO dao = new ReviewDAO();
    boolean success = dao.logicalDelete(reviewId, memberId);
    
    if (success) {
      response.sendRedirect("review.do");
      return null;
    } else {
      request.setAttribute("errorMsg", "리뷰 삭제에 실패했습니다.");
      return "/v1/views/review/review.jsp";
    }
  }
}