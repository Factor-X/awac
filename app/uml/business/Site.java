package uml.business;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Site extends Scope {

    private static final long serialVersionUID = 1L;

    public Site() {
    }

    @ManyToOne(optional = false)
    private Organization organization;

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization param) {
        this.organization = param;
    }


    public static Finder<Long, Site> find = new Finder<Long, Site>(
            Long.class, Site.class
    );

}