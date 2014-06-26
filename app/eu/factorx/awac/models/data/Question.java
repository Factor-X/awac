package eu.factorx.awac.models.data;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.factorx.awac.models.code.QuestionCode;
import eu.factorx.awac.models.knowledge.UnitCategory;

@Entity
@Table(name = "question")
public class Question implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "code")), })
	private QuestionCode code;
	@ManyToOne
	private UnitCategory unitCategory;

	public Question() {
	}

	public Question(QuestionCode code) {
		super();
		this.code = code;
	}

	public Question(QuestionCode code, UnitCategory unitCategory) {
		super();
		this.code = code;
		this.unitCategory = unitCategory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public QuestionCode getCode() {
		return code;
	}

	public void setCode(QuestionCode code) {
		this.code = code;
	}

	public UnitCategory getUnitCategory() {
		return unitCategory;
	}

	public void setUnitCategory(UnitCategory unitCategory) {
		this.unitCategory = unitCategory;
	}

}