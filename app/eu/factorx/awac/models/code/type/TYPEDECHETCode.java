
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class TYPEDECHETCode extends Code {

	private static final long serialVersionUID = 1L;

	protected TYPEDECHETCode() {
		super(CodeList.TYPEDECHET);
	}

	public TYPEDECHETCode(String key) {
		this();
		this.key = key;
	}

	public static final TYPEDECHETCode METAUX = new TYPEDECHETCode("1");
	public static final TYPEDECHETCode PLASTIQUES = new TYPEDECHETCode("2");
	public static final TYPEDECHETCode VERRE = new TYPEDECHETCode("3");
	public static final TYPEDECHETCode PAPIER_CARTON = new TYPEDECHETCode("4");
	public static final TYPEDECHETCode MATIERE_ORGANIQUE = new TYPEDECHETCode("5");
	public static final TYPEDECHETCode DECHETS_INDUSTRIELS_SPECIAUX = new TYPEDECHETCode("6");
	public static final TYPEDECHETCode DECHETS_DANGEREUX_D_ORIGINE_SANITAIRE = new TYPEDECHETCode("7");
}