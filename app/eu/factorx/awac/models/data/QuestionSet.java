package eu.factorx.awac.models.data;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.code.QuestionCode;

@Entity
@Table(name = "question_set")
public class QuestionSet extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "code")) })
	private QuestionCode code;

	private Boolean repetitionAllowed;

	@OneToMany(mappedBy = "questionSet")
	private List<Question> questions;

	protected QuestionSet() {
		super();
	}

	public QuestionSet(QuestionCode code, Boolean repetitionAllowed) {
		super();
		this.code = code;
		this.repetitionAllowed = repetitionAllowed;
	}

	public QuestionSet(QuestionCode code) {
		this(code, Boolean.FALSE);
	}

	public QuestionCode getCode() {
		return code;
	}

	public void setCode(QuestionCode code) {
		this.code = code;
	}

	public Boolean getRepetitionAllowed() {
		return repetitionAllowed;
	}

	public void setRepetitionAllowed(Boolean repetitionAllowed) {
		this.repetitionAllowed = repetitionAllowed;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

}