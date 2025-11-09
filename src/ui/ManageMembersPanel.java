package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import dao.MemberDAO;
import models.Member;

public class ManageMembersPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public ManageMembersPanel() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("👥 Manage Members", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Member ID", "Name", "Department", "Email", "Membership"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton refreshBtn = new JButton("🔄 Refresh");
        refreshBtn.addActionListener(e -> loadMembers());
        add(refreshBtn, BorderLayout.SOUTH);

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
                m.getMembership()
            };
            model.addRow(row);
        }
    }
}

