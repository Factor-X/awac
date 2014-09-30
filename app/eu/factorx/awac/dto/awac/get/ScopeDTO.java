package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

public abstract class ScopeDTO extends DTO {

    public abstract void setId(Long id);

    public abstract Long getId();

    public abstract void setName(String name);

    public abstract String getName();


}
