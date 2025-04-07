import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Database {
	public static void main (String[] args) throws ClassNotFoundException {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;

		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/JobInfo?user=root&password=Soccer33!");
	        String query = """
	                SELECT *
	                FROM Users
	            """;

	            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
	                System.out.printf("%-10s | %-18s%n", "Username", "Password");
	                System.out.println("-----------------------------");
	                while (rs.next()) {
	                    String username = rs.getString("username");
	                    String password = rs.getString("password");
	                    System.out.printf("%-18s | %-18s", username, password);
	                }
	            }
	            
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} finally {
			try {
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}
}
