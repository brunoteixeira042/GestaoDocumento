package entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Conexao.Conexao;

public class Arquivo {
    private int IdArquivo;
    private String NomeArquivo;
    private String CaminhoArquivo;
    private int UsuarioIdUsuario;
    private boolean Lixeira;

    public Arquivo(String nomeArquivo, String caminhoArquivo, int usuarioIdUsuario, boolean lixeira) {
        this.NomeArquivo = nomeArquivo;
        this.CaminhoArquivo = caminhoArquivo;
        this.UsuarioIdUsuario = usuarioIdUsuario;
        this.Lixeira = lixeira;
    }

    public int getIdArquivo() {
        return IdArquivo;
    }

    public void setIdArquivo(int idArquivo) {
        this.IdArquivo = idArquivo;
    }

    public String getNomeArquivo() {
        return NomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.NomeArquivo = nomeArquivo;
    }

    public String getCaminhoArquivo() {
        return CaminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.CaminhoArquivo = caminhoArquivo;
    }

    public int getUsuarioIdUsuario() {
        return UsuarioIdUsuario;
    }

    public void setUsuarioIdUsuario(int usuarioIdUsuario) {
        this.UsuarioIdUsuario = usuarioIdUsuario;
    }

    public boolean isLixeira() {
        return Lixeira;
    }

    public void setLixeira(boolean lixeira) {
        this.Lixeira = lixeira;
    }

    public void incluirArquivo() {
        String sql = "INSERT INTO tb_arquivo (nome_arquivo, caminho_arquivo, UsuarioIdUsuario, lixeira) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.getNomeArquivo());
            stmt.setString(2, this.getCaminhoArquivo());
            stmt.setInt(3, this.getUsuarioIdUsuario());
            stmt.setBoolean(4, this.isLixeira());
            stmt.executeUpdate();
            System.out.println("Arquivo adicionado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar Arquivo: " + e.getMessage());
        }
    }

    public void excluirArquivo(int idArquivo) {
        String sql = "DELETE FROM tb_arquivo WHERE id_arquivo = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idArquivo);
            stmt.executeUpdate();
            System.out.println("Arquivo removido com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao remover Arquivo: " + e.getMessage());
        }
    }
}
