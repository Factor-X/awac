package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

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
	private static final long serialVersionUID = 1L;
	protected ActivitySubCategoryCode() {
		super(CodeList.ActivitySubCategory);
	}
	public ActivitySubCategoryCode(String key) {
		this();
		this.key = key;
	}
}