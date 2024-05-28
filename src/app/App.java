package app;

<<<<<<< HEAD
import gui.FileManagerGUI;

public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FileManagerGUI().setVisible(true);
            }
        });
=======
import gui.LoginGUI;

public class App {
    public static void main(String[] args) {
        new LoginGUI();
>>>>>>> cf67d02b28e10e785a28adfa09cc80ffa8fc1664
    }
}
