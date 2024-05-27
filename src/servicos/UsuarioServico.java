package servicos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conexao.Conexao;
import entidades.Usuario;

public class UsuarioServico {

    public Usuario autenticarUsuario(String login, String senha) {
        String sql = "SELECT * FROM tb_usuario WHERE login_usuario = ? AND senha_usuario = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, senha);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_usuario");
                    String username = rs.getString("login_usuario");
                    String password = rs.getString("senha_usuario");
                    boolean isAdmin = rs.getBoolean("is_admin");
                    return new Usuario(id, username, password, isAdmin);
                } else {
                    throw new RuntimeException("Login ou senha inválidos");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autenticar usuário", e);
        }
    }

    public void incluirUsuario(Usuario usuario) {
        String sql = "INSERT INTO tb_usuario (login_usuario, senha_usuario, is_admin) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getSenhaUsuario());
            stmt.setBoolean(3, usuario.isAdmin());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao incluir usuário", e);
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
        String sql = "SELECT * FROM tb_usuario WHERE login_usuario = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_usuario");
                    String password = rs.getString("senha_usuario");
                    boolean isAdmin = rs.getBoolean("is_admin");
                    return new Usuario(id, username, password, isAdmin);
                } else {
                    throw new RuntimeException("Usuário não encontrado");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário", e);
        }
    }
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario, login_usuario, senha_usuario, is_admin FROM tb_usuario"; // Ajuste os nomes conforme sua tabela
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                String loginUsuario = rs.getString("login_usuario");
                String senhaUsuario = rs.getString("senha_usuario");
                boolean isAdmin = rs.getBoolean("is_admin");

                Usuario usuario = new Usuario(idUsuario, loginUsuario, senhaUsuario, isAdmin);
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários", e);
        }
        return usuarios;
    }

}
