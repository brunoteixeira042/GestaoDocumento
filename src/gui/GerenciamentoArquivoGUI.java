package gui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import entidades.Arquivo;
import entidades.Usuario;
import servicos.ArquivoServico;
import servicos.UsuarioServico;

public class GerenciamentoArquivoGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JList<String> listaArquivos;
    private DefaultListModel<String> modeloLista;
    private JButton botaoAdicionar, botaoExcluir, botaoEditar;
    private ArquivoServico servicoArquivo;
    private UsuarioServico servicoUsuario;
    private JTextField campoNomeArquivo, campoCaminhoArquivo;
    private JList<Usuario> listaUsuarios;
    private JCheckBox checkBoxLixeira;

    public GerenciamentoArquivoGUI() {
        servicoArquivo = new ArquivoServico();
        servicoUsuario = new UsuarioServico();

        setTitle("Gerenciamento de Arquivos");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout());

        JPanel painelLista = new JPanel(new GridLayout(1, 2));
        listaUsuarios = new JList<>();
        JScrollPane painelRolagemUsuarios = new JScrollPane(listaUsuarios);
        modeloLista = new DefaultListModel<>();
        listaArquivos = new JList<>(modeloLista);
        JScrollPane painelRolagemArquivos = new JScrollPane(listaArquivos);
        painelLista.add(painelRolagemUsuarios);
        painelLista.add(painelRolagemArquivos);

        JPanel painelBotoes = new JPanel();
        botaoAdicionar = new JButton("Adicionar");
        botaoExcluir = new JButton("Excluir");
        botaoEditar = new JButton("Editar");
        checkBoxLixeira = new JCheckBox("Enviar para lixeira");
        painelBotoes.add(botaoAdicionar);
        painelBotoes.add(botaoExcluir);
        painelBotoes.add(botaoEditar);
        painelBotoes.add(checkBoxLixeira);

        JPanel painelCampos = new JPanel();
        campoNomeArquivo = new JTextField(20);
        campoCaminhoArquivo = new JTextField(20);
        painelCampos.add(new JLabel("Nome do Arquivo:"));
        painelCampos.add(campoNomeArquivo);
        painelCampos.add(new JLabel("Caminho do Arquivo:"));
        painelCampos.add(campoCaminhoArquivo);

        painelPrincipal.add(painelLista, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        painelPrincipal.add(painelCampos, BorderLayout.NORTH);

        add(painelPrincipal);

        adicionarOuvintes();
        atualizarListaUsuarios();
        atualizarListaArquivos();

        setVisible(true);
    }

    private void adicionarOuvintes() {
        botaoAdicionar.addActionListener(e -> {
            Usuario usuarioSelecionado = listaUsuarios.getSelectedValue();
            if (usuarioSelecionado != null) {
                String nomeArquivo = campoNomeArquivo.getText();
                String caminhoArquivo = campoCaminhoArquivo.getText();
                boolean naLixeira = checkBoxLixeira.isSelected();
                servicoArquivo.adicionarArquivo(nomeArquivo, caminhoArquivo, usuarioSelecionado.getIdUsuario(), naLixeira);
                atualizarListaArquivos();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um usuário antes de adicionar o arquivo.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Adicione ouvintes para os outros botões, se necessário
    }

    private void atualizarListaUsuarios() {
        List<Usuario> usuarios = servicoUsuario.listarUsuarios();
        listaUsuarios.setListData(usuarios.toArray(new Usuario[0]));
    }

    public void atualizarListaArquivos() {
        modeloLista.clear();
        List<Arquivo> arquivos = servicoArquivo.listarArquivos();
        arquivos.stream().filter(a -> !a.isNaLixeira()).forEach(a -> modeloLista.addElement(a.getNomeArquivo()));
    }

    public static void main(String[] args) {
        new GerenciamentoArquivoGUI();
    }
}
