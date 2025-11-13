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
                        rs.getString("membership")
                    );
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
}
