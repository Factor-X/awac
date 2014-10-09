package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "reducingactiontype"))})
public class ReducingActionType extends Code {

	private static final long serialVersionUID = 8703399555593054064L;

	public static final CodeList CODE_TYPE = CodeList.REDUCING_ACTION_TYPE;

	private static final String BETTER_METHOD_CODE = "1";
	private static final String REDUCING_GES_CODE = "2";

	public static final ReducingActionType BETTER_METHOD = new ReducingActionType(BETTER_METHOD_CODE);
	public static final ReducingActionType REDUCING_GES = new ReducingActionType(REDUCING_GES_CODE);
	
	protected ReducingActionType() {
		super(CODE_TYPE);
	}

	public ReducingActionType(String key) {
		super(CODE_TYPE, key);
	}

}
