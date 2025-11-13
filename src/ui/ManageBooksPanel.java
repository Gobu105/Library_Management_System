package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import dao.BookDAO;
import models.Book;

public class ManageBooksPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public ManageBooksPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(24, 24, 24));

        JLabel title = new JLabel("📚 Manage Books", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(0, 255, 136));
        add(title, BorderLayout.NORTH);

        String[] columns = { "ID", "Title", "Author", "Publisher", "Available Copies" };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        styleTable(table);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttons = new JPanel();
        buttons.setBackground(new Color(24, 24, 24));

        JButton addBtn = styledButton("➕ Add");
        JButton editBtn = styledButton("✏️ Edit");
        JButton delBtn = styledButton("🗑 Delete");
        JButton refreshBtn = styledButton("🔄 Refresh");

        buttons.add(addBtn);
        buttons.add(editBtn);
        buttons.add(delBtn);
        buttons.add(refreshBtn);

        add(buttons, BorderLayout.SOUTH);

        // Actions
        addBtn.addActionListener(e -> addBook());
        editBtn.addActionListener(e -> editBook());
        delBtn.addActionListener(e -> deleteBook());
        refreshBtn.addActionListener(e -> loadBooks());

        loadBooks();
    }

    private void styleTable(JTable table) {
        table.setBackground(new Color(35, 35, 35));
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(50, 50, 50));
        table.setRowHeight(26);
        table.getTableHeader().setBackground(new Color(40, 40, 40));
        table.getTableHeader().setForeground(new Color(0, 255, 136));
        table.setSelectionBackground(new Color(0, 255, 136));
        table.setSelectionForeground(Color.BLACK);
    }

    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(45, 45, 45));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(0, 255, 136));
                btn.setForeground(Color.BLACK);
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(45, 45, 45));
                btn.setForeground(Color.WHITE);
            }
        });
        return btn;
    }

    private void loadBooks() {
        model.setRowCount(0);
        List<Book> books = BookDAO.getAllBooks();
        for (Book b : books) {
            model.addRow(new Object[] {
                    b.getId(), b.getTitle(), b.getAuthor(), b.getPublisher(), b.getAvailableCopies()
            });
        }
    }

    private void addBook() {
        JTextField title = new JTextField();
        JTextField author = new JTextField();
        JTextField publisher = new JTextField();
        JTextField copies = new JTextField();

        Object[] fields = {
                "Title:", title,
                "Author:", author,
                "Publisher:", publisher,
                "Available Copies:", copies
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Add Book", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String t = title.getText();
                String a = author.getText();
                String p = publisher.getText();
                int c = Integer.parseInt(copies.getText());
                BookDAO.addBook(new Book(0, t, a, p, c));
                loadBooks();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "⚠️ Invalid input");
            }
        }
    }

    private void editBook() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Please select a book first.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        String title = (String) model.getValueAt(row, 1);
        String author = (String) model.getValueAt(row, 2);
        String publisher = (String) model.getValueAt(row, 3);
        int copies = (int) model.getValueAt(row, 4);

        JTextField titleField = new JTextField(title);
        JTextField authorField = new JTextField(author);
        JTextField publisherField = new JTextField(publisher);
        JTextField copiesField = new JTextField(String.valueOf(copies));

        Object[] fields = {
                "Title:", titleField,
                "Author:", authorField,
                "Publisher:", publisherField,
                "Available Copies:", copiesField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Edit Book", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Book b = new Book(id, titleField.getText(), authorField.getText(), publisherField.getText(),
                    Integer.parseInt(copiesField.getText()));
            BookDAO.updateBook(b);
            loadBooks();
        }
    }

    private void deleteBook() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Select a book to delete");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this book?", "Confirm",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            BookDAO.deleteBook(id);
            loadBooks();
        }
    }

    private boolean adminMode = false;

    public ManageBooksPanel(boolean adminMode) {
        this();
        this.adminMode = adminMode;
        enableAdminControls();
    }

    private void enableAdminControls() {
        if (adminMode) {
            // Show Add/Edit/Delete buttons
            JPanel btnPanel = new JPanel();
            btnPanel.setBackground(new Color(24, 24, 24));

            JButton addBtn = styledButton("➕ Add");
            JButton editBtn = styledButton("✏️ Edit");
            JButton delBtn = styledButton("🗑 Delete");

            btnPanel.add(addBtn);
            btnPanel.add(editBtn);
            btnPanel.add(delBtn);

            add(btnPanel, BorderLayout.NORTH);

            addBtn.addActionListener(e -> addBook());
            editBtn.addActionListener(e -> editBook());
            delBtn.addActionListener(e -> deleteBook());
        }
    }

}
