package persistencia.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_NAME = "\\DB";
	private static final String DB_USERNAME = "sa";
	private static final String DB_PASSWORD = "";

	public static Connection connect() {
		Connection c = null;
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		try {
			String url = "jdbc:h2:C:\\Users\\GG-Asus\\Desktop\\Cosas\\Code Projects\\administrador-de-proyectos\\database\\db";
			c = DriverManager.getConnection(url, "sa", "");
			c.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		return c;
	}
}
