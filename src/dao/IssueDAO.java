package dao;

import java.sql.*;
import java.util.*;
import db.DBConnection;
import models.Issue;

public class IssueDAO {

    // Get all issue records
    public static List<Issue> getAllIssues() {
        List<Issue> issues = new ArrayList<>();
        String sql = "SELECT * FROM issue";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                issues.add(new Issue(
                        rs.getInt("issue_id"),
                        rs.getInt("book_id"),
                        rs.getInt("member_id"),
                        rs.getDate("issue_date"),
                        rs.getDate("due_date"),
                        rs.getDate("return_date")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return issues;
    }

    // Issue a book
    public static boolean issueBook(int bookId, int memberId, java.sql.Date dueDate) {
        String sql = "INSERT INTO issue (book_id, member_id, due_date) VALUES (?, ?, ?)";
        String updateBook = "UPDATE books SET available_copies = available_copies - 1 WHERE book_id = ? AND available_copies > 0";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst1 = conn.prepareStatement(sql);
                PreparedStatement pst2 = conn.prepareStatement(updateBook)) {

            conn.setAutoCommit(false);

            pst1.setInt(1, bookId);
            pst1.setInt(2, memberId);
            pst1.setDate(3, dueDate);
            pst1.executeUpdate();

            pst2.setInt(1, bookId);
            pst2.executeUpdate();

            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Return a book
    public static boolean returnBook(int bookId, int memberId, java.sql.Date returnDate) {
        String sql = "UPDATE issue SET return_date=? WHERE book_id=? AND member_id=? AND return_date IS NULL";
        String updateBook = "UPDATE books SET available_copies = available_copies + 1 WHERE book_id=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst1 = conn.prepareStatement(sql);
                PreparedStatement pst2 = conn.prepareStatement(updateBook)) {

            conn.setAutoCommit(false);

            pst1.setDate(1, returnDate);
            pst1.setInt(2, bookId);
            pst1.setInt(3, memberId);
            pst1.executeUpdate();

            pst2.setInt(1, bookId);
            pst2.executeUpdate();

            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Add inside IssueDAO.java
    public static int getIssuedBookCountByMember(int memberId) {
        String sql = "SELECT COUNT(*) FROM issue WHERE member_id = ? AND return_date IS NULL";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, memberId);
            ResultSet rs = pst.executeQuery();
            if (rs.next())
                return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
