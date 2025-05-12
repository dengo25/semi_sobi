package sobi.action.review;

import sobi.action.common.SobiAction;
import sobi.dao.review.CategoryDAO;
import sobi.dao.review.ReviewDAO;
import sobi.vo.member.MemberVO;
import sobi.vo.review.CategoryVO;
import sobi.vo.review.ReviewVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ReviewEditAction implements SobiAction {
  @Override
  public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("utf-8");
    
    HttpSession session = request.getSession();
    MemberVO member = (MemberVO) session.getAttribute("member");
    
    if (member == null) {
      request.setAttribute("errorMsg", "로그인이 필요합니다.");
      return "/v1/views/member/login.jsp";
    }
    
    int reviewId = Integer.parseInt(request.getParameter("reviewId"));
    ReviewDAO reviewDAO = new ReviewDAO();
    ReviewVO review = reviewDAO.getReviewsByReviewId(reviewId);
    
    // 작성자만 수정 가능
    if (!member.getMemberId().equals(review.getMemberId())) {
      request.setAttribute("errorMsg", "작성자만 수정할 수 있습니다.");
      return "/v1/views/review/review.jsp";
    }
    
    // 카테고리 목록 조회
    CategoryDAO categoryDAO = new CategoryDAO();
    List<CategoryVO> categoryList = categoryDAO.getAllCategories();
    
    // request에 데이터 바인딩
    request.setAttribute("review", review);
    request.setAttribute("categoryList", categoryList);
    
    return "/v1/views/review/reviewWrite.jsp";
  }
}