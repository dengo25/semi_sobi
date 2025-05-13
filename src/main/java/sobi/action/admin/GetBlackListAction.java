package sobi.action.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.action.common.SobiAction;
import sobi.dao.admin.BlackListDAO;
import sobi.vo.admin.BlackListVO;


public class GetBlackListAction implements SobiAction {

	@Override
	public  String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		BlackListDAO dao = new BlackListDAO();
		List<BlackListVO> list = dao.getAllBlackList(); 
		request.setAttribute("list", list);
		request.setAttribute("title", "블랙리스트");

		return "/v1/views/admin/getBlackList.jsp";
	}

}
