package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "scope_iso"))})
public class IndicatorIsoScopeCode extends Code {

	public static final IndicatorIsoScopeCode OUT_OF_SCOPE = new IndicatorIsoScopeCode("0");
	public static final IndicatorIsoScopeCode SCOPE1 = new IndicatorIsoScopeCode("1");
	public static final IndicatorIsoScopeCode SCOPE2 = new IndicatorIsoScopeCode("2");
	public static final IndicatorIsoScopeCode SCOPE3 = new IndicatorIsoScopeCode("3");
	private static final long serialVersionUID = 1L;

	protected IndicatorIsoScopeCode() {
		super(CodeList.INDICATOR_ISO_SCOPE);
	}

	public IndicatorIsoScopeCode(String key) {
		this();
		this.key = key;
	}

}
