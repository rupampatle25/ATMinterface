package utils;

import ui.UIConstants;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Global application UI styling logic.
 */
public class AppTheme {

    public static void setupTheme() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            UIManager.put("Panel.background", UIConstants.BG_COLOR);
            UIManager.put("Label.font", UIConstants.REGULAR_FONT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JTextField createRoundedTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(UIConstants.REGULAR_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(UIConstants.BORDER_COLOR, 8),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }

    public static JPasswordField createRoundedPasswordField(int columns) {
        JPasswordField field = new JPasswordField(columns);
        field.setFont(UIConstants.REGULAR_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(UIConstants.BORDER_COLOR, 8),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }

    public static JButton createRoundedButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(UIConstants.BOLD_FONT);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(bg, 10),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(bg.darker()); }
            public void mouseExited(java.awt.event.MouseEvent e) { btn.setBackground(bg); }
        });
        return btn;
    }

    public static class RoundedBorder extends AbstractBorder {
        private static final long serialVersionUID = 1L;
        private final Color color;
        private final int radius;

        public RoundedBorder(Color color, int radius) {
            this.color = color;
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
            g2.dispose();
        }
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius/2, radius/2, radius/2, radius/2);
        }
    }
}