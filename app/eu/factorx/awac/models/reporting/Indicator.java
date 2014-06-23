package eu.factorx.awac.models.reporting;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Indicator")
public class Indicator implements Serializable {

	private static final long serialVersionUID = 1L;

	public Indicator() {
	}

	@Id
	private long id;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}