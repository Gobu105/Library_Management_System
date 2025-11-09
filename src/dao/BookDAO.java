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
        	    System.out.println("Loaded book: " + b.getTitle()); // 🟡 debug print
        	    books.add(b);
        	}
	
	        System.out.println("✅ Loaded " + books.size() + " books from DB");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return books;
	}

}

