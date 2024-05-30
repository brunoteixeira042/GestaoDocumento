package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import servicos.ArquivoServico;

//public class AdicionarArquivoDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField campoNome;
    private JTextField campoCaminho;
    private JTextField campoIdUsuario;
    private JCheckBox checkBoxLixeira;
    private JButton botaoAdicionar;
    private ArquivoServico servicoArquivo;

    public AdicionarArquivoDialog(JFrame parent) {
        super(parent, "Adicionar Arquivo", true);
        servicoArquivo = new ArquivoServico();

        setSize(300, 250);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Nome do Arquivo:"));
        campoNome = new JTextField();
        add(campoNome);

        add(new JLabel("Caminho do Arquivo:"));
        campoCaminho = new JTextField();
        add(campoCaminho);

        add(new JLabel("ID do Usuário:"));
        campoIdUsuario = new JTextField();
        add(campoIdUsuario);

        add(new JLabel("Lixeira:"));
        checkBoxLixeira = new JCheckBox();
        add(checkBoxLixeira);

        botaoAdicionar = new JButton("Adicionar");
        add(new JLabel());  // Espaço reservado
        add(botaoAdicionar);

        botaoAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = campoNome.getText();
                String caminho = campoCaminho.getText();
                int idUsuario = Integer.parseInt(campoIdUsuario.getText());
                boolean naLixeira = checkBoxLixeira.isSelected();

                // Adicionar o arquivo no banco de dados
                servicoArquivo.adicionarArquivo(nome, caminho, idUsuario, naLixeira);

                dispose();
                ((GerenciamentoArquivoGUI) parent).atualizarListaArquivos();
            }
        });

        setVisible(true);
    }
}
