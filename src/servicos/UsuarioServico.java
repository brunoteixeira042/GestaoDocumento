package servicos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import entidades.Administrador;
import entidades.Usuario;
import Conexao.Conexao;

public class UsuarioServico {

    public Usuario autenticar(String login, String senha) {
        try (Connection conexao = Conexao.getConexao()) {
            String sql = "SELECT * FROM tb_usuario WHERE login_usuario = ? AND senha_usuario = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if (rs.getBoolean("is_admin")) {
                    return new Administrador(rs.getInt("id_usuario"), rs.getString("login_usuario"));
                } else {
                    return new Usuario(rs.getInt("id_usuario"), rs.getString("login_usuario"));
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autenticar o usuário", e);
        }
    }

    public List<Usuario> listarUsuarios() {
        try (Connection conexao = Conexao.getConexao()) {
            List<Usuario> usuarios = new ArrayList<>();
            String sql = "SELECT * FROM tb_usuario";
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                if (rs.getBoolean("is_admin")) {
                    usuarios.add(new Administrador(rs.getInt("id_usuario"), rs.getString("login_usuario")));
                } else {
                    usuarios.add(new Usuario(rs.getInt("id_usuario"), rs.getString("login_usuario")));
                }
            }

            return usuarios;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários", e);
        }
    }

    public void removerUsuario(int userId) {
        try (Connection conexao = Conexao.getConexao()) {
            String sql = "DELETE FROM tb_usuario WHERE id_usuario = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover o usuário", e);
        }
    }
}
