package eu.factorx.awac.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.conversion.CodesEquivalence;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.service.CodeConversionService;
import eu.factorx.awac.service.CodeLabelService;

@Repository
public class CodeLabelServiceImpl extends AbstractJPAPersistenceServiceImpl<CodeLabel> implements CodeLabelService {

	// custom cache! code labels are "strictly" read-only data for now...
	// TODO Improve this
	private static Map<CodeList, LinkedHashMap<String, CodeLabel>> allLabels = null;

	@Autowired
	private CodeConversionService codeConversionService;

	private Map<CodeList, LinkedHashMap<String, CodeLabel>> findAllCodeLabels() {
		if (allLabels != null) {
			return allLabels;
		}

		// add labels from codelabel table
		allLabels = new HashMap<>();
		for (CodeLabel codeLabel : findAll()) {
			CodeList codeList = codeLabel.getCodeList();
			if (!allLabels.containsKey(codeList)) {
				allLabels.put(codeList, new LinkedHashMap<String, CodeLabel>());
			}
			allLabels.get(codeList).put(codeLabel.getKey(), codeLabel);
		}

		// add labels of sublists
		List<CodesEquivalence> sublistsData = codeConversionService.findAllSublistsData();
		for (CodesEquivalence codesEquivalence : sublistsData) {
			CodeList codeList = codesEquivalence.getCodeList();
			if (!allLabels.containsKey(codeList)) {
				allLabels.put(codeList, new LinkedHashMap<String, CodeLabel>());
			}
			CodeLabel refCodeLabel = allLabels.get(codesEquivalence.getReferencedCodeList()).get(codesEquivalence.getReferencedCodeKey());
			CodeLabel codeLabel = new CodeLabel(codeList, codesEquivalence.getCodeKey(), refCodeLabel.getLabelEn(),
					refCodeLabel.getLabelFr(), refCodeLabel.getLabelNl());
			allLabels.get(codeList).put(codeLabel.getKey(), codeLabel);
		}

		Set<CodeList> allLabelsCodeLists = allLabels.keySet();
		// check if all code lists have labels
		CodeList[] values = CodeList.values();
		for (CodeList codeList : values) {
			if (!allLabelsCodeLists.contains(codeList)) {
				Logger.warn("No labels for the code list " + codeList.name());
			}
		}

		return allLabels;
	}

	@Override
	public HashMap<String,CodeLabel> findCodeLabelsByList(CodeList codeList) {
		HashMap<String, CodeLabel> res = findAllCodeLabels().get(codeList);
		if (res == null) {
			Logger.error("CodeLabelService - No code labels for code list: '{}'! Try again after resetting code labels data!", codeList);
			res = new HashMap<String,CodeLabel>();
		}
		return res;
	}

	@Override
	public CodeLabel findCodeLabelByCode(Code code) {
		return findAllCodeLabels().get(code.getCodeList()).get(code.getKey());
	}

	@Override
	public void removeCodeLabelsByList(CodeList... codeLists) {
		for (CodeList codeList : codeLists) {
			int nbDeleted = JPA.em().createNamedQuery(CodeLabel.REMOVE_BY_LIST).setParameter("codeList", codeList).executeUpdate();
			Logger.info("Deleted {} code labels of list '{}'", nbDeleted, codeList);
		}
	}

	@Override
	public void resetCache() {
		allLabels = null;
	}

	@Override
	public void removeAll() {
		int nbDeleted = JPA.em().createNamedQuery(CodeLabel.REMOVE_ALL).executeUpdate();
		Logger.info("Deleted {} code labels", nbDeleted);
	}

}
