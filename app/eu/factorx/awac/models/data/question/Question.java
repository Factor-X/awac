package eu.factorx.awac.models.data.question;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerType;

@Entity
@Table(name = "question")
@DiscriminatorColumn(name = "question_type")
@NamedQueries({ @NamedQuery(name = Question.FIND_BY_CODES, query = "select q from Question q where q.code in :codes"),
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
	@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "code")) })
	protected QuestionCode code;

	protected int orderIndex = 0;

	protected Double defaultValue = null;

	protected Question() {
		super();
	}

	/**
	 * @param questionSet
	 * @param orderIndex
	 * @param code
	 */
	protected Question(QuestionSet questionSet, int orderIndex, QuestionCode code) {
		super();
		this.questionSet = questionSet;
		this.orderIndex = orderIndex;
		this.code = code;
	}

	/**
	 * @param questionSet
	 * @param orderIndex
	 * @param code
	 * @param defaultValue
	 */
	public Question(QuestionSet questionSet, int orderIndex, QuestionCode code, Double defaultValue) {
		super();
		this.questionSet = questionSet;
		this.orderIndex = orderIndex;
		this.code = code;
		this.defaultValue = defaultValue;
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

	public Double getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Double defaultValue) {
		this.defaultValue = defaultValue;
	}

	public abstract AnswerType getAnswerType();

	@Override
	public String toString() {
		return "Question [questionSet=" + questionSet + ", code=" + code + ", orderIndex=" + orderIndex + "]";
	}

}
