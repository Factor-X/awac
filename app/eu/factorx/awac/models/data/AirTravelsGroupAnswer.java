package eu.factorx.awac.models.data;

import java.io.Serializable;

import javax.persistence.Entity;

import eu.factorx.awac.models.data.AirTravelInfo;

import java.util.List;

import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "AirTravelsGroupAnswer")
public class AirTravelsGroupAnswer extends QuestionAnswer implements
		Serializable {

	private static final long serialVersionUID = 1L;
	@OneToMany(mappedBy = "answer")
	private List<AirTravelInfo> airTravels;

	public AirTravelsGroupAnswer() {
	}

	public List<AirTravelInfo> getAirTravels() {
		return airTravels;
	}

	public void setAirTravels(List<AirTravelInfo> param) {
		this.airTravels = param;
	}

}