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

    public ManageMembersPanel(boolean adminMode) {
        setLayout(new BorderLayout());
        setBackground(new Color(24, 24, 24));

        JLabel title = new JLabel("👥 Manage Members", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(0, 255, 136));
        add(title, BorderLayout.NORTH);

        String[] cols = {"ID", "Name", "Department", "Email", "Phone", "Membership"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        styleTable(table);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btns = new JPanel();
        btns.setBackground(new Color(24, 24, 24));
        JButton addBtn = styledButton("➕ Add");
        JButton editBtn = styledButton("✏️ Edit");
        JButton delBtn = styledButton("🗑 Delete");
        JButton refreshBtn = styledButton("🔄 Refresh");

        btns.add(refreshBtn);
        if (adminMode) {
            btns.add(addBtn);
            btns.add(editBtn);
            btns.add(delBtn);
        }
        add(btns, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> loadMembers());
        addBtn.addActionListener(e -> addMember());
        editBtn.addActionListener(e -> editMember());
        delBtn.addActionListener(e -> deleteMember());

        loadMembers();
    }

    private void styleTable(JTable t) {
        t.setBackground(new Color(35, 35, 35));
        t.setForeground(Color.WHITE);
        t.setGridColor(new Color(50, 50, 50));
        t.getTableHeader().setBackground(new Color(40, 40, 40));
        t.getTableHeader().setForeground(new Color(0, 255, 136));
        t.setSelectionBackground(new Color(0, 255, 136));
        t.setSelectionForeground(Color.BLACK);
        t.setRowHeight(25);
    }

    private JButton styledButton(String txt) {
        JButton b = new JButton(txt);
        b.setBackground(new Color(45, 45, 45));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                b.setBackground(new Color(0, 255, 136));
                b.setForeground(Color.BLACK);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                b.setBackground(new Color(45, 45, 45));
                b.setForeground(Color.WHITE);
            }
        });
        return b;
    }

    private void loadMembers() {
        model.setRowCount(0);
        List<Member> list = MemberDAO.getAllMembers();
        for (Member m : list) {
            model.addRow(new Object[]{
                m.getMemberId(), m.getName(), m.getDepartment(), m.getEmail(), m.getPhone(), m.getMembership()
            });
        }
    }

    private void addMember() {
        JTextField name = new JTextField();
        JTextField dept = new JTextField();
        JTextField email = new JTextField();
        JTextField phone = new JTextField();
        JTextField membership = new JTextField();

        Object[] fields = {"Name:", name, "Department:", dept, "Email:", email, "Phone:", phone, "Membership:", membership};
        int ok = JOptionPane.showConfirmDialog(this, fields, "Add Member", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            MemberDAO.addMember(name.getText(), dept.getText(), email.getText(), Long.parseLong(phone.getText()), membership.getText());
            loadMembers();
        }
    }

    private void editMember() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a member first!");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        JTextField name = new JTextField((String) model.getValueAt(row, 1));
        JTextField dept = new JTextField((String) model.getValueAt(row, 2));
        JTextField email = new JTextField((String) model.getValueAt(row, 3));
        JTextField phone = new JTextField(model.getValueAt(row, 4).toString());
        JTextField membership = new JTextField((String) model.getValueAt(row, 5));

        Object[] fields = {"Name:", name, "Department:", dept, "Email:", email, "Phone:", phone, "Membership:", membership};
        int ok = JOptionPane.showConfirmDialog(this, fields, "Edit Member", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            MemberDAO.updateMember(id, name.getText(), dept.getText(), email.getText(), Long.parseLong(phone.getText()), membership.getText());
            loadMembers();
        }
    }

    private void deleteMember() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a member to delete!");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        int ok = JOptionPane.showConfirmDialog(this, "Are you sure?", "Delete", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            MemberDAO.deleteMember(id);
            loadMembers();
        }
    }
}
