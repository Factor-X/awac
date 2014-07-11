package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class OrganizationDTO extends DTO {
    private Long id;
    private String name;
    private List<SiteDTO> sites;

    public OrganizationDTO() {
        sites = new ArrayList<>();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<SiteDTO> getSites() {
        return sites;
    }

    public void setSites(List<SiteDTO> sites) {
        this.sites = sites;
    }
}
