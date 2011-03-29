package fr.esiag.mezzodijava.mezzo.coseventserver.dao;

import java.util.Collection;

public interface DAO<K, E> {

    public boolean persist(E entity);

    public Collection<E> findAll();

    public boolean remove(E entity);

    public boolean update(E entity);

}