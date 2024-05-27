package entidades;

public class Usuario {
	private int idUsuario;
	private String NomeUsuario;
	private String SenhaUsuario;
	private boolean isAdmin;

	public Usuario(int id, String nome, String senha, boolean isAdmin) {
		this.idUsuario = id;
		this.NomeUsuario = nome;
		this.SenhaUsuario = senha;
		this.isAdmin = isAdmin;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
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

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}
