package dao;
import java.sql.*;
import java.util.*;
import db.DBConnection;
import models.Member;

public class MemberDAO {

    // Fetch all members
    public static List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                members.add(new Member(
                    rs.getInt("member_id"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getString("email"),
                    rs.getLong("phone"),
                    rs.getDate("join_date"),
                    rs.getString("membership")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return members;
    }

    // Add new member
    public static boolean addMember(Member m) {
        String sql = "INSERT INTO members (name, department, email, phone, join_date, membership) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, m.getName());
            pst.setString(2, m.getDepartment());
            pst.setString(3, m.getEmail());
            pst.setLong(4, m.getPhone());
            pst.setDate(5, m.getJoinDate());
            pst.setString(6, m.getMembership());
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update existing member
    public static boolean updateMember(Member m) {
        String sql = "UPDATE members SET name=?, department=?, email=?, phone=?, join_date=?, membership=? WHERE member_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, m.getName());
            pst.setString(2, m.getDepartment());
            pst.setString(3, m.getEmail());
            pst.setLong(4, m.getPhone());
            pst.setDate(5, m.getJoinDate());
            pst.setString(6, m.getMembership());
            pst.setInt(7, m.getMemberId());
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete member
    public static boolean deleteMember(int memberId) {
        String sql = "DELETE FROM members WHERE member_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, memberId);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

