package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

public class PromiseDTO extends DTO {

	private String uuid;

	public PromiseDTO() {
	}

	public PromiseDTO(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
