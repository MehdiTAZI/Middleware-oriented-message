package fr.esiag.mezzo_jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

public class EntityManagerUtil {
	
	@PersistenceUnit
	private static EntityManagerFactory emf;

	public static final ThreadLocal<EntityManager> entitymanager = new ThreadLocal<EntityManager>();

	public static void initEntityManagerFactory() {
		if (emf == null) {
			// Create the EntityManagerFactory
			// Attention : il s'agit du nom de la PersistenceUnit du fichier
			// persistence.xml qui doit etre utilise
			emf = Persistence.createEntityManagerFactory("mezzo-jpa-test");
		}
	}

	public static EntityManager getEntityManager() {
		EntityManager em = entitymanager.get();

		if(emf == null)
			initEntityManagerFactory();
		
		// Create a new EntityManager
		if (em == null) {
			em = emf.createEntityManager();
			entitymanager.set(em);
		}

		return em;
	}

	/**
	 * 
	 * Ferme l'entityManager afin d'assurer l'isolation.
	 * 
	 */
	public static void closeEntityManager() {
		EntityManager em = entitymanager.get();
		entitymanager.set(null);
		if (em != null)
			em.close();
	}

}