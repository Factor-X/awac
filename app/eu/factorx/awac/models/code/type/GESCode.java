
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "ges"))})
public class GESCode extends Code {

    private static final long serialVersionUID = 1L;

    protected GESCode() {
        super(CodeList.GES);
    }

    public GESCode(String key) {
        this();
        this.key = key;
    }
public static final GESCode DIOXIDE_DE_CARBONE_CO2 = new GESCode("1");
public static final GESCode METHANE_CH4 = new GESCode("2");
public static final GESCode OXIDE_NITREUX_N2O = new GESCode("3");
public static final GESCode HFC_23_CHF3 = new GESCode("4");
public static final GESCode HFC_32_CH2F2 = new GESCode("5");
public static final GESCode HFC_41_CH3F = new GESCode("6");
public static final GESCode HFC_125_CHF2CF3 = new GESCode("7");
public static final GESCode HFC_134_CHF2CHF2 = new GESCode("8");
public static final GESCode HFC_134A_CH2FCF3 = new GESCode("9");
public static final GESCode HFC_143_CH3CF3 = new GESCode("10");
public static final GESCode HFC_143A_CH3CHF2 = new GESCode("11");
public static final GESCode HFC_152A_CF3CHFCF3 = new GESCode("12");
public static final GESCode HFC_227EA_CF3CH2CF3 = new GESCode("13");
public static final GESCode HFC_236FA_CHF2CH2CF3 = new GESCode("14");
public static final GESCode HFC_245FA_CH3CF2CH2CF3 = new GESCode("15");
public static final GESCode HFC_43_I0MEE_CF3CHFCHFCF2CF3 = new GESCode("16");
public static final GESCode PERFLUOROMETHANE_PFC_14_CF4 = new GESCode("17");
public static final GESCode PERFLUOROETHANE_PFC_116_C2F6 = new GESCode("18");
public static final GESCode PERFLUOROPROPANE_PFC_218_C3F8 = new GESCode("19");
public static final GESCode PERFLUOROCYCLOBUTANE_PFC_318_C_C4F8 = new GESCode("20");
public static final GESCode PERFLUOROBUTANE_PFC_3_1_10_C4F10 = new GESCode("21");
public static final GESCode PERFLUOROPENTANE_PFC_4_1_12_C5F12 = new GESCode("22");
public static final GESCode PERFLUOROHEXANE_PFC_5_1_14_C6F14 = new GESCode("23");
public static final GESCode HEXAFLUORURE_DE_SOUFFRE_SF6 = new GESCode("24");
public static final GESCode R404A_52_44_4_MIX_DE_HFC_143A_125_ET_134A = new GESCode("25");
public static final GESCode R407C_23_25_52_MIX_DE_HFC_32_125_ET_134A = new GESCode("26");
public static final GESCode R408A_47_7_46_MIX_DE_HCFC_22_HFC_125_ET_HFC_143A = new GESCode("27");
public static final GESCode R410A_50_50_MIX_DE_HFC_32_ET_125 = new GESCode("28");
public static final GESCode R507_50_50_MIX_DE_HFC_125_ET_HFC_143A = new GESCode("29");
public static final GESCode R508B_46_54_MIX_DE_HFC_23_ET_PFC_116 = new GESCode("30");
public static final GESCode CFC_11_R11_TRICHLOROFLUOROMETHANE_CCL3F = new GESCode("31");
public static final GESCode CFC_12_R12_DICHLORODIFLUOROMETHANE_CCL2F2 = new GESCode("32");
public static final GESCode CFC_13_CCLF3 = new GESCode("33");
public static final GESCode CFC_113_CCL2FCCLF2 = new GESCode("34");
public static final GESCode CFC_114_CCLF2CCLF2 = new GESCode("35");
public static final GESCode CFC_115_CCLF2CF3 = new GESCode("36");
public static final GESCode HALON_1211_CBRCLF2 = new GESCode("37");
public static final GESCode HALON_1301_CBRF3 = new GESCode("38");
public static final GESCode HALON_2402_CBRF2CBRF2 = new GESCode("39");
public static final GESCode CARBON_TETRACHLORIDE_CCL4 = new GESCode("40");
public static final GESCode METHYL_BROMIDE_CH3BR = new GESCode("41");
public static final GESCode METHYL_CHLOROFORM_CH3CCL3 = new GESCode("42");
public static final GESCode HCFC_22_R22_CHLORODIFLUOROMETHANE_CHCLF2 = new GESCode("43");
public static final GESCode HCFC_123_CHCL2CF3 = new GESCode("44");
public static final GESCode HCFC_124_CHCLFCF3 = new GESCode("45");
public static final GESCode HCFC_141B_CH3CCL2F = new GESCode("46");
public static final GESCode HCFC_142B_CH3CCLF2 = new GESCode("47");
public static final GESCode HCFC_225CA_CHCL2CF2CF3 = new GESCode("48");
public static final GESCode HCFC_225CB_CHCLFCF2CCLF2 = new GESCode("49");
public static final GESCode NITROGEN_TRIFLUORIDE_NF3 = new GESCode("50");
public static final GESCode PFC_4_1_12_C5F12 = new GESCode("51");
public static final GESCode PFC_9_1_18_C10F18 = new GESCode("52");
public static final GESCode TRIFLUOROMETHYL_PENTAFLUORURE_DE_SOUFFRE_SF5CF3 = new GESCode("53");
public static final GESCode HFE_125_CHF2OCF3 = new GESCode("54");
public static final GESCode HFE_134_CHF2OCHF2 = new GESCode("55");
public static final GESCode HFE_143A_CH3OCF3 = new GESCode("56");
public static final GESCode HCFE_235DA2_CHF2OCHCLCF3 = new GESCode("57");
public static final GESCode HFE_245CB2_CH3OCF2CHF2 = new GESCode("58");
public static final GESCode HFE_245FA2_CHF2OCH2CF3 = new GESCode("59");
public static final GESCode HFE_254CB2_CH3OCF2CHF2 = new GESCode("60");
public static final GESCode HFE_347MCC3_CH3OCF2CF2CF3 = new GESCode("61");
public static final GESCode HFE_347PCF2_CHF2CF2OCH2CF3 = new GESCode("62");
public static final GESCode HFE_356PCC3_CH3OCF2CF2CHF2 = new GESCode("63");
public static final GESCode HFE_449SL_HFE_7100_C4F9OCH3 = new GESCode("64");
public static final GESCode HFE_569SF2_HFE_7200_C4F9OC2H5 = new GESCode("65");
public static final GESCode HFE_43_10PCCC124_H_GALDEN1040X_CHF2OCF2OC2F4OCHF2 = new GESCode("66");
public static final GESCode HFE_236CA12_HG_10_CHF2OCF2OCHF2 = new GESCode("67");
public static final GESCode HFE_338PCC13_HG_01_CHF2OCF2CF2OCHF2 = new GESCode("68");
public static final GESCode PFPMIE_CF3OCF_CF3_CF2OCF2OCF3 = new GESCode("69");
public static final GESCode DIMETHYLETHER_CH3OCH3 = new GESCode("70");
public static final GESCode METHYLENE_CHLORIDE_CH2CL2 = new GESCode("71");
public static final GESCode METHYL_CHLORIDE_CH3CL = new GESCode("72");
public static final GESCode R290_PROPANE_C3H8 = new GESCode("73");
public static final GESCode R600A_ISOBUTANE_C4H10 = new GESCode("74");
public static final GESCode R406A_55_41_4_MIX_DE_HCFC_22_HCFC_142B_ET_R600A = new GESCode("75");
public static final GESCode R409A_60_25_15_MIX_DE_HCFC_22_HCFC_124_ET_HCFC_142B = new GESCode("76");
public static final GESCode R502_48_8_51_2_MIX_DE_OF_HCFC_22_ET_CFC_115 = new GESCode("77");}