package eu.factorx.awac.models.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import eu.factorx.awac.models.knowledge.Period;

@Entity
@Table(name = "site")
public class Site extends Scope implements Comparable<Site>{

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	private Organization organization;

	private String name;

	@Column(name = "nace_code")
	private String naceCode;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(name = "organizational_structure")
	private String organizationalStructure;

	@Column(name = "economic_interest")
	private String economicInterest;

	@Column(name = "operating_policy")
	private String operatingPolicy;

	@Column(name = "accounting_treatment")
	private String accountingTreatment;

	@Column(name = "percent_owned",columnDefinition="double precision default '100.00'")
	private Double percentOwned;

	@ManyToMany
	@JoinTable(name = "mm_site_period",
			joinColumns = @JoinColumn(name = "site_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "period_id", referencedColumnName = "id"))
	private List<Period> listPeriodAvailable = new ArrayList<>();

	protected Site() {
		super();
	}

	public Site(Organization organization, String name) {
		super();
		this.organization = organization;
		this.name = name;
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

	public String getEconomicInterest() {
		return economicInterest;
	}

	public void setEconomicInterest(String economicInterest) {
		this.economicInterest = economicInterest;
	}

	public String getOperatingPolicy() {
		return operatingPolicy;
	}

	public void setOperatingPolicy(String operatingPolicy) {
		this.operatingPolicy = operatingPolicy;
	}

	public String getAccountingTreatment() {
		return accountingTreatment;
	}

	public void setAccountingTreatment(String accountingTreatment) {
		this.accountingTreatment = accountingTreatment;
	}

	public Double getPercentOwned() {
		return percentOwned;
	}

	public void setPercentOwned(Double percentOwned) {
		this.percentOwned = percentOwned;
	}

	public List<Period> getListPeriodAvailable() {
		return listPeriodAvailable;
	}

	public void setListPeriodAvailable(List<Period> listPeriodAvailable) {
		this.listPeriodAvailable = listPeriodAvailable;
	}

	@Override
	public String toString() {
		return "Site [id=" + id + ", name='" + name + "', organization=" + organization  + "]";
	}

	@Override
	public int compareTo(Site o) {
		return this.getTechnicalSegment().getCreationDate().compareTo(o.getTechnicalSegment().getCreationDate());
	}
}