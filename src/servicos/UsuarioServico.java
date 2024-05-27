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

    public void adicionarUsuario(Usuario usuario) {
        String sql = "INSERT INTO tb_usuario (nome_usuario, senha_usuario, login_usuario, caminho_pasta, is_admin) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getSenhaUsuario());
            stmt.setString(3, usuario.getLoginUsuario());
            stmt.setString(4, usuario.getCaminhoPasta());
            stmt.setBoolean(5, usuario.isAdmin());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar usuário", e);
        }
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM tb_usuario";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                String nome = rs.getString("nome_usuario");
                String senha = rs.getString("senha_usuario");
                String login = rs.getString("login_usuario");
                String caminhoPasta = rs.getString("caminho_pasta");
                boolean isAdmin = rs.getBoolean("is_admin");
                usuarios.add(new Usuario(id, nome, senha, login, caminhoPasta, isAdmin));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários", e);
        }
        return usuarios;
    }

    public void excluirUsuario(int idUsuario) {
        String sql = "DELETE FROM tb_usuario WHERE id_usuario = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir usuário", e);
        }
    }

    public Usuario autenticarUsuario(String login, String senha) {
        String sql = "SELECT * FROM tb_usuario WHERE login_usuario = ? AND senha_usuario = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, senha);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_usuario");
                    String nome = rs.getString("nome_usuario");
                    String caminhoPasta = rs.getString("caminho_pasta");
                    boolean isAdmin = rs.getBoolean("is_admin");
                    return new Usuario(id, nome, senha, login, caminhoPasta, isAdmin);
                } else {
                    throw new RuntimeException("Usuário ou senha inválidos");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autenticar usuário", e);
        }
    }

    public Usuario buscarUsuario(int idUsuario) {
        String sql = "SELECT * FROM tb_usuario WHERE id_usuario = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome_usuario");
                    String senha = rs.getString("senha_usuario");
                    String login = rs.getString("login_usuario");
                    String caminhoPasta = rs.getString("caminho_pasta");
                    boolean isAdmin = rs.getBoolean("is_admin");
                    return new Usuario(idUsuario, nome, senha, login, caminhoPasta, isAdmin);
                } else {
                    throw new RuntimeException("Usuário não encontrado");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário", e);
        }
    }

    public void atualizarUsuario(Usuario usuario) {
        String sql = "UPDATE tb_usuario SET nome_usuario = ?, senha_usuario = ?, login_usuario = ?, caminho_pasta = ?, is_admin = ? WHERE id_usuario = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getSenhaUsuario());
            stmt.setString(3, usuario.getLoginUsuario());
            stmt.setString(4, usuario.getCaminhoPasta());
            stmt.setBoolean(5, usuario.isAdmin());
            stmt.setInt(6, usuario.getIdUsuario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }
}
