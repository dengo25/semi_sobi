package sobi.action.member;

import sobi.action.common.SobiAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FindMemberPasswordAction implements SobiAction {
  
  @Override
  public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setAttribute("title", "임시 비밀번호");
    return "/v1/views/member/findMemberPassword.jsp";
  }
}
