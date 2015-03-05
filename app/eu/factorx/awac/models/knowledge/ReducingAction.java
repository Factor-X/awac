package eu.factorx.awac.models.knowledge;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.joda.time.DateTime;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.type.ReducingActionStatusCode;
import eu.factorx.awac.models.code.type.ReducingActionTypeCode;
import eu.factorx.awac.models.data.file.StoredFile;

@Entity
@Table(name = "reducingaction", uniqueConstraints = {
		@UniqueConstraint(columnNames = { ReducingAction.TITLE_COLUMN, ReducingAction.SCOPE_COLUMN })
})
@NamedQueries({
		@NamedQuery(name = ReducingAction.FIND_BY_SCOPES, query = "select ra from ReducingAction ra where ra.scope in :scopes order by ra.status, ra.technicalSegment.creationDate"),
})
public class ReducingAction extends AuditedAbstractEntity {

	private static final long serialVersionUID = 7989687565472744555L;

	public static final String TITLE_COLUMN = "title";
	public static final String SCOPE_COLUMN = "scope_id";
	public static final String TYPE_COLUMN = "type";
	public static final String STATUS_COLUMN = "status";

	/**
	 * @param scopes
	 *            A collection of {@link Scope}
	 */
	public static final String FIND_BY_SCOPES = "ReducingAction.findByScopes";

	@Column(name = TITLE_COLUMN, nullable = false)
	private String title;

	@ManyToOne
	@JoinColumn(name = SCOPE_COLUMN, nullable = false)
	private Scope scope;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = Code.KEY_PROPERTY, column = @Column(name = TYPE_COLUMN, length = 1, nullable = false)) })
	private ReducingActionTypeCode type;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = Code.KEY_PROPERTY, column = @Column(name = STATUS_COLUMN, length = 1, nullable = false)) })
	private ReducingActionStatusCode status = ReducingActionStatusCode.RUNNING;

	private DateTime completionDate;

	@Column(columnDefinition = "TEXT")
	private String physicalMeasure;

	private Double ghgBenefit;

	@ManyToOne
	private Unit ghgBenefitUnit;

	private Double ghgBenefitMax;

	@ManyToOne
	private Unit ghgBenefitMaxUnit;

	private Double financialBenefit;

	private Double investmentCost;

	/**
	 * Return on investment expected duration (in years, months...)
	 */
	private String expectedPaybackTime;

	private DateTime dueDate;

	private String webSite;

	private String responsiblePerson;

	@Column(columnDefinition = "TEXT")
	private String comment;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "reducingaction_storedfile",
			joinColumns = @JoinColumn(name = "reducingaction_id"),
			inverseJoinColumns = @JoinColumn(name = "storedfile_id"))
	private List<StoredFile> documents;

	public ReducingAction() {
		super();
	}

	/**
	 * @param title
	 * @param scope
	 * @param type
	 */
	public ReducingAction(String title, Scope scope, ReducingActionTypeCode type) {
		super();
		this.title = title;
		this.scope = scope;
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public ReducingActionTypeCode getType() {
		return type;
	}

	public void setType(ReducingActionTypeCode type) {
		this.type = type;
	}

	public ReducingActionStatusCode getStatus() {
		return status;
	}

	public void setStatus(ReducingActionStatusCode status) {
		this.status = status;
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

	public Unit getGhgBenefitUnit() {
		return ghgBenefitUnit;
	}

	public void setGhgBenefitUnit(Unit ghgBenefitUnit) {
		this.ghgBenefitUnit = ghgBenefitUnit;
	}

	public Double getGhgBenefitMax() {
		return ghgBenefitMax;
	}

	public void setGhgBenefitMax(Double ghgBenefitMax) {
		this.ghgBenefitMax = ghgBenefitMax;
	}

	public Unit getGhgBenefitMaxUnit() {
		return ghgBenefitMaxUnit;
	}

	public void setGhgBenefitMaxUnit(Unit ghgBenefitMaxUnit) {
		this.ghgBenefitMaxUnit = ghgBenefitMaxUnit;
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

	public List<StoredFile> getDocuments() {
		if (documents == null) {
			return new ArrayList<>();
		}
		return documents;
	}

	public void setDocuments(List<StoredFile> documents) {
		this.documents = documents;
	}

	@Override
	public String toString() {
		return "ReducingAction{" +
				"title='" + title + '\'' +
				", scope=" + scope +
				", type=" + type +
				", status=" + status +
				'}';
	}
}
