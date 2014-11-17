package eu.factorx.awac.models.data.question;

import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerType;

@Entity
@Table(name = "question")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({ @NamedQuery(name = Question.FIND_BY_CODES, query = "select q from Question q where q.code in :codes"),
		@NamedQuery(name = Question.FIND_BY_CODE, query = "select q from Question q where q.code = :code"),
		@NamedQuery(name = Question.FIND_BY_QUESTION_SETS_IDS, query = "select q from Question q where q.questionSet.id in :questionSetsIds"),

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
	 * :questionSetsIds = a {@link Collection} of {@link Long}
	 */
	public static final String FIND_BY_QUESTION_SETS_IDS = "Question.findByQuestionSetsIds";

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	protected QuestionSet questionSet;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "code")) })
	protected QuestionCode code;

	protected int orderIndex = 0;

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

	@Override
	public String toString() {
		return "Question [questionSet=" + questionSet + ", code=" + code + ", orderIndex=" + orderIndex + "]";
	}

}
