package eu.factorx.awac.service;

import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.util.Table;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SvgGeneratorServiceTest extends AbstractBaseModelTest {

	@Autowired
	private SvgGenerator svgGenerator;

	@Autowired
	private PeriodService periodService;

	@Test
	public void _001_donut() {

		assertNotNull(svgGenerator);

		Table table = new Table();

		for (int i = 0; i < 10; i++) {
			table.setCell(0, i, "Line #" + (i + 1));
			table.setCell(1, i, Math.random() * 10);
		}

		String donut = svgGenerator.getDonut(table, "2013");

		System.out.println(donut);

		assertNotNull(donut);

	} // end of test


	@Test
	public void _002_web() {

		assertNotNull(svgGenerator);

		Table table = new Table();

		for (int i = 0; i < 10; i++) {
			table.setCell(0, i, "Line #" + (i + 1));
			table.setCell(1, i, Math.random() * 10);
			table.setCell(2, i, Math.random() * 10);
			table.setCell(3, i, Math.random() * 10);
		}

		String code = svgGenerator.getWeb(table);

		System.out.println(code);

		assertNotNull(code);

	} // end of test

	@Test
	public void _003_histogram() {

		assertNotNull(svgGenerator);

		Table table = new Table();

		for (int i = 0; i < 10; i++) {
			table.setCell(0, i, "Line #" + (i + 1));
			table.setCell(1, i, Math.random() * 10);
			table.setCell(2, i, Math.random() * 10);
			table.setCell(3, i, Math.random() * 10);
			table.setCell(4, i, Math.random() * 10);
		}

		String code = svgGenerator.getHistogram(table);

		System.out.println(code);

		assertNotNull(code);

	} // end of test
} // end of class
