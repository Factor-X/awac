package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

public class SiteDTO extends DTO {

	private Long id;
	private String name;
	private Long scope;

	public SiteDTO() {
	}

	public SiteDTO(Long id, String name) {
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

	public Long getScope() {
		return scope;
	}

	public void setScope(Long scope) {
		this.scope = scope;
	}
}
