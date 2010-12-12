package fr.esiag.mezzodijava.mezzoproto.eventserver.persistance;

import java.util.List;

public interface DAO<K, E> {

	public abstract void persist(E entity);

	public abstract void remove(E entity);

	public abstract E merge(E entity);

	public abstract void refresh(E entity);

	public abstract E findById(K id);

	public abstract E flush(E entity);

	@SuppressWarnings("unchecked")
	public abstract List<E> findAll();

}