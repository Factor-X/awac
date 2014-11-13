package eu.factorx.awac.dto.awac.shared;

import eu.factorx.awac.dto.DTO;

import java.util.ArrayList;
import java.util.List;

public class SubListDTO extends DTO {

	private String codeList;

	private String referencedCodeList;

	private List<String> keys = new ArrayList<>();

	public SubListDTO() {
	}

	public SubListDTO(String codeList, String referencedCodeList, List<String> keys) {
		this.codeList = codeList;
		this.referencedCodeList = referencedCodeList;
		this.keys = keys;
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

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}
}
