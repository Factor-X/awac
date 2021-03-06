package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

public class OrganizationEventDTO extends DTO {

	private Long id;

	private PeriodDTO period;

	private String name;

	private String description;


	public OrganizationEventDTO() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PeriodDTO getPeriod() {
		return period;
	}

	public void setPeriod(PeriodDTO period) {
		this.period = period;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "OrganizationEventDTO{" +
				"id=" + id +
				", period=" + period +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				'}';
	}

}
