
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class SECTEURPRIMAIRECode extends Code {

	private static final long serialVersionUID = 1L;

	protected SECTEURPRIMAIRECode() {
		super(CodeList.SECTEURPRIMAIRE);
	}

	public SECTEURPRIMAIRECode(String key) {
		this();
		this.key = key;
	}
/*
    public static final SECTEURPRIMAIRECode 05_EXTRACTION_DE_HOUILLE_ET_DE_LIGNITE=new

    SECTEURPRIMAIRECode("1");

    public static final SECTEURPRIMAIRECode 06_EXTRACTION_D_HYDROCARBURES=new

    SECTEURPRIMAIRECode("2");

    public static final SECTEURPRIMAIRECode 07_EXTRACTION_DE_MINERAIS_METALLIQUES=new

    SECTEURPRIMAIRECode("3");

    public static final SECTEURPRIMAIRECode 08_AUTRES_INDUSTRIES_EXTRACTIVES=new

    SECTEURPRIMAIRECode("4");

    public static final SECTEURPRIMAIRECode 09_SERVICES_DE_SOUTIEN_AUX_INDUSTRIES_EXTRACTIVES=new

    SECTEURPRIMAIRECode("5");*/
}