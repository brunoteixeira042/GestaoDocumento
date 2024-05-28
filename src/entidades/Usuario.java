package entidades;

public class Usuario {

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
    }
}
