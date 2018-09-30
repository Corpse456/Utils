package dataBase.mysql;

import dataBase.DataBaseWork;

public class MySQLWork extends DataBaseWork {
	
	private static String DB_URL = "jdbc:mysql://localhost:3306/propertyview";
    private static String USER = "propertyview";
    private static String PASSWORD = "propertyview";

    /**
     * @param dbUrl - data base URL with table name
     * @param user - username
     * @param pass - password
     */
    public MySQLWork (String dbUrl, String user, String pass) {
        this.dbUrl = dbUrl;
        this.user = user;
        this.password = pass;
        openConnection();
    }

	public MySQLWork(String dataBaseName) {
		dbUrl = DB_URL + dataBaseName;
        user = USER;
        password = PASSWORD;
	}
}
