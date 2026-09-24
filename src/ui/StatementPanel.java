package ui;

import model.Transaction;
import service.ATMService;
import utils.CurrencyFormatter;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StatementPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final transient ATMService atmService;
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
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(tableModel);
        table.setFont(UIConstants.REGULAR_FONT);
        table.setRowHeight(30);
        table.getTableHeader().setFont(UIConstants.BOLD_FONT);
        table.getTableHeader().setBackground(UIConstants.SECONDARY_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);

        // Center align Date, Time, Type; Right align Amount, Balance
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void refresh() {
        tableModel.setRowCount(0);
        if (atmService.getCurrentUser() == null || atmService.getCurrentUser().getAccount() == null) {
            return;
        }
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