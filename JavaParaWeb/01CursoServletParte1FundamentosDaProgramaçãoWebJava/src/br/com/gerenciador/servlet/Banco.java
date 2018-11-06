package br.com.gerenciador.servlet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Banco {
	
	private static List<Empresa> lista = new ArrayList<>();
	private static Integer chaveSequencial = 1;
	
	static {
		Empresa e1 = new Empresa();
		Empresa e2 = new Empresa();
		
		e1.setNome("Coca-Cola");
		e1.setId(chaveSequencial++);
		e2.setNome("McDonalds");
		e2.setId(chaveSequencial++);
		
		lista.add(e1);
		lista.add(e2);
	}

	public void adiciona(Empresa empresa) {
		empresa.setId(chaveSequencial++);
		lista.add(empresa);
		
	}

	public List<Empresa> getEmpresas() {
		return Banco.lista;
	}

	public void removeEmpresa(Integer paramId) {
		lista.removeIf(e -> e.getId().equals(paramId));
		
		// abaixo uma implementação sem lambda e sem problema de concurrent modification:
		//		Iterator<Empresa> it = lista.iterator();
		//		while(it.hasNext()) {
		//			Empresa empresa = it.next();
		//			if(empresa.getId() == paramId) {
		//				it.remove();
		//			}
		//		}
		
	}

	public Empresa getEmpresaById(Integer idEmpresa) {
		return lista.stream().filter(e -> e.getId() == idEmpresa).collect(Collectors.toList()).get(0);
	}
}
