package entidades;

public class Usuario {
    private int id;
    private String nome;
    private String senha;
    private String login;
    private String caminhoPasta;

    // Construtor com quatro Strings
    public Usuario(String nome, String senha, String login, String caminhoPasta) {
        this.nome = nome;
        this.senha = senha;
        this.login = login;
        this.caminhoPasta = caminhoPasta;
    }

    // Construtor com id e login
    public Usuario(int id, String login) {
        this.id = id;
        this.login = login;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getCaminhoPasta() { return caminhoPasta; }
    public void setCaminhoPasta(String caminhoPasta) { this.caminhoPasta = caminhoPasta; }
}
