package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.shared.ReducingActionAdviceDTO;

import java.io.Serializable;
import java.util.List;

public class ReducingActionAdviceDTOList extends DTO implements Serializable {

	private static final long serialVersionUID = 699096662915207563L;

	private List<ReducingActionAdviceDTO> reducingActionAdvices;

	private List<CodeListDTO> codeLists;

	public ReducingActionAdviceDTOList() {
		super();
	}

	/**
	 * @param reducingActionAdvices
	 * @param codeLists
	 */
	public ReducingActionAdviceDTOList(List<ReducingActionAdviceDTO> reducingActionAdvices, List<CodeListDTO> codeLists) {
		super();
		this.reducingActionAdvices = reducingActionAdvices;
		this.codeLists = codeLists;
	}

	public List<ReducingActionAdviceDTO> getReducingActionAdvices() {
		return reducingActionAdvices;
	}

	public void setReducingActionAdvices(List<ReducingActionAdviceDTO> reducingActions) {
		this.reducingActionAdvices = reducingActions;
	}

	public List<CodeListDTO> getCodeLists() {
		return codeLists;
	}

	public void setCodeLists(List<CodeListDTO> codeLists) {
		this.codeLists = codeLists;
	}

}
