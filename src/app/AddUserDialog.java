package app;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import gui.UserManagementGUI;
import servicos.UsuarioServico;

public class AddUserDialog extends JDialog {
    private JTextField userField;
    private JPasswordField passField;
    private JCheckBox isAdminCheckBox;
    private JButton saveButton;
    private UsuarioServico usuarioServico;

    public AddUserDialog(JFrame parent) {
        super(parent, "Add User", true);
        usuarioServico = new UsuarioServico();

        setSize(300, 200);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Username:"));
        userField = new JTextField();
        add(userField);

        add(new JLabel("Password:"));
        passField = new JPasswordField();
        add(passField);

        add(new JLabel("Admin:"));
        isAdminCheckBox = new JCheckBox();
        add(isAdminCheckBox);

        saveButton = new JButton("Save");
        add(new JLabel());  // Placeholder
        add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                boolean isAdmin = isAdminCheckBox.isSelected();

                // Lógica para adicionar usuário no banco de dados
                // Use usuarioServico

                dispose();
                ((UserManagementGUI) parent).refreshUserList();
            }
        });

        setVisible(true);
    }
}
