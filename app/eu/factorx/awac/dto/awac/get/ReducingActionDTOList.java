package eu.factorx.awac.dto.awac.get;

import java.io.Serializable;
import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.shared.ReducingActionDTO;

public class ReducingActionDTOList extends DTO implements Serializable {

	private static final long serialVersionUID = 699096662915207563L;

	private List<ReducingActionDTO> reducingActions;

	private List<CodeListDTO> codeLists;

	private List<UnitDTO> gwpUnits;

	/**
	 * @param reducingActions
	 * @param userScopes
	 * @param codeLists
	 * @param gwpUnits
	 */
	public ReducingActionDTOList(List<ReducingActionDTO> reducingActions, List<CodeListDTO> codeLists, List<UnitDTO> gwpUnits) {
		super();
		this.reducingActions = reducingActions;
		this.codeLists = codeLists;
		this.gwpUnits = gwpUnits;
	}

	public List<ReducingActionDTO> getReducingActions() {
		return reducingActions;
	}

	public void setReducingActions(List<ReducingActionDTO> recucingActions) {
		this.reducingActions = recucingActions;
	}

	public List<CodeListDTO> getCodeLists() {
		return codeLists;
	}

	public void setCodeLists(List<CodeListDTO> codeListDTOs) {
		this.codeLists = codeListDTOs;
	}

	public List<UnitDTO> getGwpUnits() {
		return gwpUnits;
	}

	public void setGwpUnits(List<UnitDTO> gwpUnits) {
		this.gwpUnits = gwpUnits;
	}

}
