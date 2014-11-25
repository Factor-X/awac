package eu.factorx.awac.models.forms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.data.question.QuestionSet;

@Entity
@Table(name = "form")
@NamedQueries({
		@NamedQuery(name = Form.FIND_BY_IDENTIFIER, query = "select f from Form f where f.identifier = :identifier"),
		@NamedQuery(name = Form.FIND_BY_CALCULATOR, query = "select f from Form f where f.awacCalculator = :awacCalculator")
})
public class Form extends AuditedAbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_IDENTIFIER = "Form.findByIdentifier";

	public static final String FIND_BY_CALCULATOR = "Form.findByCalculator";

	@Column(unique = true)
	private String identifier;

	@Transient
	private Integer progress;

	@ManyToMany
	@JoinTable(name = "mm_form_questionset",
			joinColumns = @JoinColumn(name = "form_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "questionset_id", referencedColumnName = "id"))
	private Set<QuestionSet> questionSets = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.MERGE}, optional = false)
    private AwacCalculator awacCalculator;

	public Form() {
	}

	public Form(String identifier) {
		super();
		this.identifier = identifier;
	}

    public AwacCalculator getAwacCalculator() {
        return awacCalculator;
    }

    public void setAwacCalculator(AwacCalculator awacCalculator) {
        this.awacCalculator = awacCalculator;
    }

    public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public Set<QuestionSet> getQuestionSets() {
		return this.questionSets;
	}

    public void setQuestionSets(Set<QuestionSet> questionSets) {
        this.questionSets = questionSets;
    }

    public List<QuestionSet> getAllQuestionSets() {
        return getAllQuestionSets(this.questionSets);
    }

    private List<QuestionSet> getAllQuestionSets(Set<QuestionSet> questionSets) {
		List<QuestionSet> result = new ArrayList<>();
		for (QuestionSet questionSet : questionSets) {
			result.add(questionSet);
			result.addAll(getAllQuestionSets(questionSet.getChildren()));
		}
		return result;
	}

    @Override
    public String toString() {
        return "Form{" +
                "identifier='" + identifier + '\'' +
                ", progress=" + progress +
                ", questionSets=" + questionSets +
                ", awacCalculator=" + awacCalculator +
                '}';
    }
}