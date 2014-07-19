package eu.factorx.awac.service;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;

import java.util.List;
import java.util.Map;

public interface CodeLabelService extends PersistenceService<CodeLabel> {

	Map<CodeList, List<CodeLabel>> findAllCodeLabels();

	List<CodeLabel> findCodeLabelsByType(CodeList type);

	CodeLabel findCodeLabelByCode(Code code);

	void resetCache();

}
