package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "reducingactionstatus")) })
public class ReducingActionStatus extends Code {

	private static final long serialVersionUID = -6520916771457529793L;

	public static final CodeList CODE_TYPE = CodeList.REDUCING_ACTION_STATUS;

	private static final String RUNNING_CODE = "1";
	private static final String DONE_CODE = "2";
	private static final String CANCELED_CODE = "3";

	public static final ReducingActionStatus RUNNING = new ReducingActionStatus(RUNNING_CODE);
	public static final ReducingActionStatus DONE = new ReducingActionStatus(DONE_CODE);
	public static final ReducingActionStatus CANCELED = new ReducingActionStatus(CANCELED_CODE);

	protected ReducingActionStatus() {
		super(CODE_TYPE);
	}

	public ReducingActionStatus(String key) {
		super(CODE_TYPE, key);
	}

}
