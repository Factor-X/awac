package eu.factorx.awac.dto.awac.shared;

import eu.factorx.awac.dto.DTO;

public class CodesEquivalenceDTO extends DTO {

	private String key;

	private String referencedCodeList;

	private String referencedCodeKey;

	public CodesEquivalenceDTO() {
		super();
	}

	public CodesEquivalenceDTO(String key, String referencedCodeList, String referencedCodeKey) {
		super();
		this.key = key;
		this.referencedCodeList = referencedCodeList;
		this.referencedCodeKey = referencedCodeKey;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getReferencedCodeList() {
		return referencedCodeList;
	}

	public void setReferencedCodeList(String referencedCodeList) {
		this.referencedCodeList = referencedCodeList;
	}

	public String getReferencedCodeKey() {
		return referencedCodeKey;
	}

	public void setReferencedCodeKey(String referencedCodeKey) {
		this.referencedCodeKey = referencedCodeKey;
	}
}
