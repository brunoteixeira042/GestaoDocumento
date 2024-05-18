package entidades;

public class Usuario {

	private int IdUsuario;
	private String NomeUsuario;
	private String SenhaUsuario;
	private String LoginUsuario;
	private String CaminhoPasta;
	

	public Usuario(String nomeUsuario, String senhaUsuario, String loginUsuario, String caminhoPasta) {
		super();
		NomeUsuario = nomeUsuario;
		SenhaUsuario = senhaUsuario;
		LoginUsuario = loginUsuario;
		CaminhoPasta = caminhoPasta;
	}

	public int getIdUsuario() {
		return IdUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		IdUsuario = idUsuario;
	}

	public String getNomeUsuario() {
		return NomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		NomeUsuario = nomeUsuario;
	}

	public String getSenhaUsuario() {
		return SenhaUsuario;
	}

	public void setSenhaUsuario(String senhaUsuario) {
		SenhaUsuario = senhaUsuario;
	}

	public String getLoginUsuario() {
		return LoginUsuario;
	}

	public void setLoginUsuario(String loginUsuario) {
		LoginUsuario = loginUsuario;
	}

	public String getCaminhoPasta() {
		return CaminhoPasta;
	}

	public void setCaminhoPasta(String caminhoPasta) {
		CaminhoPasta = caminhoPasta;
	}

}
