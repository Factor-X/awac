package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "carburant"))})
public class CARBURANTCode extends Code {

	public static final CARBURANTCode ESSENCE = new CARBURANTCode("1");
	public static final CARBURANTCode DIESEL = new CARBURANTCode("2");
	public static final CARBURANTCode BIODIESEL = new CARBURANTCode("3");
	public static final CARBURANTCode GAZ_DE_PRETROLE_LIQUEFIE_GPL = new CARBURANTCode("4");
	private static final long serialVersionUID = 1L;
	protected CARBURANTCode() {
		super(CodeList.CARBURANT);
	}
	public CARBURANTCode(String key) {
		this();
		this.key = key;
	}
}