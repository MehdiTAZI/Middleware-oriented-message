package fr.esiag.mezzodijava.mezzoproto.eventserver.persistance;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

// LGE - Classe m�re de tous les DAO
// Toutes les m�thodes sont synchronized...
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

	/* (non-Javadoc)
	 * @see fr.esiag.mezzodijava.mezzoproto.eventserver.persistance.DAO#persist(E)
	 */
	@Override
	public synchronized void persist(E entity) {
		EntityManagerUtil.initEntityManagerFactory();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(entity);
		tx.commit();
		EntityManagerUtil.closeEntityManager();
	}

	/* (non-Javadoc)
	 * @see fr.esiag.mezzodijava.mezzoproto.eventserver.persistance.DAO#remove(E)
	 */
	@Override
	public synchronized void remove(E entity) {
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.remove(entityManager.merge(entity));
		tx.commit();
	}

	/* (non-Javadoc)
	 * @see fr.esiag.mezzodijava.mezzoproto.eventserver.persistance.DAO#merge(E)
	 */
	@Override
	public synchronized E merge(E entity) {
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entity = entityManager.merge(entity);
		tx.commit();
		return entity;
	}

	/* (non-Javadoc)
	 * @see fr.esiag.mezzodijava.mezzoproto.eventserver.persistance.DAO#refresh(E)
	 */
	@Override
	public synchronized void refresh(E entity) {
		entityManager.refresh(entity);
	}

	/* (non-Javadoc)
	 * @see fr.esiag.mezzodijava.mezzoproto.eventserver.persistance.DAO#findById(K)
	 */
	@Override
	public synchronized E findById(K id) {
		return entityManager.find(entityClass, id);
	}

	/* (non-Javadoc)
	 * @see fr.esiag.mezzodijava.mezzoproto.eventserver.persistance.DAO#flush(E)
	 */
	@Override
	public synchronized E flush(E entity) {
		entityManager.flush();
		return entity;
	}

	/* (non-Javadoc)
	 * @see fr.esiag.mezzodijava.mezzoproto.eventserver.persistance.DAO#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public synchronized List<E> findAll() {
		Query q = entityManager.createQuery("SELECT h FROM "
				+ entityClass.getSimpleName() + " h");
		return q.getResultList();
	}

}
