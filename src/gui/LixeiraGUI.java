package gui;

import entidades.Arquivo;
import entidades.Usuario;
import servicos.ArquivoServico;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LixeiraGUI extends JFrame {
    private static final long serialVersionUID = 1L;
	private final ArquivoServico arquivoServico = new ArquivoServico();
    private final Usuario usuarioLogado;
    private JList<String> listaArquivosLixeira;
    private DefaultListModel<String> listModel;
    private List<Arquivo> arquivosNaLixeira;

    public LixeiraGUI(Usuario usuario) {
        this.usuarioLogado = usuario;
        setTitle("Lixeira");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout());

        listModel = new DefaultListModel<>();
        listaArquivosLixeira = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(listaArquivosLixeira);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        JButton botaoRestaurar = new JButton("Restaurar Arquivo");
        JButton botaoExcluirPermanentemente = new JButton("Excluir Permanentemente");

        painelBotoes.add(botaoRestaurar);
        painelBotoes.add(botaoExcluirPermanentemente);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        botaoRestaurar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaArquivosLixeira.getSelectedIndex();
                if (selectedIndex != -1) {
                    Arquivo arquivoSelecionado = arquivosNaLixeira.get(selectedIndex);
                    arquivoSelecionado.setNaLixeira(false);
                    arquivoServico.atualizarArquivo(arquivoSelecionado);
                    atualizarListaArquivosNaLixeira();
                } else {
                    JOptionPane.showMessageDialog(LixeiraGUI.this, "Selecione um arquivo para restaurar.");
                }
            }
        });

        botaoExcluirPermanentemente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaArquivosLixeira.getSelectedIndex();
                if (selectedIndex != -1) {
                    Arquivo arquivoSelecionado = arquivosNaLixeira.get(selectedIndex);
                    arquivoServico.excluirArquivo(arquivoSelecionado.getIdArquivo());
                    atualizarListaArquivosNaLixeira();
                } else {
                    JOptionPane.showMessageDialog(LixeiraGUI.this, "Selecione um arquivo para excluir permanentemente.");
                }
            }
        });

        add(painelPrincipal);
        atualizarListaArquivosNaLixeira();
    }

    private void atualizarListaArquivosNaLixeira() {
        arquivosNaLixeira = arquivoServico.listarArquivosNaLixeira();
        listModel.clear();
        for (Arquivo arquivo : arquivosNaLixeira) {
            listModel.addElement(arquivo.getNomeArquivo());
        }
    }

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
}
