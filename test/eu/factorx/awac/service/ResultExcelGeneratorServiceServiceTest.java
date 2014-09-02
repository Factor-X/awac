package eu.factorx.awac.service;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.util.Table;
import junit.framework.Assert;
import jxl.Workbook;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResultExcelGeneratorServiceServiceTest extends AbstractBaseModelTest {

	@Autowired
	private ResultExcelGeneratorService resultExcelGeneratorService;

	@Test
	public void _001_createWorkbook() {

		try {
			// Craft the workbook
			ByteArrayOutputStream output = new ByteArrayOutputStream();

			Table allScopes = createRandomTable(10, 10);
			Table scope1 = createRandomTable(10, 10);
			Table scope2 = createRandomTable(10, 10);
			Table scope3 = createRandomTable(10, 10);
			Table outOfScope = createRandomTable(10, 10);

//			resultExcelGeneratorService.generateExcelInStream(
//				output,
//				allScopes,
//				scope1,
//				scope2,
//				scope3,
//				outOfScope);

			// Check it
			ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());

			Workbook workbook = Workbook.getWorkbook(input);
			Assert.assertTrue("The workbook contains 5 sheets", workbook.getNumberOfSheets() == 5);

		} catch (Exception e) {
			Assert.fail("Un exception has been thrown: " + e);
		}
	}

	private Table createRandomTable(int width, int height) {
		Table table = new Table();
		Random rnd = new Random();
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				Object v = null;
				switch (rnd.nextInt(6)) {
					case 0:
						v = rnd.nextDouble();
						break;
					case 1:
						v = rnd.nextInt();
						break;
					case 2:
						v = rnd.nextLong();
						break;
					case 3:
						v = rnd.nextFloat();
						break;
					case 4:
						v = "TXT " + rnd.nextLong();
						break;
					default:
						break;
				}

				table.setCell(column, row, v);
			}
		}
		return table;
	}


}
