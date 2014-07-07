package eu.factorx.awac.models.forms;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.data.question.QuestionSet;

@Entity
@Table(name = "form")
@NamedQueries({ @NamedQuery(name = Form.FIND_BY_IDENTIFIER, query = "select f from Form f where f.identifier = :identifier") })
public class Form extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_IDENTIFIER = "Form.findByIdentifier";

	private String identifier;

	@Transient
	private Integer progress;

	@OneToMany(mappedBy = "form")
	private List<FormQuestion> questions = new ArrayList<FormQuestion>();

	public Form() {
	}

	public Form(String identifier) {
		super();
		this.identifier = identifier;
	}

	public String getName() {
		return identifier;
	}

	public void setName(String name) {
		this.identifier = name;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public List<FormQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<FormQuestion> param) {
		this.questions = param;
	}

	public List<QuestionSet> getQuestionSets() {
		List<QuestionSet> res = new ArrayList<>();
		for (FormQuestion formQuestion : this.questions) {
			res.add(formQuestion.getQuestionSet());
		}
		return res;
	}

}