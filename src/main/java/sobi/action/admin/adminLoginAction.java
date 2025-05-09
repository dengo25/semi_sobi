package sobi.action.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.action.common.SobiAction;
import sobi.dao.admin.BlackListDAO;

public class adminLoginAction implements SobiAction{
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		BlackListDAO dao = new BlackListDAO();
		int re = dao.getAdmin(request.getParameter("adminId"), request.getParameter("adminPassword"));
		
		return null;
	} 
}
