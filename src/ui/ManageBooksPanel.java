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
        table.setBackground(new Color(24, 24, 24));
        table.setGridColor(new Color(44, 44, 44));
        table.getTableHeader().setBackground(new Color(30, 30, 30));
        table.getTableHeader().setForeground(new Color(0, 255, 136));
        table.setSelectionBackground(new Color(0, 255, 136));
        table.setSelectionForeground(Color.BLACK);
        table.setRowHeight(26);
        table.setForeground(new Color(224, 224, 224));
        
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

