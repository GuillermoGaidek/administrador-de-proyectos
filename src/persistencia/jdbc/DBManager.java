package persistencia.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_URL = "jdbc:h2:C:\\Users\\GG-Asus\\Desktop\\Cosas\\Code Projects\\administrador-de-proyectos\\database\\db";
	private static final String DB_USERNAME = "sa";
	private static final String DB_PASSWORD = "";
	private static Connection c = null;

	public static Connection connect() {
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		
		try {
			c = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			c.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		
		return c;
	}
	
	
	public static void close() throws SQLException {
		if(c != null) {
			if(!c.isClosed()) {
				c.close();
			}
		}
	}
	
	
}