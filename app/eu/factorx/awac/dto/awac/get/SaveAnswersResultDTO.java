package eu.factorx.awac.dto.awac.get;

import java.util.Date;

import eu.factorx.awac.dto.DTO;

public class SaveAnswersResultDTO extends DTO {
	private Date lastUpdate;

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
