package entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Conexao.Conexao;

public class Lixeira {

    public void adicionarArquivo(int idDoArquivo) {
        String sql = "INSERT INTO tb_lixeira (id_arquivo) VALUES (?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDoArquivo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar arquivo na lixeira", e);
        }
    }

    public void removerArquivo(int idDoArquivo) {
        String sql = "DELETE FROM tb_lixeira WHERE id_arquivo = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDoArquivo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover arquivo da lixeira", e);
        }
    }
}
