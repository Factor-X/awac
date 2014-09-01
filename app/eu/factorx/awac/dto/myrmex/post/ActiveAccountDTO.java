package eu.factorx.awac.dto.myrmex.post;

import eu.factorx.awac.dto.DTO;


public class ActiveAccountDTO extends DTO {


	private String identifier;

	private Boolean isActive;

	public ActiveAccountDTO() {
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "ActiveAccountDTO{" +
				"identifier='" + identifier + '\'' +
				", isActive=" + isActive +
				'}';
	}
}
