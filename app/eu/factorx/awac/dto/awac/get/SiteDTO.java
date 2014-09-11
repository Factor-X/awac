package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.Range;
import eu.factorx.awac.dto.validation.annotations.Size;

import java.util.ArrayList;
import java.util.List;

public class SiteDTO extends DTO {

	private Long id;

	@Size(min = 1, max = 255)
	private String name;

	private Long scope;

	@Size(max = 255)
	private String naceCode;

	@Size(max = 65000)
	private String description;

	@Size(max = 255)
	private String organizationalStructure;

	@Size(max = 255)
	private String economicInterest;

	@Size(max = 255)
	private String operatingPolicy;

	@Size(max = 255)
	private String accountingTreatment;

	@Range(min = 0, max = 100)
	private Double percentOwned;

	private List<String> listPeriodAvailable;

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

	public List<String> getListPeriodAvailable() {
		return listPeriodAvailable;
	}

	public void setListPeriodAvailable(List<String> listPeriodAvailable) {
		this.listPeriodAvailable = listPeriodAvailable;
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
				", listPeriodAvailable=" + listPeriodAvailable +
				'}';
	}


	/***********************
	 *  NOT AUTO_GENERATED !!
	 *********************/

	public void addPeriodAvailable(String periodAvailableCode){
		if(this.listPeriodAvailable==null){
			this.listPeriodAvailable = new ArrayList<>();
		}
		this.listPeriodAvailable.add(periodAvailableCode);
	}
}
