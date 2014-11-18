package eu.factorx.awac.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.knowledge.Unit;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UnitServiceTest extends AbstractBaseModelTest {

	@Autowired
	private UnitService unitService;

    @Test
    public void _001_verifyAllSymbols() {

		// DataCell to consider @07/08/2014
		//	Expected size 228.

		/*
				"m",
				"dm",
				"cm",
				"dam",
				"hm",
				"km",
				"ft",
				"ft (US)",
				"Yd",
				"mi",
				"mi (US)",
				"NM; nmi",
				"m2",
				"ha",
				"a",
				"km2",
				"sq ft",
				"sq yd",
				"sq mi",
				"m3",
				"cu mi",
				"cu yd",
				"cu ft",
				"l",
				"dl",
				"dal",
				"hl",
				"bl",
				"bl; bbl",
				"gal (imp)",
				"kg",
				"g",
				"t",
				"lb",
				"sh tn",
				"long ton",
				"g/ml",
				"kg/m3",
				"kg/l",
				"lb/ft3",
				"lb/gal",
				"s",
				"d",
				"h",
				"min",
				"wk",
				"HZ",
				"m/s",
				"fps",
				"km/h",
				"mph",
				"J",
				"kW.h",
				"kcal",
				"BTU IT",
				"W",
				"V",
				"°C",
				"°F",
				"°R",
				"USD",
				"EUR",
				"ALL",
				"DZD",
				"AOA",
				"XCD",
				"ARS",
				"AMD",
				"AWG",
				"AUD",
				"AZN",
				"BSD",
				"BHD",
				"BDT",
				"BBD",
				"BYR",
				"BZD",
				"BMD",
				"BTN",
				"BOB",
				"BAM",
				"BWP",
				"BRL",
				"BND",
				"BGN",
				"MMK",
				"BIF",
				"KHR",
				"XAF",
				"CAD",
				"CVE",
				"KYD",
				"CLP",
				"CNY",
				"COP",
				"KMF",
				"CDF",
				"NZD",
				"CRC",
				"HRK",
				"CUC",
				"CUP",
				"ANG",
				"CZK",
				"DKK",
				"DJF",
				"DOP",
				"EGP",
				"ERN",
				"ETB",
				"FKP",
				"FJD",
				"GMD",
				"GEL",
				"GHS",
				"GIP",
				"GTQ",
				"GBP",
				"GNF",
				"GYD",
				"HTG",
				"HNL",
				"HKD",
				"HUF",
				"ISK",
				"INR",
				"IDR",
				"IRR",
				"IQD",
				"ILS",
				"JMD",
				"JPY",
				"JOD",
				"KZT",
				"KES",
				"KPW",
				"KWD",
				"KGS",
				"LAK",
				"LBP",
				"LSL",
				"LRD",
				"LYD",
				"CHF",
				"LTL",
				"MOP",
				"MKD",
				"MGA",
				"MWK",
				"MYR",
				"MVR",
				"MUR",
				"MXN",
				"MDL",
				"MNT",
				"MAD",
				"MZN",
				"NAD",
				"NPR",
				"XPF",
				"NIO",
				"NGN",
				"TRY",
				"NOK",
				"OMR",
				"PKR",
				"PAB",
				"PGK",
				"PYG",
				"PEN",
				"PHP",
				"PLN",
				"QAR",
				"RON",
				"RWF",
				"SHP",
				"WST",
				"STD",
				"SAR",
				"XOF",
				"RSD",
				"SCR",
				"SLL",
				"SGD",
				"SBD",
				"SOS",
				"ZAR",
				"RUB",
				"LKR",
				"SDG",
				"SRD",
				"SZL",
				"SEK",
				"SYP",
				"TWD",
				"TJS",
				"TZS",
				"THB",
				"TOP",
				"TTD",
				"TND",
				"TMT",
				"UGX",
				"UAH",
				"AED",
				"UYU",
				"UZS",
				"VUV",
				"VEF",
				"VND",
				"YER",
				"ZMW",
				"m3/y",
				"GJ",
				"MWh",
				"kWh",
				"kW",
				"k€",
				"km.passager",
				"véhicule.km",
				"passagers.km",
				"tonnes.km",
				"équivalent.habitant",
				"tCO2e",
				"unit",
				"kg éq CO2/kW froid",
				"heure",
				"kgCO2ea"",
				*/

		List <Unit> lu = unitService.findAll();

		//Logger.info(" null?" + lu);
		assertNotNull(lu);
		assertTrue(lu.size() > 0);

//		for (Unit unit : lu) {
//			//assertTrue(col.contains(unit.getSymbol()));
//			Logger.info("Symbol: " + unit.getSymbol());
//		}

//		Collection<String> col = Collections.unmodifiableList(new ArrayList<String>(Arrays.asList(
//				"m",
//				"dm",
//				"cm",
//				"dam",
//				"hm",
//				"km",
//				"ft",
//				"ft (US)",
//				"Yd",
//				"mi",
//				"mi (US)",
//				"NM; nmi",
//				"m2",
//				"ha",
//				"a",
//				"km2",
//				"sq ft",
//				"sq yd",
//				"sq mi",
//				"m3",
//				"cu mi",
//				"cu yd",
//				"cu ft",
//				"l",
//				"dl",
//				"dal",
//				"hl",
//				"bl",
//				"bl; bbl",
//				"gal (imp)",
//				"kg",
//				"g",
//				"t",
//				"lb",
//				"sh tn",
//				"long ton",
//				"g/ml",
//				"kg/m3",
//				"kg/l",
//				"lb/ft3",
//				"lb/gal",
//				"s",
//				"d",
//				"h",
//				"min",
//				"wk",
//				"HZ",
//				"m/s",
//				"fps",
//				"km/h",
//				"mph",
//				"J",
//				"kW.h",
//				"kcal",
//				"BTU IT",
//				"W",
//				"V",
//				"°C",
//				"°F",
//				"°R",
//				"USD",
//				"EUR",
//				"ALL",
//				"DZD",
//				"AOA",
//				"XCD",
//				"ARS",
//				"AMD",
//				"AWG",
//				"AUD",
//				"AZN",
//				"BSD",
//				"BHD",
//				"BDT",
//				"BBD",
//				"BYR",
//				"BZD",
//				"BMD",
//				"BTN",
//				"BOB",
//				"BAM",
//				"BWP",
//				"BRL",
//				"BND",
//				"BGN",
//				"MMK",
//				"BIF",
//				"KHR",
//				"XAF",
//				"CAD",
//				"CVE",
//				"KYD",
//				"CLP",
//				"CNY",
//				"COP",
//				"KMF",
//				"CDF",
//				"NZD",
//				"CRC",
//				"HRK",
//				"CUC",
//				"CUP",
//				"ANG",
//				"CZK",
//				"DKK",
//				"DJF",
//				"DOP",
//				"EGP",
//				"ERN",
//				"ETB",
//				"FKP",
//				"FJD",
//				"GMD",
//				"GEL",
//				"GHS",
//				"GIP",
//				"GTQ",
//				"GBP",
//				"GNF",
//				"GYD",
//				"HTG",
//				"HNL",
//				"HKD",
//				"HUF",
//				"ISK",
//				"INR",
//				"IDR",
//				"IRR",
//				"IQD",
//				"ILS",
//				"JMD",
//				"JPY",
//				"JOD",
//				"KZT",
//				"KES",
//				"KPW",
//				"KWD",
//				"KGS",
//				"LAK",
//				"LBP",
//				"LSL",
//				"LRD",
//				"LYD",
//				"CHF",
//				"LTL",
//				"MOP",
//				"MKD",
//				"MGA",
//				"MWK",
//				"MYR",
//				"MVR",
//				"MUR",
//				"MXN",
//				"MDL",
//				"MNT",
//				"MAD",
//				"MZN",
//				"NAD",
//				"NPR",
//				"XPF",
//				"NIO",
//				"NGN",
//				"TRY",
//				"NOK",
//				"OMR",
//				"PKR",
//				"PAB",
//				"PGK",
//				"PYG",
//				"PEN",
//				"PHP",
//				"PLN",
//				"QAR",
//				"RON",
//				"RWF",
//				"SHP",
//				"WST",
//				"STD",
//				"SAR",
//				"XOF",
//				"RSD",
//				"SCR",
//				"SLL",
//				"SGD",
//				"SBD",
//				"SOS",
//				"ZAR",
//				"RUB",
//				"LKR",
//				"SDG",
//				"SRD",
//				"SZL",
//				"SEK",
//				"SYP",
//				"TWD",
//				"TJS",
//				"TZS",
//				"THB",
//				"TOP",
//				"TTD",
//				"TND",
//				"TMT",
//				"UGX",
//				"UAH",
//				"AED",
//				"UYU",
//				"UZS",
//				"VUV",
//				"VEF",
//				"VND",
//				"YER",
//				"ZMW",
//				"m3/y",
//				"GJ",
//				"kW",
//				"k€",
//				"km.passager",
//				"véhicule.km",
//				"passagers.km",
//				"tonnes.km",
//				"équivalent.habitant",
//				"tCO2e",
//				"unit",
//				"kgCO2e",
//				"employé",
//				"MW.h")));


		Collection<String> col = Collections.unmodifiableList(new ArrayList<String>(Arrays.asList(
		"m",
		"km",
		"m2",
		"ha",
		"a",
		"km2",
		"m3",
		"l",
		"kg",
		"t",
		"g/ml",
		"kg/m3",
		"kg/l",
		"s",
		"d",
		"h",
		"min",
		"HZ",
		"m/s",
		"km/h",
		"J",
		"kW.h",
		"W",
		"V",
		"°C",
		"°F",
		"°R",
		"USD",
		"EUR",
		"GBP",
		"m3/y",
		"GJ",
		"kW",
		"k€",
		"véhicule.km",
		"passagers.km",
		"tonnes.km",
		"équivalent.habitant",
		"tCO2e",
		"unit",
		"kgCO2e",
		"employé",
		"MW.h"
		)));


		for (String symbol : col) {
			boolean founded=false;
			for(Unit unit : lu){
				if(unit.getSymbol().equals(symbol)){
					founded=true;
					break;
				}
			}
			if(!founded) {
				//Logger.info("unit : " + unit.getSymbol());
				assertTrue("Symbol not found : " + symbol, false);
			}
		}

	} // end of test

} // end of class
