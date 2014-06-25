package uml.code;

import play.db.ebean.Model;

import javax.persistence.Embeddable;

@Embeddable
public class QuestionCode extends Model {

    private static final long serialVersionUID = 1L;

    public static final QuestionCode Q1 = new QuestionCode("Q1");
    public static final QuestionCode Q2 = new QuestionCode("Q2");

    private String value;

    public QuestionCode() {
        super();
    }

    public QuestionCode(String value) {
        super();
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
