package ui;
import javax.swing.*;
import java.awt.*;

public class IssueReturnPanel extends JPanel {
    public IssueReturnPanel() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("📖 Issue / Return Books", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.add(new JLabel("Book ID:"));
        JTextField bookField = new JTextField();
        form.add(bookField);

        form.add(new JLabel("Member ID:"));
        JTextField memberField = new JTextField();
        form.add(memberField);

        JButton issueBtn = new JButton("Issue Book");
        JButton returnBtn = new JButton("Return Book");

        form.add(issueBtn);
        form.add(returnBtn);

        add(form, BorderLayout.CENTER);
    }
}

