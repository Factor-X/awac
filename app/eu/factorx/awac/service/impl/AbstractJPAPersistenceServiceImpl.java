package eu.factorx.awac.service.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import play.db.jpa.JPA;
import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.service.PersistenceService;

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
		return JPA.em().merge(entity);
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
		return (List<E>) JPA.em().createQuery(String.format("select e from %s e", entityClass.getName())).getResultList();
	}

	@Override
	public Long getTotalResult() {
		return JPA.em().createQuery("select count(e) from " + entityClass.getName() + " e", Long.class).getSingleResult();
	}

}