package eu.factorx.awac.dto.awac.shared;

import eu.factorx.awac.dto.DTO;

import java.util.ArrayList;
import java.util.List;

public class SubListDTO extends DTO {

	private String codeList;

	private String referencedCodeList;

	private List<SubListItemDTO> items = new ArrayList<>();

	public SubListDTO() {
		super();
	}

	public SubListDTO(String codeList, String referencedCodeList) {
		super();
		this.codeList = codeList;
		this.referencedCodeList = referencedCodeList;
	}

	public String getCodeList() {
		return codeList;
	}

	public void setCodeList(String codeList) {
		this.codeList = codeList;
	}

	public String getReferencedCodeList() {
		return referencedCodeList;
	}

	public void setReferencedCodeList(String referencedCodeList) {
		this.referencedCodeList = referencedCodeList;
	}

	public List<SubListItemDTO> getItems() {
		return items;
	}

	public void setItems(List<SubListItemDTO> items) {
		this.items = items;
	}

	public boolean addItem(SubListItemDTO subListItemDTO) {
		return this.items.add(subListItemDTO);
	}

}
