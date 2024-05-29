package servicos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conexao.Conexao;
import entidades.Arquivo;

public class ArquivoServico {

    public void adicionarArquivo(String nome, String caminho, int UsuarioIdUsuario, boolean naLixeira) {
        String sql = "INSERT INTO tb_arquivo (nome_arquivo, caminho_arquivo, UsuarioIdUsuario, lixeira) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, caminho);
            stmt.setInt(3, UsuarioIdUsuario);
            stmt.setBoolean(4, naLixeira);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar arquivo", e);
        }
    }

    public Arquivo obterArquivoPorNome(String nome) {
        String sql = "SELECT * FROM tb_arquivo WHERE nome_arquivo = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Arquivo(
                        rs.getInt("id_arquivo"),
                        rs.getString("nome_arquivo"),
                        rs.getString("caminho_arquivo"),
                        rs.getInt("UsuarioIdUsuario"), 
                        rs.getBoolean("lixeira")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter arquivo por nome", e);
        }
        return null;
    }

    public void excluirArquivo(int idArquivo) {
        String sql = "DELETE FROM tb_arquivo WHERE id_arquivo = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idArquivo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir arquivo", e);
        }
    }

    public List<Arquivo> listarArquivosPorUsuario(int idUsuario) {
        List<Arquivo> arquivos = new ArrayList<>();
        String sql = "SELECT * FROM tb_arquivo WHERE UsuarioIdUsuario = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Arquivo arquivo = new Arquivo(
                        rs.getInt("id_arquivo"),
                        rs.getString("nome_arquivo"),
                        rs.getString("caminho_arquivo"),
                        rs.getInt("UsuarioIdUsuario"),
                        rs.getBoolean("lixeira")
                    );
                    arquivos.add(arquivo);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar arquivos por usuário", e);
        }
        return arquivos;
    }


    public void atualizarArquivo(Arquivo arquivo) {
        String sql = "UPDATE tb_arquivo SET nome_arquivo = ?, caminho_arquivo = ?, UsuarioIdUsuario = ?, lixeira = ? WHERE id_arquivo = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, arquivo.getNomeArquivo());
            stmt.setString(2, arquivo.getCaminhoArquivo());
            stmt.setInt(3, arquivo.getIdUsuario());
            stmt.setBoolean(4, arquivo.isNaLixeira());
            stmt.setInt(5, arquivo.getIdArquivo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar arquivo", e);
        }
    }

    public void recuperarArquivoDaLixeira(int idArquivo) {
        String deleteSql = "DELETE FROM tb_lixeira WHERE arquivo_id_arquivo = ?";
        String updateSql = "UPDATE tb_arquivo SET lixeira = false WHERE id_arquivo = ?";
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement deleteStmt = conexao.prepareStatement(deleteSql);
             PreparedStatement updateStmt = conexao.prepareStatement(updateSql)) {
            deleteStmt.setInt(1, idArquivo);
            deleteStmt.executeUpdate();

            updateStmt.setInt(1, idArquivo);
            updateStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar arquivo da lixeira", e);
        }
    }
}
