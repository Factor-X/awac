package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

public class TYPEVEHICULECode extends Code {

	public static final TYPEVEHICULECode VOITURE_PASSAGER = new TYPEVEHICULECode("1");
	public static final TYPEVEHICULECode CAMIONNETTE = new TYPEVEHICULECode("2");
	public static final TYPEVEHICULECode CAMION = new TYPEVEHICULECode("3");
	public static final TYPEVEHICULECode AUTOBUS = new TYPEVEHICULECode("4");
	public static final TYPEVEHICULECode CAR = new TYPEVEHICULECode("5");
	public static final TYPEVEHICULECode MOTOCYCLETTE = new TYPEVEHICULECode("6");
	public static final TYPEVEHICULECode MOTO = new TYPEVEHICULECode("7");
	private static final long serialVersionUID = 1L;
	protected TYPEVEHICULECode() {
		super(CodeList.TYPEVEHICULE);
	}
	public TYPEVEHICULECode(String key) {
		this();
		this.key = key;
	}
}