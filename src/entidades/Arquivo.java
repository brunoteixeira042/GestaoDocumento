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
    private String nomeArquivo;
    private String caminhoArquivo;
    private int idUsuario;
    private boolean naLixeira;

    public Arquivo() {}

    public Arquivo(int id, String nome, String caminho, int idUsuario, boolean naLixeira) {
        this.idArquivo = id;
        this.nomeArquivo = nome;
        this.caminhoArquivo = caminho;
        this.idUsuario = idUsuario;
        this.naLixeira = naLixeira;
    }

    public Arquivo(String nome, String caminho, int idUsuario, boolean naLixeira) {
        this.nomeArquivo = nome;
        this.caminhoArquivo = caminho;
        this.idUsuario = idUsuario;
        this.naLixeira = naLixeira;
    }

    public int getIdArquivo() {
        return idArquivo;
    }

    public void setIdArquivo(int idArquivo) {
        this.idArquivo = idArquivo;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public int getIdUsuario() {
        return idUsuario;
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

    public void incluirArquivo() {
        String sql = "INSERT INTO tb_arquivo (nome_arquivo, caminho_arquivo, UsuarioIdUsuario, lixeira) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.getNomeArquivo());
            stmt.setString(2, this.getCaminhoArquivo());
            stmt.setInt(3, this.getIdUsuario());
            stmt.setBoolean(4, this.isNaLixeira());
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

    public static List<Arquivo> listarArquivos() {
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
}
