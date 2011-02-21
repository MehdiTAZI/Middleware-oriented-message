package fr.esiag.mezzodijava.nuclear.systemmonitor.DB;

import java.util.List;

public interface DAO<K,E> {

	public abstract boolean persist(E entity);

	public abstract E find(K type);

	public abstract boolean exist(E entity);
	
	public abstract List<E> findLastFive(K type);

}