package eu.factorx.awac.models.knowledge;

import java.util.List;

import javax.persistence.*;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.models.forms.AwacCalculator;

@Entity
@Table(name = "reducingactionadvice", uniqueConstraints = {
		@UniqueConstraint(columnNames = { ReducingActionAdvice.TITLE_COLUMN, ReducingActionAdvice.CALCULATOR_COLUMN })
})
public class ReducingActionAdvice extends AuditedAbstractEntity {

	private static final long serialVersionUID = 7989687565472744555L;

	public static final String TITLE_COLUMN = "title";
	public static final String CALCULATOR_COLUMN = "calculator_id";

	/**
	 * @param scopes
	 *            A collection of {@link Scope}
	 */
	public static final String FIND_BY_SCOPES = "ReducingAction.findByScopes";

	@Column(name = TITLE_COLUMN, nullable = false)
	private String title;

	@ManyToOne
	@JoinColumn(name = CALCULATOR_COLUMN, nullable = false)
	private AwacCalculator awacCalculator;

	private String physicalMeasure;

	private Double ghgBenefit;

	@ManyToOne
	private Unit ghgBenefitUnit;

	private Double financialBenefit;

	private Double investmentCost;

	/**
	 * Return on investment expected duration (in years, months...)
	 */
	private String expectedPaybackTime;

	private String webSite;

	private String responsiblePerson;

	@Column(length = 1000)
	private String comment;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "reducingactionadvice_storedfile",
			joinColumns = @JoinColumn(name = "reducingactionadvice_id"),
			inverseJoinColumns = @JoinColumn(name = "storedfile_id"))
	private List<StoredFile> documents;

	public ReducingActionAdvice() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public AwacCalculator getAwacCalculator() {
		return awacCalculator;
	}

	public void setAwacCalculator(AwacCalculator awacCalculator) {
		this.awacCalculator = awacCalculator;
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

	public Unit getGhgBenefitUnit() {
		return ghgBenefitUnit;
	}

	public void setGhgBenefitUnit(Unit ghgBenefitUnit) {
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

	public List<StoredFile> getDocuments() {
		return documents;
	}

	public void setDocuments(List<StoredFile> documents) {
		this.documents = documents;
	}

}
