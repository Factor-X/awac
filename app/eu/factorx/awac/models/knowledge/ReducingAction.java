package eu.factorx.awac.models.knowledge;

import java.util.List;

import javax.persistence.*;

import org.joda.time.LocalDate;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.ReducingActionStatus;
import eu.factorx.awac.models.code.type.ReducingActionType;
import eu.factorx.awac.models.data.file.StoredFile;

@Entity
@Table(name = "reducingaction", uniqueConstraints = {
		@UniqueConstraint(columnNames = { ReducingAction.TITLE_COLUMN_NAME, ReducingAction.SCOPE_COLUMN_NAME })
})
public class ReducingAction extends AuditedAbstractEntity {

	private static final long serialVersionUID = 7989687565472744555L;

	public static final String TITLE_COLUMN_NAME = "title";
	public static final String SCOPE_COLUMN_NAME = "scope_id";

	@Column(name = TITLE_COLUMN_NAME, nullable = false)
	private String title;

	@ManyToOne
	@JoinColumn(name = SCOPE_COLUMN_NAME, nullable = false)
	private Scope scope;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "type")) })
	@Column(length = 1, nullable = false)
	private ReducingActionType type;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "status")) })
	@Column(length = 1, nullable = false)
	private ReducingActionStatus status = ReducingActionStatus.RUNNING;

	private Integer completionYear;

	private String physicalMeasure;

	private Double ghgBenefit;

	private Double financialBenefit;

	private Double investment;

	private Integer expectedPaybackTime;

	private LocalDate dueDate;

	private String responsiblePerson;

	@Column(length = 1000)
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
	public ReducingAction(String title, Scope scope, ReducingActionType type) {
		super();
		this.title = title;
		this.scope = scope;
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public ReducingActionType getType() {
		return type;
	}

	public void setType(ReducingActionType type) {
		this.type = type;
	}

	public ReducingActionStatus getStatus() {
		return status;
	}

	public void setStatus(ReducingActionStatus status) {
		this.status = status;
	}

	public Integer getCompletionYear() {
		return completionYear;
	}

	public void setCompletionYear(Integer completionYear) {
		this.completionYear = completionYear;
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

	public Double getFinancialBenefit() {
		return financialBenefit;
	}

	public void setFinancialBenefit(Double financialBenefit) {
		this.financialBenefit = financialBenefit;
	}

	public Double getInvestment() {
		return investment;
	}

	public void setInvestment(Double investment) {
		this.investment = investment;
	}

	public Integer getExpectedPaybackTime() {
		return expectedPaybackTime;
	}

	public void setExpectedPaybackTime(Integer expectedPaybackTime) {
		this.expectedPaybackTime = expectedPaybackTime;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
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
