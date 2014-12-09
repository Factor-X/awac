package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "activitysubcategory"))})
public class ActivitySubCategoryCode extends Code {

	public static final ActivitySubCategoryCode ASC_1 = new ActivitySubCategoryCode("ASC_1");
	public static final ActivitySubCategoryCode ASC_2 = new ActivitySubCategoryCode("ASC_2");
	public static final ActivitySubCategoryCode ASC_3 = new ActivitySubCategoryCode("ASC_3");
	public static final ActivitySubCategoryCode ASC_4 = new ActivitySubCategoryCode("ASC_4");
	public static final ActivitySubCategoryCode ASC_5 = new ActivitySubCategoryCode("ASC_5");
	public static final ActivitySubCategoryCode ASC_6 = new ActivitySubCategoryCode("ASC_6");
	public static final ActivitySubCategoryCode ASC_7 = new ActivitySubCategoryCode("ASC_7");
	public static final ActivitySubCategoryCode ASC_8 = new ActivitySubCategoryCode("ASC_8");
	public static final ActivitySubCategoryCode ASC_9 = new ActivitySubCategoryCode("ASC_9");
	public static final ActivitySubCategoryCode ASC_10 = new ActivitySubCategoryCode("ASC_10");
	public static final ActivitySubCategoryCode ASC_11 = new ActivitySubCategoryCode("ASC_11");
	public static final ActivitySubCategoryCode ASC_12 = new ActivitySubCategoryCode("ASC_12");
    public static final ActivitySubCategoryCode ASC_13 = new ActivitySubCategoryCode("ASC_13");
    public static final ActivitySubCategoryCode ASC_14 = new ActivitySubCategoryCode("ASC_14");
    public static final ActivitySubCategoryCode ASC_15 = new ActivitySubCategoryCode("ASC_15");
    public static final ActivitySubCategoryCode ASC_16 = new ActivitySubCategoryCode("ASC_16");
    public static final ActivitySubCategoryCode ASC_17 = new ActivitySubCategoryCode("ASC_17");
    public static final ActivitySubCategoryCode ASC_18 = new ActivitySubCategoryCode("ASC_18");
    public static final ActivitySubCategoryCode ASC_19 = new ActivitySubCategoryCode("ASC_19");
    public static final ActivitySubCategoryCode ASC_20 = new ActivitySubCategoryCode("ASC_20");
    public static final ActivitySubCategoryCode ASC_21 = new ActivitySubCategoryCode("ASC_21");
    public static final ActivitySubCategoryCode ASC_22 = new ActivitySubCategoryCode("ASC_22");
    public static final ActivitySubCategoryCode ASC_23 = new ActivitySubCategoryCode("ASC_23");
	private static final long serialVersionUID = 1L;
	protected ActivitySubCategoryCode() {
		super(CodeList.ActivitySubCategory);
	}
	public ActivitySubCategoryCode(String key) {
		this();
		this.key = key;
	}
}