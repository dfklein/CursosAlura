package br.com.alura.listavip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.listavip.model.Convidado;
import br.com.alura.listavip.repository.ConvidadoRepository;

@Service
public class ConvidadosService {

	@Autowired
	private ConvidadoRepository repository;
	
	public Iterable<Convidado> obterTodos() {
		return repository.findAll();
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void salvar(Convidado novoConvidado) {
		repository.save(novoConvidado);
		// Exemplo de uso de uma biblioteca externa gerada pelo próprio SpringBoot
	    new br.com.alura.enviadordeemail.EmailService().enviar(novoConvidado.getNome(), novoConvidado.getEmail());
		
	}
}
