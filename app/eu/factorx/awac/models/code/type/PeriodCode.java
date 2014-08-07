package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "period"))})
public class PeriodCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final PeriodCode P2005 = new PeriodCode("2005");
	public static final PeriodCode P2006 = new PeriodCode("2006");
	public static final PeriodCode P2007 = new PeriodCode("2007");
	public static final PeriodCode P2008 = new PeriodCode("2008");
	public static final PeriodCode P2009 = new PeriodCode("2009");
	public static final PeriodCode P2010 = new PeriodCode("2010");
	public static final PeriodCode P2011 = new PeriodCode("2011");
	public static final PeriodCode P2012 = new PeriodCode("2012");
	public static final PeriodCode P2013 = new PeriodCode("2013");
	public static final PeriodCode P2014 = new PeriodCode("2014");

	protected PeriodCode() {
		super(CodeList.PERIOD);
	}

	public PeriodCode(String key) {
		this();
		this.key = key;
	}

}
