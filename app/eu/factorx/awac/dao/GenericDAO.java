package eu.factorx.awac.dao;

import java.util.List;

public interface GenericDAO {

	<T> T persist(T entity);

	<T> T merge(T entity);

	<T> void refresh(T entity);

	<T> T findById(Class<T> clasz, Integer id);

	<T> List<T> findAll(Class<T> clasz);

	<T> void remove(Class<T> clasz, Integer id);

	void flushEntityManager();

}
