package dao;

import java.sql.*;
import db.DBConnection;
import models.Member;

public class LoginDAO {

    public static Object authenticate(String username, String password) {
        String sql = "SELECT l.role, l.member_id, m.name, m.department, m.email, m.phone, m.join_date, m.membership " +
                "FROM login l LEFT JOIN members m ON l.member_id = m.member_id " +
                "WHERE l.username=? AND l.password=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                if ("member".equalsIgnoreCase(role)) {
                    Member m = new Member(
                            rs.getInt("member_id"),
                            rs.getString("name"),
                            rs.getString("department"),
                            rs.getString("email"),
                            rs.getLong("phone"),
                            rs.getDate("join_date"),
                            rs.getString("membership"));
                    return m;
                } else {
                    return role; // staff/admin
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update admin username and password
    public static boolean updateAdminProfile(String oldUsername, String newUsername, String newPassword) {
        String sql = "UPDATE login SET username = ?, password = ? WHERE username = ? AND role = 'staff'";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, newUsername);
            pst.setString(2, newPassword);
            pst.setString(3, oldUsername);

            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Fetch admin basic info
    public static ResultSet getAdminProfile(String username) {
        String sql = """
                    SELECT s.name, s.role, l.username
                    FROM login l
                    JOIN staff s ON l.staff_id = s.staff_id
                    WHERE l.username = ? AND l.role = 'staff'
                """;

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            return pst.executeQuery(); // caller must close later
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Fetch library statistics (books, members, issues, fines)
    public static int getCount(String tableName) {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        try (Connection conn = DBConnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            if (rs.next())
                return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
