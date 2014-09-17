package eu.factorx.awac.importers;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.util.data.importer.IndicatorImporter;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IndicatorImporterTest extends AbstractBaseModelTest {

	@Test
	public void _001_all() {

		IndicatorImporter importer = new IndicatorImporter();
		importer.run();

	}

}