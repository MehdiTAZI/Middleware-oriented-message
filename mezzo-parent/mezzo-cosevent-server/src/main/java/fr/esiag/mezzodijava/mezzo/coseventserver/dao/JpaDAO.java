package fr.esiag.mezzodijava.mezzo.coseventserver.dao;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

// Classe mere de tous les DAO
// Toutes les methodes sont synchronized...
public abstract class JpaDAO<K, E> implements DAO<K, E> {

	protected Class<E> entityClass;

	protected EntityManager entityManager;

	@SuppressWarnings("unchecked")
	protected JpaDAO() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		this.entityClass = (Class<E>) genericSuperclass
				.getActualTypeArguments()[1];

		entityManager = EntityManagerUtil.getEntityManager();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esiag.mezzodijava.nuclear.systemmonitor.DB.DAO#persist(E)
	 */
	@Override
	public synchronized boolean persist(E entity) {
		EntityManagerUtil.initEntityManagerFactory();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		try {
			entityManager.persist(entity);
		} catch (EntityExistsException err) {
			return false;
		}
		tx.commit();
		EntityManagerUtil.closeEntityManager();
		return true;
	}
}
