package eu.factorx.awac.dto.awac.shared;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.FullCodeLabelDTO;

import java.util.ArrayList;
import java.util.List;

public class BaseListDTO extends DTO {

	private String codeList;

	private List<FullCodeLabelDTO> codeLabels = new ArrayList<>();

	public BaseListDTO() {
		super();
	}

	public BaseListDTO(String codeList, List<FullCodeLabelDTO> codeLabels) {
		super();
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
