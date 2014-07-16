package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "fuel")) })
public class FuelCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final FuelCode FUEL_DIESEL_GASOIL_FUEL_LEGER = new FuelCode("1",
			ActivitySourceCode.DIESEL_GASOIL_OU_FUEL_LEGER);
	public static final FuelCode FUEL_GAZ_DE_PETROLE_LIQUEFIE_GPL = new FuelCode("2");
	public static final FuelCode FUEL_GAZ_NATUREL_PAUVRE = new FuelCode("3");
	public static final FuelCode FUEL_ESSENCE = new FuelCode("4");
	public static final FuelCode FUEL_CHARBON = new FuelCode("5");
	public static final FuelCode FUEL_BOIS_COPEAUX_DE_BOIS = new FuelCode("6");
	public static final FuelCode FUEL_PROPANE = new FuelCode("7");
	public static final FuelCode FUEL_BUTANE = new FuelCode("8");
	public static final FuelCode FUEL_AGGLOMERE_DE_CHARBON_OU_DE_LIGNITE = new FuelCode("9");
	public static final FuelCode FUEL_ANTHRACITE = new FuelCode("10");
	public static final FuelCode FUEL_BIOGAZ = new FuelCode("11");
	public static final FuelCode FUEL_AUTRES_PRODUITS_PETROLIERS_PARAFFINES_CIRES = new FuelCode("12");
	public static final FuelCode FUEL_BITUMES = new FuelCode("13");
	public static final FuelCode FUEL_BRIQUETTE_DE_LIGNITE = new FuelCode("14");
	public static final FuelCode FUEL_COKE = new FuelCode("15");
	public static final FuelCode FUEL_COKE_DE_PETROLE = new FuelCode("16");
	public static final FuelCode FUEL_FUEL_LOURD = new FuelCode("17");
	public static final FuelCode FUEL_GAZ_DE_COKERIE = new FuelCode("18");
	public static final FuelCode FUEL_GAZ_DE_HAUT_FOURNEAU = new FuelCode("19");
	public static final FuelCode FUEL_GAZ_NATUREL_RICHE = new FuelCode("20");
	public static final FuelCode FUEL_GOUDRON = new FuelCode("21");
	public static final FuelCode FUEL_GRAISSES = new FuelCode("22");
	public static final FuelCode FUEL_HUILES = new FuelCode("23");
	public static final FuelCode FUEL_KEROSENE = new FuelCode("24");
	public static final FuelCode FUEL_LIGNITE = new FuelCode("25");
	public static final FuelCode FUEL_METHANE = new FuelCode("26");
	public static final FuelCode FUEL_NAPHTA = new FuelCode("27");
	public static final FuelCode FUEL_PETROLE_BRUT = new FuelCode("28");
	public static final FuelCode FUEL_PETROLE_LAMPANT = new FuelCode("29");
	public static final FuelCode FUEL_TERRIL = new FuelCode("30");
	public static final FuelCode FUEL_TOURBE = new FuelCode("31");

	@Transient
	private ActivitySourceCode activitySourceCode;

	protected FuelCode() {
		super(CodeList.FUEL);
	}

	public FuelCode(String key) {
		this();
		this.key = key;
	}

	public FuelCode(String key, ActivitySourceCode activitySourceCode) {
		this();
		this.key = key;
		this.activitySourceCode = activitySourceCode;

	}
}
