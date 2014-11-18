package eu.factorx.awac.dto.awac.post;

import java.util.List;

import eu.factorx.awac.dto.DTO;

public class GetReportParametersDTO extends DTO {

	private String     periodKey;
	private String     comparedPeriodKey;
	private List<Long> scopesIds;

	public GetReportParametersDTO() {
	}

	public String getPeriodKey() {
		return periodKey;
	}

	public void setPeriodKey(String periodKey) {
		this.periodKey = periodKey;
	}

	public List<Long> getScopesIds() {
		return scopesIds;
	}

	public void setScopesIds(List<Long> scopesIds) {
		this.scopesIds = scopesIds;
	}

	public String getComparedPeriodKey() {
		return comparedPeriodKey;
	}

	public void setComparedPeriodKey(String comparedPeriodKey) {
		this.comparedPeriodKey = comparedPeriodKey;
	}
}
