package eu.factorx.awac.models.code;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class CodeType implements Serializable {

	public static final CodeType LANGUAGE = new CodeType("LANG");
	public static final CodeType GENDER = new CodeType("GENDER");
	public static final CodeType HEATING_FUEL_TYPE = new CodeType("HEATING_FUEL_TYPE");
	public static final CodeType SCOPE = new CodeType("SCOPE");;
	// ...

	public static final CodeType[] ALL = { LANGUAGE, GENDER, HEATING_FUEL_TYPE };
	private static final long serialVersionUID = 1L;

	private String key;

	protected CodeType() {
		super();
	}

	protected CodeType(String key) {
		super();
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public static CodeType getCodeType(String key) {
		for (CodeType codeType : ALL) {
			if (codeType.getKey().equals(key)) {
				return codeType;
			}
		}
		return null;
	}
}
