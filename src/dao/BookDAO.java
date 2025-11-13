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
            	Book b = new Book(
                	rs.getInt("book_id"),
                	rs.getString("title"),
                	rs.getString("author"),
                	rs.getString("publisher"),
        	        rs.getInt("available_copies")
        	    );
        	    books.add(b);
        	}
	
	        System.out.println("✅ Loaded " + books.size() + " books from DB");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return books;
	}

	public static boolean addBook(Book book) {
		String sql = "INSERT INTO books (title, author, publisher, available_copies) VALUES (?, ?, ?, ?)";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setString(1, book.getTitle());
			pst.setString(2, book.getAuthor());
			pst.setString(3, book.getPublisher());
			pst.setInt(4, book.getAvailableCopies());
			return pst.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean updateBook(Book book) {
		String sql = "UPDATE books SET title=?, author=?, publisher=?, available_copies=? WHERE book_id=?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setString(1, book.getTitle());
			pst.setString(2, book.getAuthor());
			pst.setString(3, book.getPublisher());
			pst.setInt(4, book.getAvailableCopies());
			pst.setInt(5, book.getId());
			return pst.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean deleteBook(int bookId) {
		String sql = "DELETE FROM books WHERE book_id=?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setInt(1, bookId);
			return pst.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}

