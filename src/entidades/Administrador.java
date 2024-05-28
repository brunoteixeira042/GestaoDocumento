package entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Conexao.Conexao;

public class Administrador extends Usuario {

    public Administrador(String nomeUsuario, String senhaUsuario, String loginUsuario, String caminhoPasta) {
        super(nomeUsuario, senhaUsuario, loginUsuario, caminhoPasta);
    }

    public void incluirUsuario(Usuario usuario) {
        String sql = "INSERT INTO tb_usuario (nome_usuario, senha_usuario, login_usuario, caminho_pasta) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getSenhaUsuario());
            stmt.setString(3, usuario.getLoginUsuario());
            stmt.setString(4, usuario.getCaminhoPasta());
            stmt.executeUpdate();
            System.out.println("Usuario adicionado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar Usuario: " + e.getMessage());
        }
    }

    public void excluirUsuario(int id) {
        String sql = "DELETE FROM tb_usuario WHERE id_usuario = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int afetados = stmt.executeUpdate();
            if (afetados > 0) {
                System.out.println("Usuario removido com sucesso.");
            } else {
                System.out.println("Nenhum Usuario encontrado com este ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao remover Usuario: " + e.getMessage());
        }
    }

    public void atualizarUsuario(int id, String nome, String senha) {
        String sql = "UPDATE tb_usuario SET nome_usuario = ?, senha_usuario = ? WHERE id_usuario = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, senha);
            stmt.setInt(3, id);
            int afetados = stmt.executeUpdate();
            if (afetados > 0) {
                System.out.println("Usuario atualizado com sucesso.");
            } else {
                System.out.println("Nenhum Usuario encontrado com este ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o Usuario: " + e.getMessage());
        }
    }

    public boolean buscarUsuario(String nomeUsuario) {
        String sql = "SELECT * FROM tb_usuario WHERE nome_usuario = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Usuário encontrado: Nome: " + rs.getString("nome_usuario"));
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return false;
    }
}
