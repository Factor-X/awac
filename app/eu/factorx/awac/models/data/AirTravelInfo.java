package eu.factorx.awac.models.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "air_travel_info")
public class AirTravelInfo extends Model {

	private static final long serialVersionUID = 1L;

	public AirTravelInfo() {
	}

	@Id
	private Long id;
	@ManyToOne(optional = false)
	private AirTravelsGroupAnswer answer;
	private String value1;
	private String value2;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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