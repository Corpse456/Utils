package dataBase.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import dataBase.DataBaseWork;

public class MySQL  implements DataBaseWork {
	
	private String dbUrl = "jdbc:mysql://localhost:3306/propertyview";
    private String user = "propertyview";
    private String password = "propertyview";
    private Connection conn;
    private Statement stat;

    /**
     * @param dbUrl - data base URL with table name
     * @param user - username
     * @param pass - password
     */
    public MySQL (String dbUrl, String user, String pass) {
        this.dbUrl += dbUrl;
        this.user = user;
        this.password = pass;
        openConnection();
    }
    
    /**
     * opens a connection to the database
     */
    private void openConnection () {
        try {
            conn = DriverManager.getConnection(dbUrl, user, password);
            stat = conn.createStatement();
        } catch (SQLException e) {
        	System.err.println("SQL problem:");
            System.out.println(e.getMessage());
        }
    }

	@Override
	public boolean insertlistToDataBase(List<List<String>> list, String tableName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean csvToDataBase(String path, String tableName, String delimiter) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertInto(String tableName, String... values) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> columnNames(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean customQuery(String query) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<List<String>> executeCustomQuery(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> executeCustomQueryFirstColumn(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<List<String>> findColumnsOfSomeName(String tableName, String column, String name,
			String... columnName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean eraseAll(String tableName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
}
