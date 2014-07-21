
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "gessimplifie"))})
public class GESSIMPLIFIECode extends Code {

    private static final long serialVersionUID = 1L;

    protected GESSIMPLIFIECode() {
        super(CodeList.GESSIMPLIFIE);
    }

    public GESSIMPLIFIECode(String key) {
        this();
        this.key = key;
    }
public static final GESSIMPLIFIECode DIOXIDE_DE_CARBONE_CO2 = new GESSIMPLIFIECode("1");
public static final GESSIMPLIFIECode METHANE_NH4 = new GESSIMPLIFIECode("2");
public static final GESSIMPLIFIECode PROTOXYDE_D_AZOTE_N2O = new GESSIMPLIFIECode("3");
public static final GESSIMPLIFIECode HFC_23 = new GESSIMPLIFIECode("4");
public static final GESSIMPLIFIECode HFC_32 = new GESSIMPLIFIECode("5");
public static final GESSIMPLIFIECode HFC_41 = new GESSIMPLIFIECode("6");
public static final GESSIMPLIFIECode HFC_125 = new GESSIMPLIFIECode("7");
public static final GESSIMPLIFIECode HFC_134 = new GESSIMPLIFIECode("8");
public static final GESSIMPLIFIECode R134A = new GESSIMPLIFIECode("9");
public static final GESSIMPLIFIECode HFC_143 = new GESSIMPLIFIECode("10");
public static final GESSIMPLIFIECode HFC_143A = new GESSIMPLIFIECode("11");
public static final GESSIMPLIFIECode HFC_152A = new GESSIMPLIFIECode("12");
public static final GESSIMPLIFIECode HFC_227EA = new GESSIMPLIFIECode("13");
public static final GESSIMPLIFIECode HFC_236FA = new GESSIMPLIFIECode("14");
public static final GESSIMPLIFIECode HFC_245FA = new GESSIMPLIFIECode("15");
public static final GESSIMPLIFIECode HFC_43_I0MEE = new GESSIMPLIFIECode("16");
public static final GESSIMPLIFIECode PERFLUOROMETHANE_PFC_14 = new GESSIMPLIFIECode("17");
public static final GESSIMPLIFIECode PERFLUOROETHANE_PFC_116 = new GESSIMPLIFIECode("18");
public static final GESSIMPLIFIECode PERFLUOROPROPANE_PFC_218 = new GESSIMPLIFIECode("19");
public static final GESSIMPLIFIECode PERFLUOROCYCLOBUTANE_PFC_318 = new GESSIMPLIFIECode("20");
public static final GESSIMPLIFIECode PERFLUOROBUTANE_PFC_3_1_10 = new GESSIMPLIFIECode("21");
public static final GESSIMPLIFIECode PERFLUOROPENTANE_PFC_4_1_12 = new GESSIMPLIFIECode("22");
public static final GESSIMPLIFIECode PERFLUOROHEXANE_PFC_5_1_14 = new GESSIMPLIFIECode("23");
public static final GESSIMPLIFIECode HEXAFLUORURE_DE_SOUFFRE_SF6 = new GESSIMPLIFIECode("24");
public static final GESSIMPLIFIECode _404A = new GESSIMPLIFIECode("25");
public static final GESSIMPLIFIECode _407C = new GESSIMPLIFIECode("26");
public static final GESSIMPLIFIECode R408A = new GESSIMPLIFIECode("27");
public static final GESSIMPLIFIECode _410A = new GESSIMPLIFIECode("28");
public static final GESSIMPLIFIECode _413A = new GESSIMPLIFIECode("29");
public static final GESSIMPLIFIECode R417A = new GESSIMPLIFIECode("30");
public static final GESSIMPLIFIECode R422D = new GESSIMPLIFIECode("31");
public static final GESSIMPLIFIECode R427A = new GESSIMPLIFIECode("32");
public static final GESSIMPLIFIECode R508B = new GESSIMPLIFIECode("33");
public static final GESSIMPLIFIECode R507 = new GESSIMPLIFIECode("34");
public static final GESSIMPLIFIECode ISCEON89 = new GESSIMPLIFIECode("35");
public static final GESSIMPLIFIECode R22 = new GESSIMPLIFIECode("36");
public static final GESSIMPLIFIECode R401A = new GESSIMPLIFIECode("37");
public static final GESSIMPLIFIECode R402A = new GESSIMPLIFIECode("38");
public static final GESSIMPLIFIECode _403B = new GESSIMPLIFIECode("39");
public static final GESSIMPLIFIECode _409A = new GESSIMPLIFIECode("40");}