package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.conversion.CodesEquivalence;
import eu.factorx.awac.models.code.label.CodeLabel;

public interface CodesEquivalenceService extends PersistenceService<CodesEquivalence> {

	List<CodeLabel> findCodeLabelsBySublist(CodeList codeList);

	List<CodesEquivalence> findAllSublistsData();

	void removeAll();

}
