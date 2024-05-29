package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import entidades.Arquivo;
import entidades.Usuario;
import servicos.ArquivoServico;

public class GerenciamentoArquivoGUI extends JFrame {
    private static final long serialVersionUID = 1L;
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
        initComponents();
    }

    private void initComponents() {
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new FlowLayout());

        JButton btnAdicionar = new JButton("Adicionar Arquivo");
        JButton btnExcluir = new JButton("Excluir Arquivo");
        JButton btnLixeira = new JButton("Lixeira");
        JButton btnEditarArquivo = new JButton("Editar Arquivo");

        if (usuarioLogado.isAdmin()) {
            JButton btnEditarUsuarios = new JButton("Editar Usuários");
            btnEditarUsuarios.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Lógica para abrir a interface de edição de usuários
                    new GerenciamentoUsuarioGUI().setVisible(true);
                }
            });
            panelBotoes.add(btnEditarUsuarios);
        }

        panelBotoes.add(btnAdicionar);
        panelBotoes.add(btnExcluir);
        panelBotoes.add(btnLixeira);
        panelBotoes.add(btnEditarArquivo);

        container.add(panelBotoes, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        listaArquivos = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(listaArquivos);
        container.add(scrollPane, BorderLayout.CENTER);

        atualizarListaArquivos();

        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String nome = selectedFile.getName();
                    String caminho = selectedFile.getAbsolutePath();
                    arquivoServico.adicionarArquivo(nome, caminho, usuarioLogado.getIdUsuario(), false);
                    atualizarListaArquivos();
                }
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaArquivos.getSelectedIndex();
                if (selectedIndex != -1) {
                    Arquivo arquivoSelecionado = arquivos.get(selectedIndex);
                    arquivoServico.excluirArquivo(arquivoSelecionado.getIdArquivo());
                    atualizarListaArquivos();
                }
            }
        });

        btnLixeira.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaArquivos.getSelectedIndex();
                if (selectedIndex != -1) {
                    Arquivo arquivoSelecionado = arquivos.get(selectedIndex);
                    int opcao = JOptionPane.showConfirmDialog(null, "Deseja excluir permanentemente ou restaurar o arquivo?", "Lixeira", JOptionPane.YES_NO_OPTION);
                    if (opcao == JOptionPane.YES_OPTION) {
                        arquivoServico.excluirArquivo(arquivoSelecionado.getIdArquivo());
                    } else if (opcao == JOptionPane.NO_OPTION) {
                        arquivoServico.recuperarArquivoDaLixeira(arquivoSelecionado.getIdArquivo());
                    }
                    atualizarListaArquivos();
                }
            }
        });

        btnEditarArquivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaArquivos.getSelectedIndex();
                if (selectedIndex != -1) {
                    Arquivo arquivoSelecionado = arquivos.get(selectedIndex);
                    String novoNome = JOptionPane.showInputDialog("Novo nome do arquivo:", arquivoSelecionado.getNomeArquivo());
                    if (novoNome != null && !novoNome.trim().isEmpty()) {
                        arquivoSelecionado.setNomeArquivo(novoNome);
                        arquivoServico.atualizarArquivo(arquivoSelecionado);
                        atualizarListaArquivos();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um arquivo para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void atualizarListaArquivos() {
        listModel.clear();
        arquivos = arquivoServico.listarArquivosPorUsuario(usuarioLogado.getIdUsuario());
        for (Arquivo arquivo : arquivos) {
            listModel.addElement(arquivo.getNomeArquivo());
        }
    }
}
