package uml.data;

import play.db.ebean.Model;
import uml.code.QuestionCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "question")
public class Question extends Model {

    private static final long serialVersionUID = 1L;

    public Question() {
    }

    @Id
    private long id;
    private String label;
    private String unitCategory;

    private QuestionCode code;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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