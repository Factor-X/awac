package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "nace3"))})
public class Nace3Code extends Code {

	private static final long serialVersionUID = 1L;

	public static final Nace3Code NACE_36 = new Nace3Code("36");
	public static final Nace3Code NACE_37 = new Nace3Code("37");
	public static final Nace3Code NACE_38 = new Nace3Code("38");
	public static final Nace3Code NACE_39 = new Nace3Code("39");
	public static final Nace3Code NACE_41 = new Nace3Code("41");
	public static final Nace3Code NACE_42 = new Nace3Code("42");
	public static final Nace3Code NACE_43 = new Nace3Code("43");
	public static final Nace3Code NACE_45 = new Nace3Code("45");
	public static final Nace3Code NACE_46 = new Nace3Code("46");
	public static final Nace3Code NACE_47 = new Nace3Code("47");
	public static final Nace3Code NACE_49 = new Nace3Code("49");
	public static final Nace3Code NACE_50 = new Nace3Code("50");
	public static final Nace3Code NACE_51 = new Nace3Code("51");
	public static final Nace3Code NACE_52 = new Nace3Code("52");
	public static final Nace3Code NACE_53 = new Nace3Code("53");
	public static final Nace3Code NACE_55 = new Nace3Code("55");
	public static final Nace3Code NACE_56 = new Nace3Code("56");
	public static final Nace3Code NACE_58 = new Nace3Code("58");
	public static final Nace3Code NACE_59 = new Nace3Code("59");
	public static final Nace3Code NACE_60 = new Nace3Code("60");
	public static final Nace3Code NACE_61 = new Nace3Code("61");
	public static final Nace3Code NACE_62 = new Nace3Code("62");
	public static final Nace3Code NACE_63 = new Nace3Code("63");
	public static final Nace3Code NACE_64 = new Nace3Code("64");
	public static final Nace3Code NACE_65 = new Nace3Code("65");
	public static final Nace3Code NACE_66 = new Nace3Code("66");
	public static final Nace3Code NACE_68 = new Nace3Code("68");
	public static final Nace3Code NACE_69 = new Nace3Code("69");
	public static final Nace3Code NACE_70 = new Nace3Code("70");
	public static final Nace3Code NACE_71 = new Nace3Code("71");
	public static final Nace3Code NACE_72 = new Nace3Code("72");
	public static final Nace3Code NACE_73 = new Nace3Code("73");
	public static final Nace3Code NACE_74 = new Nace3Code("74");
	public static final Nace3Code NACE_75 = new Nace3Code("75");
	public static final Nace3Code NACE_77 = new Nace3Code("77");
	public static final Nace3Code NACE_78 = new Nace3Code("78");
	public static final Nace3Code NACE_79 = new Nace3Code("79");
	public static final Nace3Code NACE_80 = new Nace3Code("80");
	public static final Nace3Code NACE_81 = new Nace3Code("81");
	public static final Nace3Code NACE_82 = new Nace3Code("82");
	public static final Nace3Code NACE_84 = new Nace3Code("84");
	public static final Nace3Code NACE_85 = new Nace3Code("85");
	public static final Nace3Code NACE_86 = new Nace3Code("86");
	public static final Nace3Code NACE_87 = new Nace3Code("87");
	public static final Nace3Code NACE_88 = new Nace3Code("88");
	public static final Nace3Code NACE_90 = new Nace3Code("90");
	public static final Nace3Code NACE_91 = new Nace3Code("91");
	public static final Nace3Code NACE_92 = new Nace3Code("92");
	public static final Nace3Code NACE_93 = new Nace3Code("93");
	public static final Nace3Code NACE_94 = new Nace3Code("94");
	public static final Nace3Code NACE_95 = new Nace3Code("95");
	public static final Nace3Code NACE_96 = new Nace3Code("96");
	public static final Nace3Code NACE_97 = new Nace3Code("97");
	public static final Nace3Code NACE_98 = new Nace3Code("98");
	public static final Nace3Code NACE_99 = new Nace3Code("99");

	protected Nace3Code() {
		super(CodeList.NACE_CODES_3);
	}

	public Nace3Code(String key) {
		this();
		this.key = key;
	}
}
