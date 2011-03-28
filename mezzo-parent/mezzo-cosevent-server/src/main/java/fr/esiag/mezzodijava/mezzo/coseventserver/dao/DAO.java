package fr.esiag.mezzodijava.mezzo.coseventserver.dao;


public interface DAO<K,E> {

	public abstract boolean persist(E entity);

}