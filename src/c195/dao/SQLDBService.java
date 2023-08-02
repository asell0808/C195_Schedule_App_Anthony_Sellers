package c195.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Anthony Sellers
 */
public class SQLDBService {
    private static Connection connection;



    /**
     *
     * It is a constructor.
     *
     */
    public SQLDBService() {}

    /**
     * Connects to the SQL Database.
     */
    public static void connect() {


        try(FileInputStream fileInputStream = new FileInputStream("src/c195/config.properties")) {
            final Properties properties = new Properties();
            properties.load(fileInputStream);

            final String protocol = properties.getProperty("database.protocol");
            final String vendor = properties.getProperty("database.vendor");
            final String location = properties.getProperty("database.location");
            final String databaseName = properties.getProperty("database.name");
            final String driver = properties.getProperty("database.driver");
            final String username = properties.getProperty("database.username");
            final String password = properties.getProperty("database.password");

            final String jdbcUrl = protocol + ":" + vendor + ":" + "//" + location + "/" + databaseName + "?connectionTimeZone = SERVER";

            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connection Successful");
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnects from the SQL Database.
     */
    public static void disconnect() {

        try {
            connection.close();
            System.out.println("Disconnected From MySQL Database");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }


    /**
     *
     * Gets the connection
     *
     * @return the connection
     */
    public static Connection getConnection() {

        return connection;
    }
}

