package ui;

import service.ATMService;
import utils.AppTheme;
import utils.DialogUtils;
import utils.InputValidator;

import javax.swing.*;
import java.awt.*;

public class ChangePinPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final JPasswordField txtOldPin;
    private final JPasswordField txtNewPin;
    private final JPasswordField txtConfirmPin;
    private final transient ATMService atmService;

    public ChangePinPanel(ATMService atmService) {
        this.atmService = atmService;
        setLayout(new GridBagLayout());
        setBackground(UIConstants.CARD_BG);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel title = new JLabel("Change PIN", SwingConstants.CENTER);
        title.setFont(UIConstants.TITLE_FONT);
        title.setForeground(UIConstants.PRIMARY_COLOR);
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; add(createLabel("Current PIN:"), gbc);
        gbc.gridx = 1; txtOldPin = AppTheme.createRoundedPasswordField(15); add(txtOldPin, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(createLabel("New PIN (4 Digits):"), gbc);
        gbc.gridx = 1; txtNewPin = AppTheme.createRoundedPasswordField(15); add(txtNewPin, gbc);

        gbc.gridx = 0; gbc.gridy = 3; add(createLabel("Confirm New PIN:"), gbc);
        gbc.gridx = 1; txtConfirmPin = AppTheme.createRoundedPasswordField(15); add(txtConfirmPin, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JButton btnChange = AppTheme.createRoundedButton("Update PIN", UIConstants.SUCCESS_COLOR, Color.WHITE);
        btnChange.addActionListener(e -> changePin());
        txtConfirmPin.addActionListener(e -> btnChange.doClick());
        add(btnChange, gbc);
    }

    public void resetAndFocus() {
        txtOldPin.setText("");
        txtNewPin.setText("");
        txtConfirmPin.setText("");
        txtOldPin.requestFocusInWindow();
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UIConstants.BOLD_FONT);
        return label;
    }

    private void changePin() {
        String oldP = new String(txtOldPin.getPassword()).trim();
        String newP = new String(txtNewPin.getPassword()).trim();
        String confP = new String(txtConfirmPin.getPassword()).trim();

        if (oldP.isEmpty()) {
            DialogUtils.showError(this, "Please enter your current PIN.");
            txtOldPin.requestFocusInWindow();
            return;
        }
        if (!InputValidator.isValidPin(newP)) {
            DialogUtils.showError(this, "New PIN must be exactly 4 digits.");
            txtNewPin.requestFocusInWindow();
            return;
        }
        if (!newP.equals(confP)) {
            DialogUtils.showError(this, "New PIN and Confirm PIN do not match.");
            txtConfirmPin.requestFocusInWindow();
            return;
        }

        try {
            atmService.changePin(oldP, newP);
            DialogUtils.showSuccess(this, "PIN successfully changed!");
            resetAndFocus();
        } catch (Exception ex) {
            DialogUtils.showError(this, ex.getMessage());
            txtOldPin.requestFocusInWindow();
        }
    }
}