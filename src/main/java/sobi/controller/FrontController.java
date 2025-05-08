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

/**
 * Servlet implementation class FrontController
 */
@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HashMap<String, SobiAction> map = new HashMap<String, SobiAction>();
	
    public void init(ServletConfig config) throws ServletException{
		String path = config.getServletContext().getRealPath("WEB-INF");
		// System.out.println("path : "+path);
		// path : /Users/wang_si/semi_sobi/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/semi_sobi/WEB-INF
		try {
			Reader reader = new FileReader(path + "/sobi.properties");
			Properties prop = new Properties();
			prop.load(reader);
			
			Iterator iter = prop.keySet().iterator();
			while(iter.hasNext()) {
				String key = (String)iter.next();
				String clsName = prop.getProperty(key);
				/* 모든 프로퍼티의 키, 값이 나
				System.out.println("key : " + key);
				System.out.println("clsName : " + clsName);
				*/
				Object obj = Class.forName(clsName).newInstance();	// 객체화 처리 
				map.put(key, (SobiAction)obj); // 객체화 해준 클래스 key, value 를 map 에 넣는다
			}
			System.out.println("front action 완료!");
		}catch(Exception e) {
			System.out.println("int action Exception : "+e.getMessage());
			e.printStackTrace();
		}
		
		
		try {
			Reader menuReader = new FileReader(path + "/sobi.menu.properties");
			Properties menuProp = new Properties();
			menuProp.load(menuReader);
			System.out.println("메뉴 프로퍼티 로드 완료!");
			
			ArrayList<MenuVO> menuList = new ArrayList<>();
			for(int i = 2; i <= 5; i++) {
				String name = menuProp.getProperty("menu."+i+".name");
				String link = menuProp.getProperty("menu."+i+".link");

				if(name != null && link != null) {
					menuList.add(new MenuVO(name,link));
					System.out.println("기본 메뉴 : "+i+" / "+name+" / "+link);
				}
			}
			config.getServletContext().setAttribute("menuList", menuList);
			
			HashMap<Integer, String> otherMenu = new HashMap<Integer, String>();
			// 1,6,7,8,9,
			otherMenu.put(1,"main");
			otherMenu.put(6,"admin");
			otherMenu.put(7,"login");
			otherMenu.put(8,"logout");
			otherMenu.put(9,"mypage");
			System.out.println(otherMenu); 
			
			for(HashMap.Entry<Integer, String> entry : otherMenu.entrySet()) {
				int menuNum = entry.getKey();
				String menuName = entry.getValue();
				
				String name = menuProp.getProperty("menu."+menuNum+".name");
				String link = menuProp.getProperty("menu."+menuNum+".link");
				
				if(name != null && link != null) {
					MenuVO vo = new MenuVO(name,link);
					System.out.println("그외 메뉴 : "+menuName+" / "+name+" / "+link);
					config.getServletContext().setAttribute(menuName, vo);
				}
			}
			System.out.println("메뉴 프로퍼티 완료!");
		} catch (Exception e) {
			System.out.println("int menu Exception : "+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
     * @see HttpServlet#HttpServlet()
     */
    public FrontController() {
        super();
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String uri =request.getRequestURI(); // 현재 주소 
		String cmd = uri.substring(uri.lastIndexOf("/") + 1); // 예: testAction.do

	    // 액션 꺼내오기
	    SobiAction action = map.get(cmd);
	    if (action != null) {
	        System.out.println(">>> 액션 실행: " + cmd + " → " + action.getClass().getName());
	        String viewPage = action.process(request, response);  // 액션 실행

	        if (viewPage != null) {
	            if (viewPage.endsWith(".do")) {
	                response.sendRedirect(viewPage);
	            } else if (viewPage.endsWith(".jsp")) {
	                request.setAttribute("contentPage", viewPage);
	                RequestDispatcher dispatcher = request.getRequestDispatcher("/v1/views/common/layout.jsp");
	                dispatcher.forward(request, response);
	            } else {
	                response.setContentType("application/json;charset=UTF-8");
	                response.getWriter().print(viewPage);
	            }
	        }
	    }
		
		else {
			// 액션이 없을경우
			String page = uri.substring(uri.lastIndexOf("/") + 1, uri.lastIndexOf(".")); // .do 를 뺀 주소
			// ex) /sobi_test/board/content.do → page = "content"
			
			String folder = uri.split("/")[uri.split("/").length -2];
			// ex) "board"
			
			String contentPage = "/v1/views/" + folder + "/" + page + ".jsp"; // /WEB-INF/
			String title = page.toUpperCase(); // title만 변경
			
			request.setAttribute("contentPage", contentPage);
			request.setAttribute("title", title);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/v1/views/common/layout.jsp"); // /WEB-INF
			dispatcher.forward(request, response);
			
			System.out.println("uri: " + uri);
			System.out.println("page: " + page);
			System.out.println("folder: " + folder);
			System.out.println("contentPage: " + contentPage);
			System.out.println("-----------------");
		}
		
		
		

	}
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("-----------------");
		System.out.println("doGet start!");
		process(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost start!");		
		process(request,response);
	}

}
