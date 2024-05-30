package gui;

import entidades.Arquivo;
import entidades.Usuario;
import servicos.ArquivoServico;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class GerenciamentoArquivoGUI extends JFrame {
    private static final long serialVersionUID = -803695865767720747L;
	private final ArquivoServico arquivoServico = new ArquivoServico();
    private final Usuario usuarioLogado;
    private JList<String> listaArquivos;
    private DefaultListModel<String> listModel;
    private List<Arquivo> arquivos;

    public GerenciamentoArquivoGUI(Usuario usuario) {
        this.usuarioLogado = usuario;
        setTitle("Gerenciamento de Arquivos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout());

        listModel = new DefaultListModel<>();
        listaArquivos = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(listaArquivos);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        JButton botaoAdicionar = new JButton("Adicionar Arquivo");
        JButton botaoExcluir = new JButton("Excluir Arquivo");
        JButton botaoEditar = new JButton("Editar Arquivo");
        JButton botaoLixeira = new JButton("Lixeira");
        JButton botaoSair = new JButton("Sair");

        painelBotoes.add(botaoAdicionar);
        painelBotoes.add(botaoExcluir);
        painelBotoes.add(botaoEditar);
        painelBotoes.add(botaoLixeira);
        painelBotoes.add(botaoSair);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        if (usuarioLogado.isAdmin()) {
            JButton botaoGerenciarUsuarios = new JButton("Gerenciar Usuários");
            painelBotoes.add(botaoGerenciarUsuarios);

            botaoGerenciarUsuarios.addActionListener(e -> {
                // Código para abrir a tela de gerenciamento de usuários
                new GerenciamentoUsuarioGUI().setVisible(true);
            });
        }

        botaoAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(GerenciamentoArquivoGUI.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File arquivoSelecionado = fileChooser.getSelectedFile();
                    String nome = arquivoSelecionado.getName();
                    String caminho = arquivoSelecionado.getAbsolutePath();
                    arquivoServico.adicionarArquivo(nome, caminho, usuarioLogado.getIdUsuario(), false);
                    atualizarListaArquivos();
                }
            }
        });

        botaoExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaArquivos.getSelectedIndex();
                if (selectedIndex != -1) {
                    Arquivo arquivoSelecionado = arquivos.get(selectedIndex);
                    arquivoSelecionado.setNaLixeira(true);
                    arquivoServico.atualizarArquivo(arquivoSelecionado);
                    atualizarListaArquivos();
                } else {
                    JOptionPane.showMessageDialog(GerenciamentoArquivoGUI.this, "Selecione um arquivo para excluir.");
                }
            }
        });

        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaArquivos.getSelectedIndex();
                if (selectedIndex != -1) {
                    Arquivo arquivoSelecionado = arquivos.get(selectedIndex);
                    String novoNome = JOptionPane.showInputDialog(GerenciamentoArquivoGUI.this, "Edite o nome do arquivo:", arquivoSelecionado.getNomeArquivo());
                    if (novoNome != null && !novoNome.trim().isEmpty()) {
                        arquivoSelecionado.setNomeArquivo(novoNome);
                        arquivoServico.atualizarArquivo(arquivoSelecionado);
                        atualizarListaArquivos();
                    }
                } else {
                    JOptionPane.showMessageDialog(GerenciamentoArquivoGUI.this, "Selecione um arquivo para editar.");
                }
            }
        });

        botaoLixeira.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LixeiraGUI(usuarioLogado,GerenciamentoArquivoGUI.this).setVisible(true);
            }
        });


        botaoSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginGUI().setVisible(true);
            }
        });

        add(painelPrincipal);
        atualizarListaArquivos();
    }

    public void atualizarListaArquivos() {
        arquivos = arquivoServico.listarArquivos();
        listModel.clear();
        for (Arquivo arquivo : arquivos) {
            if (!arquivo.isNaLixeira()) {
                listModel.addElement(arquivo.getNomeArquivo());
            }
        }
    }
}