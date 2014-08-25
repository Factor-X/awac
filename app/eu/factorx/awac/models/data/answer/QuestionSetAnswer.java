package eu.factorx.awac.models.data.answer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.knowledge.Period;

@Entity
@NamedQueries({ @NamedQuery(name = QuestionSetAnswer.FIND_DISTINCT_PERIODS, query = "select distinct qsa.period from QuestionSetAnswer qsa where qsa.scope.id = :scopeId"), })
public class QuestionSetAnswer extends AuditedAbstractEntity {

	/**
	 * @param scopeId
	 *            : a {@link Long}
	 */
	public static final String FIND_DISTINCT_PERIODS = "QuestionSetAnswer.findAllDistinctPeriodsByScopeId";

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	private QuestionSet questionSet;

	@ManyToOne(optional = false)
	private Period period;

	@ManyToOne(optional = false)
	private Scope scope;

	@ManyToOne()
	private QuestionSetAnswer parent = null;

	private Integer repetitionIndex = 0;

	@OneToMany(mappedBy = "questionSetAnswer", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
	private List<QuestionAnswer> questionAnswers;

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
	private List<QuestionSetAnswer> children;

	/**
	 * @param questionSet
	 * @param period
	 * @param scope
	 * @param parent
	 * @param repetitionIndex
	 */
	public QuestionSetAnswer(Scope scope, Period period, QuestionSet questionSet, Integer repetitionIndex, QuestionSetAnswer parent) {
		super();
		this.scope = scope;
		this.period = period;
		this.questionSet = questionSet;
		this.repetitionIndex = repetitionIndex;
		this.parent = parent;
		this.questionAnswers = new ArrayList<QuestionAnswer>();
		this.children = new ArrayList<QuestionSetAnswer>();
	}

	public QuestionSetAnswer() {
		super();
	}

	public QuestionSet getQuestionSet() {
		return questionSet;
	}

	public void setQuestionSet(QuestionSet questionSet) {
		this.questionSet = questionSet;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public QuestionSetAnswer getParent() {
		return parent;
	}

	public void setParent(QuestionSetAnswer parent) {
		this.parent = parent;
	}

	public Integer getRepetitionIndex() {
		return repetitionIndex;
	}

	public void setRepetitionIndex(Integer repetitionIndex) {
		this.repetitionIndex = repetitionIndex;
	}

	public List<QuestionAnswer> getQuestionAnswers() {
		return questionAnswers;
	}

	public void setQuestionAnswers(List<QuestionAnswer> questionAnswers) {
		this.questionAnswers = questionAnswers;
	}

	public List<QuestionSetAnswer> getChildren() {
		return children;
	}

	public void setChildren(List<QuestionSetAnswer> children) {
		this.children = children;
	}

	public QuestionSetAnswer appendChild(QuestionSetAnswer subQuestionSetAnswer) {
		this.getChildren().add(subQuestionSetAnswer);
		return this;
	}

	public static Map<String, Integer> createRepetitionMap(QuestionSetAnswer questionSetAnswer) {
		Map<String, Integer> res = new HashMap<>();
		while (questionSetAnswer != null) {
			QuestionSet questionSet = questionSetAnswer.getQuestionSet();
			if (questionSet.getRepetitionAllowed()) {
				res.put(questionSet.getCode().getKey(), questionSetAnswer.getRepetitionIndex());
			}
			questionSetAnswer = questionSetAnswer.getParent();
		}
		return res;
	}

	public static Map<String, Integer> createNormalizedRepetitionMap(QuestionSetAnswer questionSetAnswer) {
		Map<String, Integer> res = new HashMap<>();
		while (questionSetAnswer != null) {
			QuestionSet questionSet = questionSetAnswer.getQuestionSet();
			if (questionSet.getRepetitionAllowed()) {
				res.put(questionSet.getCode().getKey(), questionSetAnswer.getRepetitionIndex());
			} else {
				res.put(questionSet.getCode().getKey(), 0);
			}
			questionSetAnswer = questionSetAnswer.getParent();
		}
		return res;
	}

	@Override
	public String toString() {
		return "QuestionSetAnswer [questionSet=" + questionSet.getCode().getKey() + ", repetitionIndex=" + repetitionIndex + ", parent=" + parent + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof QuestionSetAnswer)) {
			return false;
		}
		QuestionSetAnswer rhs = (QuestionSetAnswer) obj;
		return new EqualsBuilder().append(this.questionSet, rhs.questionSet).append(this.period, rhs.period).append(this.scope, rhs.scope).append(this.parent, rhs.parent)
				.append(this.repetitionIndex, rhs.repetitionIndex).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(47, 89).append(this.questionSet).append(this.period).append(this.scope).append(this.parent).append(this.repetitionIndex).toHashCode();
	}
}
