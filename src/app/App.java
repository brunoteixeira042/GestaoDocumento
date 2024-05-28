package app;

import gui.LoginGUI;

public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginGUI();
        });
    }
}
