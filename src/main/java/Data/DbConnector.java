package Data;

import Model.GameEventsLog;
import Model.UsersTypes.Judge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class DbConnector
{
    public static final String API = "jdbc";
    public static final String DATABASE = "mysql";
    public static final String SERVER_NAME_DB_RUNNING_ON = "localhost";
    public static final String PORT = "3306";
    public static final String DB_NAME = "football_system_db";
    public static final String URL = API + ":" + DATABASE + "://" + SERVER_NAME_DB_RUNNING_ON + ":" + PORT + "/" + DB_NAME;

    public static final String URL1 = "jdbc:mysql://localhost:3306/";
//    public static final String USER = "noy";
//    public static final String PASSWORD = "roiL0210";

//    public static final String USER = "noy";
    public static final String USER = "root";
//    public static final String PASSWORD = "roiL0210";
    public static final String PASSWORD = "root";

    private ArrayList<String> sqlCommandCreateTablesList = new ArrayList<>();

    private static final DbConnector instance = new DbConnector();

    //private constructor to avoid client applications to use constructor
    public static DbConnector getInstance()
    {
        return instance;
    }

    private DbConnector()
    {
    }

    /**
     * Get a connection to database
     * @return Connection object
     */
    public static Connection getConnection()
    {
        try
        {
//            Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            String urlNew = URL + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

            Connection connectionDb = DriverManager.getConnection(urlNew, USER, PASSWORD);
//            System.out.println("Connected to football_system_db");

            return connectionDb;
        }
        catch (SQLException | ClassNotFoundException ex)
        {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }

//    public void


    public static void main(String[] args) throws SQLException
    {
        System.out.println(DbConnector.URL);
//        System.out.println(DbConnector.URL1);
//        System.out.println(DbConnector.USER);
//        System.out.println(DbConnector.PASSWORD);

        System.out.println("BEFORE");

        DbConnector dbConnector = DbConnector.getInstance();

        Connection connection = dbConnector.getConnection();


//        String season1 = "sfsasf";
        String sql = "INSERT INTO Season " +
                "VALUES ( 'season1' );";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

        System.out.println("AFTER");
    }

}
