package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;

import java.util.List;

public class GetReportParametersDTO extends DTO {

	private String     periodKey;
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
}
