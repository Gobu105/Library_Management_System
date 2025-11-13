package dao;

import java.sql.*;
import java.util.*;
import db.DBConnection;
import models.Member;

public class MemberDAO {

    // ✅ 1. Register a new member (insert into members + login)
    public static boolean registerMember(Member m, String username, String password) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement pst1 = null;
        PreparedStatement pst2 = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // 1️⃣ Insert into members
            String sql1 = "INSERT INTO members (name, department, email, phone, join_date, membership) VALUES (?, ?, ?, ?, ?, ?)";
            pst1 = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            pst1.setString(1, m.getName());
            pst1.setString(2, m.getDepartment());
            pst1.setString(3, m.getEmail());
            pst1.setLong(4, m.getPhone());
            pst1.setDate(5, new java.sql.Date(System.currentTimeMillis()));
            pst1.setString(6, m.getMembership());

            int affected = pst1.executeUpdate();

            if (affected > 0) {
                rs = pst1.getGeneratedKeys();
                if (rs.next()) {
                    int memberId = rs.getInt(1);

                    // 2️⃣ Insert into login
                    String sql2 = "INSERT INTO login (username, password, role, member_id) VALUES (?, ?, 'member', ?)";
                    pst2 = conn.prepareStatement(sql2);
                    pst2.setString(1, username);
                    pst2.setString(2, password);
                    pst2.setInt(3, memberId);
                    pst2.executeUpdate();
                }
                conn.commit();
                success = true;
            }
        } catch (Exception e) {
            System.out.println("❌ Registration Error: " + e.getMessage());
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.out.println("Rollback failed: " + ex.getMessage());
            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst1 != null) pst1.close();
                if (pst2 != null) pst2.close();
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException ignored) {}
        }
        return success;
    }

    // ✅ 2. Get all members from DB (for ManageMembersPanel)
    public static List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();

        String query = "SELECT member_id, name, department, email, phone, join_date, membership FROM members";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Member m = new Member(
                    rs.getInt("member_id"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getString("email"),
                    rs.getLong("phone"),
                    rs.getDate("join_date"),
                    rs.getString("membership")
                );
                members.add(m);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error loading members: " + e.getMessage());
        }

        return members;
    }
}
