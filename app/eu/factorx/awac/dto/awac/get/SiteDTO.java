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

    public void setScope(Long scope) {
        this.scope = scope;
    }

    public Long getScope() {
        return scope;
    }
}