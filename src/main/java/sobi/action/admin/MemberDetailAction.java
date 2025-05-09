package sobi.action.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.action.common.SobiAction;
import sobi.dao.admin.MemberDAO;

import sobi.vo.admin.MemberVO;



public class MemberDetailAction implements SobiAction {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String memberId = request.getParameter("memberId");
		MemberDAO dao = new MemberDAO();
		
		MemberVO member = dao.findById(memberId);
		
		request.setAttribute("member", member);
		return "/v1/views/admin/memberDetail.jsp";
	}

}
