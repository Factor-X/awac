package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "indicatorcategory"))})
public class IndicatorCategoryCode extends Code {

	public static final IndicatorCategoryCode IC_1 = new IndicatorCategoryCode("IC_1");
	public static final IndicatorCategoryCode IC_2 = new IndicatorCategoryCode("IC_2");
	public static final IndicatorCategoryCode IC_3 = new IndicatorCategoryCode("IC_3");
	public static final IndicatorCategoryCode IC_4 = new IndicatorCategoryCode("IC_4");
	public static final IndicatorCategoryCode IC_5 = new IndicatorCategoryCode("IC_5");
	public static final IndicatorCategoryCode IC_6 = new IndicatorCategoryCode("IC_6");
	public static final IndicatorCategoryCode IC_7 = new IndicatorCategoryCode("IC_7");
	public static final IndicatorCategoryCode IC_8 = new IndicatorCategoryCode("IC_8");
	public static final IndicatorCategoryCode IC_9 = new IndicatorCategoryCode("IC_9");
	public static final IndicatorCategoryCode IC_10 = new IndicatorCategoryCode("IC_10");
	public static final IndicatorCategoryCode IC_11 = new IndicatorCategoryCode("IC_11");
	public static final IndicatorCategoryCode IC_12 = new IndicatorCategoryCode("IC_12");
	public static final IndicatorCategoryCode IC_13 = new IndicatorCategoryCode("IC_13");
	public static final IndicatorCategoryCode IC_14 = new IndicatorCategoryCode("IC_14");
	public static final IndicatorCategoryCode IC_15 = new IndicatorCategoryCode("IC_15");
	public static final IndicatorCategoryCode IC_16 = new IndicatorCategoryCode("IC_16");
	private static final long serialVersionUID = 1L;
	protected IndicatorCategoryCode() {
		super(CodeList.IndicatorCategory);
	}
	public IndicatorCategoryCode(String key) {
		this();
		this.key = key;
	}
}