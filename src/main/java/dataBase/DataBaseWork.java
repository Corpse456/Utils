package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import workWithFiles.fileIO.ReaderFromFile;

/**
 * Operations with data base
 * 
 * @author Neznaev_AI
 *
 */
public abstract class  DataBaseWork {
    
	public static final String SELECT_COLUMN_NAME_WITH_DEFAULT = "SELECT column_name, column_default FROM information_schema.columns WHERE table_name = '";
    public static final String SELECT_COLUMN_NAME = "SELECT column_name FROM information_schema.columns WHERE table_name = '";
    public Connection conn;
    public Statement stat;
    public String dbUrl;
    public String user;
    public String password;

    /**
     * opens a connection to the database
     */
    public void openConnection () {
        try {
            conn = DriverManager.getConnection(dbUrl, user, password);
            stat = conn.createStatement();
        } catch (SQLException e) {
        	System.err.println("SQL problem:");
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * @param list containing the values of rows and columns
     * @param tableName - the name of the table with which operations will be performed
     * @return <b>true</b> if operation was successful;</br>
     *         <b>false</b> otherwise
     *         
     */
    public boolean insertlistToDataBase(List<List<String>> values, String tableName) {
    	List<String> columnNames = columnNamesWithoutSerial(tableName);
        String query = queryShaper(tableName, columnNames, values);
        if (!customQuery(query)) return false;
        return true;
	}

    /**
     * @param path to csv-file
     * @param tableName - name of table to insert
     * @return <b>true</b> if operation was successful;</br>
     *         <b>false</b> otherwise
     */
    public boolean csvToDataBase(String path, String tableName, String delimiter) {
    	ReaderFromFile reader = new ReaderFromFile(path);
        List<List<String>> content = new ArrayList<>();

        while (reader.isReady()) {
            String[] split = reader.readLine().split(delimiter);
            content.add(Arrays.asList(split));
        }

        return insertlistToDataBase(content, tableName);
	}
    
    /**
     * @param tableName - the name of the table with which operations will be performed
     * @param the values of columns
     * @return
     */
    public boolean insertInto(String tableName, String... values) {
    	List<String> columnNames = columnNamesWithoutSerial(tableName);
        List<List<String>> valuesList = new ArrayList<>();
        List<String> asList = Arrays.asList(values);
        valuesList.add(asList);

        String query = queryShaper(tableName, columnNames, valuesList);
        return customQuery(query);
	}
    
    /**
     * @param tableName
     * @param columnNames
     * @param values
     * @return
     */
    private String queryShaper (String tableName, List<String> columnNames, List<List<String>> values) {
        StringBuilder query = new StringBuilder();

        query.append("INSERT INTO ");
        query.append(tableName);
        inBraces(columnNames, query, "");
        query.append(" values ");
        for (int i = 0; i < values.size(); i++) {
            inBraces(values.get(i), query, "'");
            if (i != values.size() - 1) query.append(", ");
        }
        query.append(";");

        return query.toString();
    }

    private void inBraces (List<String> values, StringBuilder query, String quotes) {
        query.append("(" + quotes);
        for (int i = 0; i < values.size(); i++) {
            if (!values.get(i).isEmpty()) query.append(values.get(i).replaceAll("'", "''"));
            if (i != values.size() - 1) {
                query.append(quotes + ", " + quotes);
            }
        }
        query.append(quotes + ")");
    }
    
    /**
     * @param tableName - the name of the table with which operations will be performed
     * @return the values of columns without ID column
     */
    public List<String> columnNamesWithoutSerial (String tableName) {
        List<String> columnNames = new ArrayList<>();
        String query = SELECT_COLUMN_NAME_WITH_DEFAULT + tableName + "'";

        List<List<String>> columnWithDefault = executeCustomQuery(query);
        for (List<String> list : columnWithDefault) {
            if (list.get(1) == null || !list.get(1).contains("nextval")) columnNames.add(list.get(0));
        }
        return columnNames;
    }

    /**
     * @param tableName - the name of the table with which operations will be performed
     * @return all column names for this table
     */
    public List<String> columnNames(String tableName) {
    	List<String> columnNames = new ArrayList<>();
        String query = SELECT_COLUMN_NAME + tableName + "'";

        List<String> columnWithDefault = executeCustomQueryFirstColumn(query);
        for (String line : columnWithDefault) {
            columnNames.add(line);
        }
        return columnNames;
	}

    
    /**
     * @param query  - SQL query 
     * @return <b>true</b> if operation was successful</br>
     *         <b>false</b> otherwise
     */
    public boolean customQuery (String query) {
    	int execute = 0;

        try {
            execute = stat.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(query);
            return false;
        }
        return execute > 0;
	}
    
    /**
     * @param query - SQL query 
     * @return rows and columns satisfying the request
     */
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
    
    /**
     * @param query - SQL query 
     * @return rows and first column satisfying the request
     */
    public List<String> executeCustomQueryFirstColumn (String query) {
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
    
    /**
     * @param tableName - the name of the table with which operations will be performed
     * @param columnName - name of column where needs to find
     * @param name - the element whose id must be found
     * @return
     */
    public List<List<String>> findColumnsOfSomeName(String tableName, String column, String name, String ...columnNames) {
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

    /**
     * @param tableName - the name of the table with which operations will be performed
     * @return <b>true</b> if deletion was successful</br>
     *         <b>false</b> otherwise
     */
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

    /**
     *  Closes the connection to the database
     */
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
