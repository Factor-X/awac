package eu.factorx.awac.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.service.CodeLabelService;

@Repository
public class CodeLabelServiceImpl extends AbstractJPAPersistenceServiceImpl<CodeLabel> implements CodeLabelService {

	// custom cache! code labels are "strictly" read-only data for now...
	// TODO Improve this
	private static Map<CodeList, List<CodeLabel>> allLabels = null;

	@Override
	public Map<CodeList, List<CodeLabel>> findAllCodeLabels() {
		if (allLabels == null) {
			allLabels = new LinkedHashMap<>();
			for (CodeLabel codeLabel : super.findAll()) {
				CodeList type = codeLabel.getCode().getCodeList();
				if (!allLabels.containsKey(type)) {
					allLabels.put(type, new ArrayList<CodeLabel>());
				}
				allLabels.get(type).add(codeLabel);
			}
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
			if (StringUtils.equals(key, codeLabel.getCode().getKey())) {
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
