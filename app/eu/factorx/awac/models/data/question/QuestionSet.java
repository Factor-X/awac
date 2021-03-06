package eu.factorx.awac.models.data.question;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import eu.factorx.awac.models.forms.Form;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.code.type.QuestionCode;

@Entity
@Table(name = "question_set")
@NamedQueries({
        @NamedQuery(name = QuestionSet.FIND_BY_CODE, query = "select q from QuestionSet q where q.code = :code"),
})

public class QuestionSet extends AuditedAbstractEntity {

	private static final long serialVersionUID = 1L;
    public static final String FIND_BY_CODE = "questionSet.findByCode";

    @Enumerated
	@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "code")) })
	private QuestionCode code;

	private Boolean repetitionAllowed;

	@ManyToOne
	private QuestionSet parent;

	@OneToMany(mappedBy = "questionSet", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Question> questions = new ArrayList<>();

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<QuestionSet> children = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "mm_form_questionset",
            joinColumns= @JoinColumn(name = "questionset_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "form_id", referencedColumnName = "id"))
    private Set<Form> formList = new HashSet<>();

	protected QuestionSet() {
		super();
	}

	public QuestionSet(QuestionCode code, Boolean repetitionAllowed, QuestionSet parent) {
		super();
		this.code = code;
		this.repetitionAllowed = repetitionAllowed;
		this.parent = parent;
	}

	public QuestionSet(QuestionCode code, Boolean repetitionAllowed) {
		super();
		this.code = code;
		this.repetitionAllowed = repetitionAllowed;
	}

    public Set<Form> getFormList() {
        return formList;
    }

    public void setFormList(Set<Form> formList) {
        this.formList = formList;
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

	public QuestionSet getParent() {
		return parent;
	}

	public void setParent(QuestionSet parent) {
		this.parent = parent;
	}

	public Set<QuestionSet> getChildren() {
		return children;
	}

	public void setChildren(Set<QuestionSet> children) {
		this.children = children;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public boolean addQuestion(Question question) {
		return this.questions.add(question);
	}

	public boolean addChild(QuestionSet questionSet) {
		return this.getChildren().add(questionSet);
	}

	@Override
	public String toString() {
		return "QuestionSet [code=" + code + ", repetitionAllowed=" + repetitionAllowed + ", parent=" + parent + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof QuestionSet)) {
			return false;
		}
		QuestionSet rhs = (QuestionSet) obj;
		return new EqualsBuilder().append(this.code, rhs.code).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(53, 13).append(this.code).toHashCode();
	}

}
