package app;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import entidades.Arquivo;
import gui.GerenciamentoArquivoGUI;
import servicos.ArquivoServico;

///public class AtualizarArquivoDialog extends JDialog {
    private static final long serialVersionUID = 1L;
	private JTextField campoNome;
    private JTextField campoCaminho;
    private JCheckBox checkBoxLixeira;
    private JButton botaoAtualizar;
    private ArquivoServico servicoArquivo;
    private Arquivo arquivo;

    public AtualizarArquivoDialog(JFrame parent, Arquivo arquivo) {
        super(parent, "Atualizar Arquivo", true);
        this.setArquivo(arquivo);
        servicoArquivo = new ArquivoServico();

        setSize(300, 200);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Nome do Arquivo:"));
        campoNome = new JTextField(arquivo.getNomeArquivo());
        add(campoNome);

        add(new JLabel("Caminho do Arquivo:"));
        campoCaminho = new JTextField(arquivo.getCaminhoArquivo());
        add(campoCaminho);

        add(new JLabel("Lixeira:"));
        checkBoxLixeira = new JCheckBox();
        checkBoxLixeira.setSelected(arquivo.isNaLixeira());
        add(checkBoxLixeira);

        botaoAtualizar = new JButton("Atualizar");
        add(new JLabel());  // Espaço reservado
        add(botaoAtualizar);

        botaoAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arquivo.setNomeArquivo(campoNome.getText());
                arquivo.setCaminhoArquivo(campoCaminho.getText());
                arquivo.setNaLixeira(checkBoxLixeira.isSelected());

                // Atualizar o arquivo no banco de dados
                servicoArquivo.atualizarArquivo(arquivo);

                dispose();
                ((GerenciamentoArquivoGUI) parent).atualizarListaArquivos();
            }
        });

        setVisible(true);
    }

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}
}
