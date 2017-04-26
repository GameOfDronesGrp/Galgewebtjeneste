package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** @author Ronnie Dalsgaard */
public class Connector {
	private final String HOST     = "gruppe11sql.cxyxxbzjqour.us-west-2.rds.amazonaws.com";
    private final int    PORT     = 3306;
    private final String DATABASE = "GalgeLegDB";
    private final String USERNAME = "gruppe11"; 
    private final String PASSWORD = "gruppe11";
		
//    private final String HOST     = "Localhost";
//    private final int    PORT     = 3306;
//    private final String DATABASE = "Projekt";
//    private final String USERNAME = "root"; 
//    private final String PASSWORD = "";
    private static Connection connection;
    
    public Connector() throws ClassNotFoundException {
        try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
			connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
    }
    
    public Connection getConnection(){
    	return connection;
    }
    
    public ResultSet doQuery(String query) throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(query);
        return res;
    }
    
    public void doUpdate(String query) throws SQLException{
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(query);
    }
}