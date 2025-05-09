package sobi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionProvider {
	
	public static final String KAKAO_REST_API_KEY = "284beb883703b498906a57f5a12b5543";
    public static final String KAKAO_REDIRECT_URI = "http://localhost:8080/v1/view/member/kakao_login_callback.do";
    public static Connection getConnection() {
        Connection conn = null;
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://kosta-semi.c1rvwthbmnyb.ap-northeast-2.rds.amazonaws.com:3306/semi_sobi?serverTimezone=Asia/Seoul&characterEncoding=UTF-8";
            String username = "admin";
            String password = "zxasqw12!!";

            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println("예외발생:" + e.getMessage());
        }
        return conn;
    }

    public static void close(Connection conn, Statement stmt) {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close(); // 수정했어
        } catch (Exception e) {
            System.out.println("예외발생:" + e.getMessage());
        }
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close(); // 수정했어
        } catch (Exception e) {
            System.out.println("예외발생:" + e.getMessage());
        }
    }
}
