package sobi.controller;

import sobi.action.SobiAction;
import sobi.action.member.CheckIdAction;
import sobi.action.member.JoinProcessAction;
import sobi.action.member.LoginProcessAction;


import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HashMap<String, SobiAction> map = new HashMap<>();
	
	@Override
	public void init() throws ServletException {
		System.out.println("FrontController init()");
		
		// 액션 클래스 등록
		map.put("join_process", new JoinProcessAction());
		map.put("checkId", new CheckIdAction());
		map.put("login_process", new LoginProcessAction());
		// 필요 시 계속 추가
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();                // 예: /v1/views/user/login.do
		String context = request.getContextPath();           // 예: /
		String path = uri.substring(context.length());       // 예: /v1/views/user/login.do 또는 /checkId.do
		
		String page = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
		String[] parts = path.split("/");
		String folder = (parts.length >= 5 && "views".equals(parts[2])) ? parts[3] : null;
		
		try {
			if (map.containsKey(page)) {
				SobiAction action = map.get(page);
				String view = action.pro(request, response);
				
				if (view == null) return; // 직접 응답 완료된 경우
				
				if (view.startsWith("redirect:")) {
					response.sendRedirect(view.substring("redirect:".length()));
					return;
				}
				
				request.setAttribute("contentPage", view);
				request.setAttribute("title", page.toUpperCase());
				RequestDispatcher dispatcher = request.getRequestDispatcher("/v1/views/common/layout.jsp");
				dispatcher.forward(request, response);
				return;
			}
			
			// fallback JSP 처리 (folder가 있는 경우만)
			if (folder != null) {
				String contentPage = "/v1/views/" + folder + "/" + page + ".jsp";
				request.setAttribute("contentPage", contentPage);
				request.setAttribute("title", page.toUpperCase());
				RequestDispatcher dispatcher = request.getRequestDispatcher("/v1/views/common/layout.jsp");
				dispatcher.forward(request, response);
			} else {
				throw new ServletException("잘못된 요청 경로: " + path);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("[FrontController 처리 중 오류] " + e.getMessage());
		}
		
		System.out.println("uri: " + uri);
		System.out.println("page: " + page);
		System.out.println("folder: " + folder);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
}
