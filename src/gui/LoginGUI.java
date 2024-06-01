package gui;

import entidades.Usuario;
import servicos.UsuarioServico;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField campoLogin;
    private JPasswordField campoSenha;
    private JButton botaoLogin;
    private UsuarioServico servicoUsuario;

    public LoginGUI() {
        super("Login");
        servicoUsuario = new UsuarioServico();

        setLayout(new GridLayout(8, 2));

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
                    if (usuario != null) {
                        JOptionPane.showMessageDialog(LoginGUI.this, "Bem-vindo " + usuario.getNomeUsuario());
                        new GerenciamentoArquivoGUI(usuario).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(LoginGUI.this, "Usuário ou senha inválidos", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(LoginGUI.this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginGUI();
    }
}
