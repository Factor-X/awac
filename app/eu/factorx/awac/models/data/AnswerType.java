package eu.factorx.awac.models.data;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.code.CodeType;

@Embeddable
public class AnswerType implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Integer BOOLEAN_TYPE = 1;
	private static final Integer INTEGER_TYPE = 2;
	private static final Integer DOUBLE_TYPE = 3;
	private static final Integer STRING_TYPE = 4;
	private static final Integer VALUE_SELECTION_TYPE = 5;
	private static final Integer ENTITY_SELECTION_TYPE = 6;

	public static final AnswerType BOOLEAN = new AnswerType(BOOLEAN_TYPE);
	public static final AnswerType INTEGER = new AnswerType(INTEGER_TYPE);
	public static final AnswerType DOUBLE = new AnswerType(DOUBLE_TYPE);
	public static final AnswerType STRING = new AnswerType(STRING_TYPE);

	private Integer type;

	@Embedded
	private CodeType valuesList = null;

	private String entityType = null;

	protected AnswerType() {
		super();
	}

	protected AnswerType(Integer type) {
		super();
		this.type = type;
	}

	protected AnswerType(Integer type, CodeType valuesList) {
		super();
		this.type = type;
		this.valuesList = valuesList;
	}

	protected AnswerType(Integer type, String entityType) {
		super();
		this.type = type;
		this.entityType = entityType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public CodeType getValuesList() {
		return valuesList;
	}

	public void setValuesList(CodeType valuesList) {
		this.valuesList = valuesList;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public static AnswerType valueSelection(CodeType valuesList) {
		return new AnswerType(VALUE_SELECTION_TYPE, valuesList);
	}

	public static AnswerType entitySelection(Class<? extends AbstractEntity> entityClass) {
		return new AnswerType(ENTITY_SELECTION_TYPE, entityClass.getName());
	}

}
