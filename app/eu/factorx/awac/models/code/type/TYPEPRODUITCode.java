package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "typeproduit"))})
public class TYPEPRODUITCode extends Code {

	public static final TYPEPRODUITCode FINAL = new TYPEPRODUITCode("1");
	public static final TYPEPRODUITCode INTERMEDIAIRE = new TYPEPRODUITCode("2");
	private static final long serialVersionUID = 1L;

	protected TYPEPRODUITCode() {
		super(CodeList.TYPEPRODUIT);
	}
	public TYPEPRODUITCode(String key) {
		this();
		this.key = key;
	}
}