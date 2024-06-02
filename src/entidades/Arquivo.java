package entidades;

public class Arquivo {

    private int idArquivo;
    private String nomeArquivo;
    private String caminhoArquivo;
    private int idUsuario;
    private boolean naLixeira;

    public Arquivo() {}

    public Arquivo(int id, String nome, String caminho, int idUsuario, boolean naLixeira) {
        this.idArquivo = id;
        this.nomeArquivo = nome;
        this.caminhoArquivo = caminho;
        this.idUsuario = idUsuario;
        this.naLixeira = naLixeira;
    }

    public Arquivo(String nome, String caminho, int idUsuario, boolean naLixeira) {
        this.nomeArquivo = nome;
        this.caminhoArquivo = caminho;
        this.idUsuario = idUsuario;
        this.naLixeira = naLixeira;
    }

    public int getIdArquivo() {
        return idArquivo;
    }

    public void setIdArquivo(int idArquivo) {
        this.idArquivo = idArquivo;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean isNaLixeira() {
        return naLixeira;
    }

    public void setNaLixeira(boolean naLixeira) {
        this.naLixeira = naLixeira;
    }
}