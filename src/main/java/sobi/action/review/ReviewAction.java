package sobi.action.review;

import sobi.action.common.SobiAction;
import sobi.dao.review.ReviewDAO;
import sobi.vo.review.ReviewVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

public class ReviewAction implements SobiAction {
  @Override
  public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("utf-8");
    
    String keyword = request.getParameter("keyword");
    if (keyword == null) keyword = "";
    
    int pageNum = 1;
    if (request.getParameter("pageNum") != null) {
      pageNum = Integer.parseInt(request.getParameter("pageNum"));
    }
    
    int pageSize = 10; // 한 페이지당 10개
    HashMap<String, Object> paramMap = new HashMap<>();
    paramMap.put("keyword", keyword);
    paramMap.put("pageNum", pageNum);
    paramMap.put("pageSize", pageSize);
    
    ReviewDAO dao = new ReviewDAO();
    List<ReviewVO> list = dao.getAll(paramMap);
    
    int totalCount = dao.getCount(keyword);
    int totalPaging = (int) Math.ceil(totalCount / (double) pageSize);
    
    // 순번 붙이기
    int startNo = (pageNum - 1) * pageSize + 1;
    for (ReviewVO vo : list) {
      vo.setRowNo(startNo++);
    }
    
    request.setAttribute("list", list);
    request.setAttribute("count", totalCount);
    request.setAttribute("pageNum", pageNum);
    request.setAttribute("totalPaging", totalPaging);
    request.setAttribute("title", "리뷰 목록");
    
    return "/v1/views/review/review.jsp";
  }
}
