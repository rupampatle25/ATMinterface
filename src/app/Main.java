package app;

import service.ATMService;
import ui.LoginFrame;
import utils.AppTheme;

import javax.swing.SwingUtilities;

/**
 * Entry point for the ATM Interface application.
 */
public class Main {
    public static void main(String[] args) {
        // Setup pure Java custom Flat-style theme before launching GUI
        AppTheme.setupTheme();

        // Launch application on Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            ATMService atmService = new ATMService();
            LoginFrame loginFrame = new LoginFrame(atmService);
            loginFrame.setVisible(true);
        });
    }
}