package ui;
import javax.swing.*;
import java.awt.*;

public class ManageMembersPanel extends JPanel {
    public ManageMembersPanel() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("👥 Manage Members", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Member ID", "Name", "Department", "Membership"};
        Object[][] data = {}; // later: fetch from MemberDAO
        JTable table = new JTable(data, columns);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel actions = new JPanel();
        actions.add(new JButton("Add Member"));
        actions.add(new JButton("Edit Member"));
        actions.add(new JButton("Delete Member"));
        add(actions, BorderLayout.SOUTH);
    }
}

