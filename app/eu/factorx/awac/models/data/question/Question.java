package eu.factorx.awac.models.data.question;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "question")
@DiscriminatorColumn(name = "question_type")
@NamedQueries({
		@NamedQuery(name = Question.FIND_BY_CODES, query = "select q from Question q where q.code in :codes"),
		@NamedQuery(name = Question.FIND_BY_CODE, query = "select q from Question q where q.code = :code"),
		@NamedQuery(name = Question.FIND_BY_QUESTION_SETS, query = "select q from Question q where q.questionSet in :questionSets"),

})
public abstract class Question extends AuditedAbstractEntity {

	/**
	 * :codes = a {@link List} of {@link QuestionCode}
	 */
	public static final String FIND_BY_CODES = "Question.findByCodes";
	/**
	 * :code = a {@link QuestionCode}
	 */
	public static final String FIND_BY_CODE = "Question.findByCode";
	/**
	 * :questionSets = a {@link List} of {@link QuestionSet}
	 */
	public static final String FIND_BY_QUESTION_SETS = "Question.findByQuestionSets";
	private static final long serialVersionUID = 1L;
	@ManyToOne(optional = false)
	protected QuestionSet questionSet;

	@Embedded
	@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "code"))})
	protected QuestionCode code;

	protected int orderIndex = 0;

	protected Question() {
		super();
	}

	protected Question(QuestionSet questionSet, int orderIndex, QuestionCode code) {
		super();
		this.questionSet = questionSet;
		this.orderIndex = orderIndex;
		this.code = code;
	}

	public QuestionSet getQuestionSet() {
		return questionSet;
	}

	public void setQuestionSet(QuestionSet questionSet) {
		this.questionSet = questionSet;
	}

	public QuestionCode getCode() {
		return code;
	}

	public void setCode(QuestionCode code) {
		this.code = code;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public abstract AnswerType getAnswerType();

}
