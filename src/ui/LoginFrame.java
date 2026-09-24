package ui;

import service.ATMService;
import utils.AppTheme;
import utils.DialogUtils;
import utils.InputValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Frame for user authentication.
 */
public class LoginFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtCard;
    private JPasswordField txtPin;
    private JButton btnLogin;
    private final transient ATMService atmService;

    public LoginFrame(ATMService atmService) {
        this.atmService = atmService;
        initUI();
        setupKeyBindings();
    }

    private void initUI() {
        setTitle(UIConstants.APP_TITLE + " - Login");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (DialogUtils.confirmExit(LoginFrame.this)) {
                    System.exit(0);
                }
            }
        });
        setLayout(new BorderLayout());
        getContentPane().setBackground(UIConstants.BG_COLOR);

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(UIConstants.PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        JLabel titleLabel = new JLabel("Welcome to Secure Bank ATM");
        titleLabel.setFont(UIConstants.TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Main Content (Card & Pin)
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblCard = new JLabel("Card Number (12 Digits):");
        lblCard.setFont(UIConstants.BOLD_FONT);
        txtCard = AppTheme.createRoundedTextField(20);
        txtCard.setText("123456789012"); // Sample credentials pre-filled for testing

        JLabel lblPin = new JLabel("PIN (4 Digits):");
        lblPin.setFont(UIConstants.BOLD_FONT);
        txtPin = AppTheme.createRoundedPasswordField(20);
        txtPin.setText("1234"); // Sample credentials pre-filled for testing

        gbc.gridx = 0; gbc.gridy = 0; centerPanel.add(lblCard, gbc);
        gbc.gridx = 1; gbc.gridy = 0; centerPanel.add(txtCard, gbc);
        gbc.gridx = 0; gbc.gridy = 1; centerPanel.add(lblPin, gbc);
        gbc.gridx = 1; gbc.gridy = 1; centerPanel.add(txtPin, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonPanel.setOpaque(false);

        btnLogin = AppTheme.createRoundedButton("Login", UIConstants.SUCCESS_COLOR, Color.WHITE);
        JButton btnReset = AppTheme.createRoundedButton("Reset", UIConstants.SECONDARY_COLOR, Color.WHITE);
        JButton btnExit = AppTheme.createRoundedButton("Exit", UIConstants.DANGER_COLOR, Color.WHITE);

        btnLogin.addActionListener(e -> login());
        btnReset.addActionListener(e -> {
            txtCard.setText("");
            txtPin.setText("");
            txtCard.requestFocusInWindow();
        });
        btnExit.addActionListener(e -> { if(DialogUtils.confirmExit(this)) System.exit(0); });

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnReset);
        buttonPanel.add(btnExit);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void login() {
        String card = txtCard.getText().trim();
        String pin = new String(txtPin.getPassword()).trim();

        if (!InputValidator.isValidCardNumber(card)) {
            DialogUtils.showError(this, "Card number must be exactly 12 digits.");
            txtCard.requestFocusInWindow();
            return;
        }
        if (!InputValidator.isValidPin(pin)) {
            DialogUtils.showError(this, "PIN must be exactly 4 digits.");
            txtPin.requestFocusInWindow();
            return;
        }

        if (atmService.login(card, pin)) {
            dispose();
            new ATMFrame(atmService).setVisible(true);
        } else {
            DialogUtils.showError(this, "Invalid Card Number or PIN!");
            txtPin.setText("");
            txtPin.requestFocusInWindow();
        }
    }

    private void setupKeyBindings() {
        JRootPane root = this.getRootPane();
        root.setDefaultButton(btnLogin); // ENTER key triggers login
        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ESC_EXIT");
        root.getActionMap().put("ESC_EXIT", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if(DialogUtils.confirmExit(LoginFrame.this)) System.exit(0);
            }
        });
    }
}