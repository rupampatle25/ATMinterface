package ui;

import service.ATMService;
import utils.CurrencyFormatter;

import javax.swing.*;
import java.awt.*;

/**
 * Panel to display user's current bank balance.
 */
public class BalancePanel extends JPanel {
    private final JLabel lblBalance;
    private final ATMService atmService;

    public BalancePanel(ATMService atmService) {
        this.atmService = atmService;
        setLayout(new BorderLayout());
        setBackground(UIConstants.CARD_BG);
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel lblTitle = new JLabel("Available Balance", SwingConstants.CENTER);
        lblTitle.setFont(UIConstants.HEADER_FONT);
        lblTitle.setForeground(UIConstants.TEXT_COLOR);

        lblBalance = new JLabel("", SwingConstants.CENTER);
        lblBalance.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblBalance.setForeground(UIConstants.PRIMARY_COLOR);

        add(lblTitle, BorderLayout.NORTH);
        add(lblBalance, BorderLayout.CENTER);
    }

    public void refresh() {
        double balance = atmService.getCurrentUser().getAccount().getBalance();
        lblBalance.setText(CurrencyFormatter.format(balance));
    }
}