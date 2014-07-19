
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class SECTEURPRINCIPALCode extends Code {

	private static final long serialVersionUID = 1L;

	protected SECTEURPRINCIPALCode() {
		super(CodeList.SECTEURPRINCIPAL);
	}

	public SECTEURPRINCIPALCode(String key) {
		this();
		this.key = key;
	}

	public static final SECTEURPRINCIPALCode INDUSTRIE_PRIMAIRE_HORMIS_LE_SECTEUR_AGRICOLE = new SECTEURPRINCIPALCode("1");
	public static final SECTEURPRINCIPALCode PRODUCTION_DE_BIENS_INTERMEDIAIRES = new SECTEURPRINCIPALCode("2");
	public static final SECTEURPRINCIPALCode PRODUCTION_DE_BIENS_DE_CONSOMMATION = new SECTEURPRINCIPALCode("3");
	public static final SECTEURPRINCIPALCode TERTIAIRE = new SECTEURPRINCIPALCode("4");
}