package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.knowledge.ReducingActionAdvice;
import eu.factorx.awac.service.ReducingActionAdviceService;

@Component
public class ReducingActionAdviceServiceImpl extends AbstractJPAPersistenceServiceImpl<ReducingActionAdvice> implements ReducingActionAdviceService {

	@Override
	public List<ReducingActionAdvice> findByCalculator(AwacCalculator awacCalculator) {
		return JPA.em().createNamedQuery(ReducingActionAdvice.FIND_BY_CALCULATOR, ReducingActionAdvice.class).setParameter("awacCalculator", awacCalculator).getResultList();
	}

}
