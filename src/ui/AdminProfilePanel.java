package ui;

import javax.swing.*;
import java.awt.*;

public class AdminProfilePanel extends JPanel {
    public AdminProfilePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(24, 24, 24));

        JLabel title = new JLabel("👤 Admin Profile", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(0, 255, 136));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBackground(new Color(30, 30, 30));
        form.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();

        form.add(new JLabel("New Username:"));
        form.add(username);
        form.add(new JLabel("New Password:"));
        form.add(password);

        JButton saveBtn = new JButton("💾 Update");
        saveBtn.setBackground(new Color(0, 255, 136));
        saveBtn.setForeground(Color.BLACK);
        saveBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "✅ Profile updated!"));

        add(form, BorderLayout.CENTER);
        add(saveBtn, BorderLayout.SOUTH);
    }
}
