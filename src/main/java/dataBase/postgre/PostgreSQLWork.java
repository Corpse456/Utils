package dataBase.postgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dataBase.DataBaseWork;
import workWithFiles.fileIO.ReaderFromFile;

/**
 * Operation with Postrge DB
 * 
 * @author Neznaev_AI
 *
 */
public class PostgreSQLWork implements DataBaseWork {

    private static final String SELECT_COLUMN_NAME_WITH_DEFAULT = "SELECT column_name, column_default FROM information_schema.columns WHERE table_name = '";
    private static final String SELECT_COLUMN_NAME = "SELECT column_name FROM information_schema.columns WHERE table_name = '";
    private String dbUrl = "jdbc:postgresql://localhost:5432/";
    private String user = "postgres";
    private String password = "admin";
    private Connection conn;
    private Statement stat;

    /**
     * @param dbUrl - data base URL with table name
     * @param user - username
     * @param pass - password
     */
    public PostgreSQLWork (String dbUrl, String user, String pass) {
        this.dbUrl = dbUrl;
        this.user = user;
        this.password = pass;
        openConnection();
    }

    /**
     * @param dataBaseName - the name of the date base with which operations
     *            will be performed
     */
    public PostgreSQLWork (String dataBaseName) {
        dbUrl = dbUrl + dataBaseName;
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
            e.printStackTrace();
        }
    }

    @Override
    public boolean csvToDataBase (String path, String tableName, String delimiter) {
        ReaderFromFile reader = new ReaderFromFile(path);
        List<List<String>> content = new ArrayList<>();

        while (reader.isReady()) {
            String[] split = reader.readLine().split(delimiter);
            content.add(Arrays.asList(split));
        }

        return insertlistToDataBase(content, tableName);
    }

    @Override
    public boolean insertlistToDataBase (List<List<String>> values, String tableName) {
        List<String> columnNames = columnNamesWithoutSerial(tableName);
        String query = queryShaper(tableName, columnNames, values);
        if (!customQuery(query)) return false;
        return true;
    }

    @Override
    public boolean insertInto (String tableName, String ...values) {
        List<String> columnNames = columnNamesWithoutSerial(tableName);
        List<List<String>> valuesList = new ArrayList<>();
        List<String> asList = Arrays.asList(values);
        valuesList.add(asList);

        String query = queryShaper(tableName, columnNames, valuesList);
        return customQuery(query);
    }

    @Override
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

    @Override
    public List<String> columnNames (String tableName) {
        List<String> columnNames = new ArrayList<>();
        String query = SELECT_COLUMN_NAME + tableName + "'";

        List<String> columnWithDefault = executeCustomQueryFirstColumn(query);
        for (String line : columnWithDefault) {
            columnNames.add(line);
        }
        return columnNames;
    }

    private List<String> columnNamesWithoutSerial (String tableName) {
        List<String> columnNames = new ArrayList<>();
        String query = SELECT_COLUMN_NAME_WITH_DEFAULT + tableName + "'";

        List<List<String>> columnWithDefault = executeCustomQuery(query);
        for (List<String> list : columnWithDefault) {
            if (list.get(1) == null || !list.get(1).contains("nextval")) columnNames.add(list.get(0));
        }
        return columnNames;
    }

    @Override
    public List<List<String>> executeCustomQuery (String query) {
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

    @Override
    public boolean eraseAll (String tableName) {
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
    public void close () {
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

    public static void main (String ...args) {
        List<String> list = new ReaderFromFile(args[0]).readAllAsLIst();

        PostgreSQLWork db = new PostgreSQLWork("Games");
        long time = System.currentTimeMillis();
        for (String string : list) {
            boolean done = db.insertInto("games", string.split(";"));
            if (!done) {
                System.out.println("Hren");
                break;
            }
        }
        db.close();
        System.out.println(System.currentTimeMillis() - time / 1000.0 / 60);
    }
}
