package eu.factorx.awac.dto.awac.get;

import java.util.ArrayList;
import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;

public class OrganizationDTO extends DTO {
	private Long id;
	private String name;
	private List<SiteDTO> sites;
	private List<PersonDTO> users;

	public OrganizationDTO() {
		sites = new ArrayList<>();
		users = new ArrayList<>();
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

	public List<SiteDTO> getSites() {
		return sites;
	}

	public void setSites(List<SiteDTO> sites) {
		this.sites = sites;
	}

	public List<PersonDTO> getUsers() {
		return users;
	}

	public void setUsers(List<PersonDTO> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "OrganizationDTO{" +
				"id=" + id +
				", name='" + name + '\'' +
				", sites=" + sites +
				", users=" + users +
				'}';
	}
}
