package br.com.casadocodigo.loja.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.casadocodigo.loja.models.Usuario;

@Repository
public class UsuarioDAO implements UserDetailsService {

	@PersistenceContext
	private EntityManager manager;

	@Override
	// Observe que o retorno do método é uma entidade que precisa implementar a interface UserDetails.
	// Veja a classe Usuario.class para ver a implementação.
	public Usuario loadUserByUsername (String email) throws UsernameNotFoundException {
	    List<Usuario> usuarios = manager.createQuery("select u from Usuario u where u.email = :email", Usuario.class)
	            .setParameter("email", email)
	            .getResultList();

	    if(usuarios.isEmpty()){
	        throw new UsernameNotFoundException("O usuário "+ email +" não foi encontrado");
	    }

	    return usuarios.get(0);
	}

}
