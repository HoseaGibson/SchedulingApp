/**
 * Database connector that will open a valid connection to database
 * when program is ran and closes the connection when it is closed
 */
package DBManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectorData {
    private static final String DB_NAME = "U05lgp";
    private static final String DB_URL = "jdbc:mysql://52.206.157.109/"+DB_NAME;
    private static final String USERNAME = "U05lgp";
    private static final String PASSWORD = "53688538601";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
            
    static Connection DB_CONN;
    
    public static void openConnection() throws ClassNotFoundException, SQLException, Exception
    {
        Class.forName(DRIVER);
        DB_CONN = (Connection) DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        System.out.println("Connection Opened Successfully");
    }
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception
    {
        DB_CONN.close();
        System.out.println("Connection Closed Successfully");
    }
    
     //Returns Connection
    public static Connection getConn(){
    
        return DB_CONN;
    }
}
