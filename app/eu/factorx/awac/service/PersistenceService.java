package eu.factorx.awac.service;

import java.util.List;

public interface PersistenceService<E> {

	E save(E entity);

	E update(E entity);

	void remove(E entity);

	E findById(Long id);

	List<E> findAll();

	Long getTotalResult();

}