package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dao.IssueDAO;
import dao.BookDAO;
import dao.MemberDAO;
import models.Book;
import models.Member;

public class IssueReturnPanel extends JPanel {
    private JTextField memberIdField, bookIdField;
    private JButton issueBtn, returnBtn;
    private JLabel statusLabel;

    public IssueReturnPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(18, 18, 18));

        // ===== HEADER =====
        JLabel header = new JLabel("📖 Issue / Return Books", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(new Color(0, 255, 136));
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(header, BorderLayout.NORTH);

        // ===== CENTER FORM =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(28, 28, 28));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 255, 136), 1),
                BorderFactory.createEmptyBorder(30, 50, 30, 50)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JLabel memberLbl = label("👤 Member ID:");
        JLabel bookLbl = label("📚 Book ID:");

        memberIdField = createField();
        bookIdField = createField();

        issueBtn = new JButton("✅ Issue Book");
        issueBtn.setBackground(new Color(0, 255, 136));
        issueBtn.setForeground(Color.BLACK);
        issueBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        returnBtn = new JButton("↩️ Return Book");
        returnBtn.setBackground(new Color(255, 100, 100));
        returnBtn.setForeground(Color.WHITE);
        returnBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.LIGHT_GRAY);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(memberLbl, gbc);
        gbc.gridx = 1; formPanel.add(memberIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(bookLbl, gbc);
        gbc.gridx = 1; formPanel.add(bookIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(issueBtn, gbc);
        gbc.gridx = 1; formPanel.add(returnBtn, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        formPanel.add(statusLabel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // ===== BUTTON LOGIC =====
        issueBtn.addActionListener(e -> issueBook());
        returnBtn.addActionListener(e -> returnBook());
    }

    private JLabel label(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        return lbl;
    }

    private JTextField createField() {
        JTextField tf = new JTextField();
        tf.setBackground(new Color(40, 40, 40));
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
        tf.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));
        return tf;
    }

    private void issueBook() {
        try {
            int memberId = Integer.parseInt(memberIdField.getText().trim());
            int bookId = Integer.parseInt(bookIdField.getText().trim());

            // 🔒 Prevent more than 5 books issued
            int issuedCount = IssueDAO.getIssuedBookCountByMember(memberId);
            if (issuedCount >= 5) {
                JOptionPane.showMessageDialog(this,
                    "⚠️ This member already has 5 issued books.\nReturn some books before issuing more.",
                    "Limit Reached", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean ok = IssueDAO.issueBook(memberId, bookId, null);
            if (ok) {
                statusLabel.setText("✅ Book issued successfully!");
                statusLabel.setForeground(new Color(0, 255, 136));
            } else {
                statusLabel.setText("❌ Failed to issue book!");
                statusLabel.setForeground(Color.RED);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Please enter valid IDs.");
        }
    }

    private void returnBook() {
        try {
            int memberId = Integer.parseInt(memberIdField.getText().trim());
            int bookId = Integer.parseInt(bookIdField.getText().trim());

            boolean ok = IssueDAO.returnBook(memberId, bookId, null);
            if (ok) {
                statusLabel.setText("↩️ Book returned successfully!");
                statusLabel.setForeground(new Color(0, 255, 136));
            } else {
                statusLabel.setText("❌ Failed to return book!");
                statusLabel.setForeground(Color.RED);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Please enter valid IDs.");
        }
    }
}
