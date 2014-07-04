package eu.factorx.awac.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeLabel;
import eu.factorx.awac.models.code.CodeType;
import eu.factorx.awac.service.CodeLabelService;

@Repository
public class CodeLabelServiceImpl extends AbstractJPAPersistenceServiceImpl<CodeLabel> implements CodeLabelService {

	// custom cache! code labels are "strictly" read-only data for now...
	// TODO Improve this
	private static Map<CodeType, List<CodeLabel>> allLabels = null;

	@Override
	public Map<CodeType, List<CodeLabel>> findAllCodeLabels() {
		if (allLabels == null) {
			allLabels = new LinkedHashMap<CodeType, List<CodeLabel>>();
			for (CodeLabel codeLabel : super.findAll()) {
				CodeType type = codeLabel.getCodeType();
				if (!allLabels.containsKey(type)) {
					allLabels.put(type, new ArrayList<CodeLabel>());
				}
				allLabels.get(type).add(codeLabel);
			}
		}
		return allLabels;
	}

	@Override
	public List<CodeLabel> findCodeLabelsByType(CodeType type) {
		return findAllCodeLabels().get(type);
	}

	@Override
	public CodeLabel findCodeLabelByCode(Code code) {
		Integer codeValue = code.getValue();
		for (CodeLabel codeLabel : findCodeLabelsByType(code.getCodeType())) {
			if (codeValue.equals(codeLabel.getCodeValue())) {
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
