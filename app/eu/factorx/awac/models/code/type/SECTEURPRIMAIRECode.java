package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "secteurprimaire"))})
public class SECTEURPRIMAIRECode extends Code {

	public static final SECTEURPRIMAIRECode _05_EXTRACTION_DE_HOUILLE_ET_DE_LIGNITE = new SECTEURPRIMAIRECode("1");
	public static final SECTEURPRIMAIRECode _06_EXTRACTION_D_HYDROCARBURES = new SECTEURPRIMAIRECode("2");
	public static final SECTEURPRIMAIRECode _07_EXTRACTION_DE_MINERAIS_METALLIQUES = new SECTEURPRIMAIRECode("3");
	public static final SECTEURPRIMAIRECode _08_AUTRES_INDUSTRIES_EXTRACTIVES = new SECTEURPRIMAIRECode("4");
	public static final SECTEURPRIMAIRECode _09_SERVICES_DE_SOUTIEN_AUX_INDUSTRIES_EXTRACTIVES = new SECTEURPRIMAIRECode("5");
	private static final long serialVersionUID = 1L;
	protected SECTEURPRIMAIRECode() {
		super(CodeList.SECTEURPRIMAIRE);
	}
	public SECTEURPRIMAIRECode(String key) {
		this();
		this.key = key;
	}
}