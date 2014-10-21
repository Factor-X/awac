package eu.factorx.awac.dto.awac.shared;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;
import eu.factorx.awac.dto.validation.annotations.Size;

import java.io.Serializable;
import java.util.Date;

public class ReducingActionDTO extends DTO implements Serializable {

	private static final long serialVersionUID = 936537094692911494L;

	public ReducingActionDTO() {
		super();
	}

	private Long id;

	@NotNull
	@Size(min = 1, max = 255)
	private String title;

	@NotNull
	private String scopeTypeKey;

	private Long scopeId;

	@NotNull
	private String typeKey;

	@NotNull
	private String statusKey;

	private Date completionDate;

	@Size(max = 255)
	private String physicalMeasure;

	private Double ghgBenefit;

	private String ghgBenefitUnitKey;

	private Double financialBenefit;

	private Double investmentCost;

	@Size(max = 255)
	private String expectedPaybackTime;

	private Date dueDate;

	@Size(max = 255)
	private String webSite;

	@Size(max = 255)
	private String responsiblePerson;

	@Size(max = 1000)
	private String comment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getScopeTypeKey() {
		return scopeTypeKey;
	}

	public void setScopeTypeKey(String scopeTypeKey) {
		this.scopeTypeKey = scopeTypeKey;
	}

	public Long getScopeId() {
		return scopeId;
	}

	public void setScopeId(Long scopeId) {
		this.scopeId = scopeId;
	}

	public String getTypeKey() {
		return typeKey;
	}

	public void setTypeKey(String typeKey) {
		this.typeKey = typeKey;
	}

	public String getStatusKey() {
		return statusKey;
	}

	public void setStatusKey(String statusKey) {
		this.statusKey = statusKey;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public String getPhysicalMeasure() {
		return physicalMeasure;
	}

	public void setPhysicalMeasure(String physicalMeasure) {
		this.physicalMeasure = physicalMeasure;
	}

	public Double getGhgBenefit() {
		return ghgBenefit;
	}

	public void setGhgBenefit(Double ghgBenefit) {
		this.ghgBenefit = ghgBenefit;
	}

	public String getGhgBenefitUnitKey() {
		return ghgBenefitUnitKey;
	}

	public void setGhgBenefitUnitKey(String ghgBenefitUnitKey) {
		this.ghgBenefitUnitKey = ghgBenefitUnitKey;
	}

	public Double getFinancialBenefit() {
		return financialBenefit;
	}

	public void setFinancialBenefit(Double financialBenefit) {
		this.financialBenefit = financialBenefit;
	}

	public Double getInvestmentCost() {
		return investmentCost;
	}

	public void setInvestmentCost(Double investmentCost) {
		this.investmentCost = investmentCost;
	}

	public String getExpectedPaybackTime() {
		return expectedPaybackTime;
	}

	public void setExpectedPaybackTime(String expectedPaybackTime) {
		this.expectedPaybackTime = expectedPaybackTime;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
