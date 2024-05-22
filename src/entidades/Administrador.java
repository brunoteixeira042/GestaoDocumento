package entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Conexao.Conexao;

public class Administrador extends Usuario {

    public Administrador(String nome, String senha, String login, String caminhoPasta) {
        super(nome, senha, login, caminhoPasta);
    }

    public Administrador(int id, String login) {
        super(id, login);
    }

    public void incluirUsuario(Usuario usuario) {
        String sql = "INSERT INTO tb_usuario (nome_usuario, senha_usuario, login_usuario, caminho_pasta) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getLogin());
            stmt.setString(4, usuario.getCaminhoPasta());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao incluir usuário", e);
        }
    }

    public void atualizarUsuario(int id, String novoNome, String novaSenha) {
        String sql = "UPDATE tb_usuario SET nome_usuario = ?, senha_usuario = ? WHERE id_usuario = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setString(2, novaSenha);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }

    public boolean buscarUsuario(String login) {
        String sql = "SELECT * FROM tb_usuario WHERE login_usuario = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário", e);
        }
    }

    // Métodos específicos de administrador, como mover, restaurar, e excluir arquivos, podem ser implementados aqui
}
