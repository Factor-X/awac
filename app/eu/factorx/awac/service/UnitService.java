package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.knowledge.Unit;

public interface UnitService extends PersistenceService<Unit> {

	Unit findBySymbol(String symbol);

	List<String> findAllSymbols();

}
