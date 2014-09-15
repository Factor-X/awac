package eu.factorx.awac.service;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.code.type.ActivityCategoryCode;
import eu.factorx.awac.models.code.type.ActivitySubCategoryCode;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.knowledge.BaseIndicator;
import eu.factorx.awac.service.impl.IndicatorSearchParameter;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IndicatorServiceTest extends AbstractBaseModelTest {

	@Autowired
	private BaseIndicatorService indicatorService;

    @Test
    public void _001_findAll() {

		List <BaseIndicator> li = indicatorService.findAll();

		assertNotNull(li);
		assertEquals(64,li.size());

	} // end of test

	@Test
	public void _002_findByParameters() {


		Collection<String> col = Collections.unmodifiableList(new ArrayList<String>(Arrays.asList(
				"Electricité et vapeur achetées",
				"Production de l'électricité et vapeur achetées")));

		// assume following search parameters
		IndicatorSearchParameter searchParameter = new IndicatorSearchParameter(
														InterfaceTypeCode.ENTERPRISE,
														ActivityCategoryCode.AC_1,
														ActivitySubCategoryCode.ASC_3,
														false
				);


		List <BaseIndicator> li = indicatorService.findByParameters(searchParameter);
		assertNotNull(li);
		assertEquals(col.size(),li.size());

		for (BaseIndicator baseIndicator : li) {
			assertTrue(col.contains(baseIndicator.getName()));
		}
	}

	@Test
	public void _003_findAllIndicatorNames() {
		
		/*
		DataCell @07082014
			"Transport et stockage de marchandises amont - véhicules propres: froid",
			"Utilisation des produits vendus",
			"Mobilité - déplacements professionnels - véhicules propres",
			"Infrastructures",
			"Electricité et vapeur achetées",
			"Combustibles fossiles (hors scope)",
			"Systèmes de refroidissement",
			"Transport et stockage de marchandises amont - véhicules propres",
			"Production de l'électricité et vapeur achetées",
			"Transport et distribution aval - véhicules tiers"",
			"Transport et stockage de marchandises amont - véhicules tiers: froid",
			"Déchets générés par les opérations",
			"Consommation de combustibles",
			"Mobilité - déplacements professionnels ou des visiteurs - véhicules tiers",
			"Achat de biens et de services",
			"Traitement de fin de vie des produits vendus",
			"Transport et distribution aval - véhicules propres",
			"Franchises",
			"Processus de production",
			"Production des combustibles",
			"Transport et distribution aval",
			"Traitement des produits vendus",
			"Transport et stockage de marchandises amont",
			"Mobilité - trajets domiciles-travail des employé.",
			"Transport et stockage de marchandises amont - véhicules tiers",
			"Actifs loués (aval)",
			"Investissements",

		 */

		Collection<String> col = Collections.unmodifiableList(new ArrayList<String>(Arrays.asList(
				"Transport et stockage de marchandises amont - véhicules propres: froid",
				"Utilisation des produits vendus",
				"Mobilité - déplacements professionnels - véhicules propres",
				"Infrastructures",
				"Electricité et vapeur achetées",
				"Combustibles fossiles (hors scope)",
				"Systèmes de refroidissement",
				"Transport et stockage de marchandises amont - véhicules propres",
				"Production de l'électricité et vapeur achetées",
				"Transport et distribution aval - véhicules tiers",
				"Transport et stockage de marchandises amont - véhicules tiers: froid",
				"Déchets générés par les opérations",
				"Consommation de combustibles",
				"Mobilité - déplacements professionnels ou des visiteurs - véhicules tiers",
				"Achat de biens et de services",
				"Traitement de fin de vie des produits vendus",
				"Transport et distribution aval - véhicules propres",
				"Franchises",
				"Processus de production",
				"Production des combustibles",
				"Transport et distribution aval",
				"Traitement des produits vendus",
				"Transport et stockage de marchandises amont",
				"Mobilité - trajets domiciles-travail des employés",
				"Transport et stockage de marchandises amont - véhicules tiers",
				"Actifs loués (aval)",
				"Investissements"
				)));

		List <String> ls = indicatorService.findAllIndicatorNames();
		assertNotNull(ls);
		assertEquals(col.size(),ls.size());

		for (Object indicatorName : col) {
			//Logger.info("indicatorName: " + indicatorName);
			//Logger.info("contains? " + ls.contains(indicatorName));
			assertTrue(ls.contains(indicatorName));
		}
	} // end of test


} // end of class