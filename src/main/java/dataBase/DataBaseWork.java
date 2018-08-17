package dataBase;

import java.util.List;

/**
 * Operations with data base
 * 
 * @author Neznaev_AI
 *
 */
public abstract class  DataBaseWork {
    
    /**
     * @param list containing the values of rows and columns
     * @param tableName - the name of the table with which operations will be performed
     * @return <b>true</b> if operation was successful;</br>
     *         <b>false</b> otherwise
     *         
     */
    public boolean insertlistToDataBase(List<List<String>> list, String tableName) {
		return false;
	}

    /**
     * @param path to csv-file
     * @param tableName - name of table to insert
     * @return <b>true</b> if operation was successful;</br>
     *         <b>false</b> otherwise
     */
    public boolean csvToDataBase(String path, String tableName, String delimiter) {
		return false;
	}
    
    /**
     * @param tableName - the name of the table with which operations will be performed
     * @param values the values of columns
     * @return
     */
    public boolean insertInto(String tableName, String... values) {
		return false;
	}

    /**
     * @param tableName - the name of the table with which operations will be performed
     * @return all column names for this table
     */
    public List<String> columnNames(String tableName) {
		return null;
	}

    
    /**
     * @param query  - SQL query 
     * @return <b>true</b> if operation was successful</br>
     *         <b>false</b> otherwise
     */
    public boolean customQuery (String query) {
		return false;
	}
    
    /**
     * @param query - SQL query 
     * @return rows and columns satisfying the request
     */
    public List<List<String>> executeCustomQuery(String query) {
		return null;
	}
    
    /**
     * @param query - SQL query 
     * @return rows and first column satisfying the request
     */
    public List<String> executeCustomQueryFirstColumn (String query) {
		return null;
	}
    
    /**
     * @param tableName - the name of the table with which operations will be performed
     * @param columnName - name of column where needs to find
     * @param name - the element whose id must be found
     * @return
     */
    public List<List<String>> findColumnsOfSomeName(String tableName, String column, String name, String ...columnName) {
		return null;
	}

    /**
     * @param tableName - the name of the table with which operations will be performed
     * @return <b>true</b> if deletion was successful</br>
     *         <b>false</b> otherwise
     */
    public boolean eraseAll(String tableName) {
		return false;
	}

    /**
     *  Closes the connection to the database
     */
    public void close() {}
}
