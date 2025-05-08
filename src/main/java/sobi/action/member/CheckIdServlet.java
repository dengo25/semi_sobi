package sobi.action.member;

import sobi.dao.member.MemberDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/checkId.json")
public class CheckIdServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String id = request.getParameter("member_id");
    boolean exists = new MemberDAO().isIdExist(id);
    
    
    
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write("{\"exists\": " + exists + "}");
  }
}
