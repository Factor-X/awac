package eu.factorx.awac.models.knowledge;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.type.ReducingActionTypeCode;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.models.forms.AwacCalculator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "reducingactionadvice", uniqueConstraints = {
		@UniqueConstraint(columnNames = { ReducingActionAdvice.TITLE_COLUMN, ReducingActionAdvice.CALCULATOR_COLUMN })
})
@NamedQueries({
		@NamedQuery(name = ReducingActionAdvice.FIND_BY_CALCULATOR, query = "select a from ReducingActionAdvice a where a.awacCalculator = :awacCalculator order by type, technicalSegment.creationDate"),
})
public class ReducingActionAdvice extends AuditedAbstractEntity {

	private static final long serialVersionUID = 7989687565472744555L;

	public static final String TITLE_COLUMN = "title";
	public static final String CALCULATOR_COLUMN = "calculator_id";
	public static final String TYPE_COLUMN = "type";

	public static final String FIND_BY_CALCULATOR = "findByCalculator";

	@Column(name = TITLE_COLUMN, nullable = false)
	private String title;

	@ManyToOne
	@JoinColumn(name = CALCULATOR_COLUMN, nullable = false)
	private AwacCalculator awacCalculator;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = Code.KEY_PROPERTY, column = @Column(name = TYPE_COLUMN, length = 1, nullable = false)) })
	private ReducingActionTypeCode type;

	@Column(columnDefinition = "TEXT")
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

	@Column(columnDefinition = "TEXT")
	private String comment;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "reducingactionadvice_storedfile",
			joinColumns = @JoinColumn(name = "reducingactionadvice_id"),
			inverseJoinColumns = @JoinColumn(name = "storedfile_id"),
			uniqueConstraints = {
					@UniqueConstraint(columnNames = { "reducingactionadvice_id", "storedfile_id" }, name = "uk_reducingactionadvice_storedfile_logical_pkey")
			})
	private List<StoredFile> documents = new ArrayList<>();

	@OneToMany(mappedBy = "actionAdvice", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<ReducingActionAdviceBaseIndicatorAssociation> baseIndicatorAssociations = new HashSet<>();

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

	public ReducingActionTypeCode getType() {
		return type;
	}

	public void setType(ReducingActionTypeCode type) {
		this.type = type;
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

	public Set<ReducingActionAdviceBaseIndicatorAssociation> getBaseIndicatorAssociations() {
		return baseIndicatorAssociations;
	}

	public void setBaseIndicatorAssociations(Set<ReducingActionAdviceBaseIndicatorAssociation> baseIndicatorAssociations) {
		this.getBaseIndicatorAssociations().clear();
		this.getBaseIndicatorAssociations().addAll(baseIndicatorAssociations);
	}

	public List<StoredFile> getDocuments() {
		return documents;
	}

	public void setDocuments(List<StoredFile> documents) {
		this.documents.clear();
		this.documents.addAll(documents);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		ReducingActionAdvice that = (ReducingActionAdvice) o;

		if (!awacCalculator.equals(that.awacCalculator)) return false;
		if (!title.equals(that.title)) return false;
		if (!type.equals(that.type)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + title.hashCode();
		result = 31 * result + awacCalculator.hashCode();
		result = 31 * result + type.hashCode();
		return result;
	}
}
