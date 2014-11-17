package eu.factorx.awac.service;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.code.type.BaseIndicatorCode;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.ReducingActionTypeCode;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.models.knowledge.BaseIndicator;
import eu.factorx.awac.models.knowledge.ReducingActionAdvice;
import eu.factorx.awac.models.knowledge.ReducingActionAdviceBaseIndicatorAssociation;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReducingActionAdviceServiceTest extends AbstractBaseModelTest {

	@Autowired
	private ReducingActionAdviceService reducingActionAdviceService;

	@Autowired
	private AwacCalculatorService awacCalculatorService;

	@Autowired
	private BaseIndicatorService baseIndicatorService;

	@Autowired
	private StoredFileService storedFileService;

	@Test
	public void _001_testCRUD() {
		// create
		ReducingActionAdvice advice = new ReducingActionAdvice();
		advice.setAwacCalculator(awacCalculatorService.findByCode(InterfaceTypeCode.ENTERPRISE));
		advice.setTitle("New advice");
		advice.setType(ReducingActionTypeCode.REDUCING_GES);

		BaseIndicator bi_1 = baseIndicatorService.getByCode(new BaseIndicatorCode("BI_1"));
		ReducingActionAdviceBaseIndicatorAssociation biAssociation = new ReducingActionAdviceBaseIndicatorAssociation(advice, bi_1, 0.2);
		advice.getBaseIndicatorAssociations().add(biAssociation);

		List<StoredFile> storedFiles = storedFileService.findAll();
		if (!storedFiles.isEmpty()) {
			advice.getDocuments().add(storedFiles.get(0));
		}

		advice = reducingActionAdviceService.saveOrUpdate(advice);
		Long adviceId = advice.getId();

		// retrieve
		ReducingActionAdvice foundAdvice = reducingActionAdviceService.findById(adviceId);
		Assert.assertNotNull(foundAdvice);
		Set<ReducingActionAdviceBaseIndicatorAssociation> foundBiAssociations = foundAdvice.getBaseIndicatorAssociations();
		Assert.assertEquals(1, foundBiAssociations.size());
		ReducingActionAdviceBaseIndicatorAssociation foundBiAssociation = foundBiAssociations.iterator().next();
		Assert.assertEquals(biAssociation, foundBiAssociation);

		// update
		foundAdvice.getBaseIndicatorAssociations().clear();
		ReducingActionAdvice updatedAdvice = reducingActionAdviceService.saveOrUpdate(foundAdvice);
		Assert.assertTrue(updatedAdvice.getBaseIndicatorAssociations().isEmpty());

		BaseIndicator bi_2 = baseIndicatorService.getByCode(new BaseIndicatorCode("BI_2"));
		Set<ReducingActionAdviceBaseIndicatorAssociation> biAssociations = new HashSet<>();
		biAssociations.add(new ReducingActionAdviceBaseIndicatorAssociation(advice, bi_1, 0.2));
		biAssociations.add(new ReducingActionAdviceBaseIndicatorAssociation(advice, bi_2, 0.4));
		updatedAdvice.setBaseIndicatorAssociations(biAssociations);

		ReducingActionAdvice updatedAdvice2 = reducingActionAdviceService.saveOrUpdate(updatedAdvice);

		foundBiAssociations = updatedAdvice2.getBaseIndicatorAssociations();
		Assert.assertEquals(2, foundBiAssociations.size());

		//delete
		reducingActionAdviceService.remove(updatedAdvice2);
		Assert.assertNull(reducingActionAdviceService.findById(adviceId));
	} // end of test method

} // end of class
