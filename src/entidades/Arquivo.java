package entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Conexao.Conexao;

public class Arquivo {
	private int IdArquivo;
	private String NomeArquivo;
	private String CaminhoArquivo;
	private int UsuarioIdUsuario;
	private boolean Lixeira;

	public Arquivo(String nomeArquivo, String caminhoArquivo, int usuarioIdUsuario, boolean lixeira) {
		super();
		NomeArquivo = nomeArquivo;
		CaminhoArquivo = caminhoArquivo;
		UsuarioIdUsuario = usuarioIdUsuario;
		Lixeira = lixeira;
	}

	public int getIdArquivo() {
		return IdArquivo;
	}

	public void setIdArquivo(int idArquivo) {
		IdArquivo = idArquivo;
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

	public int getUsuarioIdUsuario() {
		return UsuarioIdUsuario;
	}

	public void setUsuarioIdUsuario(int usuarioIdUsuario) {
		UsuarioIdUsuario = usuarioIdUsuario;
	}

	public boolean isLixeira() {
		return Lixeira;
	}

	public void setLixeira(boolean lixeira) {
		Lixeira = lixeira;
	}

	public void incluirArquivo(Arquivo arquivo) {
		String sql = "INSERT INTO tb_arquivo (nome_arquivo, caminho_arquivo, UsuarioIdUsuario, lixeira) VALUES (?, ?, ?, ?)";
		try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, arquivo.getNomeArquivo());
			stmt.setString(2, arquivo.getCaminhoArquivo());
			stmt.setInt(3, arquivo.getUsuarioIdUsuario());
			stmt.setBoolean(4, arquivo.isLixeira());
			stmt.executeUpdate();
			System.out.println("Arquivo adicionado com sucesso.");
		} catch (SQLException e) {
			System.out.println("Erro ao adicionar Arquivo: " + e.getMessage());
		}
	}

	public Arquivo buscarArquivo(int id) {
	    String sql = "SELECT * FROM tb_arquivo WHERE nome_arquivo = ?";
	    try (Connection conn = Conexao.getConexao(); 
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, id);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            // Utilize o construtor adequado para criar a instância de Arquivo
	            return new Arquivo(
	                rs.getString("nome_arquivo"),
	                rs.getString("caminho_arquivo"),
	                rs.getInt("UsuarioIdUsuario"),
	                rs.getBoolean("lixeira")
	            );
	        }
	    } catch (SQLException e) {
	        System.out.println("Erro ao buscar arquivo: " + e.getMessage());
	    }
	    return null;
	}

	public void excluirArquivo(int id) {
		try (Connection conn = Conexao.getConexao()) {
			excluirArquivo(conn, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void excluirArquivo(Connection conn, int id) throws SQLException {
		String sqlUpdate = "UPDATE tb_arquivo SET lixeira = TRUE WHERE id = ?";
		String sqlInsert = "INSERT INTO tb_lixeira (arquivo_id) VALUES (?)";

		try {
			conn.setAutoCommit(false);

			try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate);
					PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {

				// Atualizar campo lixeira na tabela tb_arquivo
				stmtUpdate.setInt(1, id);
				int afetadosUpdate = stmtUpdate.executeUpdate();

				if (afetadosUpdate > 0) {
					// Inserir registro na tabela tb_lixeira
					stmtInsert.setInt(1, id);
					stmtInsert.executeUpdate();

					// Confirmar transação
					conn.commit();
					System.out.println("Arquivo movido para a lixeira com sucesso.");
				} else {
					System.out.println("Nenhum arquivo encontrado com este ID.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Erro ao mover arquivo para a lixeira: " + e.getMessage());
				conn.rollback();
			}
		} finally {
			conn.setAutoCommit(true);
		}
	}
}