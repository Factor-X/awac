package eu.factorx.awac.dto.awac.post;

import java.io.Serializable;

import org.joda.time.DateTime;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;
import eu.factorx.awac.dto.validation.annotations.Size;

public class CreateReducingActionDTO extends DTO implements Serializable {

	private static final long serialVersionUID = 936537094692911494L;

	public CreateReducingActionDTO() {
		super();
	}

	@NotNull
	@Size(min = 1, max = 255)
	private String title;

	@NotNull
	private String scopeTypeKey;

	private Long scopeId;

	@NotNull
	private String typeKey;

	@Size(max = 255)
	private String physicalMeasure;

	private Double ghgBenefit;

	private String ghgBenefitUnitKey;

	private Double investmentCost;

	private Double financialBenefit;

	private String expectedPaybackTime;

	private DateTime dueDate;

	@Size(max = 255)
	private String webSite;

	@Size(max = 255)
	private String responsiblePerson;

	@Size(max = 1000)
	private String comment;

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

	public Double getInvestmentCost() {
		return investmentCost;
	}

	public void setInvestmentCost(Double investmentCost) {
		this.investmentCost = investmentCost;
	}

	public Double getFinancialBenefit() {
		return financialBenefit;
	}

	public void setFinancialBenefit(Double financialBenefit) {
		this.financialBenefit = financialBenefit;
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

	@Override
	public String toString() {
		return "CreateReducingActionDTO [title=" + title + ", scopeId=" + scopeId + ", typeKey=" + typeKey + ", physicalMeasure=" + physicalMeasure + "]";
	}

}
