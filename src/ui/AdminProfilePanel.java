package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import dao.LoginDAO;
import db.DBConnection;
import java.sql.Connection;

public class AdminProfilePanel extends JPanel {
    private JLabel nameLbl, roleLbl, userLbl, bookLbl, memberLbl, issueLbl, fineLbl;
    private JTextField newUsernameField;
    private JPasswordField passwordField, confirmPasswordField;

    public AdminProfilePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(18, 18, 18));

        // ===== TITLE =====
        JLabel header = new JLabel("👑 Admin Dashboard & Profile", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setForeground(new Color(0, 255, 136));
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(header, BorderLayout.NORTH);

        // ===== MAIN CONTENT =====
        JPanel content = new JPanel(new GridLayout(2, 1, 20, 20));
        content.setBackground(new Color(18, 18, 18));
        content.setBorder(BorderFactory.createEmptyBorder(10, 40, 40, 40));

        // === Top Info Section ===
        JPanel topSection = new JPanel(new GridLayout(1, 2, 20, 0));
        topSection.setBackground(new Color(18, 18, 18));

        // --- Left: Profile Card ---
        JPanel profileCard = new JPanel();
        profileCard.setLayout(new BoxLayout(profileCard, BoxLayout.Y_AXIS));
        profileCard.setBackground(new Color(28, 28, 28));
        profileCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 255, 136), 1),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        nameLbl = profileLabel("👤 Name: ");
        roleLbl = profileLabel("🧩 Role: ");
        userLbl = profileLabel("🔑 Username: ");

        profileCard.add(nameLbl);
        profileCard.add(Box.createVerticalStrut(5));
        profileCard.add(roleLbl);
        profileCard.add(Box.createVerticalStrut(5));
        profileCard.add(userLbl);
        profileCard.add(Box.createVerticalStrut(10));

        // --- Right: Stats Card ---
        JPanel statsCard = new JPanel(new GridBagLayout());
        statsCard.setBackground(new Color(28, 28, 28));
        statsCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 255, 136), 1),
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        bookLbl = statLabel("📚 Total Books: ");
        memberLbl = statLabel("👥 Total Members: ");
        issueLbl = statLabel("📖 Books Issued: ");
        fineLbl = statLabel("💸 Total Fines: ");

        gbc.gridy = 0; statsCard.add(bookLbl, gbc);
        gbc.gridy = 1; statsCard.add(memberLbl, gbc);
        gbc.gridy = 2; statsCard.add(issueLbl, gbc);
        gbc.gridy = 3; statsCard.add(fineLbl, gbc);

        topSection.add(profileCard);
        topSection.add(statsCard);

        // === Bottom Update Section ===
        JPanel updateCard = new JPanel(new GridBagLayout());
        updateCard.setBackground(new Color(28, 28, 28));
        updateCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 255, 136), 1),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints u = new GridBagConstraints();
        u.insets = new Insets(10, 10, 10, 10);
        u.fill = GridBagConstraints.HORIZONTAL;
        u.weightx = 1;

        JLabel title = new JLabel("🛠️ Update Credentials");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(0, 255, 136));
        u.gridx = 0; u.gridy = 0; u.gridwidth = 2;
        updateCard.add(title, u);

        newUsernameField = createTextField(true);
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        stylePassword(passwordField);
        stylePassword(confirmPasswordField);

        u.gridwidth = 1;
        u.gridy = 1; updateCard.add(label("New Username:"), u);
        u.gridx = 1; updateCard.add(newUsernameField, u);
        u.gridx = 0; u.gridy = 2; updateCard.add(label("New Password:"), u);
        u.gridx = 1; updateCard.add(passwordField, u);
        u.gridx = 0; u.gridy = 3; updateCard.add(label("Confirm Password:"), u);
        u.gridx = 1; updateCard.add(confirmPasswordField, u);

        JButton updateBtn = new JButton("💾 Save Changes");
        updateBtn.setBackground(new Color(0, 255, 136));
        updateBtn.setForeground(Color.BLACK);
        updateBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        updateBtn.setFocusPainted(false);
        updateBtn.addActionListener(e -> handleUpdate());
        u.gridx = 0; u.gridy = 4; u.gridwidth = 2; u.anchor = GridBagConstraints.CENTER;
        updateCard.add(updateBtn, u);

        content.add(topSection);
        content.add(updateCard);
        add(content, BorderLayout.CENTER);

        loadAdminData();
        loadStats();
    }

    // === Helper methods ===
    private JLabel profileLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lbl.setForeground(new Color(230, 230, 230));
        return lbl;
    }

    private JLabel statLabel(String text) {
        JLabel lbl = new JLabel(text + "0");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lbl.setForeground(new Color(0, 255, 136));
        return lbl;
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return l;
    }

    private JTextField createTextField(boolean editable) {
        JTextField f = new JTextField();
        f.setEditable(editable);
        f.setBackground(new Color(40, 40, 40));
        f.setForeground(Color.WHITE);
        f.setCaretColor(Color.WHITE);
        f.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));
        return f;
    }

    private void stylePassword(JPasswordField f) {
        f.setBackground(new Color(40, 40, 40));
        f.setForeground(Color.WHITE);
        f.setCaretColor(Color.WHITE);
        f.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));
    }

    private void loadAdminData() {
        try (Connection conn = DBConnection.getConnection()) {
            String username = "admin";
            ResultSet rs = LoginDAO.getAdminProfile(username);
            if (rs != null && rs.next()) {
                nameLbl.setText("👤 Name: " + rs.getString("name"));
                roleLbl.setText("🧩 Role: " + rs.getString("role"));
                userLbl.setText("🔑 Username: " + rs.getString("username"));
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "⚠️ Error loading admin info: " + e.getMessage());
        }
    }

    private void loadStats() {
        bookLbl.setText("📚 Total Books: " + LoginDAO.getCount("books"));
        memberLbl.setText("👥 Total Members: " + LoginDAO.getCount("members"));
        issueLbl.setText("📖 Books Issued: " + LoginDAO.getCount("issue"));
        fineLbl.setText("💸 Total Fines: " + LoginDAO.getCount("fines"));
    }

    private void handleUpdate() {
        String oldUser = userLbl.getText().replace("🔑 Username: ", "").trim();
        String newUser = newUsernameField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();
        String confirm = new String(confirmPasswordField.getPassword()).trim();

        if (newUser.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Please fill all fields!");
            return;
        }

        if (!pass.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "❌ Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean ok = LoginDAO.updateAdminProfile(oldUser, newUser, pass);
        if (ok) {
            JOptionPane.showMessageDialog(this, "✅ Profile updated successfully!");
            userLbl.setText("🔑 Username: " + newUser);
            newUsernameField.setText("");
            passwordField.setText("");
            confirmPasswordField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "❌ Update failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
