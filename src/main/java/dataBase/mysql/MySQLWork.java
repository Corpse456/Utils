package dataBase.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dataBase.DataBaseWork;

public class MySQLWork extends DataBaseWork {
	
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
    public MySQLWork (String dbUrl, String user, String pass) {
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
		List<List<String>> answer = new ArrayList<>();

        try (ResultSet res = stat.executeQuery(query)) {
            while (res.next()) {
                List<String> row = new ArrayList<>();
                int colAmount = res.getMetaData().getColumnCount();

                for (int i = 1; i <= colAmount; i++) {
                    row.add(res.getString(i));
                }
                answer.add(row);
            }
        } catch (SQLException e) {
            System.out.println(query);
            e.printStackTrace();
        }
        return answer;
	}

	@Override
	public List<String> executeCustomQueryFirstColumn(String query) {
		List<String> answer = new ArrayList<>();

        try (ResultSet res = stat.executeQuery(query)) {
            while (res.next()) {
                answer.add(res.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answer;
	}

	@Override
	public List<List<String>> findColumnsOfSomeName(String tableName, String column, String name, String... columnNames) {
		String columnName = "";
        for (int i = 0; i < columnNames.length; i++) {
            columnName += columnNames[i];
            
            if (i != columnNames.length - 1) columnName += ",";
        }
        
        name = name.replace(" ", "%").replaceAll("'", "''");
        
        String query = "SELECT " + columnName + " FROM " + tableName + " WHERE " + column + " like '" + name + "'";
        System.out.println(query);
         
        List<List<String>> answer = executeCustomQuery(query);
        
        return answer;
	}

	@Override
	public boolean eraseAll(String tableName) {
		String query = "DELETE FROM " + tableName;
        boolean execute = false;
        try {
            execute = stat.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return execute;
        }
        return execute;
	}

	@Override
	public void close() {
		if (stat != null) try {
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (conn != null) try {
            conn.close();
        } catch (SQLException e) {
				e.printStackTrace();
		}
	}
}
