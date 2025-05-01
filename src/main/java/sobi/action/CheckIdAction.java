package sobi.action;

import sobi.dao.UserDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class CheckIdAction implements SobiAction{
  @Override
  public String pro(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("member_id");
    boolean exists = false;
    
    if (id != null && !id.trim().isEmpty()) {
      UserDAO dao = new UserDAO();
      exists = dao.isIdExist(id);
    }
    
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.write("{\"exists\":" + exists + "}");
    out.flush();
    out.close();
    
    return null; // ✅ 직접 응답 처리하므로 JSP 포워딩 없음
  }
}