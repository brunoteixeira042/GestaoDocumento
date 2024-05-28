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

import entidades.Usuario;
import gui.GerenciamentoUsuarioGUI;
import servicos.UsuarioServico;

public class AtualizarUsuarioDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JTextField campoLogin;
    private JTextField campoCaminhoPasta;
    private JCheckBox checkBoxAdmin;
    private JButton botaoSalvar;
    private UsuarioServico usuarioServico;
    private Usuario usuario;

    public AtualizarUsuarioDialog(JFrame parent, Usuario usuario) {
        super(parent, "Atualizar Usuário", true);
        this.setUsuario(usuario);
        usuarioServico = new UsuarioServico();

        setSize(300, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Nome de Usuário:"));
        campoUsuario = new JTextField(usuario.getNomeUsuario());
        add(campoUsuario);

        add(new JLabel("Senha:"));
        campoSenha = new JPasswordField(usuario.getSenhaUsuario());
        add(campoSenha);

        add(new JLabel("Login:"));
        campoLogin = new JTextField(usuario.getLoginUsuario());
        add(campoLogin);

        add(new JLabel("Caminho da Pasta:"));
        campoCaminhoPasta = new JTextField(usuario.getCaminhoPasta());
        add(campoCaminhoPasta);

        add(new JLabel("Administrador:"));
        checkBoxAdmin = new JCheckBox();
        checkBoxAdmin.setSelected(usuario.isAdmin());
        add(checkBoxAdmin);

        botaoSalvar = new JButton("Salvar");
        add(new JLabel());  // Espaço reservado
        add(botaoSalvar);

        botaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String novoNome = campoUsuario.getText();
                String novaSenha = new String(campoSenha.getPassword());
                String novoLogin = campoLogin.getText();
                String novoCaminhoPasta = campoCaminhoPasta.getText();
                boolean novoAdmin = checkBoxAdmin.isSelected();

                usuario.setNomeUsuario(novoNome);
                usuario.setSenhaUsuario(novaSenha);
                usuario.setLoginUsuario(novoLogin);
                usuario.setCaminhoPasta(novoCaminhoPasta);
                usuario.setAdmin(novoAdmin);

                usuarioServico.atualizarUsuario(usuario);

                dispose();
                ((GerenciamentoUsuarioGUI) parent).atualizarListaUsuarios();
            }
        });

        setVisible(true);
    }

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
