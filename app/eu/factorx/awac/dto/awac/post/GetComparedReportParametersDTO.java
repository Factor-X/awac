package eu.factorx.awac.dto.awac.post;

import java.util.List;

import eu.factorx.awac.dto.DTO;

public class GetComparedReportParametersDTO extends DTO {

	private String     period1Key;
	private String     period2Key;
	private List<Long> scopesIds;

	public GetComparedReportParametersDTO() {
	}

	public List<Long> getScopesIds() {
		return scopesIds;
	}

	public void setScopesIds(List<Long> scopesIds) {
		this.scopesIds = scopesIds;
	}

	public String getPeriod1Key() {
		return period1Key;
	}

	public void setPeriod1Key(String period1Key) {
		this.period1Key = period1Key;
	}

	public String getPeriod2Key() {
		return period2Key;
	}

	public void setPeriod2Key(String period2Key) {
		this.period2Key = period2Key;
	}
}
