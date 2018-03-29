package dataBase;

import java.util.List;

public interface DataBaseWork {

    /**
     * @param tableName
     * @param args
     * @return
     */
    boolean insert(String tableName, String... args);

    /**
     * @param tableName - the name of the table with which operations will be performed
     * @return all columnNames for this table
     */
    List<String> columnNames(String tableName);

    /**
     * @param query - SQL query 
     * @return rows and columns satisfying the request
     */
    List<List<String>> executeCustomQuery(String query);

    /**
     * @param list
     * @param tableName - the name of the table with which operations will be performed
     * @return
     */
    boolean listToDataBase(List<String[]> list, String tableName);

    /**
     * @param tableName - the name of the table with which operations will be performed
     */
    void eraseAll(String tableName);

    /**
     *  Closes the connection to the database
     */
    void close();

}
