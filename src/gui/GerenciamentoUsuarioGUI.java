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

import app.AdicionarUsuarioDialog;
import app.AtualizarUsuarioDialog;
import entidades.Usuario;
import servicos.UsuarioServico;

public class GerenciamentoUsuarioGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable tabelaUsuarios;
    private DefaultTableModel modeloTabela;
    private JButton botaoAdicionar, botaoRemover, botaoAtualizar;
    private UsuarioServico servicoUsuario;

    public GerenciamentoUsuarioGUI() {
        servicoUsuario = new UsuarioServico();

        setTitle("Gerenciamento de Usuários");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        atualizarListaUsuarios();

        setVisible(true);
    }

    private void initUI() {
        JPanel painel = new JPanel(new BorderLayout());

        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome de usuário", "Função"}, 0);
        tabelaUsuarios = new JTable(modeloTabela);

        JScrollPane painelRolagem = new JScrollPane(tabelaUsuarios);
        painel.add(painelRolagem, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        botaoAdicionar = new JButton("Adicionar Usuário");
        botaoRemover = new JButton("Remover Usuário");
        botaoAtualizar = new JButton("Atualizar Usuário");

        painelBotoes.add(botaoAdicionar);
        painelBotoes.add(botaoRemover);
        painelBotoes.add(botaoAtualizar);

        painel.add(painelBotoes, BorderLayout.SOUTH);

        add(painel);

        adicionarListeners();
    }

    private void adicionarListeners() {
        botaoAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdicionarUsuarioDialog(GerenciamentoUsuarioGUI.this, servicoUsuario);
            }
        });

        botaoRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tabelaUsuarios.getSelectedRow();
                if (linhaSelecionada >= 0) {
                    int idUsuario = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
                    servicoUsuario.excluirUsuario(idUsuario);
                    atualizarListaUsuarios();
                }
            }
        });

        botaoAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tabelaUsuarios.getSelectedRow();
                if (linhaSelecionada >= 0) {
                    int idUsuario = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
                    Usuario usuario = servicoUsuario.buscarUsuario(idUsuario);
                    new AtualizarUsuarioDialog(GerenciamentoUsuarioGUI.this, usuario);
                }
            }
        });

    }

    public void atualizarListaUsuarios() {
        modeloTabela.setRowCount(0);

        List<Usuario> usuarios = servicoUsuario.listarUsuarios();

        for (Usuario usuario : usuarios) {
            modeloTabela.addRow(new Object[]{
                usuario.getIdUsuario(),
                usuario.getNomeUsuario(),
                usuario.isAdmin() ? "Admin" : "Usuário"
            });
        }
    }

    public static void main(String[] args) {
        new GerenciamentoUsuarioGUI();
    }
}
