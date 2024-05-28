package entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Conexao.Conexao;

public class Administrador extends Usuario {

    public Administrador(int id, String nome, String senha, String login, String caminhoPasta, boolean isAdmin) {
        super(id, nome, senha, login, caminhoPasta, isAdmin);
    }

    public Administrador(int id, String nome, String senha, String login, String caminhoPasta) {
        this(id, nome, senha, login, caminhoPasta, true); // Assumindo que administradores sempre têm isAdmin=true
    }

    public void incluirUsuario(Usuario usuario) {
        String sql = "INSERT INTO tb_usuario (nome_usuario, senha_usuario, login_usuario, caminho_pasta, is_admin) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getSenhaUsuario());
            stmt.setString(3, usuario.getLoginUsuario());
            stmt.setString(4, usuario.getCaminhoPasta());
            stmt.setBoolean(5, usuario.isAdmin());
            stmt.executeUpdate();
            System.out.println("Usuário adicionado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao incluir usuário: " + e.getMessage());
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
            int afetados = stmt.executeUpdate();
            if (afetados > 0) {
                System.out.println("Usuário atualizado com sucesso.");
            } else {
                System.out.println("Nenhum usuário encontrado com este ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    public void excluirUsuario(int id) {
        String sql = "DELETE FROM tb_usuario WHERE id_usuario = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int afetados = stmt.executeUpdate();
            if (afetados > 0) {
                System.out.println("Usuário removido com sucesso.");
            } else {
                System.out.println("Nenhum usuário encontrado com este ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir usuário: " + e.getMessage());
        }
    }

    public Usuario buscarUsuario(String nomeUsuario) {
        String sql = "SELECT * FROM tb_usuario WHERE nome_usuario = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nome_usuario"),
                        rs.getString("senha_usuario"),
                        rs.getString("login_usuario"),
                        rs.getString("caminho_pasta"),
                        rs.getBoolean("is_admin")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return null;
    }
}
