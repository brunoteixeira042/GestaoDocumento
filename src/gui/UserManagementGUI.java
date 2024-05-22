package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import app.AddUserDialog;
import app.UpdateUserDialog;
import entidades.Administrador;
import entidades.Usuario;
import servicos.UsuarioServico;

public class UserManagementGUI extends JFrame {
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JButton addButton, removeButton, updateButton;
    private UsuarioServico usuarioServico;

    public UserManagementGUI() {
        usuarioServico = new UsuarioServico();

        setTitle("User Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        refreshUserList();

        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Username", "Role"}, 0);
        userTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(userTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add User");
        removeButton = new JButton("Remove User");
        updateButton = new JButton("Update User");

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        addListeners();
    }

    private void addListeners() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddUserDialog(UserManagementGUI.this);
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int userId = (int) tableModel.getValueAt(selectedRow, 0);
                    usuarioServico.removerUsuario(userId);
                    refreshUserList();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int userId = (int) tableModel.getValueAt(selectedRow, 0);
                    new UpdateUserDialog(UserManagementGUI.this, userId);
                }
            }
        });
    }

    public void refreshUserList() {
        tableModel.setRowCount(0);

        List<Usuario> usuarios = usuarioServico.listarUsuarios();

        for (Usuario usuario : usuarios) {
            tableModel.addRow(new Object[]{
                    usuario.getId(),
                    usuario.getNome(),
                    usuario instanceof Administrador ? "Admin" : "User"
            });
        }
    }
}
