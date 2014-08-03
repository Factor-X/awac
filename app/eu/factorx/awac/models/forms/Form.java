package eu.factorx.awac.models.forms;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.data.question.QuestionSet;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "form")
@NamedQueries({ @NamedQuery(name = Form.FIND_BY_IDENTIFIER, query = "select f from Form f where f.identifier = :identifier") })
public class Form extends AbstractEntity {

	public static final String FIND_BY_IDENTIFIER = "Form.findByIdentifier";
	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private String identifier;

	@Transient
	private Integer progress;

	@ManyToMany
	@JoinTable(name = "mm_form_questionset",
			joinColumns = @JoinColumn(name = "form_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "questionset_id", referencedColumnName = "id"))
	private List<QuestionSet> questionSets = new ArrayList<>();

	public Form() {
	}

	public Form(String identifier) {
		super();
		this.identifier = identifier;
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

	public List<QuestionSet> getQuestionSets() {
		return questionSets;
	}

	public void setQuestionSets(List<QuestionSet> questionSet) {
		this.questionSets = questionSet;
	}

}