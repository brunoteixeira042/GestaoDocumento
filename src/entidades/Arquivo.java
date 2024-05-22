package entidades;

import Conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Arquivo {
    private int idArquivo;
    private String nomeArquivo;
    private String caminhoArquivo;
    private int usuarioId;
    private boolean lixeira;

    public Arquivo(String nomeArquivo, String caminhoArquivo, int usuarioId, boolean lixeira) {
        this.nomeArquivo = nomeArquivo;
        this.caminhoArquivo = caminhoArquivo;
        this.usuarioId = usuarioId;
        this.lixeira = lixeira;
    }

    public void incluirArquivo() {
        String sql = "INSERT INTO tb_arquivo (nome_arquivo, caminho_arquivo, UsuarioIdUsuario, lixeira) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeArquivo);
            stmt.setString(2, caminhoArquivo);
            stmt.setInt(3, usuarioId);
            stmt.setBoolean(4, lixeira);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao incluir arquivo", e);
        }
    }

    // Getters e Setters
    public int getIdArquivo() { return idArquivo; }
    public void setIdArquivo(int idArquivo) { this.idArquivo = idArquivo; }
    public String getNomeArquivo() { return nomeArquivo; }
    public void setNomeArquivo(String nomeArquivo) { this.nomeArquivo = nomeArquivo; }
    public String getCaminhoArquivo() { return caminhoArquivo; }
    public void setCaminhoArquivo(String caminhoArquivo) { this.caminhoArquivo = caminhoArquivo; }
    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public boolean isLixeira() { return lixeira; }
    public void setLixeira(boolean lixeira) { this.lixeira = lixeira; }
}
