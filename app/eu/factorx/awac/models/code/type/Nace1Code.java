package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "nace1"))})
public class Nace1Code extends Code {

	public static final Nace1Code NACE_05 = new Nace1Code("05");
	public static final Nace1Code NACE_06 = new Nace1Code("06");
	public static final Nace1Code NACE_07 = new Nace1Code("07");
	public static final Nace1Code NACE_08 = new Nace1Code("08");
	public static final Nace1Code NACE_09 = new Nace1Code("09");
	private static final long serialVersionUID = 1L;

	protected Nace1Code() {
		super(CodeList.SECTEURPRIMAIRE);
	}

	public Nace1Code(String key) {
		this();
		this.key = key;
	}
}
