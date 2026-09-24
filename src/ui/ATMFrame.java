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
    private final ATMService atmService;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    // Panels
    private BalancePanel balancePanel;
    private StatementPanel statementPanel;

    private JLabel statusTimeLabel;

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
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                exitApplication();
            }
        });

        setJMenuBar(createMenuBar());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UIConstants.PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel welcomeLabel = new JLabel("Welcome, " + atmService.getCurrentUser().getName());
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
        statementPanel = new StatementPanel(atmService);

        cardPanel.add(balancePanel, "Balance");
        cardPanel.add(new DepositPanel(atmService, this), "Deposit");
        cardPanel.add(new WithdrawPanel(atmService, this), "Withdraw");
        cardPanel.add(statementPanel, "Statement");
        cardPanel.add(new ChangePinPanel(atmService), "Pin");

        add(cardPanel, BorderLayout.CENTER);

        // Status Bar
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        statusTimeLabel = new JLabel();
        statusBar.add(new JLabel("Secure Connection Established"), BorderLayout.WEST);
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
        cardLayout.show(cardPanel, name);
    }

    private void logout() {
        atmService.logout();
        dispose();
        new LoginFrame(atmService).setVisible(true);
    }

    private void exitApplication() {
        if (DialogUtils.confirmExit(this)) System.exit(0);
    }

    private void startClock() {
        new Timer(1000, e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy | hh:mm:ss a");
            statusTimeLabel.setText(sdf.format(new Date()));
        }).start();
    }

    private void setupKeyBindings() {
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ESC_EXIT");
        getRootPane().getActionMap().put("ESC_EXIT", new AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) { exitApplication(); }
        });
    }
}