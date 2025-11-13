package ui;

import javax.swing.*;
import java.awt.*;
import models.Member;

public class Dashboard extends JFrame {
    private String role;
    private JPanel mainPanel;
    private Member loggedMember;

    // For staff/admin login
    public Dashboard(String role) {
        this(role, null);
    }

    // Overloaded constructor for member login
    public Dashboard(String role, Member member) {
        this.role = role;
        this.loggedMember = member;

        setTitle("Library Dashboard - " + role.toUpperCase());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Color bg = new Color(18, 18, 18);
        Color sidebarColor = new Color(30, 30, 30);
        Color textColor = new Color(224, 224, 224);
        Color accent = new Color(0, 255, 136);

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(8, 1, 10, 10));
        sidebar.setBackground(sidebarColor);
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton logoutBtn = styledButton("🚪 Logout", new Color(255, 85, 85));

        // 🧩 Role-based menu setup
        if (role.equalsIgnoreCase("admin")) {
            // 👑 Admin: Can manage books and members only
            JButton booksBtn = styledButton("📚 Manage Books", accent);
            JButton membersBtn = styledButton("👥 Manage Members", accent);

            sidebar.add(booksBtn);
            sidebar.add(membersBtn);

            booksBtn.addActionListener(e -> switchPanel(new ManageBooksPanel()));
            membersBtn.addActionListener(e -> switchPanel(new ManageMembersPanel()));

        } else if (role.equalsIgnoreCase("staff")) {
            // 🧰 Staff: Can manage books + issue/return
            JButton booksBtn = styledButton("📚 Manage Books", accent);
            JButton issueBtn = styledButton("📖 Issue / Return", accent);

            sidebar.add(booksBtn);
            sidebar.add(issueBtn);

            booksBtn.addActionListener(e -> switchPanel(new ManageBooksPanel()));
            issueBtn.addActionListener(e -> switchPanel(new IssueReturnPanel()));

        } else if (role.equalsIgnoreCase("member")) {
            // 👤 Member: Available books, issued books, profile
            JButton availableBooksBtn = styledButton("🔍 Available Books", accent);
            JButton myBooksBtn = styledButton("📘 My Issued Books", accent);
            JButton profileBtn = styledButton("👤 My Profile", accent);

            sidebar.add(availableBooksBtn);
            sidebar.add(myBooksBtn);
            sidebar.add(profileBtn);

            availableBooksBtn.addActionListener(e -> switchPanel(new ManageBooksPanel())); // read-only
            myBooksBtn.addActionListener(e -> switchPanel(new IssueReturnPanel())); // optional placeholder

            profileBtn.addActionListener(e -> {
                if (loggedMember != null) {
                    switchPanel(new ProfilePanel(
                        loggedMember.getMemberId(),
                        loggedMember.getName(),
                        loggedMember.getDepartment(),
                        loggedMember.getEmail(),
                        loggedMember.getPhone(),
                        loggedMember.getMembership()
                    ));
                } else {
                    JOptionPane.showMessageDialog(this, "⚠️ Member info not loaded properly.");
                }
            });
        }

        sidebar.add(logoutBtn);
        add(sidebar, BorderLayout.WEST);

        // Main panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bg);

        JLabel welcome = new JLabel("Welcome, " + role + "!", SwingConstants.CENTER);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcome.setForeground(textColor);
        mainPanel.add(welcome, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }

    private JButton styledButton(String text, Color accent) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(new Color(224, 224, 224));
        btn.setBackground(new Color(45, 45, 45));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(accent);
                btn.setForeground(Color.BLACK);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(45, 45, 45));
                btn.setForeground(new Color(224, 224, 224));
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
