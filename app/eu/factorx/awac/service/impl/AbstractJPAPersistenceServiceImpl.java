package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.service.PersistenceService;
import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Repository;
import play.db.jpa.JPA;

import javax.annotation.PostConstruct;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.List;

@Repository
public abstract class AbstractJPAPersistenceServiceImpl<E extends AbstractEntity> implements PersistenceService<E> {

	protected Class<E> entityClass;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[0];
	}

	@Override
	public E saveOrUpdate(final E entity) {
		if ((entity.getId() != null) && (entity instanceof AuditedAbstractEntity)) {
			((AuditedAbstractEntity) entity).preUpdate();
		}
		JPA.em().persist(entity);
		return entity;
	}

	@Override
	public E update(final E entity) {
		if (entity instanceof AuditedAbstractEntity) {
			// Forces update of technical segment, and then of the entity...
			// Useful in cases where only children of entity are actually updated: in a business point of view, when we called this method, we may want that the technical segment
			// of given entity was updated.
			((AuditedAbstractEntity) entity).preUpdate();
		}
		E merge = JPA.em().merge(entity);
		return merge;
	}

	@Override
	public void remove(E entity) {
		JPA.em().remove(JPA.em().merge(entity));
	}

	@Override
	public void remove(Iterable<E> entities) {
		Iterator<E> iterator = entities.iterator();
		while (iterator.hasNext()) {
			remove(iterator.next());
		}
	}

	@Override
	public void removeAll() {
		List<E> allEntities = findAll();
		for (E entity : allEntities) {
			remove(entity);
		}
	}

	@Override
	public E findById(final Long id) {
		return JPA.em().find(entityClass, id);
	}

	@Override
	public List<E> findAll() {
		CriteriaBuilder cb = JPA.em().getCriteriaBuilder();
		CriteriaQuery<E> cq = cb.createQuery(entityClass);
		Root<E> rootEntry = cq.from(entityClass);
		CriteriaQuery<E> all = cq.select(rootEntry);
		TypedQuery<E> allQuery = JPA.em().createQuery(all).setHint(QueryHints.CACHEABLE, Boolean.TRUE);
		return allQuery.getResultList();
	}

	@Override
	public Long getTotalResult() {
		return JPA.em().createQuery("select count(e) from " + entityClass.getName() + " e", Long.class).getSingleResult();
	}

}
