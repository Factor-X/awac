package eu.factorx.awac.dto.awac.shared;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.UpdateCodeLabelDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateCodeLabelsDTO extends DTO {

	private Map<String, List<UpdateCodeLabelDTO>> codeLabelsByList;

	public UpdateCodeLabelsDTO() {
		codeLabelsByList = new HashMap<>();
	}

	public Map<String, List<UpdateCodeLabelDTO>> getCodeLabelsByList() {
		return codeLabelsByList;
	}

	public void setCodeLabelsByList(Map<String, List<UpdateCodeLabelDTO>> codeLabelsByList) {
		this.codeLabelsByList = codeLabelsByList;
	}

	public void putCodeLabels(String codeList, List<UpdateCodeLabelDTO> codeLabels) {
		codeLabelsByList.put(codeList, codeLabels);
	}

}
