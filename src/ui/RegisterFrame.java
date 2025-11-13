package ui;

import javax.swing.*;
import java.awt.*;
import dao.MemberDAO;
import models.Member;

public class RegisterFrame extends JFrame {
    private JTextField nameField, deptField, emailField, phoneField, usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> membershipBox;
    private JButton registerButton, backButton;

    public RegisterFrame() {
        setTitle("📝 Register New Member");
        setSize(480, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(18, 18, 18));

        JLabel heading = new JLabel("Library Membership Registration", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setForeground(new Color(224, 224, 224));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(heading, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(8, 2, 10, 12));
        form.setBackground(new Color(30, 30, 30));
        form.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel nameLbl = lbl("Full Name:");
        JLabel deptLbl = lbl("Department:");
        JLabel emailLbl = lbl("Email:");
        JLabel phoneLbl = lbl("Phone:");
        JLabel memberLbl = lbl("Membership:");
        JLabel userLbl = lbl("Username:");
        JLabel passLbl = lbl("Password:");

        nameField = new JTextField();
        deptField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        membershipBox = new JComboBox<>(new String[] {"Free", "Premium", "Life-Time"});

        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(0, 255, 136));
        registerButton.setForeground(Color.BLACK);
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        backButton = new JButton("⬅ Back");
        backButton.setBackground(new Color(45, 45, 45));
        backButton.setForeground(new Color(224, 224, 224));

        form.add(nameLbl); form.add(nameField);
        form.add(deptLbl); form.add(deptField);
        form.add(emailLbl); form.add(emailField);
        form.add(phoneLbl); form.add(phoneField);
        form.add(memberLbl); form.add(membershipBox);
        form.add(userLbl); form.add(usernameField);
        form.add(passLbl); form.add(passwordField);
        form.add(registerButton); form.add(backButton);

        add(form, BorderLayout.CENTER);

        registerButton.addActionListener(e -> handleRegister());
        backButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }

    private JLabel lbl(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(new Color(200, 200, 200));
        return l;
    }

    private void handleRegister() {
        try {
            Member m = new Member(0,
                    nameField.getText().trim(),
                    deptField.getText().trim(),
                    emailField.getText().trim(),
                    Long.parseLong(phoneField.getText().trim()),
                    new java.sql.Date(System.currentTimeMillis()),
                    (String) membershipBox.getSelectedItem());

            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty() || m.getName().isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ Please fill all fields!");
                return;
            }

            boolean ok = MemberDAO.registerMember(m, username, password);
            if (ok) {
                JOptionPane.showMessageDialog(this, "✅ Registration Successful!");
                dispose();
                new LoginFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "❌ Registration Failed!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "⚠️ Invalid input!");
        }
    }
}
