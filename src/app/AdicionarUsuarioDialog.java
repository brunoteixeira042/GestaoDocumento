package app;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import entidades.Usuario;
import gui.GerenciamentoUsuarioGUI;
import servicos.UsuarioServico;

public class AdicionarUsuarioDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private JTextField campoNome;
    private JTextField campoSenha;
    private JTextField campoLogin;
    private JTextField campoCaminhoPasta;
    private JCheckBox checkBoxAdmin;
    private JButton botaoAdicionar;
    private UsuarioServico servicoUsuario;

    public AdicionarUsuarioDialog(JFrame parent) {
        super(parent, "Adicionar Usuário", true);
        servicoUsuario = new UsuarioServico();

        setSize(300, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Nome do Usuário:"));
        campoNome = new JTextField();
        add(campoNome);

        add(new JLabel("Senha do Usuário:"));
        campoSenha = new JTextField();
        add(campoSenha);

        add(new JLabel("Login do Usuário:"));
        campoLogin = new JTextField();
        add(campoLogin);

        add(new JLabel("Caminho da Pasta:"));
        campoCaminhoPasta = new JTextField();
        add(campoCaminhoPasta);

        add(new JLabel("Administrador:"));
        checkBoxAdmin = new JCheckBox();
        add(checkBoxAdmin);

        botaoAdicionar = new JButton("Adicionar");
        add(new JLabel());  // Espaço reservado
        add(botaoAdicionar);

        botaoAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = campoNome.getText();
                String senha = campoSenha.getText();
                String login = campoLogin.getText();
                String caminhoPasta = campoCaminhoPasta.getText();
                boolean isAdmin = checkBoxAdmin.isSelected();

                if (nome.isEmpty() || senha.isEmpty() || login.isEmpty() || caminhoPasta.isEmpty()) {
                    JOptionPane.showMessageDialog(AdicionarUsuarioDialog.this, "Todos os campos são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Adicionar o usuário no banco de dados
                Usuario novoUsuario = new Usuario(0, nome, senha, login, caminhoPasta, isAdmin);
                servicoUsuario.adicionarUsuario(novoUsuario);

                dispose();
                if (parent instanceof GerenciamentoUsuarioGUI) {
                    ((GerenciamentoUsuarioGUI) parent).atualizarListaUsuarios();
                }
            }
        });

        setVisible(true);
    }
}
