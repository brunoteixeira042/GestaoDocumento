package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import entidades.Arquivo;
import servicos.ArquivoServico;

public class GerenciamentoArquivoGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JList<String> listaArquivos;
	private DefaultListModel<String> modeloLista;
	private JButton botaoAdicionar;
	private JButton botaoExcluir;
	private ArquivoServico servicoArquivo;

	public GerenciamentoArquivoGUI() {
		servicoArquivo = new ArquivoServico();

		setTitle("Gerenciamento de Arquivos");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		modeloLista = new DefaultListModel<>();
		listaArquivos = new JList<>(modeloLista);
		JScrollPane painelRolagem = new JScrollPane(listaArquivos);

		botaoAdicionar = new JButton("Adicionar");
		botaoExcluir = new JButton("Excluir");

		JPanel painelBotoes = new JPanel();
		painelBotoes.add(botaoAdicionar);
		painelBotoes.add(botaoExcluir);

		add(painelRolagem, BorderLayout.CENTER);
		add(painelBotoes, BorderLayout.SOUTH);

		adicionarOuvintes();
		atualizarListaArquivos();

		setVisible(true);
	}

	private void adicionarOuvintes() {
		botaoExcluir.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        int indiceSelecionado = listaArquivos.getSelectedIndex();
		        if (indiceSelecionado != -1) {
		            String nomeArquivoSelecionado = modeloLista.get(indiceSelecionado);
		            Arquivo arquivoSelecionado = servicoArquivo.obterArquivoPorNome(nomeArquivoSelecionado);
		            if (arquivoSelecionado != null) {
		                servicoArquivo.deletarArquivo(arquivoSelecionado.getIdArquivo());
		                atualizarListaArquivos();
		            } else {
		                JOptionPane.showMessageDialog(GerenciamentoArquivoGUI.this,
		                    "Não foi possível encontrar o arquivo selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
		            }
		        }
		    }
		});

	}

	public void atualizarListaArquivos() {
		// Limpe a lista atual antes de preenchê-la novamente
		modeloLista.clear();
		// Obtenha a lista atualizada de arquivos do serviço de arquivo
		List<Arquivo> arquivos = servicoArquivo.listarArquivos();
		// Adicione os nomes dos arquivos à lista de modelos
		for (Arquivo arquivo : arquivos) {
			if (!arquivo.isNaLixeira()) {
				modeloLista.addElement(arquivo.getNomeArquivo());
			}
		}
	}

	public static void main(String[] args) {
		new GerenciamentoArquivoGUI();
	}
}
