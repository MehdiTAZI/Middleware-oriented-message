package fr.esiag.mezzodijava.nuclear.systemmonitor.DB;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.esiag.mezzodijava.nuclear.systemmonitor.DB.DAO#findById(K)
	 */
	@Override
	public synchronized E find(K type) {
		return entityManager.find(entityClass, type);
	}

	@Override
	public synchronized boolean exist(E entity) {
		return entityManager.contains(entity);
	}

	@Override
	public synchronized List<E> findLastFive(K type) {
		Query q = entityManager.createQuery("SELECT h FROM " + entityClass.getSimpleName() + " h WHERE h.type = :types ORDER BY id DESC");
		q.setParameter("types", type);
		q.setMaxResults(5);
		return q.getResultList();
	}
}
