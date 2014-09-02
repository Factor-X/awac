package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

import javax.persistence.Column;

public class SiteDTO extends DTO {

	private Long id;
	private String name;
	private Long scope;
	private String naceCode;
	private String description;
	private String organizationalStructure;
	private String economicInterest;
	private String operatingPolicy;
	private String accountingTreatment;
	private Double percentOwned;

	public SiteDTO() {
	}

	public SiteDTO(Long id, String name) {
		this.id = id;
		this.name = name;
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

	@Override
	public String toString() {
		return "SiteDTO{" +
				"id=" + id +
				", name='" + name + '\'' +
				", scope=" + scope +
				", naceCode='" + naceCode + '\'' +
				", description='" + description + '\'' +
				", organizationalStructure='" + organizationalStructure + '\'' +
				", economicInterest='" + economicInterest + '\'' +
				", operatingPolicy='" + operatingPolicy + '\'' +
				", accountingTreatment='" + accountingTreatment + '\'' +
				", percentOwned=" + percentOwned +
				'}';
	}
}
