package ui;

import service.ATMService;
import utils.AppTheme;
import utils.DialogUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Main dashboard application frame.
 */
public class ATMFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private final transient ATMService atmService;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    // Panels
    private BalancePanel balancePanel;
    private StatementPanel statementPanel;
    private DepositPanel depositPanel;
    private WithdrawPanel withdrawPanel;
    private ChangePinPanel changePinPanel;

    private JLabel statusTimeLabel;
    private Timer clockTimer;

    public ATMFrame(ATMService atmService) {
        this.atmService = atmService;
        initUI();
        startClock();
        setupKeyBindings();
    }

    private void initUI() {
        setTitle(UIConstants.APP_TITLE + " - Dashboard");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                exitApplication();
            }
        });

        setJMenuBar(createMenuBar());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UIConstants.PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        String userName = (atmService.getCurrentUser() != null) ? atmService.getCurrentUser().getName() : "Customer";
        JLabel welcomeLabel = new JLabel("Welcome, " + userName);
        welcomeLabel.setFont(UIConstants.TITLE_FONT);
        welcomeLabel.setForeground(Color.WHITE);
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        // Sidebar Navigation
        JPanel sidebar = new JPanel(new GridLayout(6, 1, 10, 15));
        sidebar.setBackground(UIConstants.SECONDARY_COLOR);
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        sidebar.setPreferredSize(new Dimension(220, 0));

        sidebar.add(createNavButton("Check Balance", "Balance"));
        sidebar.add(createNavButton("Deposit Money", "Deposit"));
        sidebar.add(createNavButton("Withdraw Money", "Withdraw"));
        sidebar.add(createNavButton("Mini Statement", "Statement"));
        sidebar.add(createNavButton("Change PIN", "Pin"));

        JButton btnLogout = AppTheme.createRoundedButton("Logout", UIConstants.DANGER_COLOR, Color.WHITE);
        btnLogout.addActionListener(e -> logout());
        sidebar.add(btnLogout);

        add(sidebar, BorderLayout.WEST);

        // Main Content Area using CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cardPanel.setBackground(UIConstants.BG_COLOR);

        balancePanel = new BalancePanel(atmService);
        depositPanel = new DepositPanel(atmService, this);
        withdrawPanel = new WithdrawPanel(atmService, this);
        statementPanel = new StatementPanel(atmService);
        changePinPanel = new ChangePinPanel(atmService);

        cardPanel.add(balancePanel, "Balance");
        cardPanel.add(depositPanel, "Deposit");
        cardPanel.add(withdrawPanel, "Withdraw");
        cardPanel.add(statementPanel, "Statement");
        cardPanel.add(changePinPanel, "Pin");

        add(cardPanel, BorderLayout.CENTER);

        // Status Bar
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBorder(BorderFactory.createEmptyBorder(6, 15, 6, 15));
        JLabel statusConnLabel = new JLabel("Secure Connection Established");
        statusConnLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statusTimeLabel = new JLabel();
        statusTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statusBar.add(statusConnLabel, BorderLayout.WEST);
        statusBar.add(statusTimeLabel, BorderLayout.EAST);
        add(statusBar, BorderLayout.SOUTH);

        showPanel("Balance"); // Default view
    }

    private JMenuBar createMenuBar() {
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem mnuLogout = new JMenuItem("Logout");
        mnuLogout.addActionListener(e -> logout());
        JMenuItem mnuExit = new JMenuItem("Exit");
        mnuExit.addActionListener(e -> exitApplication());
        file.add(mnuLogout); file.addSeparator(); file.add(mnuExit);

        JMenu help = new JMenu("Help");
        JMenuItem mnuAbout = new JMenuItem("About");
        mnuAbout.addActionListener(e -> DialogUtils.showAbout(this));
        help.add(mnuAbout);

        mb.add(file); mb.add(help);
        return mb;
    }

    private JButton createNavButton(String title, String cardName) {
        JButton btn = AppTheme.createRoundedButton(title, UIConstants.BG_COLOR, UIConstants.TEXT_COLOR);
        btn.addActionListener(e -> showPanel(cardName));
        return btn;
    }

    public void showPanel(String name) {
        if (name.equals("Balance")) balancePanel.refresh();
        if (name.equals("Statement")) statementPanel.refresh();
        if (name.equals("Deposit")) depositPanel.resetAndFocus();
        if (name.equals("Withdraw")) withdrawPanel.resetAndFocus();
        if (name.equals("Pin")) changePinPanel.resetAndFocus();
        cardLayout.show(cardPanel, name);
    }

    private void logout() {
        if (clockTimer != null && clockTimer.isRunning()) {
            clockTimer.stop();
        }
        atmService.logout();
        dispose();
        new LoginFrame(atmService).setVisible(true);
    }

    private void exitApplication() {
        if (DialogUtils.confirmExit(this)) {
            if (clockTimer != null && clockTimer.isRunning()) {
                clockTimer.stop();
            }
            System.exit(0);
        }
    }

    private void startClock() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy | hh:mm:ss a");
        statusTimeLabel.setText(sdf.format(new Date()));
        clockTimer = new Timer(1000, e -> statusTimeLabel.setText(sdf.format(new Date())));
        clockTimer.start();
    }

    private void setupKeyBindings() {
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ESC_EXIT");
        getRootPane().getActionMap().put("ESC_EXIT", new AbstractAction() {
            private static final long serialVersionUID = 1L;
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) { exitApplication(); }
        });
    }
}