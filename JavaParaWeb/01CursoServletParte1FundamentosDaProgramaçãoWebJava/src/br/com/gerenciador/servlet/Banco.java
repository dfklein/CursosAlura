package br.com.gerenciador.servlet;

import java.util.ArrayList;
import java.util.List;

public class Banco {
	
	private static List<Empresa> lista = new ArrayList<>();
	
	static {
		Empresa e1 = new Empresa();
		Empresa e2 = new Empresa();
		
		e1.setNome("Coca-Cola");
		e2.setNome("McDonalds");
		
		lista.add(e1);
		lista.add(e2);
	}

	public void adiciona(Empresa empresa) {
		lista.add(empresa);
		
	}

	public List<Empresa> getEmpresas() {
		return Banco.lista;
	}
}
