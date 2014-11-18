package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "code"))})
public class IndicatorCode extends Code {

	private static final long serialVersionUID = 1L;

	protected IndicatorCode() {
		super(CodeList.INDICATOR);
	}

	public IndicatorCode(String key) {
		this();
		this.key = key;
	}

}
