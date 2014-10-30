package eu.factorx.awac.dto.awac.shared;

import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.post.FilesUploadedDTO;

public class ActionAdviceDTO extends DTO {

	private String title;

	private String interfaceTypeKey;

	private String physicalMeasure;

	private Double ghgBenefit;

	private String ghgBenefitUnitKey;

	private Double financialBenefit;

	private Double investmentCost;

	private String expectedPaybackTime;

	private String webSite;

	private String responsiblePerson;

	private String comment;

	private List<FilesUploadedDTO> files;

	private List<BaseIndicatorDTO> baseIndicatorAssociations;

	public ActionAdviceDTO() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInterfaceTypeKey() {
		return interfaceTypeKey;
	}

	public void setInterfaceTypeKey(String interfaceTypeKey) {
		this.interfaceTypeKey = interfaceTypeKey;
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

	public List<FilesUploadedDTO> getFiles() {
		return files;
	}

	public void setFiles(List<FilesUploadedDTO> files) {
		this.files = files;
	}

	public List<BaseIndicatorDTO> getBaseIndicatorAssociations() {
		return baseIndicatorAssociations;
	}

	public void setBaseIndicatorAssociations(List<BaseIndicatorDTO> baseIndicatorAssociations) {
		this.baseIndicatorAssociations = baseIndicatorAssociations;
	}

}
