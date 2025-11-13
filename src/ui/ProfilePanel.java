package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import dao.ProfileDAO;
import models.Book;
import models.Fine;

public class ProfilePanel extends JPanel {
    private int memberId;
    private JLabel nameLbl, deptLbl, emailLbl, phoneLbl, membershipLbl;
    private JTable issuedTable, finesTable;

    public ProfilePanel(int memberId, String name, String department, String email, long phone, String membership) {
        this.memberId = memberId;
        setLayout(new BorderLayout());
        setBackground(new Color(24, 24, 24));

        // 🧾 Profile Header
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        infoPanel.setBackground(new Color(30, 30, 30));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Member Details"));
        infoPanel.setForeground(Color.WHITE);

        nameLbl = new JLabel("Name: " + name);
        deptLbl = new JLabel("Department: " + department);
        emailLbl = new JLabel("Email: " + email);
        phoneLbl = new JLabel("Phone: " + phone);
        membershipLbl = new JLabel("Membership: " + membership);

        for (JLabel lbl : new JLabel[]{nameLbl, deptLbl, emailLbl, phoneLbl, membershipLbl})
            lbl.setForeground(new Color(220, 220, 220));

        infoPanel.add(nameLbl);
        infoPanel.add(deptLbl);
        infoPanel.add(emailLbl);
        infoPanel.add(phoneLbl);
        infoPanel.add(membershipLbl);

        add(infoPanel, BorderLayout.NORTH);

        // 📚 Issued Books Section
        String[] issuedCols = {"Book ID", "Title", "Author"};
        DefaultTableModel issuedModel = new DefaultTableModel(issuedCols, 0);
        issuedTable = new JTable(issuedModel);
        styleTable(issuedTable);

        // 💰 Fines Section
        String[] fineCols = {"Book ID", "Amount", "Status"};
        DefaultTableModel fineModel = new DefaultTableModel(fineCols, 0);
        finesTable = new JTable(fineModel);
        styleTable(finesTable);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        centerPanel.setBackground(new Color(24, 24, 24));
        centerPanel.add(wrapTable("📘 Issued Books", issuedTable));
        centerPanel.add(wrapTable("💸 Fines", finesTable));

        add(centerPanel, BorderLayout.CENTER);

        // 🔄 Load data
        loadIssuedBooks(issuedModel);
        loadFines(fineModel);
    }

    private void styleTable(JTable table) {
        table.setBackground(new Color(35, 35, 35));
        table.setForeground(new Color(230, 230, 230));
        table.setGridColor(new Color(60, 60, 60));
        table.getTableHeader().setBackground(new Color(40, 40, 40));
        table.getTableHeader().setForeground(new Color(0, 255, 136));
        table.setSelectionBackground(new Color(0, 255, 136));
        table.setSelectionForeground(Color.BLACK);
    }

    private JScrollPane wrapTable(String title, JTable table) {
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder(title));
        scroll.getViewport().setBackground(new Color(24, 24, 24));
        return scroll;
    }

    private void loadIssuedBooks(DefaultTableModel model) {
        model.setRowCount(0);
        List<Book> books = ProfileDAO.getIssuedBooks(memberId);
        for (Book b : books) {
            model.addRow(new Object[]{b.getId(), b.getTitle(), b.getAuthor()});
        }
    }

    private void loadFines(DefaultTableModel model) {
        model.setRowCount(0);
        List<Fine> fines = ProfileDAO.getFines(memberId);
        for (Fine f : fines) {
            model.addRow(new Object[]{f.getBookId(), f.getAmount(), f.getStatus()});
        }
    }
}
