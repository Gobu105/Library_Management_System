package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import dao.StaffDAO;
import models.Staff;

public class ManageStaffPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public ManageStaffPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(18, 18, 18));

        JLabel title = new JLabel("🧑‍💼 Manage Staff", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(0, 255, 136));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Role", "Username", "Password"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        table.setBackground(new Color(28, 28, 28));
        table.setForeground(Color.WHITE);
        table.setRowHeight(28);
        table.setGridColor(new Color(0, 255, 136));
        table.setSelectionBackground(new Color(0, 255, 136));
        table.setSelectionForeground(Color.BLACK);

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(new Color(28, 28, 28));
        add(scroll, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(18, 18, 18));

        JButton addBtn = new JButton("➕ Add Staff");
        JButton editBtn = new JButton("✏️ Edit");
        JButton delBtn = new JButton("🗑️ Delete");

        for (JButton b : new JButton[]{addBtn, editBtn, delBtn}) {
            b.setBackground(new Color(0, 255, 136));
            b.setForeground(Color.BLACK);
            b.setFocusPainted(false);
            b.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnPanel.add(b);
        }

        add(btnPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addStaff());
        editBtn.addActionListener(e -> editStaff());
        delBtn.addActionListener(e -> deleteStaff());

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        List<Staff> staffList = StaffDAO.getAllStaff();
        for (Staff s : staffList) {
            model.addRow(new Object[]{s.getStaffId(), s.getName(), s.getRole(), s.getUsername(), s.getPassword()});
        }
    }

    private void addStaff() {
        JTextField nameField = new JTextField();
        JTextField userField = new JTextField();
        JTextField passField = new JTextField();
        String[] roles = {"staff", "admin"};
        JComboBox<String> roleBox = new JComboBox<>(roles);

        Object[] form = {
            "Name:", nameField,
            "Username:", userField,
            "Password:", passField,
            "Role:", roleBox
        };

        int opt = JOptionPane.showConfirmDialog(this, form, "Add Staff", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            boolean ok = StaffDAO.addStaff(nameField.getText(), userField.getText(), passField.getText(), (String) roleBox.getSelectedItem());
            if (ok) {
                JOptionPane.showMessageDialog(this, "✅ Staff added successfully!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Failed to add staff!");
            }
        }
    }

    private void editStaff() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Select a staff to edit!");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        JTextField nameField = new JTextField(model.getValueAt(row, 1).toString());
        JTextField userField = new JTextField(model.getValueAt(row, 3).toString());
        JTextField passField = new JTextField(model.getValueAt(row, 4).toString());
        String[] roles = {"staff", "admin"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        roleBox.setSelectedItem(model.getValueAt(row, 2).toString());

        Object[] form = {
            "Name:", nameField,
            "Username:", userField,
            "Password:", passField,
            "Role:", roleBox
        };

        int opt = JOptionPane.showConfirmDialog(this, form, "Edit Staff", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            boolean ok = StaffDAO.updateStaff(id, nameField.getText(), userField.getText(), passField.getText(), (String) roleBox.getSelectedItem());
            if (ok) {
                JOptionPane.showMessageDialog(this, "✅ Updated successfully!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Update failed!");
            }
        }
    }

    private void deleteStaff() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Select a staff to delete!");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this staff?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = StaffDAO.deleteStaff(id);
            if (ok) {
                JOptionPane.showMessageDialog(this, "🗑️ Staff deleted.");
                loadData();
            }
        }
    }
}
