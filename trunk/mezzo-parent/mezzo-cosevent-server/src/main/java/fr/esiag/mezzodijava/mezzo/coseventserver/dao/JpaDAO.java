package fr.esiag.mezzodijava.mezzo.coseventserver.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.esiag.mezzodijava.mezzo.coseventserver.ctr.ChannelCtr;

// Classe mere de tous les DAO
// Toutes les methodes sont synchronized...
public abstract class JpaDAO<K, E> implements DAO<K, E> {

    private static Logger log = LoggerFactory.getLogger(ChannelCtr.class);
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
	log.info("persist of {}", entity);
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
     * @see fr.esiag.mezzodijava.nuclear.systemmonitor.DB.DAO#persist(E)
     */
    @Override
    public synchronized boolean remove(E entity) {
	EntityManagerUtil.initEntityManagerFactory();
	EntityTransaction tx = entityManager.getTransaction();
	tx.begin();
	log.info("persist of {}", entity);
	try {
	    entityManager.remove(entity);
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
     * @see fr.esiag.mezzodijava.nuclear.systemmonitor.DB.DAO#persist(E)
     */
    @Override
    public synchronized boolean update(E entity) {
	EntityManagerUtil.initEntityManagerFactory();
	EntityTransaction tx = entityManager.getTransaction();
	tx.begin();
	log.info("persist of {}", entity);
	try {
	    entityManager.merge(entity);
	} catch (EntityExistsException err) {
	    return false;
	}
	tx.commit();
	EntityManagerUtil.closeEntityManager();
	return true;
    }

    public Collection<E> findAll() {
	Query query = entityManager.createQuery("SELECT c FROM "
		+ entityClass.getSimpleName() + " c");
	return (Collection<E>) query.getResultList();
    }
}
