package eu.factorx.awac.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;

public interface CodeLabelService extends PersistenceService<CodeLabel> {

	HashMap<String, CodeLabel> findCodeLabelsByList(CodeList codeList);

	CodeLabel findCodeLabelByCode(Code code);

	void removeCodeLabelsByList(CodeList... codeLists);

	Map<CodeList, List<CodeLabel>> findAllBaseLists();

	void removeAll();

}
