package eu.factorx.awac.models.forms;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.data.question.QuestionSet;

@Entity
@Table(name = "form")
@NamedQueries({@NamedQuery(name = Form.FIND_BY_IDENTIFIER, query = "select f from Form f where f.identifier = :identifier")})
public class Form extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_IDENTIFIER = "Form.findByIdentifier";

	private String identifier;

	@Transient
	private Integer progress;

	@ManyToMany
	@JoinTable(name = "mm_form_questionset")
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