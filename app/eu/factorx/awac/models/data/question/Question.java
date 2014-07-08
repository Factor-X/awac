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

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerType;

@Entity
@Table(name = "question")
@DiscriminatorColumn(name = "question_type")
@NamedQueries({ @NamedQuery(name = Question.FIND_BY_CODES, query = "select q from Question q where q.code in :codes"),
		@NamedQuery(name = Question.FIND_BY_CODE, query = "select q from Question q where q.code = :code"), })
public abstract class Question extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * :codes = a {@link List} of {@link QuestionCode}
	 */
	public static final String FIND_BY_CODES = "Question.findByCodes";

	/**
	 * :code = a {@link QuestionCode}
	 */
	public static final String FIND_BY_CODE = "Question.findByCode";

	@ManyToOne(optional = false)
	protected QuestionSet questionSet;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "code")) })
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
