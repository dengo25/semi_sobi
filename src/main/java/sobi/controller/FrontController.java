package sobi.controller;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sobi.action.common.SobiAction;
import sobi.vo.common.MenuVO;

@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HashMap<String, SobiAction> map = new HashMap<>();
	
	public FrontController() {
		super();
	}
	
	public void init(ServletConfig config) throws ServletException {
		String path = config.getServletContext().getRealPath("WEB-INF");
		
		// sobi.properties (액션 매핑)
		try {
			Reader reader = new FileReader(path + "/sobi.properties");
			Properties prop = new Properties();
			prop.load(reader);
			
			Iterator iter = prop.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				String clsName = prop.getProperty(key);
				Object obj = Class.forName(clsName).newInstance();
				map.put(key, (SobiAction) obj);
			}
			System.out.println("front action 완료!");
		} catch (Exception e) {
			System.out.println("int action Exception : " + e.getMessage());
			e.printStackTrace();
		}
		
		// sobi.menu.properties (메뉴 정보)
		try {
			Reader menuReader = new FileReader(path + "/sobi.menu.properties");
			Properties menuProp = new Properties();
			menuProp.load(menuReader);
			System.out.println("메뉴 프로퍼티 로드 완료!");
			
			ArrayList<MenuVO> menuList = new ArrayList<>();
			for (int i = 3; i <= 5; i++) {
				String name = menuProp.getProperty("menu." + i + ".name");
				String link = menuProp.getProperty("menu." + i + ".link");
				if (name != null && link != null) {
					menuList.add(new MenuVO(name, link));
					System.out.println("기본 메뉴 : " + i + " / " + name + " / " + link);
				}
			}
			config.getServletContext().setAttribute("menuList", menuList);
			
			HashMap<Integer, String> otherMenu = new HashMap<>();
			otherMenu.put(1, "main");
			otherMenu.put(6, "admin");
			otherMenu.put(7, "login");
			otherMenu.put(8, "logout");
			otherMenu.put(9, "mypage");
			
			for (HashMap.Entry<Integer, String> entry : otherMenu.entrySet()) {
				int menuNum = entry.getKey();
				String menuName = entry.getValue();
				String name = menuProp.getProperty("menu." + menuNum + ".name");
				String link = menuProp.getProperty("menu." + menuNum + ".link");
				if (name != null && link != null) {
					MenuVO vo = new MenuVO(name, link);
					config.getServletContext().setAttribute(menuName, vo);
					System.out.println("그외 메뉴 : " + menuName + " / " + name + " / " + link);
				}
			}
			System.out.println("메뉴 프로퍼티 완료!");
		} catch (Exception e) {
			System.out.println("int menu Exception : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		String cmd = uri.substring(uri.lastIndexOf("/") + 1); // ex: login.do
		
		SobiAction action = map.get(cmd);
		
		if (action != null) {
			System.out.println(">>> 액션 실행: " + cmd + " → " + action.getClass().getName());
			String viewPage = null;
			try {
				viewPage = action.process(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (viewPage != null) {
				if (viewPage.startsWith("redirect:")) {
					String path = viewPage.substring("redirect:".length());
					response.sendRedirect(request.getContextPath() + path);
				} else if (viewPage.endsWith(".jsp")) {
					request.setAttribute("contentPage", viewPage);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/v1/views/common/layout.jsp");
					dispatcher.forward(request, response);
				} else {
					response.setContentType("application/json;charset=UTF-8");
					response.getWriter().print(viewPage);
				}
			}
		} else {
			// 액션이 없는 경우 기본 JSP 처리
			String page = uri.substring(uri.lastIndexOf("/") + 1, uri.lastIndexOf("."));
			String folder = uri.split("/")[uri.split("/").length - 2];
			
			String contentPage = "/v1/views/" + folder + "/" + page + ".jsp";
			String title = page.toUpperCase();
			
			request.setAttribute("contentPage", contentPage);
			request.setAttribute("title", title);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/v1/views/common/layout.jsp");
			dispatcher.forward(request, response);
			
			System.out.println("uri: " + uri);
			System.out.println("page: " + page);
			System.out.println("folder: " + folder);
			System.out.println("contentPage: " + contentPage);
			System.out.println("-----------------");
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("-----------------");
		System.out.println("doGet start!");
		process(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPost start!");
		process(request, response);
	}
}