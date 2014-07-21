package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "secteurtype"))})
public class SECTEURTYPECode extends Code {

	public static final SECTEURTYPECode PUBLIC = new SECTEURTYPECode("1");
	public static final SECTEURTYPECode PRIVE = new SECTEURTYPECode("2");
	private static final long serialVersionUID = 1L;

	protected SECTEURTYPECode() {
		super(CodeList.SECTEURTYPE);
	}
	public SECTEURTYPECode(String key) {
		this();
		this.key = key;
	}
}