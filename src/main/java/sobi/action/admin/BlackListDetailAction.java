package sobi.action.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.action.common.SobiAction;

public class BlackListDetailAction implements SobiAction {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String memberId= request.getParameter("memberId");
		request.setAttribute("memberId", memberId);
		request.setAttribute("title", "블랙리스트");
		
		return "/v1/views/admin/blackListDetail.jsp";
	}

}
