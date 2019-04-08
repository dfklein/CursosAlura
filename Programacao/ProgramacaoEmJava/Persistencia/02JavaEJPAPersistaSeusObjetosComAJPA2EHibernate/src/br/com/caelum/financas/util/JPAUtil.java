package br.com.caelum.financas.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("financas"); // a String � o nome do persistence unit declarado no persistence.xml que voc� vai utilizar
	
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

}
