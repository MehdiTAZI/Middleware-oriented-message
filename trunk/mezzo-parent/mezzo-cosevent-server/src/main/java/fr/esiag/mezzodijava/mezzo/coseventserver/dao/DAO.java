package fr.esiag.mezzodijava.mezzo.coseventserver.dao;

import java.util.Collection;

public interface DAO<K, E> {

	/**
	 * 
	 * @param entity persist the entity
	 * @return true if success
	 */
    public boolean persist(E entity);

    /**
     * 
     * @return a collection containing all the elements
     */
    public Collection<E> findAll();

    /**
     * 
     * @param entity the entity to remove
     * @return true if success
     */
    public boolean remove(E entity);

    /**
     * 
     * @param entity the entity to update
     * @return true if success
     */
    public boolean update(E entity);

}