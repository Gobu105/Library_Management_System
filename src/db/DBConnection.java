package db;
import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASS = "root";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Always open a fresh connection
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            System.out.println("❌ DB Connection Failed: " + e.getMessage());
            return null;
        }
    }
}

