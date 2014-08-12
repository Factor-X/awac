package eu.factorx.awac.dto.awac.shared;

import org.joda.time.DateTime;

import eu.factorx.awac.dto.DTO;

public class ReturnDTO extends DTO {

	private DateTime dateTime;

	public ReturnDTO() {
		super();
		this.dateTime = DateTime.now();
	}

	public DateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}

}
