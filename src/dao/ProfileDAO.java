package dao;

import java.sql.*;
import java.util.*;
import db.DBConnection;
import models.Book;
import models.Fine;

public class ProfileDAO {

    public static List<Book> getIssuedBooks(int memberId) {
        List<Book> books = new ArrayList<>();
        String query = """
            SELECT b.book_id, b.title, b.author
            FROM issue i
            JOIN books b ON i.book_id = b.book_id
            WHERE i.member_id = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, memberId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Book b = new Book(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    "", 0
                );
                books.add(b);
            }

            System.out.println("✅ Loaded " + books.size() + " issued books for member " + memberId);

        } catch (Exception e) {
            System.out.println("❌ Error fetching issued books: " + e.getMessage());
        }

        return books;
    }

    public static List<Fine> getFines(int memberId) {
        List<Fine> fines = new ArrayList<>();
        String query = "SELECT * FROM fines WHERE member_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, memberId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                fines.add(new Fine(
                    rs.getInt("member_id"),
                    rs.getInt("book_id"),
                    rs.getDouble("amount"),
                    rs.getString("status")
                ));
            }

            System.out.println("✅ Loaded " + fines.size() + " fines for member " + memberId);

        } catch (Exception e) {
            System.out.println("❌ Error fetching fines: " + e.getMessage());
        }

        return fines;
    }
}
