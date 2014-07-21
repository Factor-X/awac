package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.service.CodeLabelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import play.Logger;

import java.util.*;

@Repository
public class CodeLabelServiceImpl extends AbstractJPAPersistenceServiceImpl<CodeLabel> implements CodeLabelService {

	// custom cache! code labels are "strictly" read-only data for now...
	// TODO Improve this
	private static Map<CodeList, List<CodeLabel>> allLabels = null;

	@Override
	public Map<CodeList, List<CodeLabel>> findAllCodeLabels() {
		if (allLabels != null) {
			return allLabels;
		}

		allLabels = new LinkedHashMap<>();
		for (CodeLabel codeLabel : super.findAll()) {
			CodeList type = codeLabel.getCodeList();
			if (!allLabels.containsKey(type)) {
				allLabels.put(type, new ArrayList<CodeLabel>());
			}
			allLabels.get(type).add(codeLabel);
		}

		Set<CodeList> allLabelsCodeLists = allLabels.keySet();
		// check if all code lists have labels
		CodeList[] values = CodeList.values();
		for (CodeList codeList : values) {
			if (!allLabelsCodeLists.contains(codeList)) {
				Logger.error("No labels for the code list " + codeList.name());
			}
		}
		// check if all labels are linked to an existing codelist
		if (!(CodeList.values().length == allLabelsCodeLists.size())) {
			Logger.error("CodeList enum contains " + CodeList.values().length + " values, since there are " + allLabelsCodeLists.size()
					+ " distinct codeList used in all code labels");
		}

		return allLabels;
	}

	@Override
	public List<CodeLabel> findCodeLabelsByType(CodeList type) {
		return findAllCodeLabels().get(type);
	}

	@Override
	public CodeLabel findCodeLabelByCode(Code code) {
		String key = code.getKey();
		for (CodeLabel codeLabel : findCodeLabelsByType(code.getCodeList())) {
			if (StringUtils.equals(key, codeLabel.getKey())) {
				return codeLabel;
			}
		}
		return null;
	}

	@Override
	public void resetCache() {
		allLabels = null;
		findAllCodeLabels();
	}

}
