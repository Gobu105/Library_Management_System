package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame {
    private String role;
    private JPanel mainPanel;

    public Dashboard(String role) {
        this.role = role;

        setTitle("Library Management System - " + role.toUpperCase());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ==== Sidebar ====
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(5, 1, 10, 10));
        sidebar.setBackground(new Color(30, 30, 30));

        JButton booksBtn = new JButton("📚 Manage Books");
        JButton membersBtn = new JButton("👥 Manage Members");
        JButton issueBtn = new JButton("📖 Issue / Return");
        JButton logoutBtn = new JButton("🚪 Logout");

        for (JButton btn : new JButton[]{booksBtn, membersBtn, issueBtn, logoutBtn}) {
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            sidebar.add(btn);
        }

        add(sidebar, BorderLayout.WEST);

        // ==== Main Content Panel ====
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JLabel("Welcome, " + role + "!", SwingConstants.CENTER), BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // ==== Button Actions ====
        booksBtn.addActionListener(e -> switchPanel(new ManageBooksPanel()));
        membersBtn.addActionListener(e -> switchPanel(new ManageMembersPanel()));
        issueBtn.addActionListener(e -> switchPanel(new IssueReturnPanel()));
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }

    private void switchPanel(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}

