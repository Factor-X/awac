package eu.factorx.awac.service;

import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;

public interface CodeLabelService extends PersistenceService<CodeLabel<?>> {

	Map<CodeList, List<CodeLabel<?>>> findAllCodeLabels();

	List<CodeLabel<?>> findCodeLabelsByType(CodeList type);

	CodeLabel<?> findCodeLabelByCode(Code code);

	void resetCache();

}
