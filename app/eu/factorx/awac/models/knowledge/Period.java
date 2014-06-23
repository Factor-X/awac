package eu.factorx.awac.models.knowledge;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Period implements Serializable {

	private static final long serialVersionUID = 1L;

	public Period() {
	}

	@Id
	private long id;
	private String label;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String param) {
		this.label = param;
	}

}