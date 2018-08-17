package dataBase.postgre;

import dataBase.DataBaseWork;

/**
 * Operation with Postrge DB
 * 
 * @author Neznaev_AI
 *
 */
public class PostgreSQL extends DataBaseWork {

    private static String DB_URL = "jdbc:postgresql://localhost:5432/";
    private static String USER = "postgres";
    private static String PASSWORD = "admin";

    /**
     * @param dbUrl - data base URL with table name
     * @param user - username
     * @param pass - password
     */
    public PostgreSQL (String dbUrl, String user, String pass) {
        this.dbUrl = dbUrl;
        this.user = user;
        this.password = pass;
        openConnection();
    }

    /**
     * @param dataBaseName - the name of the date base with which operations
     *            will be performed
     */
    public PostgreSQL (String dataBaseName) {
        dbUrl = DB_URL + dataBaseName;
        user = USER;
        password = PASSWORD;
        openConnection();
    }
}
