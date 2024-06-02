package entidades;


public class Administrador extends Usuario {

    public Administrador(int id, String nome, String senha, String login, String caminhoPasta, boolean isAdmin) {
        super(id, nome, senha, login, caminhoPasta, isAdmin);
    }

    public Administrador(int id, String nome, String senha, String login, String caminhoPasta) {
        this(id, nome, senha, login, caminhoPasta, true); // Assumindo que administradores sempre têm isAdmin=true
    }
}