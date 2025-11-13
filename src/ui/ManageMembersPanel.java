package ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import dao.MemberDAO;
import models.Member;

public class ManageMembersPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;

    public ManageMembersPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(18, 18, 18));

        JLabel title = new JLabel("👥 Manage Members", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(0, 255, 136));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"Member ID", "Name", "Department", "Email", "Phone", "Membership"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setBackground(new Color(24, 24, 24));
        table.setForeground(new Color(224, 224, 224));
        table.setGridColor(new Color(44, 44, 44));
        table.getTableHeader().setBackground(new Color(30, 30, 30));
        table.getTableHeader().setForeground(new Color(0, 255, 136));
        table.setSelectionBackground(new Color(0, 255, 136));
        table.setSelectionForeground(Color.BLACK);
        table.setRowHeight(26);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Search and refresh panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(18, 18, 18));

        searchField = new JTextField();
        searchField.setBackground(new Color(30, 30, 30));
        searchField.setForeground(new Color(224, 224, 224));
        searchField.setCaretColor(Color.WHITE);
        searchField.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 255, 136)),
                "Search Member (Name / Dept / Email)",
                0, 0,
                new Font("Segoe UI", Font.PLAIN, 12),
                new Color(0, 255, 136)
        ));

        JButton refreshBtn = new JButton("🔄 Refresh");
        refreshBtn.setBackground(new Color(0, 255, 136));
        refreshBtn.setForeground(Color.BLACK);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        refreshBtn.addActionListener(e -> loadMembers());

        bottomPanel.add(searchField, BorderLayout.CENTER);
        bottomPanel.add(refreshBtn, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        // Live search filtering
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void filter() {
                String text = searchField.getText().trim().toLowerCase();
                if (text.length() == 0) sorter.setRowFilter(null);
                else sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
        });

        loadMembers();
    }

    private void loadMembers() {
        model.setRowCount(0);
        List<Member> members = MemberDAO.getAllMembers();
        for (Member m : members) {
            Object[] row = {
                m.getMemberId(),
                m.getName(),
                m.getDepartment(),
                m.getEmail(),
                m.getPhone(),
                m.getMembership()
            };
            model.addRow(row);
        }
    }
}
