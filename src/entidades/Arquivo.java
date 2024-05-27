 package entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Conexao.Conexao;

public class Arquivo {
    private int idArquivo;
    private String NomeArquivo;
    private String CaminhoArquivo;
    private int idUsuario;
    private boolean naLixeira;

    // Construtores

    public Arquivo() {}

    public Arquivo(int id, String nome, String caminho, int idUsuario, boolean naLixeira) {
        this.idArquivo = id;
        this.NomeArquivo = nome;
        this.CaminhoArquivo = caminho;
        this.idUsuario = idUsuario;
        this.naLixeira = naLixeira;
    }

    public Arquivo(String nome, String caminho, int idUsuario, boolean naLixeira) {
        this.NomeArquivo = nome;
        this.CaminhoArquivo= caminho;
        this.idUsuario = idUsuario;
        this.naLixeira = naLixeira;
    }

    // Getters e Setters

    
    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdArquivo() {
		return idArquivo;
	}

	public void setIdArquivo(int idArquivo) {
		this.idArquivo = idArquivo;
	}

	public String getNomeArquivo() {
		return NomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		NomeArquivo = nomeArquivo;
	}

	public String getCaminhoArquivo() {
		return CaminhoArquivo;
	}

	public void setCaminhoArquivo(String caminhoArquivo) {
		CaminhoArquivo = caminhoArquivo;
	}

	public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean isNaLixeira() {
        return naLixeira;
    }

    public void setNaLixeira(boolean naLixeira) {
        this.naLixeira = naLixeira;
    }

    // Métodos de banco de dados

    public static List<Arquivo> listarArquivos() {
        List<Arquivo> arquivos = new ArrayList<>();
        String sql = "SELECT * FROM tb_arquivo";

        try (Connection conexao = Conexao.getConexao();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Arquivo arquivo = new Arquivo(
                        rs.getInt("id_arquivo"),
                        rs.getString("nome"),
                        rs.getString("caminho"),
                        rs.getInt("id_usuario"),
                        rs.getBoolean("na_lixeira")
                );
                arquivos.add(arquivo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arquivos;
    }

    public void adicionarArquivo() {
        String sql = "INSERT INTO tb_arquivo (nome_arquivo, caminho_arquivo, id_usuario, na_lixeira) VALUES (?, ?, ?)";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, NomeArquivo);
            stmt.setString(2, CaminhoArquivo);
            stmt.setBoolean(3, naLixeira);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarArquivo() {
        String sql = "UPDATE tb_arquivo SET nome_arquivo = ?, caminho_arquivo = ?, UsuarioIdUsuario = ?, lixeira = ? WHERE id_arquivo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, NomeArquivo);
            stmt.setString(2, CaminhoArquivo);
            stmt.setInt(3, idUsuario);
            stmt.setBoolean(4, naLixeira);
            stmt.setInt(5, idArquivo);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removerArquivo() {
        String sql = "DELETE FROM tb_arquivo WHERE id_arquivo = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idArquivo);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
