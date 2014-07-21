package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "traitementdechet"))})
public class TRAITEMENTDECHETCode extends Code {

	public static final TRAITEMENTDECHETCode INCINERATION = new TRAITEMENTDECHETCode("1");
	public static final TRAITEMENTDECHETCode CET_AVEC_VALORISATION = new TRAITEMENTDECHETCode("2");
	public static final TRAITEMENTDECHETCode RECYCLAGE = new TRAITEMENTDECHETCode("3");
	public static final TRAITEMENTDECHETCode INCONNU = new TRAITEMENTDECHETCode("4");
	private static final long serialVersionUID = 1L;
	protected TRAITEMENTDECHETCode() {
		super(CodeList.TRAITEMENTDECHET);
	}
	public TRAITEMENTDECHETCode(String key) {
		this();
		this.key = key;
	}
}