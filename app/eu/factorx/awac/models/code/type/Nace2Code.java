package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "nace2")) })
public class Nace2Code extends Code {

	private static final long serialVersionUID = 1L;

	public static final Nace2Code NACE_10 = new Nace2Code("10");
	public static final Nace2Code NACE_11 = new Nace2Code("11");
	public static final Nace2Code NACE_12 = new Nace2Code("12");
	public static final Nace2Code NACE_13 = new Nace2Code("13");
	public static final Nace2Code NACE_14 = new Nace2Code("14");
	public static final Nace2Code NACE_15 = new Nace2Code("15");
	public static final Nace2Code NACE_16 = new Nace2Code("16");
	public static final Nace2Code NACE_17 = new Nace2Code("17");
	public static final Nace2Code NACE_18 = new Nace2Code("18");
	public static final Nace2Code NACE_19 = new Nace2Code("19");
	public static final Nace2Code NACE_20 = new Nace2Code("20");
	public static final Nace2Code NACE_21 = new Nace2Code("21");
	public static final Nace2Code NACE_22 = new Nace2Code("22");
	public static final Nace2Code NACE_23 = new Nace2Code("23");
	public static final Nace2Code NACE_24 = new Nace2Code("24");
	public static final Nace2Code NACE_25 = new Nace2Code("25");
	public static final Nace2Code NACE_26 = new Nace2Code("26");
	public static final Nace2Code NACE_27 = new Nace2Code("27");
	public static final Nace2Code NACE_28 = new Nace2Code("28");
	public static final Nace2Code NACE_29 = new Nace2Code("29");
	public static final Nace2Code NACE_30 = new Nace2Code("30");
	public static final Nace2Code NACE_31 = new Nace2Code("31");
	public static final Nace2Code NACE_32 = new Nace2Code("32");
	public static final Nace2Code NACE_33 = new Nace2Code("33");
	public static final Nace2Code NACE_35 = new Nace2Code("35");

	protected Nace2Code() {
		super(CodeList.NACE_CODES_2);
	}

	public Nace2Code(String key) {
		this();
		this.key = key;
	}
}
