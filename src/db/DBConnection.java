package db;
import java.sql.*;

public class DBConnection {
    private static Connection conn = null;
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASS = "root";

    public static Connection getConnection() {
        try {
            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Reconnect if null OR closed
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("✅ Database Connected Successfully!");
            }
        } catch (Exception e) {
            System.out.println("❌ DB Connection Failed: " + e.getMessage());
        }
        return conn;
    }
}

