package sobi.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import sobi.action.review.*;

public class DispatcherServlet extends HttpServlet {
    private Map<String, Action> commandMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        try {
            // /WEB-INF/action.properties 파일 로드
            String path = getServletContext().getRealPath("/WEB-INF/action.properties");
            Properties props = new Properties();
            props.load(new FileInputStream(path));

            // 각 명령어에 해당하는 Action 객체 생성하여 맵에 저장
            for (String key : props.stringPropertyNames()) {
                String className = props.getProperty(key);
                Class<?> clazz = Class.forName(className);
                Action action = (Action) clazz.getDeclaredConstructor().newInstance();
                commandMap.put(key, action);
            }
        } catch (Exception e) {
            throw new ServletException("DispatcherServlet 초기화 실패", e);
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String uri = request.getServletPath().substring(1); // ex) reviewDetail.do
        Action action = commandMap.get(uri);

        if (action == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "요청 경로가 올바르지 않습니다: " + uri);
            return;
        }

        try {
            ActionForward forward = action.execute(request, response);
            if (forward.isRedirect()) {
                response.sendRedirect(forward.getPath());
            } else {
                RequestDispatcher rd = request.getRequestDispatcher(forward.getPath());
                rd.forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("요청 처리 중 예외 발생: " + uri, e);
        }
    }
}