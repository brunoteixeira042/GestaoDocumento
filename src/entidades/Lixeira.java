package entidades;

import Conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Lixeira {

    public void adicionarArquivo(int arquivoId) {
        String sql = "INSERT INTO tb_lixeira (arquivo_id_arquivo) VALUES (?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, arquivoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar arquivo na lixeira", e);
        }
    }

    public void removerArquivo(int arquivoId) {
        String sql = "DELETE FROM tb_lixeira WHERE arquivo_id_arquivo = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, arquivoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover arquivo da lixeira", e);
        }
    }
}
