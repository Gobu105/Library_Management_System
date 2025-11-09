package db;
import java.sql.*;

public class DBConnection {
    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/library_db", // DB URL
                    "root",   // <-- username
                    "root"    // <-- password
                );
                System.out.println("✅ Database Connected Successfully!");
            } catch (Exception e) {
                System.out.println("❌ DB Connection Failed: " + e.getMessage());
            }
        }
        return conn;
    }
}

