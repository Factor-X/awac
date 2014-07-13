package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "question_code")) })
public class QuestionCode extends Code {

	private static final long serialVersionUID = 1L;

    public static final QuestionCode A1 = new QuestionCode("A1");
    public static final QuestionCode A2 = new QuestionCode("A2");
    public static final QuestionCode A3 = new QuestionCode("A3");
    public static final QuestionCode A4 = new QuestionCode("A4");
    public static final QuestionCode A5 = new QuestionCode("A5");
    public static final QuestionCode A6 = new QuestionCode("A6");
    public static final QuestionCode A7 = new QuestionCode("A7");
    public static final QuestionCode A8 = new QuestionCode("A8");
    public static final QuestionCode A9 = new QuestionCode("A9");
    public static final QuestionCode A10 = new QuestionCode("A10");
    public static final QuestionCode A11 = new QuestionCode("A11");
    public static final QuestionCode A12 = new QuestionCode("A12");

    protected QuestionCode() {
        super(CodeList.QUESTION);
    }

    public QuestionCode(String key) {
        this();
        this.key = key;
    }

}
