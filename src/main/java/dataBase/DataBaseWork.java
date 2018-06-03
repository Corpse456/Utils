package dataBase;

import java.util.List;

/**
 * Operations with data base
 * 
 * @author Neznaev_AI
 *
 */
public interface DataBaseWork {
    
    /**
     * @param list containing the values of rows and columns
     * @param tableName - the name of the table with which operations will be performed
     * @return <b>true</b> if operation was successful;</br>
     *         <b>false</b> otherwise
     *         
     */
    public boolean insertlistToDataBase(List<List<String>> list, String tableName);

    /**
     * @param path to csv-file
     * @param tableName - name of table to insert
     * @return <b>true</b> if operation was successful;</br>
     *         <b>false</b> otherwise
     */
    public boolean csvToDataBase(String path, String tableName, String delimiter);
    
    /**
     * @param tableName - the name of the table with which operations will be performed
     * @param values the values of columns
     * @return
     */
    public boolean insertInto(String tableName, String... values);

    /**
     * @param tableName - the name of the table with which operations will be performed
     * @return all column names for this table
     */
    public List<String> columnNames(String tableName);

    
    /**
     * @param query  - SQL query 
     * @return <b>true</b> if operation was successful</br>
     *         <b>false</b> otherwise
     */
    public boolean customQuery (String query);
    
    /**
     * @param query - SQL query 
     * @return rows and columns satisfying the request
     */
    public List<List<String>> executeCustomQuery(String query);
    
    /**
     * @param query - SQL query 
     * @return rows and first column satisfying the request
     */
    public List<String> executeCustomQueryFirstColumn (String query);
    
    /**
     * @param tableName - the name of the table with which operations will be performed
     * @param columnName - name of column where needs to find
     * @param name - the element whose id must be found
     * @return
     */
    public int findColumnsOfSomeName(String tableName, String column, String name, String ...columnName);

    /**
     * @param tableName - the name of the table with which operations will be performed
     * @return <b>true</b> if deletion was successful</br>
     *         <b>false</b> otherwise
     */
    public boolean eraseAll(String tableName);

    /**
     *  Closes the connection to the database
     */
    public void close();
}
