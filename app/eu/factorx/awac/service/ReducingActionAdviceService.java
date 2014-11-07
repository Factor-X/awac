package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.knowledge.ReducingActionAdvice;

public interface ReducingActionAdviceService extends PersistenceService<ReducingActionAdvice> {

	List<ReducingActionAdvice> findByCalculator(AwacCalculator awacCalculator);

}
