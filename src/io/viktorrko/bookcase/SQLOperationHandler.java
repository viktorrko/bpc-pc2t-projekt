package io.viktorrko.bookcase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLOperationHandler {
	private static Connection connection = SQLConnectionHandler.getInstance().getConnection();
		
	public static boolean executeStatement(String sql) {
		try (Statement statement = connection.createStatement()) {
			statement.execute(sql);
        }
		catch (SQLException e) {
            System.out.println("Error executing statement");
            return false;
        }
		return true;
	}
	
	public static ResultSet executeQueryStatement(String sql) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);
			
		return rs;
	}
	
	public static boolean isTableEmpty() {
		String sql = "SELECT COUNT(*) AS rowcount FROM books";
		
		try (ResultSet rs = executeQueryStatement(sql)) {
			if (rs != null && rs.getInt("rowcount") > 0)
				return false;
		}
		catch (SQLException e) {
			// System.out.println(e.getMessage());
		}
		
		return true;
	}
	
	public static void initializeTable() {
		String sql = "DROP TABLE IF EXISTS books";
		executeStatement(sql);
		
		sql = "CREATE TABLE IF NOT EXISTS books (\"type\" TEXT NOT NULL, title TEXT PRIMARY KEY NOT NULL, authors TEXT, \"year\" SMALLINT, available BOOLEAN, \"parameter\" TEXT);";
		executeStatement(sql);
		
		/*sql = "DELETE FROM books";
		executeStatement(sql);*/
	}
}
