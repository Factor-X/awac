package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.CodesEquivalenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import play.Logger;
import play.db.jpa.JPA;

import java.util.*;

@Repository
public class CodeLabelServiceImpl extends AbstractJPAPersistenceServiceImpl<CodeLabel> implements CodeLabelService {

	@Autowired
	private CodesEquivalenceService codesEquivalenceService;

	@Override
	public HashMap<String, CodeLabel> findCodeLabelsByList(CodeList codeList) {
		List<CodeLabel> resultList = JPA.em().createNamedQuery(CodeLabel.FIND_BY_LIST, CodeLabel.class)
				.setParameter(CodeLabel.CODE_LIST_PROPERTY_NAME, codeList).getResultList();

		// if resultList is empty, check if codeList is a "sublist"
		if (resultList.isEmpty()) {
			resultList = codesEquivalenceService.findCodeLabelsBySublist(codeList);
			if (resultList.isEmpty()) {
				Logger.error("CodeLabelService - No code labels for code list: '{}'! Try again after resetting code labels data!", codeList);
			}
		}

		HashMap<String, CodeLabel> res = new LinkedHashMap<>();
		for (CodeLabel codeLabel : resultList) {
			res.put(codeLabel.getKey(), codeLabel);
		}

		return res;
	}

	@Override
	public CodeLabel findCodeLabelByCode(Code code) {
		return findCodeLabelsByList(code.getCodeList()).get(code.getKey());
	}

	@Override
	public void removeCodeLabelsByList(CodeList... codeLists) {
		for (CodeList codeList : codeLists) {
			int nbDeleted = JPA.em().createNamedQuery(CodeLabel.REMOVE_BY_LIST).setParameter("codeList", codeList).executeUpdate();
			Logger.info("Deleted {} code labels of list '{}'", nbDeleted, codeList);
		}
	}

	@Override
	public Map<CodeList, List<CodeLabel>> findAllBaseLists() {
		Map<CodeList, List<CodeLabel>> res = new LinkedHashMap<>();
		for (CodeLabel codeLabel : findAll()) {
			CodeList codeList = codeLabel.getCodeList();
			if (!res.containsKey(codeList)) {
				res.put(codeList, new ArrayList<CodeLabel>());
			}
			res.get(codeList).add(codeLabel);
		}
		return res;
	}

	@Override
	public void removeAll() {
		int nbDeleted = JPA.em().createNamedQuery(CodeLabel.REMOVE_ALL).executeUpdate();
		Logger.info("Deleted {} code labels", nbDeleted);
	}

}
