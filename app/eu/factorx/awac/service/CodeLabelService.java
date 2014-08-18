package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;

public interface CodeLabelService extends PersistenceService<CodeLabel> {

	List<CodeLabel> findCodeLabelsByList(CodeList type);

	CodeLabel findCodeLabelByCode(Code code);

	int removeCodeLabelsByList(CodeList codeList);

	void resetCache();

}
