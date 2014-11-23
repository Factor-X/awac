package eu.factorx.awac.dto.awac.shared;

import eu.factorx.awac.dto.DTO;

import java.util.List;

public class UpdateBaseListsDTO extends DTO {

	List<BaseListDTO> baseLists;

	public UpdateBaseListsDTO() {
	}

	public UpdateBaseListsDTO(List<BaseListDTO> baseLists) {
		this.baseLists = baseLists;
	}

	public List<BaseListDTO> getBaseLists() {
		return baseLists;
	}

	public void setBaseLists(List<BaseListDTO> baseLists) {
		this.baseLists = baseLists;
	}
}
