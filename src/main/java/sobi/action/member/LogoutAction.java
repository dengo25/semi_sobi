package sobi.action.member;

import sobi.action.common.SobiAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutAction implements SobiAction {
  @Override
  public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    //세션 무효화
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return "redirect:/v1/views/main/main.do";
  }
}
