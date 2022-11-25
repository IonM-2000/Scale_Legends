package scale_legends;

import java.sql.*;

public class DataBaseHandler {
	private String url;
	private String username;
	private String password;

	private Statement statement;
	private Connection connection;
	public ResultSet resultSet;

	private static DataBaseHandler singleInstance = null;

	private DataBaseHandler(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;

		this.statement = null;
		this.resultSet = null;
		this.connection = null;
	}

	public static DataBaseHandler getInstance() {
		if(singleInstance == null){
			singleInstance = new DataBaseHandler("jdbc:mysql://localhost", "root", "root");
		}
		return singleInstance;
	}

	public boolean connect() {
		try {
			this.connection = DriverManager.getConnection(url, username, password);
			this.statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			return true;
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return false;
	}

	public boolean disconnect() {
		try {
			this.connection.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean sendQuery(String query) {
		this.resultSet = null;
		try {
			this.resultSet = this.statement.executeQuery(query);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
