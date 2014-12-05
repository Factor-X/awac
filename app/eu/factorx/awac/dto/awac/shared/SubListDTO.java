package eu.factorx.awac.dto.awac.shared;

import eu.factorx.awac.dto.DTO;

import java.util.ArrayList;
import java.util.List;

public class SubListDTO extends DTO {

	private String codeList;

	private String referencedCodeList;

	private List<SubListItemDTO> items = new ArrayList<>();

	private String calculators;

	public SubListDTO() {
		super();
	}

	public SubListDTO(String codeList, String referencedCodeList, String calculators) {
		super();
		this.codeList = codeList;
		this.referencedCodeList = referencedCodeList;
		this.calculators = calculators;
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

	public String getCalculators() {
		return calculators;
	}

	public void setCalculators(String calculators) {
		this.calculators = calculators;
	}

	public boolean addItem(SubListItemDTO subListItemDTO) {
		return this.items.add(subListItemDTO);
	}

}
