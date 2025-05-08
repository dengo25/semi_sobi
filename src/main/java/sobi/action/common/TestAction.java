package sobi.action.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.dao.community.TestDAO;

public class TestAction implements SobiAction {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		TestDAO dao = new TestDAO();
		int re = dao.getAll();
		
		request.setAttribute("list",re); 
		
		System.out.println("start action! : "+re);
		System.out.println(">>> TestAction 실행됨!");
		
		return "component.jsp";
	}

}
