package ui;

import service.ATMService;
import utils.AppTheme;
import utils.DialogUtils;
import utils.InputValidator;

import javax.swing.*;
import java.awt.*;

public class DepositPanel extends JPanel {
    private final JTextField txtAmount;
    private final ATMService atmService;

    public DepositPanel(ATMService atmService, ATMFrame parentFrame) {
        this.atmService = atmService;
        setLayout(new GridBagLayout());
        setBackground(UIConstants.CARD_BG);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;

        JLabel title = new JLabel("Deposit Money");
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
        JButton btnDeposit = AppTheme.createRoundedButton("Confirm Deposit", UIConstants.SUCCESS_COLOR, Color.WHITE);
        btnDeposit.addActionListener(e -> depositMoney(parentFrame));
        add(btnDeposit, gbc);
    }

    private void depositMoney(ATMFrame parentFrame) {
        double amount = InputValidator.parseAmount(txtAmount.getText());
        if (amount <= 0) {
            DialogUtils.showError(this, "Please enter a valid amount greater than zero.");
            return;
        }

        try {
            atmService.deposit(amount);
            DialogUtils.showSuccess(this, "Successfully deposited " + utils.CurrencyFormatter.format(amount));
            txtAmount.setText("");
            parentFrame.showPanel("Balance"); // Navigate to balance to show updated amount
        } catch (Exception ex) {
            DialogUtils.showError(this, ex.getMessage());
        }
    }
}