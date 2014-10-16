package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "reducingactionstatus")) })
public class ReducingActionStatusCode extends Code {

	private static final long serialVersionUID = -6520916771457529793L;

	private static final CodeList CODE_TYPE = CodeList.REDUCING_ACTION_STATUS;

	private static final String RUNNING_KEY = "1";
	private static final String DONE_KEY = "2";
	private static final String CANCELED_KEY = "3";

	public static final ReducingActionStatusCode RUNNING = new ReducingActionStatusCode(RUNNING_KEY);
	public static final ReducingActionStatusCode DONE = new ReducingActionStatusCode(DONE_KEY);
	public static final ReducingActionStatusCode CANCELED = new ReducingActionStatusCode(CANCELED_KEY);

	public static final ReducingActionStatusCode[] ALL = new ReducingActionStatusCode[] {RUNNING, DONE, CANCELED}; 

	protected ReducingActionStatusCode() {
		super(CODE_TYPE);
	}

	public ReducingActionStatusCode(String key) {
		super(CODE_TYPE, key);
	}

	public static ReducingActionStatusCode valueOf(String key) {
		for (ReducingActionStatusCode code : ALL) {
			if (code.getKey().equals(key)) {
				return code;
			}
		}
		return null;
	}
}
