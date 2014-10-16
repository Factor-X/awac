package eu.factorx.awac.dto.awac.get;

import java.io.Serializable;

import org.joda.time.DateTime;

import eu.factorx.awac.dto.DTO;

public class ReducingActionDTO extends DTO implements Serializable {

	private static final long serialVersionUID = 936537094692911494L;

	public ReducingActionDTO() {
		super();
	}

	private String title;

	private ScopeDTO scope;

	private String typeKey;

	private String statusKey;

	private DateTime completionDate;

	private String physicalMeasure;

	private Double ghgBenefit;

	private UnitDTO ghgBenefitUnit;

	private Double financialBenefit;

	private Double investmentCost;

	private String expectedPaybackTime;

	private DateTime dueDate;

	private String webSite;

	private String responsiblePerson;

	private String comment;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ScopeDTO getScope() {
		return scope;
	}

	public void setScope(ScopeDTO scope) {
		this.scope = scope;
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

	public DateTime getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(DateTime completionDate) {
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

	public UnitDTO getGhgBenefitUnit() {
		return ghgBenefitUnit;
	}

	public void setGhgBenefitUnit(UnitDTO ghgBenefitUnit) {
		this.ghgBenefitUnit = ghgBenefitUnit;
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

	public DateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(DateTime dueDate) {
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
