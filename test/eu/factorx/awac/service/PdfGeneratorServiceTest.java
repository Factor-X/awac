package eu.factorx.awac.service;

import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import play.api.templates.Html;
import play.mvc.Result;
import scala.collection.mutable.StringBuilder;
import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.util.Table;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
//@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PdfGeneratorServiceTest extends AbstractBaseModelTest {

	private final static String ORGANIZATION_NAME = "Factor-X";

	@Autowired
	private PdfGenerator pdfGenerator;

	@Autowired
	private SvgGenerator svgGenerator;

	//@Test
	public void _001_basic() throws FileNotFoundException {

		assertNotNull(pdfGenerator);


		Table table = new Table();
		for (int i = 0; i < 10; i++) {
			table.setCell(0,i, "Line #" + (i+1));
			table.setCell(1,i, Math.random() * 10 );
		}
		String donut = svgGenerator.getDonut(table,"2013");

		pdfGenerator.setMemoryResource("mem://svg/donut", donut);

		String content =  "\n" +
			"<!DOCTYPE html>\n" +
			"<head>\n" +
			"    <title>PDF test</title>\n" +
			"    <link rel=\"stylesheet\" href=\"/public/stylesheets/pdf.css\"/>\n" +
			"</head>\n" +
			"<body>\n" +
			"    <img src=\"/public/images/factorxlogo.gif\"/><br/>\n" +
			"    <h1>AWAC</h1>\n" +
			"\n" +
			"    <img src=\"mem://svg/donut\" style=\"width: 4cm; height: 4cm\"/><br/>\n" +
			"    <table border=\"2\">\n" +
			"        <caption>Account Information List</caption><br/>\n" +
			"            <tr>\n" +
			"                <th>Message: Hello world</th>\n" +
			"                <th>Identifier</th>\n" +
			"                <th>First Name</th>\n" +
			"                <th>Last Name</th>\n" +
			"            </tr>\n" +
			"    </table>\n" +
			"</body>";

		StringBuilder sb = new StringBuilder(content);
		Html html = new Html(sb);
		Result result = pdfGenerator.ok(html);

		//play.api.mvc.SimpleResult wrappedResult = (play.api.mvc.SimpleResult) result.getWrappedResult();
		//new FileOutputStream("pdf_generator_001_basic.pdf").write( wrappedResult.body().toString() );

		assertNotNull(result);

	} // end of test

} // end of class
