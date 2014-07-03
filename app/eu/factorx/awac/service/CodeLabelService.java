package eu.factorx.awac.service;

import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeLabel;
import eu.factorx.awac.models.code.CodeType;

public interface CodeLabelService extends PersistenceService<CodeLabel> {

	Map<CodeType, List<CodeLabel>> findAllCodeLabels();

	List<CodeLabel> findCodeLabelsByType(CodeType type);

	CodeLabel findCodeLabelByCode(Code code);

	void resetCache();

}
