package eu.factorx.awac.models.business;

import javax.persistence.*;

import eu.factorx.awac.models.knowledge.Period;
import org.apache.commons.lang3.builder.EqualsBuilder;

import eu.factorx.awac.models.code.type.ScopeTypeCode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@NamedQueries({
        @NamedQuery(name = Product.FIND_BY_ORGANIZATION, query = "select p from Product p where p.organization = :organization"),
})
public class Product extends Scope  implements Comparable<Product> {

	private static final long serialVersionUID = 1L;
    public static final String FIND_BY_ORGANIZATION = "product_FIND_BY_ORGANIZATION";

	@ManyToOne(optional = false)
	private Organization organization;

	private String name;

	private String description;

    @ManyToMany (cascade = CascadeType.ALL)
    @JoinTable(name = "mm_product_period",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "period_id", referencedColumnName = "id"))
    private List<Period> listPeriodAvailable = new ArrayList<>();

	protected Product() {
		super();
	}

	public Product(Organization organization, String name) {
		super();
		this.organization = organization;
		this.name = name;
	}

    public List<Period> getListPeriodAvailable() {
        return listPeriodAvailable;
    }

    public void setListPeriodAvailable(List<Period> listPeriodAvailable) {
        this.listPeriodAvailable = listPeriodAvailable;
    }

    public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Product)) {
			return false;
		}
		Product rhs = (Product) obj;
		return new EqualsBuilder().append(this.organization, rhs.organization).append(this.name, rhs.name).isEquals();
	}

    @Override
    public String toString() {
        return "Product{" +
                "organization=" + organization +
                ", name='" + name + '\'' +
                ", listPeriodAvailable=" + listPeriodAvailable +
                '}';
    }

	@Override
	public int compareTo(Product o) {
		return this.getTechnicalSegment().getCreationDate().compareTo(o.getTechnicalSegment().getCreationDate());
	}

    @Override
	public ScopeTypeCode getScopeType() {
		return ScopeTypeCode.PRODUCT;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}