package eu.factorx.awac.dto.awac.shared;

import java.io.Serializable;
import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.post.FilesUploadedDTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;
import eu.factorx.awac.dto.validation.annotations.Size;

public class ReducingActionAdviceDTO extends DTO implements Serializable {

	private static final long serialVersionUID = 936537094692911494L;

	private Long id;

	@NotNull
	@Size(min = 1, max = 255)
	private String title;

	@NotNull
	private String interfaceTypeKey;

	@NotNull
	private String typeKey;

	@Size(max = 255)
	private String physicalMeasure;

	private Double financialBenefit;

	private Double investmentCost;

	private Double computedGhgBenefit;

	private String computedGhgBenefitUnitKey;

	@Size(max = 255)
	private String expectedPaybackTime;

	@Size(max = 255)
	private String webSite;

	@Size(max = 255)
	private String responsiblePerson;

	@Size(max = 1000)
	private String comment;

	private List<FilesUploadedDTO> files;

	private List<BaseIndicatorAssociationDTO> baseIndicatorAssociations;

	public static class BaseIndicatorAssociationDTO extends DTO implements Serializable {

		private static final long serialVersionUID = 884352172637531943L;

		@NotNull
		private String baseIndicatorKey;

		@NotNull
		private Double percent;

		public BaseIndicatorAssociationDTO() {
			super();
		}

		public BaseIndicatorAssociationDTO(String baseIndicatorKey, Double percent) {
			super();
			this.baseIndicatorKey = baseIndicatorKey;
			this.percent = percent;
		}

		public String getBaseIndicatorKey() {
			return baseIndicatorKey;
		}

		public void setBaseIndicatorKey(String baseIndicatorKey) {
			this.baseIndicatorKey = baseIndicatorKey;
		}

		public Double getPercent() {
			return percent;
		}

		public void setPercent(Double percent) {
			this.percent = percent;
		}
	}

	public ReducingActionAdviceDTO() {
		super();
	}

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

	public String getInterfaceTypeKey() {
		return interfaceTypeKey;
	}

	public void setInterfaceTypeKey(String interfaceTypeKey) {
		this.interfaceTypeKey = interfaceTypeKey;
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

	public Double getComputedGhgBenefit() {
		return computedGhgBenefit;
	}

	public void setComputedGhgBenefit(Double computedGhgBenefit) {
		this.computedGhgBenefit = computedGhgBenefit;
	}

	public String getComputedGhgBenefitUnitKey() {
		return computedGhgBenefitUnitKey;
	}

	public void setComputedGhgBenefitUnitKey(String computedGhgBenefitUnitKey) {
		this.computedGhgBenefitUnitKey = computedGhgBenefitUnitKey;
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

	public List<BaseIndicatorAssociationDTO> getBaseIndicatorAssociations() {
		return baseIndicatorAssociations;
	}

	public void setBaseIndicatorAssociations(List<BaseIndicatorAssociationDTO> baseIndicatorAssociations) {
		this.baseIndicatorAssociations = baseIndicatorAssociations;
	}

}
