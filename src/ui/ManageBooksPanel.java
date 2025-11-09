package ui;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import dao.BookDAO;
import models.Book;
import javax.swing.table.DefaultTableModel;

public class ManageBooksPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public ManageBooksPanel() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("📚 Manage Books", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Book ID", "Title", "Author", "Available Copies"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadBooks();  // 👈 Fetch from DB when panel loads
    }

    private void loadBooks() {
        model.setRowCount(0); // clear table
        List<Book> books = BookDAO.getAllBooks();
        for (Book b : books) {
            Object[] row = {b.getId(), b.getTitle(), b.getAuthor(), b.getAvailableCopies()};
            model.addRow(row);
        }
    }
}

