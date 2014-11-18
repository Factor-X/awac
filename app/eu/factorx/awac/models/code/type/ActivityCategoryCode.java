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

	public static final ActivityCategoryCode AC_1 = new ActivityCategoryCode("AC_1");
	public static final ActivityCategoryCode AC_2 = new ActivityCategoryCode("AC_2");
	public static final ActivityCategoryCode AC_3 = new ActivityCategoryCode("AC_3");
	public static final ActivityCategoryCode AC_4 = new ActivityCategoryCode("AC_4");
	public static final ActivityCategoryCode AC_5 = new ActivityCategoryCode("AC_5");
	public static final ActivityCategoryCode AC_6 = new ActivityCategoryCode("AC_6");
	public static final ActivityCategoryCode AC_7 = new ActivityCategoryCode("AC_7");
	public static final ActivityCategoryCode AC_8 = new ActivityCategoryCode("AC_8");
	public static final ActivityCategoryCode AC_9 = new ActivityCategoryCode("AC_9");
	public static final ActivityCategoryCode AC_10 = new ActivityCategoryCode("AC_10");
	public static final ActivityCategoryCode AC_11 = new ActivityCategoryCode("AC_11");
	public static final ActivityCategoryCode AC_12 = new ActivityCategoryCode("AC_12");
	public static final ActivityCategoryCode AC_13 = new ActivityCategoryCode("AC_13");
	public static final ActivityCategoryCode AC_14 = new ActivityCategoryCode("AC_14");
	public static final ActivityCategoryCode AC_15 = new ActivityCategoryCode("AC_15");
	public static final ActivityCategoryCode AC_16 = new ActivityCategoryCode("AC_16");
    public static final ActivityCategoryCode AC_17 = new ActivityCategoryCode("AC_17");
    public static final ActivityCategoryCode AC_18 = new ActivityCategoryCode("AC_18");
    public static final ActivityCategoryCode AC_19 = new ActivityCategoryCode("AC_19");
	
    private static final long serialVersionUID = 1L;
	protected ActivityCategoryCode() {
		super(CodeList.ActivityCategory);
	}
	public ActivityCategoryCode(String key) {
		this();
		this.key = key;
	}
}