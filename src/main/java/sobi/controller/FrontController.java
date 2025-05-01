package sobi.controller;

import sobi.action.*;

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
		
		// 요청 이름에 맞게 액션 등록
		map.put("join_process", new JoinProcessAction());
		map.put("checkId", new CheckIdAction());
		// 필요 시 여기에 추가 등록
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI(); // ex: /board_test/v1/views/user/login.do
		String context = request.getContextPath(); // ex: /board_test
		String path = uri.substring(context.length()); // ex: /v1/views/user/login.do
		
		// 🔧 정확한 페이지명 추출 (login)
		String page = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
		
		// 🔧 폴더명 추출 (user)
		String[] parts = path.split("/");
		String folder = parts.length >= 4 ? parts[3] : "user"; // /v1/views/user/login.do 기준
		
		try {
			if (map.containsKey(page)) {
				SobiAction action = map.get(page);
				String view = action.pro(request, response);
				
				if (view == null) return; // JSON 응답 등 직접 처리
				
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
			
			// ⛔ fallback JSP 경로 설정 (ex: /v1/views/user/login.jsp)
			String contentPage = "/v1/views/" + folder + "/" + page + ".jsp";
			request.setAttribute("contentPage", contentPage);
			request.setAttribute("title", page.toUpperCase());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/v1/views/common/layout.jsp");
			dispatcher.forward(request, response);
			
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
