package eu.factorx.awac.service.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import play.db.jpa.JPA;
import eu.factorx.awac.models.AbstractEntity;
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
	public E save(final E entity) {
		JPA.em().persist(entity);
		return entity;
	}

	@Override
	public E update(final E entity) {
		return JPA.em().merge(entity);
	}

	@Override
	public void remove(final E entity) {
		JPA.em().remove(JPA.em().merge(entity));
	}

	@Override
	public E findById(final Long id) {
		return JPA.em().find(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findAll() {
		return JPA.em().createNamedQuery(AbstractEntity.FIND_ALL).getResultList();
	}

	@Override
	public Long getTotalResult() {
		return (Long) JPA.em().createNamedQuery(AbstractEntity.TOTAL_RESULT).getSingleResult();
	}

}