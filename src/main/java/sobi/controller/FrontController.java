package sobi.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FrontController
 */
@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontController() {
        super();
    }

    private void process(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		String uri =request.getRequestURI(); // 현재 주소 
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
