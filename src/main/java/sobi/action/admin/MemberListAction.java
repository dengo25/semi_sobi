package sobi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.action.common.SobiAction;
import sobi.dao.admin.MemberDAO;
import sobi.vo.admin.MemberVO;


public class MemberListAction implements SobiAction {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MemberDAO dao = new MemberDAO();
		List<MemberVO> list = new ArrayList<MemberVO>();
		list = dao.getReviewandCommentCount();

		request.setAttribute("list", list);
		return "/v1/views/admin/memberList.jsp";
	}

}
