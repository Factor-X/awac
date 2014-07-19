package eu.factorx.awac.models.data.answer;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.Question;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question_answer")
@NamedQueries({@NamedQuery(name = QuestionAnswer.FIND_BY_CODES, query = "select qa from QuestionAnswer qa where qa.question.code in :codes"),})
public class QuestionAnswer extends AbstractEntity {

	/**
	 * @param codes : a Collection of {@link QuestionCode}
	 */
	public static final String FIND_BY_CODES = "QuestionAnswer.findByCodes";
	private static final long serialVersionUID = 1L;

	// ATTRIBUTES
	@ManyToOne(optional = false)
	private Account dataOwner;

	@Embedded
	private AuditInfo auditInfo;

	@ManyToOne(optional = false)
	private QuestionSetAnswer questionSetAnswer;

	@ManyToOne(optional = false, targetEntity = Question.class)
	private Question question;

	@OneToMany(mappedBy = "questionAnswer", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AnswerValue> answerValues;

	// CONSTRUCTORS

	protected QuestionAnswer() {
		super();
	}

	public QuestionAnswer(Account dataOwner, AuditInfo auditInfo, QuestionSetAnswer questionSetAnswer, Question question) {
		super();
		this.dataOwner = dataOwner;
		this.auditInfo = auditInfo;
		this.questionSetAnswer = questionSetAnswer;
		this.question = question;
		this.answerValues = new ArrayList<>();
	}

	public Account getDataOwner() {
		return dataOwner;
	}

	public void setDataOwner(Account dataOwner) {
		this.dataOwner = dataOwner;
	}

	public AuditInfo getAuditInfo() {
		return auditInfo;
	}

	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}

	public QuestionSetAnswer getQuestionSetAnswer() {
		return questionSetAnswer;
	}

	public void setQuestionSetAnswer(QuestionSetAnswer questionSetAnswer) {
		this.questionSetAnswer = questionSetAnswer;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public List<AnswerValue> getAnswerValues() {
		return answerValues;
	}

	public void setAnswerValues(List<AnswerValue> answerValues) {
		this.answerValues = answerValues;
	}

}