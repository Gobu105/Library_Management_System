package dao;

import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Staff;

public class StaffDAO {

    public static List<Staff> getAllStaff() {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT s.staff_id, s.name, s.role, l.username, l.password FROM staff s JOIN login l ON s.staff_id = l.staff_id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(new Staff(
                        rs.getInt("staff_id"),
                        rs.getString("name"),
                        rs.getString("role"),
                        rs.getString("username"),
                        rs.getString("password")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean addStaff(String name, String username, String password, String role) {
        String insertStaff = "INSERT INTO staff(name, role) VALUES(?, ?)";
        String insertLogin = "INSERT INTO login(username, password, role, staff_id) VALUES(?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            PreparedStatement pst = conn.prepareStatement(insertStaff, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, name);
            pst.setString(2, role);
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            int staffId = 0;
            if (rs.next()) staffId = rs.getInt(1);

            PreparedStatement pst2 = conn.prepareStatement(insertLogin);
            pst2.setString(1, username);
            pst2.setString(2, password);
            pst2.setString(3, role);
            pst2.setInt(4, staffId);
            pst2.executeUpdate();

            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateStaff(int staffId, String name, String username, String password, String role) {
        String sql1 = "UPDATE staff SET name=?, role=? WHERE staff_id=?";
        String sql2 = "UPDATE login SET username=?, password=?, role=? WHERE staff_id=?";
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement pst1 = conn.prepareStatement(sql1);
            pst1.setString(1, name);
            pst1.setString(2, role);
            pst1.setInt(3, staffId);
            pst1.executeUpdate();

            PreparedStatement pst2 = conn.prepareStatement(sql2);
            pst2.setString(1, username);
            pst2.setString(2, password);
            pst2.setString(3, role);
            pst2.setInt(4, staffId);
            pst2.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteStaff(int staffId) {
        String sql1 = "DELETE FROM login WHERE staff_id=?";
        String sql2 = "DELETE FROM staff WHERE staff_id=?";
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement pst1 = conn.prepareStatement(sql1);
            pst1.setInt(1, staffId);
            pst1.executeUpdate();

            PreparedStatement pst2 = conn.prepareStatement(sql2);
            pst2.setInt(1, staffId);
            pst2.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
