package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "unitcategory")) })
public class UnitCategoryCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final UnitCategoryCode LENGTH = new UnitCategoryCode("UC5002");
	public static final UnitCategoryCode AREA = new UnitCategoryCode("UC5003");
	public static final UnitCategoryCode VOLUME = new UnitCategoryCode("UC5004");
	public static final UnitCategoryCode MASS = new UnitCategoryCode("UC5005");
	public static final UnitCategoryCode DENSITY = new UnitCategoryCode("UC5006");
	public static final UnitCategoryCode DURATION = new UnitCategoryCode("UC5007");
	public static final UnitCategoryCode FREQUENCY = new UnitCategoryCode("UC5008");
	public static final UnitCategoryCode SPEED = new UnitCategoryCode("UC5009");
	public static final UnitCategoryCode ENERGY = new UnitCategoryCode("UC5010");
	public static final UnitCategoryCode POWER = new UnitCategoryCode("UC5011");
	public static final UnitCategoryCode VOLTAGE = new UnitCategoryCode("UC5012");
	public static final UnitCategoryCode TEMPERATURE = new UnitCategoryCode("UC5013");
	public static final UnitCategoryCode CURRENCY = new UnitCategoryCode("UC5014");
	public static final UnitCategoryCode WATER_CONSUMPTION = new UnitCategoryCode("UC5015");
	public static final UnitCategoryCode GWP = new UnitCategoryCode("UC5016");
	public static final UnitCategoryCode TIME = new UnitCategoryCode("UC5017");

	protected UnitCategoryCode() {
		super(CodeList.UNIT_CATEGORY);
	}

	public UnitCategoryCode(String key) {
		this();
		this.key = key;
	}

}
