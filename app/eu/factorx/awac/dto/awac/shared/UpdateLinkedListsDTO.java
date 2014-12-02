package eu.factorx.awac.dto.awac.shared;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.CodeListDTO;
import eu.factorx.awac.util.Keyed;

import java.util.List;

public class UpdateLinkedListsDTO extends DTO {

	private List<LinkedListDTO> linkedLists;

	private List<CodeListDTO> codeLabels;

	public UpdateLinkedListsDTO() {
	}

	public UpdateLinkedListsDTO(List<LinkedListDTO> linkedLists, List<CodeListDTO> codeLabels) {
		this.linkedLists = linkedLists;
		this.codeLabels = codeLabels;
	}

	public List<LinkedListDTO> getLinkedLists() {
		return linkedLists;
	}

	public void setLinkedLists(List<LinkedListDTO> linkedLists) {
		this.linkedLists = linkedLists;
	}

	public List<CodeListDTO> getCodeLabels() {
		return codeLabels;
	}

	public void setCodeLabels(List<CodeListDTO> codeLabels) {
		this.codeLabels = codeLabels;
	}
}
