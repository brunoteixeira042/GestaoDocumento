package entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Lixeira {
    private int IdLixeira;
    private int arquivoIdArquivo;

    public Lixeira(int codLixeira, int arquivoCodArquivo) {
        this.IdLixeira = codLixeira;
        this.arquivoIdArquivo = arquivoCodArquivo;
    }

    // Getters e Setters
    public int getCodLixeira() {
        return IdLixeira;
    }

    public void setCodLixeira(int codLixeira) {
        this.IdLixeira = codLixeira;
    }

    public int getArquivoCodArquivo() {
        return arquivoIdArquivo;
    }

    public void setArquivoCodArquivo(int arquivoCodArquivo) {
        this.arquivoIdArquivo = arquivoCodArquivo;
    }

    public void adicionarArquivo(Connection conn, Arquivo arquivo) throws SQLException {
        String sql = "INSERT INTO tb_lixeira (arquivo_id) VALUES (?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, arquivo.getIdArquivo());
            pstmt.executeUpdate();
        }
        arquivo.excluirArquivo(arquivo.getIdArquivo()); // Chamando excluirArquivo com o ID do arquivo
    }

    public void excluirArquivoDefinitivo(Connection conn, int codArquivo) throws SQLException {
        String sql = "DELETE FROM tb_lixeira WHERE arquivo_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, codArquivo);
            pstmt.executeUpdate();
        }

        sql = "DELETE FROM tb_arquivo WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, codArquivo);
            pstmt.executeUpdate();
        }
    }

    public void restaurarArquivo(Connection conn, int codArquivo) throws SQLException {
        String sql = "DELETE FROM tb_lixeira WHERE arquivo_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, codArquivo);
            pstmt.executeUpdate();
        }

        sql = "UPDATE tb_arquivo SET lixeira = FALSE WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, codArquivo);
            pstmt.executeUpdate();
        }
    }
}
