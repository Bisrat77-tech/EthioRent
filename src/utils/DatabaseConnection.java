package utils;
import java.net.URL;
import java.sql.*;

public class DatabaseConnection {
    private static final String PASSWORD = "Myserver123##";
    private static final String URL = "EthioRent_db";
    private static final String USERNAME = "root";
    private static Connection connection = null;

    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("Connected to MySQL Database!");
        }catch (ClassNotFoundException | SQLException e){
            System.out.println("Database connection failed: " + e.getMessage());
        }
        return connection;
    }
    public static void closeConnection(){
        try {
            if (connection != null && !connection.isClosed()){
                connection.close();
            }
        }catch (SQLException e){
            System.out.println("Enter closing connection: " + e.getMessage());
        }
    }
    public static boolean testConnection(){
        try {
            getConnection();
            return connection != null && !connection.isClosed();
        }catch (Exception e){
            return false;
        }
    }
}
