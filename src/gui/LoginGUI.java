package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import entidades.Usuario;
import servicos.UsuarioServico;

public class LoginGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField campoLogin;
	private JPasswordField campoSenha;
	private JButton botaoLogin;
	private UsuarioServico servicoUsuario;

	public LoginGUI() {
		super("Login");
		servicoUsuario = new UsuarioServico();

		setLayout(new GridLayout(3, 2));

		add(new JLabel("Login:"));
		campoLogin = new JTextField();
		add(campoLogin);

		add(new JLabel("Senha:"));
		campoSenha = new JPasswordField();
		add(campoSenha);

		botaoLogin = new JButton("Login");
		add(botaoLogin);

		botaoLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String login = campoLogin.getText();
				String senha = new String(campoSenha.getPassword());

				try {
					Usuario usuario = servicoUsuario.autenticarUsuario(login, senha);
					JOptionPane.showMessageDialog(LoginGUI.this, "Bem-vindo " + usuario.getNomeUsuario());
					// Navegar para a próxima tela, por exemplo:
					//new GerenciamentoArquivoGUI().setVisible(true);
					//dispose();
					new GerenciamentoUsuarioGUI().setVisible(true);
					dispose();
				} catch (RuntimeException ex) {
					JOptionPane.showMessageDialog(LoginGUI.this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		setSize(300, 150);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new LoginGUI();
	}
}
