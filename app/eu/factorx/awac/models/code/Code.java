package eu.factorx.awac.models.code;

import play.db.ebean.Model;

//@Entity
//@DiscriminatorColumn(name = "type")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Code extends Model {

	private static final long serialVersionUID = 1L;

	protected Integer value;
	protected String code;

	public Code() {
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
