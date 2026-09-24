package ui;

import service.ATMService;
import utils.AppTheme;
import utils.DialogUtils;
import utils.InputValidator;

import javax.swing.*;
import java.awt.*;

public class WithdrawPanel extends JPanel {
    private final JTextField txtAmount;
    private final ATMService atmService;

    public WithdrawPanel(ATMService atmService, ATMFrame parentFrame) {
        this.atmService = atmService;
        setLayout(new GridBagLayout());
        setBackground(UIConstants.CARD_BG);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;

        JLabel title = new JLabel("Withdraw Money");
        title.setFont(UIConstants.TITLE_FONT);
        title.setForeground(UIConstants.PRIMARY_COLOR);
        add(title, gbc);

        gbc.gridy = 1; gbc.gridwidth = 1;
        JLabel lblAmount = new JLabel("Enter Amount (₹):");
        lblAmount.setFont(UIConstants.BOLD_FONT);
        add(lblAmount, gbc);

        gbc.gridx = 1;
        txtAmount = AppTheme.createRoundedTextField(15);
        add(txtAmount, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JButton btnWithdraw = AppTheme.createRoundedButton("Confirm Withdrawal", UIConstants.SUCCESS_COLOR, Color.WHITE);
        btnWithdraw.addActionListener(e -> withdrawMoney(parentFrame));
        add(btnWithdraw, gbc);
    }

    private void withdrawMoney(ATMFrame parentFrame) {
        double amount = InputValidator.parseAmount(txtAmount.getText());
        if (amount <= 0) {
            DialogUtils.showError(this, "Please enter a valid positive amount.");
            return;
        }

        try {
            atmService.withdraw(amount);
            DialogUtils.showSuccess(this, "Please collect your cash: " + utils.CurrencyFormatter.format(amount));
            txtAmount.setText("");
            parentFrame.showPanel("Balance");
        } catch (IllegalArgumentException ex) {
            DialogUtils.showError(this, ex.getMessage());
        }
    }
}