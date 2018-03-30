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
    boolean listToDataBase(List<String[]> list, String tableName);

    /**
     * @param tableName - the name of the table with which operations will be performed
     * @param values the values of columns
     * @return
     */
    boolean insertInto(String tableName, String... values);

    /**
     * @param tableName - the name of the table with which operations will be performed
     * @return all column names for this table
     */
    List<String> columnNames(String tableName);

    
    /**
     * @param query  - SQL query 
     * @return <b>true</b> if operation was successful</br>
     *         <b>false</b> otherwise
     */
    boolean customQuery (String query);
    
    /**
     * @param query - SQL query 
     * @return rows and columns satisfying the request
     */
    List<List<String>> executeCustomQuery(String query);
    
    /**
     * @param query - SQL query 
     * @return rows and first column satisfying the request
     */
    List<String> executeCustomQueryFirstColumn (String query);

    /**
     * @param tableName - the name of the table with which operations will be performed
     * @return <b>true</b> if deletion was successful</br>
     *         <b>false</b> otherwise
     */
    boolean eraseAll(String tableName);

    /**
     *  Closes the connection to the database
     */
    void close();
}
