package eu.factorx.awac.dto.awac.get;

import java.util.ArrayList;
import java.util.List;

import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;
import eu.factorx.awac.dto.validation.annotations.Range;
import eu.factorx.awac.dto.validation.annotations.Size;

public class SiteDTO extends ScopeDTO {

	private Long id;

	@Size(min = 1, max = 255)
	private String name;

	private Long scope;

	@Size(max = 255)
	private String naceCode;

	@Size(max = 65000)
	private String description;

	@Size(max = 255)
    @NotNull
	private String organizationalStructure;

	@Range(min = 0, max = 100)
	private Double percentOwned;

	private List<PeriodDTO> listPeriodAvailable;

    private List<PersonDTO> listPersons;

	public SiteDTO() {
	}

	public SiteDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

    public List<PersonDTO> getListPersons() {
        return listPersons;
    }

    public void setListPersons(List<PersonDTO> listPersons) {
        this.listPersons = listPersons;
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

	public String getNaceCode() {
		return naceCode;
	}

	public void setNaceCode(String naceCode) {
		this.naceCode = naceCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrganizationalStructure() {
		return organizationalStructure;
	}

	public void setOrganizationalStructure(String organizationalStructure) {
		this.organizationalStructure = organizationalStructure;
	}

	public Double getPercentOwned() {
		return percentOwned;
	}

	public void setPercentOwned(Double percentOwned) {
		this.percentOwned = percentOwned;
	}



    public List<PeriodDTO> getListPeriodAvailable() {
        return listPeriodAvailable;
    }

    public void setListPeriodAvailable(List<PeriodDTO> listPeriodAvailable) {
        this.listPeriodAvailable = listPeriodAvailable;
    }

    @Override
    public String toString() {
        return "SiteDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", scope=" + scope +
                ", naceCode='" + naceCode + '\'' +
                ", description='" + description + '\'' +
                ", organizationalStructure='" + organizationalStructure + '\'' +
                ", percentOwned=" + percentOwned +
                ", listPeriodAvailable=" + listPeriodAvailable +
                '}';
    }

    /***********************
	 *  NOT AUTO_GENERATED !!
	 *********************/

	public void addPeriodAvailable(PeriodDTO periodDTO){
		if(this.listPeriodAvailable==null){
			this.listPeriodAvailable = new ArrayList<>();
		}
		this.listPeriodAvailable.add(periodDTO);
	}


    public void addPerson(PersonDTO personDTO) {
        if(this.listPersons==null){
            this.listPersons= new ArrayList<>();
        }
        this.listPersons.add(personDTO);
    }

}
