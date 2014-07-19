package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "activitycategory"))})
public class ActivityCategoryCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final ActivityCategoryCode ENERGIE = new ActivityCategoryCode("1");
	public static final ActivityCategoryCode EMISSION = new ActivityCategoryCode("2");
	public static final ActivityCategoryCode FROID = new ActivityCategoryCode("3");
	public static final ActivityCategoryCode TRANSPORT = new ActivityCategoryCode("4");
	public static final ActivityCategoryCode MOBILITE = new ActivityCategoryCode("5");
	public static final ActivityCategoryCode STOCKAGE_AMONT = new ActivityCategoryCode("6");
	public static final ActivityCategoryCode DECHET = new ActivityCategoryCode("7");
	public static final ActivityCategoryCode ACHAT = new ActivityCategoryCode("8");
	public static final ActivityCategoryCode INFRASTRUCTURE = new ActivityCategoryCode("9");
	public static final ActivityCategoryCode STOCKAGE_AVAL = new ActivityCategoryCode("10");
	public static final ActivityCategoryCode TRAITEMENT = new ActivityCategoryCode("11");
	public static final ActivityCategoryCode UTILISATION = new ActivityCategoryCode("12");
	public static final ActivityCategoryCode FIN_DE_VIE = new ActivityCategoryCode("13");
	public static final ActivityCategoryCode ACTIF_LOUE = new ActivityCategoryCode("14");
	public static final ActivityCategoryCode FRANCHISE = new ActivityCategoryCode("15");
	public static final ActivityCategoryCode INVESTISSEMENT_SCOPE_1 = new ActivityCategoryCode("16");
	public static final ActivityCategoryCode INVESTISSEMENT_SCOPE_2 = new ActivityCategoryCode("17");

	protected ActivityCategoryCode() {
		super(CodeList.ACTIVITY_CATEGORY);
	}

	public ActivityCategoryCode(String key) {
		this();
		this.key = key;
	}

}
