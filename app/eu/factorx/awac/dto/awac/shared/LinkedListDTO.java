package eu.factorx.awac.dto.awac.shared;

import eu.factorx.awac.dto.DTO;

import java.util.List;

public class LinkedListDTO extends DTO {

	private String codeList;

	private List<LinkedListItemDTO> items;

	public LinkedListDTO() {
	}

	public LinkedListDTO(String codeList, List<LinkedListItemDTO> items) {
		this.codeList = codeList;
		this.items = items;
	}

	public String getCodeList() {
		return codeList;
	}

	public void setCodeList(String codeList) {
		this.codeList = codeList;
	}

	public List<LinkedListItemDTO> getItems() {
		return items;
	}

	public void setItems(List<LinkedListItemDTO> items) {
		this.items = items;
	}
}
