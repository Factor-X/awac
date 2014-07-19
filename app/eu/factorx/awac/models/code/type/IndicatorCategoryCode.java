package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "indicatorcategory"))})
public class IndicatorCategoryCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final IndicatorCategoryCode USAGE_ENERGIE_FOSSILE = new IndicatorCategoryCode("1");
	public static final IndicatorCategoryCode OUTSIDE_SCOPE_COMBUSTIBLES_ORGANIQUES = new IndicatorCategoryCode("2");
	public static final IndicatorCategoryCode PRODUCTION_ENERGIE_FOSSILE = new IndicatorCategoryCode("3");
	public static final IndicatorCategoryCode ACHATS_ELECTRICITE = new IndicatorCategoryCode("4");
	public static final IndicatorCategoryCode PRODUCTION_ELECTRICITE_ACHETEE = new IndicatorCategoryCode("5");
	public static final IndicatorCategoryCode PERTES_ELECTRIQUES = new IndicatorCategoryCode("6");
	public static final IndicatorCategoryCode ACHATS_DE_VAPEUR = new IndicatorCategoryCode("7");
	public static final IndicatorCategoryCode GENERATION_DE_VAPEUR = new IndicatorCategoryCode("8");
	public static final IndicatorCategoryCode EMISSION_DIRECTE = new IndicatorCategoryCode("9");
	public static final IndicatorCategoryCode DECHET = new IndicatorCategoryCode("10");
	public static final IndicatorCategoryCode PRODUCTION_DE_MATIERE = new IndicatorCategoryCode("11");
	public static final IndicatorCategoryCode DIRECT_CO2E = new IndicatorCategoryCode("12");
	public static final IndicatorCategoryCode PERTES_DE_VAPEUR = new IndicatorCategoryCode("13");
	public static final IndicatorCategoryCode AMORTISSEMENT_VEHICULE_SOCIETE = new IndicatorCategoryCode("14");
	public static final IndicatorCategoryCode AMORTISSEMENT_VEHICULE_NON_SOCIETE = new IndicatorCategoryCode("15");

	protected IndicatorCategoryCode() {
		super(CodeList.INDICATOR_CATEGORY);
	}

	public IndicatorCategoryCode(String key) {
		this();
		this.key = key;
	}

}
