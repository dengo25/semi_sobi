package sobi.action.member;

import sobi.action.common.SobiAction;
import sobi.dao.member.MemberDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckIdAction implements SobiAction {
  @Override
  public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String id = request.getParameter("member_id");
    boolean exist = new MemberDAO().isIdExist(id);
    
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write("{\"exists\": " + exist + "}");
    return null;
  }
}
