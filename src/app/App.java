package app;

import entidades.Administrador;
import entidades.Arquivo;

public class App {
	public static void main(String[] args) {
		Administrador administrador = new Administrador("José", "123", "jose01", "/home/bruno/Documentos/teste/");

		
		//administrador.incluirUsuario(administrador);
		//administrador.atualizarUsuario(5,"Mario","1234");
		//administrador.buscarUsuario();
		
		Arquivo arquivo = new Arquivo("teste","/home/bruno/Documentos/GED/", 5, true);
		arquivo.incluirArquivo(arquivo);
	}
}
