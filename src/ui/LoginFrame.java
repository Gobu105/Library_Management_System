package ui;

import javax.swing.*;
import java.awt.*;
import dao.LoginDAO;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    public LoginFrame() {
        setTitle("📚 Library Management - Login");
        setSize(420, 340);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(18, 18, 18));

        // 🧾 Title
        JLabel heading = new JLabel("Library Management System", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setForeground(new Color(0, 255, 136));
        heading.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0));
        add(heading, BorderLayout.NORTH);

        // 🧩 Center Panel
        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 10, 14));
        centerPanel.setBackground(new Color(30, 30, 30));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        userLabel.setForeground(new Color(220, 220, 220));
        passLabel.setForeground(new Color(220, 220, 220));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 255, 136));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setFocusPainted(false);

        registerButton = new JButton("Register New Member");
        registerButton.setBackground(new Color(45, 45, 45));
        registerButton.setForeground(new Color(0, 255, 136));
        registerButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);

        centerPanel.add(userLabel);
        centerPanel.add(usernameField);
        centerPanel.add(passLabel);
        centerPanel.add(passwordField);
        centerPanel.add(loginButton);
        centerPanel.add(registerButton);
        add(centerPanel, BorderLayout.CENTER);

        // 🧠 Button actions
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> {
            dispose();
            new RegisterFrame().setVisible(true);
        });
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Please enter both username and password.", "Missing Info",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object result = dao.LoginDAO.authenticate(username, password);

        if (result instanceof String) {
            String role = (String) result;
            JOptionPane.showMessageDialog(this, "✅ Welcome " + username + " (" + role + ")");
            new Dashboard(role).setVisible(true);
            dispose();
        } else if (result instanceof models.Member) {
            models.Member member = (models.Member) result;
            JOptionPane.showMessageDialog(this, "✅ Welcome " + member.getName() + " (" + member.getMembership() + ")");
            new Dashboard("member", member).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Invalid username or password.", "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
