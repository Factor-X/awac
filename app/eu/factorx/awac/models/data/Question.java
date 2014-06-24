package eu.factorx.awac.models.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;
import eu.factorx.awac.models.code.QuestionCode;

@Entity
@Table(name = "question")
public class Question extends Model {

	private static final long serialVersionUID = 1L;

	public Question() {
	}

	@Id
	private Long id;
	private String label;
	private String unitCategory;

	private QuestionCode code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String param) {
		this.label = param;
	}

	public String getUnitCategory() {
		return unitCategory;
	}

	public void setUnitCategory(String param) {
		this.unitCategory = param;
	}

	public QuestionCode getCode() {
		return code;
	}

	public void setCode(QuestionCode param) {
		this.code = param;
	}

}