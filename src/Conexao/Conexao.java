package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao{
    public static Connection getConexao() {
        try {
            String url = "jdbc:mysql://localhost/Ged";
            String usuario = "root";
            String senha = "Bruno123@";
            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }
}
