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

	public static final PeriodCode P2000 = new PeriodCode("2000");
	public static final PeriodCode P2001 = new PeriodCode("2001");
	public static final PeriodCode P2002 = new PeriodCode("2002");
	public static final PeriodCode P2003 = new PeriodCode("2003");
	public static final PeriodCode P2004 = new PeriodCode("2004");
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
    public static final PeriodCode P2015 = new PeriodCode("2015");
    public static final PeriodCode P2016 = new PeriodCode("2016");
    public static final PeriodCode P2017 = new PeriodCode("2017");
    public static final PeriodCode P2018 = new PeriodCode("2018");
    public static final PeriodCode P2019 = new PeriodCode("2019");
    public static final PeriodCode P2020 = new PeriodCode("2020");
    public static final PeriodCode P2021 = new PeriodCode("2021");
    public static final PeriodCode P2022 = new PeriodCode("2022");
    public static final PeriodCode P2023 = new PeriodCode("2023");
    public static final PeriodCode P2024 = new PeriodCode("2024");
    public static final PeriodCode P2025 = new PeriodCode("2025");
    public static final PeriodCode P2026 = new PeriodCode("2026");
    public static final PeriodCode P2027 = new PeriodCode("2027");
    public static final PeriodCode P2028 = new PeriodCode("2028");
    public static final PeriodCode P2029 = new PeriodCode("2029");
    public static final PeriodCode P2030 = new PeriodCode("2030");


	protected PeriodCode() {
		super(CodeList.PERIOD);
	}

	public PeriodCode(String key) {
		this();
		this.key = key;
	}

}
