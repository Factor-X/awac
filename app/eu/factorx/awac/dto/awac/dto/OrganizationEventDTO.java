package eu.factorx.awac.dto.awac.dto;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.AccountDTO;
import eu.factorx.awac.dto.awac.get.OrganizationDTO;
import eu.factorx.awac.dto.awac.get.PeriodDTO;
import eu.factorx.awac.dto.awac.get.SiteDTO;

import java.util.List;

public class OrganizationEventDTO extends DTO {


	private OrganizationDTO organization;

	private PeriodDTO period;

	private String name;

	private String description;


	public OrganizationEventDTO() {
	}

	public OrganizationDTO getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationDTO organization) {
		this.organization = organization;
	}

	public PeriodDTO getPeriod() {
		return period;
	}

	public void setPeriod(PeriodDTO period) {
		this.period = period;
	}

	@Override
	public String toString() {
		return "OrganizationEventDTO [organization=" + organization.toString() + "] + [Period=" + period.toString() + "]";
	}

}
