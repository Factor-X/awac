package eu.factorx.awac.models.data;

import java.util.List;

import javax.persistence.OneToMany;

//@Entity
//@Table(name = "air_travels_group_answer")
public class AirTravelsGroupAnswer extends QuestionAnswer {

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