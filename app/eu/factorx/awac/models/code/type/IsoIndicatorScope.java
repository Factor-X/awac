package eu.factorx.awac.models.code.type;

import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

/**
 * ISO Scope (1, 2 or 3)
 *
 */
@Embeddable
public class IsoIndicatorScope extends Code {

	private static final long serialVersionUID = 1L;

	private static final CodeList CODE_TYPE = CodeList.INDICATOR_SCOPE;

	public static final IsoIndicatorScope OUT_OF_SCOPE = new IsoIndicatorScope("0");
	public static final IsoIndicatorScope SCOPE1 = new IsoIndicatorScope("1");
	public static final IsoIndicatorScope SCOPE2 = new IsoIndicatorScope("2");
	public static final IsoIndicatorScope SCOPE3 = new IsoIndicatorScope("3");

	protected IsoIndicatorScope() {
		super();
	}

	public IsoIndicatorScope(String key) {
		super(CODE_TYPE, key);
	}

}
