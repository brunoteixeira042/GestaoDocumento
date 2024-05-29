package servicos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Conexao.Conexao;
import entidades.Arquivo;

public class ArquivoServico {

    public void adicionarArquivo(String nome, String caminho, int UsuarioIdUsuario, boolean naLixeira) {
        String sql = "INSERT INTO tb_arquivo (nome_arquivo, caminho_arquivo, UsuarioIdUsuario, lixeira) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nome);
            stmt.setString(2, caminho);
            stmt.setInt(3, UsuarioIdUsuario);
            stmt.setBoolean(4, naLixeira);
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int idArquivo = rs.getInt(1);
                if (naLixeira) {
                    adicionarArquivoALixeira(idArquivo);
                }
            }
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
        removerArquivoDaLixeira(idArquivo);
        String sql = "DELETE FROM tb_arquivo WHERE id_arquivo = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idArquivo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir arquivo", e);
        }
    }

    public List<Arquivo> listarArquivos() {
        List<Arquivo> arquivos = new ArrayList<>();
        String sql = "SELECT * FROM tb_arquivo";
        try (Connection conexao = Conexao.getConexao();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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
        } catch (SQLException e) {
            e.printStackTrace();
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

            if (arquivo.isNaLixeira()) {
                adicionarArquivoALixeira(arquivo.getIdArquivo());
            } else {
                removerArquivoDaLixeira(arquivo.getIdArquivo());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar arquivo", e);
        }
    }

    private void adicionarArquivoALixeira(int idArquivo) {
        String sql = "INSERT INTO tb_lixeira (arquivo_id_arquivo) VALUES (?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idArquivo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar arquivo à lixeira", e);
        }
    }

    private void removerArquivoDaLixeira(int idArquivo) {
        String sql = "DELETE FROM tb_lixeira WHERE arquivo_id_arquivo = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idArquivo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover arquivo da lixeira", e);
        }
    }

    public List<Arquivo> listarArquivosNaLixeira() {
        List<Arquivo> arquivosNaLixeira = new ArrayList<>();
        String sql = "SELECT * FROM tb_arquivo WHERE lixeira = true";
        try (Connection conexao = Conexao.getConexao();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Arquivo arquivo = new Arquivo(
                        rs.getInt("id_arquivo"),
                        rs.getString("nome_arquivo"),
                        rs.getString("caminho_arquivo"),
                        rs.getInt("UsuarioIdUsuario"),
                        rs.getBoolean("lixeira")
                );
                arquivosNaLixeira.add(arquivo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arquivosNaLixeira;
    }
}
