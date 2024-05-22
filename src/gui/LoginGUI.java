package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import entidades.Administrador;
import entidades.Usuario;
import servicos.UsuarioServico;

public class LoginGUI extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;

    public LoginGUI() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Username:"));
        userField = new JTextField();
        panel.add(userField);

        panel.add(new JLabel("Password:"));
        passField = new JPasswordField();
        panel.add(passField);

        loginButton = new JButton("Login");
        panel.add(new JLabel());  // Placeholder
        panel.add(loginButton);

        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                UsuarioServico usuarioServico = new UsuarioServico();
                Usuario usuario = usuarioServico.autenticar(username, password);

                if (usuario != null) {
                    dispose();
                    if (usuario instanceof Administrador) {
                        new FileManagementGUI((Administrador) usuario);
                    } else {
                        new FileManagementGUI(usuario);
                    }
                } else {
                    JOptionPane.showMessageDialog(LoginGUI.this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
