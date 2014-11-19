package eu.factorx.awac.dto.awac.shared;

import eu.factorx.awac.dto.DTO;

import java.util.List;

public class CodesEquivalenceListDTO extends DTO {

	private String codeList;

	private List<CodesEquivalenceDTO> codesEquivalences;

	public CodesEquivalenceListDTO() {
		super();
	}

	public CodesEquivalenceListDTO(String codeList, List<CodesEquivalenceDTO> codesEquivalences) {
		this.codeList = codeList;
		this.codesEquivalences = codesEquivalences;
	}

	public String getCodeList() {
		return codeList;
	}

	public void setCodeList(String codeList) {
		this.codeList = codeList;
	}

	public List<CodesEquivalenceDTO> getCodesEquivalences() {
		return codesEquivalences;
	}

	public void setCodesEquivalences(List<CodesEquivalenceDTO> codesEquivalences) {
		this.codesEquivalences = codesEquivalences;
	}
}
