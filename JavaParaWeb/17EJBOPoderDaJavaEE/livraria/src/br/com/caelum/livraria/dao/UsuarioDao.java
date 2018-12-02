package br.com.caelum.livraria.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Usuario;


@Stateless
public class UsuarioDao {
	
//	@PersistenceContext(name="livraria")
//	private EntityManager manager;

	private Banco banco = new Banco();

	public Usuario buscaPeloLogin(String login) {
//		TypedQuery<Usuario> query = manager.createQuery("SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class);
//		query.setParameter("login", login);
//		return query.getSingleResult();
		return this.banco.buscaPeloNome(login);
	}
	
}
