package eu.factorx.awac.models.data.question;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.code.type.QuestionCode;

@Entity
@Table(name = "question_set")
public class QuestionSet extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_SCOPE_AND_PERIOD = "";

	@Enumerated
	@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "code")) })
	private QuestionCode code;

	private Boolean repetitionAllowed;

	@OneToMany(mappedBy = "questionSet", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Question<?>> questions = new ArrayList<>();

	protected QuestionSet() {
		super();
	}

	public QuestionSet(QuestionCode code, Boolean repetitionAllowed) {
		super();
		this.code = code;
		this.repetitionAllowed = repetitionAllowed;
	}

	public QuestionSet(QuestionCode code, Boolean repetitionAllowed, List<Question<?>> questions) {
		super();
		this.code = code;
		this.repetitionAllowed = repetitionAllowed;
		this.questions = questions;
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

	public List<? extends Question<?>> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question<?>> questions) {
		this.questions = questions;
	}

	public <T extends Question<?>> boolean addQuestion(T question) {
		return this.questions.add(question);
	}

}