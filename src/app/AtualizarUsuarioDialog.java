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

import gui.GerenciamentoUsuarioGUI;
import servicos.UsuarioServico;

public class AtualizarUsuarioDialog extends JDialog {
    private static final long serialVersionUID = 1L;
	private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JCheckBox checkBoxAdmin;
    private JButton botaoSalvar;
    private UsuarioServico usuarioServico;
    private int idUsuario;

    public AtualizarUsuarioDialog(JFrame parent, int idUsuario) {
        super(parent, "Atualizar Usuário", true);
        setUsuarioServico(new UsuarioServico());
        this.setIdUsuario(idUsuario);

        setSize(300, 200);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Nome de Usuário:"));
        campoUsuario = new JTextField();
        add(campoUsuario);

        add(new JLabel("Senha:"));
        campoSenha = new JPasswordField();
        add(campoSenha);

        add(new JLabel("Administrador:"));
        checkBoxAdmin = new JCheckBox();
        add(checkBoxAdmin);

        botaoSalvar = new JButton("Salvar");
        add(new JLabel());  // Espaço reservado
        add(botaoSalvar);

        // Carregar os dados do usuário
        // Use servicoUsuario para obter os detalhes do usuário com o idUsuario

        botaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoUsuario.getText();
                new String(campoSenha.getPassword());
                checkBoxAdmin.isSelected();

                // Lógica para atualizar usuário no banco de dados
                // Use servicoUsuario

                dispose();
                ((GerenciamentoUsuarioGUI) parent).atualizarListaUsuarios();
            }
        });

        setVisible(true);
    }

	public UsuarioServico getUsuarioServico() {
		return usuarioServico;
	}

	public void setUsuarioServico(UsuarioServico usuarioServico) {
		this.usuarioServico = usuarioServico;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
}
