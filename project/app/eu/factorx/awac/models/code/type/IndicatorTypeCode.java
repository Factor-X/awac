package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "indicatortype")) })
public class IndicatorTypeCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final IndicatorTypeCode CARBON = new IndicatorTypeCode("1");
	public static final IndicatorTypeCode WATER = new IndicatorTypeCode("2");

	protected IndicatorTypeCode() {
		super(CodeList.INDICATOR_TYPE);
	}

	public IndicatorTypeCode(String key) {
		this();
		this.key = key;
	}

}
