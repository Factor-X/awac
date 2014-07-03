package eu.factorx.awac.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import play.Logger;
import play.db.jpa.JPA;

public class GenericDAOImpl {

	private static EntityManager em = JPA.em();

	public static <T> T persist(T entity) {
		Logger.info("Persist entity: " + entity);
		em.persist(entity);
		return entity;
	}

	public static <T> T merge(T entity) {
		Logger.info("Merge entity: " + entity);
		return em.merge(entity);
	}

	public static void flushEntityManager() {
		Logger.info("Flush entity manager");
		em.flush();
	}

	public static <T> void refresh(T entity) {
		Logger.info("Refresh entity: " + entity);
		em.refresh(entity);
	}

	public static <T> T findById(Class<T> clasz, Integer id) {
		Logger.info("Get entity of type' " + clasz + "' with id = " + id);
		return em.find(clasz, id);
	}

	public static <T> List<T> findAll(Class<T> clasz) {
		Logger.info("Get all entities of type '" + clasz.getName() + "'");
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = cb.createQuery(clasz);
		Root<T> entity = criteriaQuery.from(clasz);
		return em.createQuery(criteriaQuery.select(entity)).getResultList();
	}

	public static <T> void remove(Class<T> clasz, Integer id) {
		Logger.info("Remove entity of type' " + clasz + "' with id = " + id);
		em.remove(em.getReference(clasz, id));
	}

}
