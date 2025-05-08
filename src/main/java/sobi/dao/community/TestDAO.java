package sobi.dao.community;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import sobi.db.ConnectionProvider;

public class TestDAO {
	
	
	public int getAll() {
		int re = -1;
		String sql = "SELECT * FROM CATEGORY c";
		
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				rs.getInt(1);
				rs.getString(2);
			}
			ConnectionProvider.close(conn, stmt, rs);
			
		} catch (Exception e) {
			System.out.println("ex : " +e.getMessage());
		}
		
		System.out.println("start!");
		return re;
	}
	
	
}
