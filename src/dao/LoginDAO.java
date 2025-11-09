package dao;
import java.sql.*;
import db.DBConnection;

public class LoginDAO {
    public static String authenticate(String username, String password) {
        String sql = "SELECT role FROM login WHERE username=? AND password=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) return rs.getString("role");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

