package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "reducingactiontype")) })
public class ReducingActionTypeCode extends Code {

	private static final long serialVersionUID = 8703399555593054064L;

	private static final CodeList CODE_TYPE = CodeList.REDUCING_ACTION_TYPE;

	private static final String REDUCING_GES_KEY = "1";
	private static final String BETTER_METHOD_KEY = "2";

	public static final ReducingActionTypeCode REDUCING_GES = new ReducingActionTypeCode(REDUCING_GES_KEY);
	public static final ReducingActionTypeCode BETTER_METHOD = new ReducingActionTypeCode(BETTER_METHOD_KEY);

	public static final ReducingActionTypeCode[] ALL = new ReducingActionTypeCode[] {REDUCING_GES, BETTER_METHOD};

	protected ReducingActionTypeCode() {
		super(CODE_TYPE);
	}

	public ReducingActionTypeCode(String key) {
		super(CODE_TYPE, key);
	}

	public static ReducingActionTypeCode valueOf(String key) {
		for (ReducingActionTypeCode code : ALL) {
			if (code.getKey().equals(key)) {
				return code;
			}
		}
		return null;
	}
}
