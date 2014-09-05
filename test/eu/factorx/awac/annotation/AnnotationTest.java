package eu.factorx.awac.annotation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import play.Logger;
import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.util.FileUtil;

//import java.util.List;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnnotationTest extends AbstractBaseModelTest {


    @Test
    public void _001_checkAnnotationNotNull() throws IOException {

		InputStream is = play.Play.application().resourceAsStream("public/javascripts/scripts/NotNull.js");
		String javascript = IOUtils.toString(is, "UTF-8");
		Logger.info("javascript content : " + javascript);
		assertNotNull(javascript);

    } // end of test method

	@Test
	public void _002_checkAnnotationNull() throws IOException {

		String javascript = FileUtil.getContents("public/javascripts/scripts/Null.js");
		Logger.info("javascript content : " + javascript);
		assertNotNull(javascript);

	} // end of test method

	@Test
	public void _003_checkAnnotationPattern() throws IOException {

		String javascript = FileUtil.getContents("public/javascripts/scripts/Pattern.js");
		Logger.info("javascript content : " + javascript);
		assertNotNull(javascript);

	} // end of test method

	@Test
	public void _004_checkAnnotationSize() throws IOException {

		String javascript = FileUtil.getContents("public/javascripts/scripts/Size.js");
		Logger.info("javascript content : " + javascript);
		assertNotNull(javascript);

	} // end of test method

	@Test
	public void _005_checkAnnotationUnknownFile() throws IOException {

		String javascript = null;
		try {
			javascript = FileUtil.getContents("public/javascripts/scripts/Unknown.js");
		} catch (Exception e) {
			javascript = null;
		}
		Logger.info("javascript content : " + javascript);
		assertNull(javascript);

	} // end of test method

} // end of class
