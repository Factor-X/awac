package eu.factorx.awac.models.data.answer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.knowledge.Period;

@Entity
@NamedQueries({
		@NamedQuery(name = QuestionSetAnswer.FIND_BY_SCOPE_AND_PERIOD, query = "select qsa from QuestionSetAnswer qsa where qsa.scope = :scope and qsa.period = :period"),
		@NamedQuery(name = QuestionSetAnswer.FIND_BY_SCOPE_AND_PERIOD_AND_QUESTION_SETS, query = "select qsa from QuestionSetAnswer qsa where qsa.scope = :scope and qsa.period = :period and qsa.questionSet in :questionSets and qsa.parent is null"),})
public class QuestionSetAnswer extends AbstractEntity {

	/**
	 * @param scope
	 * : a {@link Scope}
	 * @param period
	 * : a {@link Period}
	 */
	public static final String FIND_BY_SCOPE_AND_PERIOD = "QuestionAnswer.findByScopeAndPeriod";

	/**
	 * @param codes : a Collection of {@link QuestionCode}
	 */
	public static final String FIND_BY_CODES = "QuestionAnswer.findByCodes";

	/**
	 * @param scope : a {@link Scope}
	 * @param period : a {@link Period}
	 * @param questionSets: a Collection of {@link QuestionSet}
	 */
	public static final String FIND_BY_SCOPE_AND_PERIOD_AND_QUESTION_SETS = "QuestionAnswer.findByScopeAndPeriodAndQuestionSets";

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

	@OneToMany(mappedBy = "questionSetAnswer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<QuestionAnswer> questionAnswers;

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<QuestionSetAnswer> children;

	/**
	 * @param questionSet
	 * @param period
	 * @param scope
	 * @param parent
	 * @param repetitionIndex
	 */
	public QuestionSetAnswer(Scope scope, Period period, QuestionSet questionSet,
            			Integer repetitionIndex, QuestionSetAnswer parent) {
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

	@Override
	public String toString() {
		return "QuestionSetAnswer [questionSet=" + questionSet + ", parent=" + parent + ", repetitionIndex=" + repetitionIndex + "]";
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
		return new EqualsBuilder().append(this.questionSet, rhs.questionSet).append(this.period, rhs.period).append(this.scope, rhs.scope)
				.append(this.parent, rhs.parent).append(this.repetitionIndex, rhs.repetitionIndex).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(47, 89).append(this.questionSet).append(this.period).append(this.scope).append(this.parent)
				.append(this.repetitionIndex).toHashCode();
	}
}
