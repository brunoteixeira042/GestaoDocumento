package app;

import gui.FileManagerGUI;

public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FileManagerGUI().setVisible(true);
            }
        });
    }
}
