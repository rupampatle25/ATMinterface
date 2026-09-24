package utils;

import ui.UIConstants;
import javax.swing.*;
import java.awt.*;

/**
 * Reusable dialog utilities.
 */
public class DialogUtils {

    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showSuccess(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirmExit(Component parent) {
        int result = JOptionPane.showConfirmDialog(parent,
                "Do you really want to exit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    public static void showAbout(Component parent) {
        String msg = "ATM Interface Application\nVersion 1.0\nDeveloped for CodSoft Internship.";
        JOptionPane.showMessageDialog(parent, msg, "About", JOptionPane.INFORMATION_MESSAGE);
    }
}