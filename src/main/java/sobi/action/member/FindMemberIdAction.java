package sobi.action.member;

import sobi.action.common.SobiAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FindMemberIdAction implements SobiAction {
  @Override
  public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    return "/v1/views/member/findMemberId.jsp";
  }
}
