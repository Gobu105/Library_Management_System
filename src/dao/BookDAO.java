package dao;
import java.sql.*;
import java.util.*;
import db.DBConnection;
import models.Book;

public class BookDAO {
    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                books.add(new Book(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("available_copies")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}

