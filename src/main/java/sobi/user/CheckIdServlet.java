package sobi.user;

import sobi.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ajax/user/checkId")
public class CheckIdServlet extends HttpServlet {
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
  }
}