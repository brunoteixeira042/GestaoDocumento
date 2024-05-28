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

    public void adicionarArquivo(String nome, String caminho, int idUsuario, boolean naLixeira) {
        String sql = "INSERT INTO tb_arquivo (nome_arquivo, caminho_arquivo, id_usuario, lixeira) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, caminho);
            stmt.setInt(3, idUsuario);
            stmt.setBoolean(4, naLixeira);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar arquivo", e);
        }
    }

    public void atualizarArquivo(Arquivo arquivo) {
        String sql = "UPDATE tb_arquivo SET nome_arquivo = ?, caminho_arquivo = ?, id_usuario = ?, lixeira = ? WHERE id_arquivo = ?";
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

    public List<Arquivo> listarArquivos() {
        List<Arquivo> arquivos = new ArrayList<>();
        String sql = "SELECT * FROM tb_arquivo";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id_arquivo");
                String nome = rs.getString("nome_arquivo");
                String caminho = rs.getString("caminho_arquivo");
                int idUsuario = rs.getInt("UsuarioIdUsuario");
                boolean naLixeira = rs.getBoolean("lixeira");
                arquivos.add(new Arquivo(id, nome, caminho, idUsuario, naLixeira));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar arquivos", e);
        }
        return arquivos;
    }


    public void deletarArquivo(int idArquivo) {
        String sql = "DELETE FROM tb_arquivo WHERE id_arquivo = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idArquivo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar arquivo", e);
        }
    }
    
    
    public Arquivo obterArquivoPorNome(String nome) {
        String sql = "SELECT * FROM tb_arquivo WHERE nome_arquivo = ?";
        try (Connection conn = Conexao.getConexao(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_arquivo");
                    String nomeArquivo = rs.getString("nome_arquivo");
                    String caminho = rs.getString("caminho_arquivo");
                    int idUsuario = rs.getInt("id_usuario");
                    boolean naLixeira = rs.getBoolean("lixeira");
                    return new Arquivo(id, nomeArquivo, caminho, idUsuario, naLixeira);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter arquivo por nome", e);
        }
        return null;
    }

}
