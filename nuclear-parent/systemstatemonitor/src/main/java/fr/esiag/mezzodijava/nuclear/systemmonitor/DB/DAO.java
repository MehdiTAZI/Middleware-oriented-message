package fr.esiag.mezzodijava.nuclear.systemmonitor.DB;

public interface DAO<K,E> {

	public abstract boolean persist(E entity);

	public abstract E find(K id);

	public abstract boolean exist(E entity);

}