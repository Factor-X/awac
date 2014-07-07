package eu.factorx.awac.dto.myrmex.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.models.account.Person;

/**
 * Created by florian on 4/07/14.
 */
public class MyselfDTO  extends DTO{


    private PersonDTO person;
    private String organizationName;
    private long organizationId;
    private String identifier;

    public PersonDTO getPerson() {
        return person;

    }

    public void setPersonDTO(PersonDTO person) {
        this.person = person;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
