package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "activitysector"))})
public class ActivitySectorCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final ActivitySectorCode PRIMARY = new ActivitySectorCode("1");
	public static final ActivitySectorCode SECONDARY_INTERMEDIATE_GOODS = new ActivitySectorCode("2");
	public static final ActivitySectorCode SECONDARY_CONSUMPTION_GOODS = new ActivitySectorCode("3");
	public static final ActivitySectorCode TERTIARY = new ActivitySectorCode("4");

	protected ActivitySectorCode() {
		super(CodeList.SITE_SECTORS);
	}

	public ActivitySectorCode(String key) {
		this();
		this.key = key;
	}
}