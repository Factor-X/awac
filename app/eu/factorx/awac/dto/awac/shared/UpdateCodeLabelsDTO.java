package eu.factorx.awac.dto.awac.shared;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.FullCodeLabelDTO;

import java.util.List;

public class UpdateCodeLabelsDTO extends DTO {

	private String codeList;

	private List<FullCodeLabelDTO> codeLabels;

	public UpdateCodeLabelsDTO() {
	}

	public UpdateCodeLabelsDTO(String codeList, List<FullCodeLabelDTO> codeLabels) {
		this.codeList = codeList;
		this.codeLabels = codeLabels;
	}

	public String getCodeList() {
		return codeList;
	}

	public void setCodeList(String codeList) {
		this.codeList = codeList;
	}

	public List<FullCodeLabelDTO> getCodeLabels() {
		return codeLabels;
	}

	public void setCodeLabels(List<FullCodeLabelDTO> codeLabels) {
		this.codeLabels = codeLabels;
	}
}
