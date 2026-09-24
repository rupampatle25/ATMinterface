package ui;

import model.Transaction;
import service.ATMService;
import utils.CurrencyFormatter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StatementPanel extends JPanel {
    private final ATMService atmService;
    private final DefaultTableModel tableModel;

    public StatementPanel(ATMService atmService) {
        this.atmService = atmService;
        setLayout(new BorderLayout(10, 10));
        setBackground(UIConstants.CARD_BG);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("Mini Statement (Last 10 Transactions)", SwingConstants.CENTER);
        lblTitle.setFont(UIConstants.HEADER_FONT);
        lblTitle.setForeground(UIConstants.PRIMARY_COLOR);
        add(lblTitle, BorderLayout.NORTH);

        String[] columns = {"Date", "Time", "Type", "Amount", "Balance"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(tableModel);
        table.setFont(UIConstants.REGULAR_FONT);
        table.setRowHeight(30);
        table.getTableHeader().setFont(UIConstants.BOLD_FONT);
        table.getTableHeader().setBackground(UIConstants.SECONDARY_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void refresh() {
        tableModel.setRowCount(0);
        List<Transaction> transactions = atmService.getTransactionService().getMiniStatement(atmService.getCurrentUser().getAccount(), 10);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        for (Transaction t : transactions) {
            tableModel.addRow(new Object[]{
                    t.getTimestamp().format(dateFormatter),
                    t.getTimestamp().format(timeFormatter),
                    t.getType(),
                    CurrencyFormatter.format(t.getAmount()),
                    CurrencyFormatter.format(t.getBalanceAfter())
            });
        }
    }
}