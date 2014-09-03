package eu.factorx.awac.dto.awac.post;


import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;

public class AssignPeriodToSiteDTO extends DTO{

	@NotNull
	private String periodKeyCode;

	@NotNull
	private Long siteId;

	@NotNull
	private Boolean assign;

	public AssignPeriodToSiteDTO() {
	}

	public String getPeriodKeyCode() {
		return periodKeyCode;
	}

	public void setPeriodKeyCode(String periodKeyCode) {
		this.periodKeyCode = periodKeyCode;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Boolean isAssign() {
		return assign;
	}

	public void setAssign(Boolean assign) {
		this.assign = assign;
	}

	@Override
	public String toString() {
		return "AssignPeriodToSiteDTO{" +
				"periodKeyCode='" + periodKeyCode + '\'' +
				", siteId=" + siteId +
				", assign=" + assign +
				'}';
	}
}
