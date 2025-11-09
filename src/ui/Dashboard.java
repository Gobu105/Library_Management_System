package ui;
import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    private String role;
    private JPanel mainPanel;

    public Dashboard(String role) {
        this.role = role;

        setTitle("Library Dashboard - " + role.toUpperCase());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(5, 1, 10, 10));
        sidebar.setBackground(new Color(35, 35, 40));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton booksBtn = styledButton("📚  Manage Books");
        JButton membersBtn = styledButton("👥  Manage Members");
        JButton issueBtn = styledButton("📖  Issue / Return");
        JButton logoutBtn = styledButton("🚪  Logout");

        sidebar.add(booksBtn);
        sidebar.add(membersBtn);
        sidebar.add(issueBtn);
        sidebar.add(logoutBtn);
        add(sidebar, BorderLayout.WEST);

        // Main content
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(25, 25, 25));
        JLabel welcome = new JLabel("Welcome, " + role + "!", SwingConstants.CENTER);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcome.setForeground(Color.WHITE);
        mainPanel.add(welcome, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // Button actions
        booksBtn.addActionListener(e -> switchPanel(new ManageBooksPanel()));
        membersBtn.addActionListener(e -> switchPanel(new ManageMembersPanel()));
        issueBtn.addActionListener(e -> switchPanel(new IssueReturnPanel()));
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }

    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(60, 63, 65));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(46, 204, 113));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(60, 63, 65));
            }
        });

        return btn;
    }

    private void switchPanel(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}

