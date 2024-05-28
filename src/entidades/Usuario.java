package entidades;

public class Usuario {
    private int idUsuario;
    private String nomeUsuario;
    private String senhaUsuario;
    private String loginUsuario;
    private String caminhoPasta;
    private boolean isAdmin;

<<<<<<< HEAD
    private int IdUsuario;
    private String NomeUsuario;
    private String SenhaUsuario;
    private String LoginUsuario;
    private String CaminhoPasta;

    public Usuario(String nomeUsuario, String senhaUsuario, String loginUsuario, String caminhoPasta) {
        this.NomeUsuario = nomeUsuario;
        this.SenhaUsuario = senhaUsuario;
        this.LoginUsuario = loginUsuario;
        this.CaminhoPasta = caminhoPasta;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.IdUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return NomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.NomeUsuario = nomeUsuario;
    }

    public String getSenhaUsuario() {
        return SenhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.SenhaUsuario = senhaUsuario;
    }

    public String getLoginUsuario() {
        return LoginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.LoginUsuario = loginUsuario;
    }

    public String getCaminhoPasta() {
        return CaminhoPasta;
    }

    public void setCaminhoPasta(String caminhoPasta) {
        this.CaminhoPasta = caminhoPasta;
=======
    public Usuario(int id, String nome, String senha, String login, String caminhoPasta, boolean isAdmin) {
        this.idUsuario = id;
        this.nomeUsuario = nome;
        this.senhaUsuario = senha;
        this.loginUsuario = login;
        this.caminhoPasta = caminhoPasta;
        this.isAdmin = isAdmin;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public String getCaminhoPasta() {
        return caminhoPasta;
    }

    public void setCaminhoPasta(String caminhoPasta) {
        this.caminhoPasta = caminhoPasta;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
>>>>>>> cf67d02b28e10e785a28adfa09cc80ffa8fc1664
    }
}
