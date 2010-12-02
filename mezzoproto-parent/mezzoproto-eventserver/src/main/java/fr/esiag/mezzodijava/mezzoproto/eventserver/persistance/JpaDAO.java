package fr.esiag.mezzodijava.mezzoproto.eventserver.persistance;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

// LGE - Classe m�re de tous les DAO
// Toutes les m�thodes sont synchronized...
public abstract class JpaDAO<K, E> {
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

	public synchronized void persist(E entity) {
		EntityManagerUtil.initEntityManagerFactory();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(entity);
		tx.commit();
		EntityManagerUtil.closeEntityManager();
	}

	public synchronized void remove(E entity) {
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.remove(entityManager.merge(entity));
		tx.commit();
	}

	public synchronized E merge(E entity) {
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entity = entityManager.merge(entity);
		tx.commit();
		return entity;
	}

	public synchronized void refresh(E entity) {
		entityManager.refresh(entity);
	}

	public synchronized E findById(K id) {
		return entityManager.find(entityClass, id);
	}

	public synchronized E flush(E entity) {
		entityManager.flush();
		return entity;
	}

	@SuppressWarnings("unchecked")
	public synchronized List<E> findAll() {
		Query q = entityManager.createQuery("SELECT h FROM "
				+ entityClass.getSimpleName() + " h");
		return q.getResultList();
	}

}
