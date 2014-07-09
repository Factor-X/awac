package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

public class UnitDTO extends DTO {

	private Long id;

	private String name;

	protected UnitDTO() {
		super();
	}

	public UnitDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
