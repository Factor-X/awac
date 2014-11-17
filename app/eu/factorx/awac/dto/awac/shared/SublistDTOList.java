package eu.factorx.awac.dto.awac.shared;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.CodeListDTO;

import java.util.List;

public class SublistDTOList extends DTO {

	private List<SubListDTO> sublists;

	private List<CodeListDTO> codeLabels;

	public SublistDTOList() {
		super();
	}

	public SublistDTOList(List<SubListDTO> sublists, List<CodeListDTO> codeLabels) {
		this.sublists = sublists;
		this.codeLabels = codeLabels;
	}

	public List<SubListDTO> getSublists() {
		return sublists;
	}

	public void setSublists(List<SubListDTO> sublists) {
		this.sublists = sublists;
	}

	public List<CodeListDTO> getCodeLabels() {
		return codeLabels;
	}

	public void setCodeLabels(List<CodeListDTO> codeLabels) {
		this.codeLabels = codeLabels;
	}
}
