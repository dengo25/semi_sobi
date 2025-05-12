package sobi.action.review;

import sobi.action.common.SobiAction;
import sobi.dao.review.CategoryDAO;
import sobi.vo.member.MemberVO;
import sobi.vo.review.CategoryVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ReviewWriteAction implements SobiAction {
  
  @Override
  public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    CategoryDAO dao = new CategoryDAO();
    List<CategoryVO> categoryList = dao.getAllCategories();
    
    CategoryDAO categoryDAO = new CategoryDAO();
    request.setAttribute("categoryList", categoryDAO.getAllCategories());
    
    HttpSession session = request.getSession();
    request.setAttribute("member", (MemberVO) session.getAttribute("loginUser"));
    
    request.setAttribute("categoryList", categoryList);
    
    return "/v1/views/review/reviewWrite.jsp";
  }
}
