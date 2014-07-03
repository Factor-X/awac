package eu.factorx.awac.models.data;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.business.User;

@Entity
@Table(name = "question_answer")
public class QuestionAnswer extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	private Question question;

	@ManyToOne(optional = false)
	private QuestionSetAnswer questionSetAnswer;

	@ManyToOne(optional = false)
	private User dataOwner;

	@Embedded
	private AuditInfo auditInfo;

	private Short repetitionIndex;

	@OneToMany(mappedBy = "questionAnswer")
	private List<AnswerData> answerData;

	@Transient
	private List<? extends AnswerValue> answerValues;

	protected QuestionAnswer() {
		super();
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public QuestionSetAnswer getQuestionSetAnswer() {
		return questionSetAnswer;
	}

	public void setQuestionSetAnswer(QuestionSetAnswer questionSetAnswer) {
		this.questionSetAnswer = questionSetAnswer;
	}

	public User getDataOwner() {
		return dataOwner;
	}

	public void setDataOwner(User dataOwner) {
		this.dataOwner = dataOwner;
	}

	public AuditInfo getAuditInfo() {
		return auditInfo;
	}

	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}

	public Short getRepetitionIndex() {
		return repetitionIndex;
	}

	public void setRepetitionIndex(Short repetitionIndex) {
		this.repetitionIndex = repetitionIndex;
	}

	public List<AnswerData> getAnswerData() {
		return answerData;
	}

	public void setAnswerData(List<AnswerData> answerData) {
		this.answerData = answerData;
	}

	public List<? extends AnswerValue> getAnswerValues() {
		return answerValues;
	}

	public void setAnswerValues(List<? extends AnswerValue> answerValues) {
		this.answerValues = answerValues;
	}

}