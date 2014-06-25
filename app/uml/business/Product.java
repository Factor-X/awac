package uml.business;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Product extends Scope {

    private static final long serialVersionUID = 1L;

    public Product() {
    }

    @ManyToOne(optional = false)
    private Organization organization;

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization param) {
        this.organization = param;
    }

}