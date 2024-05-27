package entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Conexao.Conexao;

public class Administrador extends Usuario {

    public Administrador(int id, String nome, String senha, boolean isAdmin) {
        super(id, nome, senha, isAdmin);
    }

    public Administrador(int id, String username) {
        super(id, username, null, true); // Assumindo que administradores sempre têm isAdmin=true
    }

    public void incluirUsuario(Usuario usuario) {
        String sql = "INSERT INTO tb_usuario (nome_usuario, senha_usuario, isAdmin) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getSenhaUsuario());
            stmt.setBoolean(3, usuario.isAdmin());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao incluir usuário", e);
        }
    }

    public void atualizarUsuario(int id, String novoUsername, String novaSenha, boolean isAdmin) {
        String sql = "UPDATE tb_usuario SET nome_usuario = ?, senha_usuario = ?, isAdmin = ? WHERE id_usuario = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoUsername);
            stmt.setString(2, novaSenha);
            stmt.setBoolean(3, isAdmin);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }

    public void excluirUsuario(int id) {
        String sql = "DELETE FROM tb_usuario WHERE id_usuario = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir usuário", e);
        }
    }

    public Usuario buscarUsuario(String username) {
        String sql = "SELECT * FROM tb_usuario WHERE nome_usuario = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nome_usuario"),
                        rs.getString("senha_usuario"),
                        rs.getBoolean("isAdmin")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário", e);
        }
        return null;
    }
}
