package eu.factorx.awac.models.data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "AirTravelDetail")
public class AirTravelInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	public AirTravelInfo() {
	}

	@Id
	private long id;
	@ManyToOne(optional = false)
	private AirTravelsGroupAnswer answer;
	private String value1;
	private String value2;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AirTravelsGroupAnswer getAnswer() {
		return answer;
	}

	public void setAnswer(AirTravelsGroupAnswer param) {
		this.answer = param;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String param) {
		this.value1 = param;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String param) {
		this.value2 = param;
	}

}