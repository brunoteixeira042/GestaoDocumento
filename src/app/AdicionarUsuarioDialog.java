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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import entidades.Usuario;
import servicos.UsuarioServico;

public class AdicionarUsuarioDialog extends JDialog {
    private static final long serialVersionUID = 1L;
	private JTextField nomeField;
	private JTextField senhaField;
	private JCheckBox isAdminCheckbox; // Adicionado um checkbox para selecionar se o usuário é um administrador
	private JButton addButton;
	private UsuarioServico usuarioServico;

	public AdicionarUsuarioDialog(JFrame parent) {
		super(parent, "Adicionar Usuário", true);
		usuarioServico = new UsuarioServico();

		setSize(300, 200); // Aumentei a altura para acomodar o novo checkbox
		setLocationRelativeTo(parent);
		setLayout(new GridLayout(4, 2)); // Aumentei o número de linhas no layout

		add(new JLabel("Nome de Usuário:"));
		nomeField = new JTextField();
		add(nomeField);

		add(new JLabel("Senha:"));
		senhaField = new JPasswordField();
		add(senhaField);

		// Adiciona um checkbox para selecionar se o usuário é um administrador
		add(new JLabel("Administrador:"));
		isAdminCheckbox = new JCheckBox();
		add(isAdminCheckbox);

		addButton = new JButton("Adicionar");
		add(new JLabel());
		add(addButton);

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nome = nomeField.getText();
				String senha = senhaField.getText();

				try {
					usuarioServico.incluirUsuario(new Usuario(0, nome, senha, false)); // Cria um novo usuário com ID 0
					dispose();
				} catch (RuntimeException ex) {
					JOptionPane.showMessageDialog(AdicionarUsuarioDialog.this, ex.getMessage(), "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		setVisible(true);
	}
}
