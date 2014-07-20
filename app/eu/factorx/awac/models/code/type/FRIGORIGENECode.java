package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class FRIGORIGENECode extends Code {

	public static final FRIGORIGENECode DIOXIDE_DE_CARBONE_CO2 = new FRIGORIGENECode("1");
	public static final FRIGORIGENECode METHANE_NH4 = new FRIGORIGENECode("2");
	public static final FRIGORIGENECode PROTOXYDE_D_AZOTE_N2O = new FRIGORIGENECode("3");
	public static final FRIGORIGENECode HFC_23 = new FRIGORIGENECode("4");
	public static final FRIGORIGENECode HFC_32 = new FRIGORIGENECode("5");
	public static final FRIGORIGENECode HFC_41 = new FRIGORIGENECode("6");
	public static final FRIGORIGENECode HFC_125 = new FRIGORIGENECode("7");
	public static final FRIGORIGENECode HFC_134 = new FRIGORIGENECode("8");
	public static final FRIGORIGENECode R134A = new FRIGORIGENECode("9");
	public static final FRIGORIGENECode HFC_143 = new FRIGORIGENECode("10");
	public static final FRIGORIGENECode HFC_143A = new FRIGORIGENECode("11");
	public static final FRIGORIGENECode HFC_152A = new FRIGORIGENECode("12");
	public static final FRIGORIGENECode HFC_227EA = new FRIGORIGENECode("13");
	public static final FRIGORIGENECode HFC_236FA = new FRIGORIGENECode("14");
	public static final FRIGORIGENECode HFC_245FA = new FRIGORIGENECode("15");
	public static final FRIGORIGENECode HFC_43_I0MEE = new FRIGORIGENECode("16");
	public static final FRIGORIGENECode PERFLUOROMETHANE_PFC_14 = new FRIGORIGENECode("17");
	public static final FRIGORIGENECode PERFLUOROETHANE_PFC_116 = new FRIGORIGENECode("18");
	public static final FRIGORIGENECode PERFLUOROPROPANE_PFC_218 = new FRIGORIGENECode("19");
	public static final FRIGORIGENECode PERFLUOROCYCLOBUTANE_PFC_318 = new FRIGORIGENECode("20");
	public static final FRIGORIGENECode PERFLUOROBUTANE_PFC_3_1_10 = new FRIGORIGENECode("21");
	public static final FRIGORIGENECode PERFLUOROPENTANE_PFC_4_1_12 = new FRIGORIGENECode("22");
	public static final FRIGORIGENECode PERFLUOROHEXANE_PFC_5_1_14 = new FRIGORIGENECode("23");
	public static final FRIGORIGENECode HEXAFLUORURE_DE_SOUFFRE_SF6 = new FRIGORIGENECode("24");
	public static final FRIGORIGENECode _404A = new FRIGORIGENECode("25");
	public static final FRIGORIGENECode _407C = new FRIGORIGENECode("26");
	public static final FRIGORIGENECode R408A = new FRIGORIGENECode("27");
	public static final FRIGORIGENECode _410A = new FRIGORIGENECode("28");
	public static final FRIGORIGENECode _413A = new FRIGORIGENECode("29");
	public static final FRIGORIGENECode R417A = new FRIGORIGENECode("30");
	public static final FRIGORIGENECode R422D = new FRIGORIGENECode("31");
	public static final FRIGORIGENECode R427A = new FRIGORIGENECode("32");
	public static final FRIGORIGENECode R508B = new FRIGORIGENECode("33");
	public static final FRIGORIGENECode R507 = new FRIGORIGENECode("34");
	public static final FRIGORIGENECode ISCEON89 = new FRIGORIGENECode("35");
	public static final FRIGORIGENECode R22 = new FRIGORIGENECode("36");
	public static final FRIGORIGENECode R401A = new FRIGORIGENECode("37");
	public static final FRIGORIGENECode R402A = new FRIGORIGENECode("38");
	public static final FRIGORIGENECode _403B = new FRIGORIGENECode("39");
	private static final long serialVersionUID = 1L;
	protected FRIGORIGENECode() {
		super(CodeList.FRIGORIGENE);
	}
	public FRIGORIGENECode(String key) {
		this();
		this.key = key;
	}
}