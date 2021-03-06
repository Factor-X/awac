package eu.factorx.awac.controllers;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.callAction;
import static play.test.Helpers.status;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;

import play.mvc.Result;
import play.test.FakeRequest;
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
//@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PDFTest extends AbstractBaseModelTest {

	//@Test
	public void _001_pdfGeneration() {

		// ConnectionFormDTO
		ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

		// perform save
		// Fake request
		FakeRequest fr = new FakeRequest();
		fr.withHeader("Content-type", "application/json");
		fr.withSession(SecuredController.SESSION_IDENTIFIER_STORE, cfDto.getLogin());

		// Call controller action
		Result result = callAction(
				eu.factorx.awac.controllers.routes.ref.PdfController.asPdf(),
				fr
		); // callAction

		//analyse result
		// expecting an HTTP 200 return code
		assertEquals(200, status(result));
	} // end of authenticateSuccess test

	//@Test
	public void _001_pdfWithSvgGeneration() {

		// ConnectionFormDTO
		ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

		// Fake request
		FakeRequest fr = new FakeRequest();
		fr.withHeader("Content-type", "application/json");
		fr.withSession(SecuredController.SESSION_IDENTIFIER_STORE, cfDto.getLogin());

		// Call controller action
		Result result = callAction(
				eu.factorx.awac.controllers.routes.ref.PdfController.withSvg(),
				fr
		); // callAction

		//analyse result
		// expecting an HTTP 200 return code
		assertEquals(200, status(result));



	} // end of authenticateSuccess test

}
