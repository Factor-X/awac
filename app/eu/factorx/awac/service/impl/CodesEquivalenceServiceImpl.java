package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.conversion.CodesEquivalence;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.service.CodesEquivalenceService;
import org.springframework.stereotype.Component;
import play.Logger;
import play.db.jpa.JPA;

import java.util.List;

@Component
public class CodesEquivalenceServiceImpl extends AbstractJPAPersistenceServiceImpl<CodesEquivalence> implements CodesEquivalenceService {

	@Override
	public List<CodeLabel> findCodeLabelsBySublist(CodeList codeList) {
		List<CodeLabel> resultList = JPA.em().createNamedQuery(CodesEquivalence.FIND_SUBLIST_CODE_LABELS, CodeLabel.class)
				.setParameter(CodesEquivalence.CODE_LIST_PROPERTY_NAME, codeList).getResultList();
		if (resultList.isEmpty()) {
			Logger.warn("CodesEquivalenceService - Cannot find code labels for sublist '{}'", codeList);
		}
		return resultList;
	}

	@Override
	public List<CodesEquivalence> findAllSublistsData() {
		return JPA.em().createNamedQuery(CodesEquivalence.FIND_ALL_SUBLISTS_DATA, CodesEquivalence.class).getResultList();
	}

	@Override
	public Boolean isLinkedList(CodeList codeList) {
		Long nbCodesEquivalences = JPA.em()
				.createNamedQuery(CodesEquivalence.COUNT_LINKED_LIST_EQUIVALENCES, Long.class)
				.setParameter(CodesEquivalence.CODE_LIST_PROPERTY_NAME, codeList)
				.getSingleResult();
		return (nbCodesEquivalences > 0);
	}

	@Override
	public List<CodesEquivalence> findAllLinkedListsData() {
		return JPA.em().createNamedQuery(CodesEquivalence.FIND_ALL_LINKED_LISTS_DATA, CodesEquivalence.class).getResultList();
	}

	@Override
	public void removeAll() {
		int nbDeleted = JPA.em().createNamedQuery(CodesEquivalence.REMOVE_ALL).executeUpdate();
		Logger.info("Deleted {} code equivalences", nbDeleted);
	}

}
