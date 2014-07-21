package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "pourcentsimplifie"))})
public class POURCENTSIMPLIFIECode extends Code {

	public static final POURCENTSIMPLIFIECode _0 = new POURCENTSIMPLIFIECode("1");
	public static final POURCENTSIMPLIFIECode _0_1 = new POURCENTSIMPLIFIECode("2");
	public static final POURCENTSIMPLIFIECode _0_2 = new POURCENTSIMPLIFIECode("3");
	public static final POURCENTSIMPLIFIECode _0_3 = new POURCENTSIMPLIFIECode("4");
	public static final POURCENTSIMPLIFIECode _0_4 = new POURCENTSIMPLIFIECode("5");
	public static final POURCENTSIMPLIFIECode _0_5 = new POURCENTSIMPLIFIECode("6");
	public static final POURCENTSIMPLIFIECode _0_6 = new POURCENTSIMPLIFIECode("7");
	public static final POURCENTSIMPLIFIECode _0_7 = new POURCENTSIMPLIFIECode("8");
	public static final POURCENTSIMPLIFIECode _0_8 = new POURCENTSIMPLIFIECode("9");
	public static final POURCENTSIMPLIFIECode _0_9 = new POURCENTSIMPLIFIECode("10");
	public static final POURCENTSIMPLIFIECode _1 = new POURCENTSIMPLIFIECode("11");
	private static final long serialVersionUID = 1L;
	protected POURCENTSIMPLIFIECode() {
		super(CodeList.POURCENTSIMPLIFIE);
	}
	public POURCENTSIMPLIFIECode(String key) {
		this();
		this.key = key;
	}
}