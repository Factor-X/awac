package eu.factorx.awac.dto.awac.shared;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.UpdateCodeLabelDTO;

import java.util.ArrayList;
import java.util.List;

public class BaseListDTO extends DTO {

	private String codeList;

	private List<UpdateCodeLabelDTO> codeLabels = new ArrayList<>();

	private String calculators;

	public BaseListDTO() {
		super();
	}

	public BaseListDTO(String codeList, List<UpdateCodeLabelDTO> codeLabels, String calculators) {
		super();
		this.codeList = codeList;
		this.codeLabels = codeLabels;
		this.calculators = calculators;
	}

	public String getCodeList() {
		return codeList;
	}

	public void setCodeList(String codeList) {
		this.codeList = codeList;
	}

	public List<UpdateCodeLabelDTO> getCodeLabels() {
		return codeLabels;
	}

	public void setCodeLabels(List<UpdateCodeLabelDTO> codeLabels) {
		this.codeLabels = codeLabels;
	}

	public String getCalculators() {
		return calculators;
	}

	public void setCalculators(String calculators) {
		this.calculators = calculators;
	}
}
