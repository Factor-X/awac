package uml.business;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Organization extends Scope {

    private static final long serialVersionUID = 1L;

    public Organization() {
    }

    @OneToMany(mappedBy = "organization")
    private List<Site> sites;

    public List<Site> getSites() {
        return sites;
    }

    public void setSites(List<Site> param) {
        this.sites = param;
    }

}