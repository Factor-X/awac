package eu.factorx.awac.buisness.bad.littleemitter;

import com.fasterxml.jackson.databind.JsonNode;
import eu.factorx.awac.controllers.SecuredController;
import eu.factorx.awac.dto.awac.get.OrganizationDTO;
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.callAction;
import static play.test.Helpers.status;

/**
* Created by florian on 11/09/14.
*/

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BadTest extends AbstractBaseModelTest {

    private static Long scopeId = null;

    
    @Autowired
    private BAD_APE_BAD1ATest bad_APE_BAD1ATest;
    
    @Autowired
    private BAD_APE_BAD1BTest bad_APE_BAD1BTest;
    
    @Autowired
    private BAD_APE_BAD1CTest bad_APE_BAD1CTest;
    
    @Autowired
    private BAD_APE_BAD1DTest bad_APE_BAD1DTest;
    
    @Autowired
    private BAD_APE_BAD1GTest bad_APE_BAD1GTest;
    
    @Autowired
    private BAD_APE_BAD1HTest bad_APE_BAD1HTest;
    
    @Autowired
    private BAD_APE_BAD1ITest bad_APE_BAD1ITest;
    
    @Autowired
    private BAD_APE_BAD1JTest bad_APE_BAD1JTest;
    
    @Autowired
    private BAD_APE_BAD1KTest bad_APE_BAD1KTest;
    
    @Autowired
    private BAD_APE_BAD2BTest bad_APE_BAD2BTest;
    
    @Autowired
    private BAD_APE_BAD3Test bad_APE_BAD3Test;
    
    @Autowired
    private BAD_APE_BAD4Test bad_APE_BAD4Test;
    
    @Autowired
    private BAD_APE_BAD5ATest bad_APE_BAD5ATest;
    
    @Autowired
    private BAD_APE_BAD5BTest bad_APE_BAD5BTest;
    
    @Autowired
    private BAD_APE_BAD5CTest bad_APE_BAD5CTest;
    
    @Autowired
    private BAD_APE_BAD6Test bad_APE_BAD6Test;
    
    @Autowired
    private BAD_APE_BAD7ATest bad_APE_BAD7ATest;
    
    @Autowired
    private BAD_APE_BAD7BTest bad_APE_BAD7BTest;
    
    @Autowired
    private BAD_APE_BAD7CTest bad_APE_BAD7CTest;
    
    @Autowired
    private BAD_APE_BAD7ETest bad_APE_BAD7ETest;
    
    @Autowired
    private BAD_APE_BAD7FTest bad_APE_BAD7FTest;
    
    @Autowired
    private BAD_APE_BAD7GTest bad_APE_BAD7GTest;
    
    @Autowired
    private BAD_APE_BAD7HTest bad_APE_BAD7HTest;
    
    @Autowired
    private BAD_APE_BAD8ATest bad_APE_BAD8ATest;
    
    @Autowired
    private BAD_APE_BAD8BTest bad_APE_BAD8BTest;
    
    @Autowired
    private BAD_APE_BAD8ETest bad_APE_BAD8ETest;
    
    @Autowired
    private BAD_APE_BAD8FTest bad_APE_BAD8FTest;
    
    @Autowired
    private BAD_APE_BAD8GTest bad_APE_BAD8GTest;
    
    @Autowired
    private BAD_APE_BAD8HTest bad_APE_BAD8HTest;
    
    @Autowired
    private BAD_APE_BAD9Test bad_APE_BAD9Test;
    
    @Autowired
    private BAD_APE_BAD10Test bad_APE_BAD10Test;
    
    @Autowired
    private BAD_APE_BAD11Test bad_APE_BAD11Test;
    
    @Autowired
    private BAD_APE_BAD12Test bad_APE_BAD12Test;
    
    @Autowired
    private BAD_APE_BAD13Test bad_APE_BAD13Test;
    
    @Autowired
    private BAD_APE_BAD14Test bad_APE_BAD14Test;
    
    @Autowired
    private BAD_APE_BAD15Test bad_APE_BAD15Test;
    
    @Autowired
    private BAD_APE_BAD16ATest bad_APE_BAD16ATest;
    
    @Autowired
    private BAD_APE_BAD16BTest bad_APE_BAD16BTest;
    
    @Autowired
    private BAD_APE_BAD16CTest bad_APE_BAD16CTest;
    
    @Autowired
    private BAD_APE_BAD17ATest bad_APE_BAD17ATest;
    
    @Autowired
    private BAD_APE_BAD17BTest bad_APE_BAD17BTest;
    
    @Autowired
    private BAD_APE_BAD17CTest bad_APE_BAD17CTest;
    
    @Autowired
    private BAD_APE_BAD18ATest bad_APE_BAD18ATest;
    
    @Autowired
    private BAD_APE_BAD18BTest bad_APE_BAD18BTest;
    
    @Autowired
    private BAD_APE_BAD18CTest bad_APE_BAD18CTest;
    
    @Autowired
    private BAD_APE_BAD19ATest bad_APE_BAD19ATest;
    
    @Autowired
    private BAD_APE_BAD19BTest bad_APE_BAD19BTest;
    
    @Autowired
    private BAD_APE_BAD19CTest bad_APE_BAD19CTest;
    
    @Autowired
    private BAD_APE_BAD19DTest bad_APE_BAD19DTest;
    
    @Autowired
    private BAD_APE_BAD19ETest bad_APE_BAD19ETest;
    
    @Autowired
    private BAD_APE_BAD19FTest bad_APE_BAD19FTest;
    
    @Test
    public void _000_initialize() {
        // ConnectionFormDTO
        ConnectionFormDTO cfDto = new ConnectionFormDTO("user20", "password", InterfaceTypeCode.LITTLEEMITTER.getKey(), "");

        /*
        start with a percentage too big => except an error

        */

        // perform save
        // Fake request
        FakeRequest saveFakeRequest = new FakeRequest();
        saveFakeRequest.withHeader("Content-type", "application/json");
        saveFakeRequest.withSession(SecuredController.SESSION_IDENTIFIER_STORE, cfDto.getLogin());

        // Call controller action
        Result result = callAction(
        eu.factorx.awac.controllers.routes.ref.OrganizationController.getMyOrganization(),
        saveFakeRequest
        ); // callAction

        //analyse result
        // expecting an HTTP 200 return code
        assertEquals(200, status(result));


        //analyse result
        OrganizationDTO resultDTO = getDTO(result, OrganizationDTO.class);

        assertNotNull(resultDTO.getId());

        scopeId = resultDTO.getId();

    }
        @Test
    public void _001_badAPE_BAD1A() {
        bad_APE_BAD1ATest.test(scopeId);
    }
        @Test
    public void _002_badAPE_BAD1B() {
        bad_APE_BAD1BTest.test(scopeId);
    }
        @Test
    public void _003_badAPE_BAD1C() {
        bad_APE_BAD1CTest.test(scopeId);
    }
        @Test
    public void _004_badAPE_BAD1D() {
        bad_APE_BAD1DTest.test(scopeId);
    }
        @Test
    public void _005_badAPE_BAD1G() {
        bad_APE_BAD1GTest.test(scopeId);
    }
        @Test
    public void _006_badAPE_BAD1H() {
        bad_APE_BAD1HTest.test(scopeId);
    }
        @Test
    public void _007_badAPE_BAD1I() {
        bad_APE_BAD1ITest.test(scopeId);
    }
        @Test
    public void _008_badAPE_BAD1J() {
        bad_APE_BAD1JTest.test(scopeId);
    }
        @Test
    public void _009_badAPE_BAD1K() {
        bad_APE_BAD1KTest.test(scopeId);
    }
        @Test
    public void _0010_badAPE_BAD2B() {
        bad_APE_BAD2BTest.test(scopeId);
    }
        @Test
    public void _0011_badAPE_BAD3() {
        bad_APE_BAD3Test.test(scopeId);
    }
        @Test
    public void _0012_badAPE_BAD4() {
        bad_APE_BAD4Test.test(scopeId);
    }
        @Test
    public void _0013_badAPE_BAD5A() {
        bad_APE_BAD5ATest.test(scopeId);
    }
        @Test
    public void _0014_badAPE_BAD5B() {
        bad_APE_BAD5BTest.test(scopeId);
    }
        @Test
    public void _0015_badAPE_BAD5C() {
        bad_APE_BAD5CTest.test(scopeId);
    }
        @Test
    public void _0016_badAPE_BAD6() {
        bad_APE_BAD6Test.test(scopeId);
    }
        @Test
    public void _0017_badAPE_BAD7A() {
        bad_APE_BAD7ATest.test(scopeId);
    }
        @Test
    public void _0018_badAPE_BAD7B() {
        bad_APE_BAD7BTest.test(scopeId);
    }
        @Test
    public void _0019_badAPE_BAD7C() {
        bad_APE_BAD7CTest.test(scopeId);
    }
        @Test
    public void _0020_badAPE_BAD7E() {
        bad_APE_BAD7ETest.test(scopeId);
    }
        @Test
    public void _0021_badAPE_BAD7F() {
        bad_APE_BAD7FTest.test(scopeId);
    }
        @Test
    public void _0022_badAPE_BAD7G() {
        bad_APE_BAD7GTest.test(scopeId);
    }
        @Test
    public void _0023_badAPE_BAD7H() {
        bad_APE_BAD7HTest.test(scopeId);
    }
        @Test
    public void _0024_badAPE_BAD8A() {
        bad_APE_BAD8ATest.test(scopeId);
    }
        @Test
    public void _0025_badAPE_BAD8B() {
        bad_APE_BAD8BTest.test(scopeId);
    }
        @Test
    public void _0026_badAPE_BAD8E() {
        bad_APE_BAD8ETest.test(scopeId);
    }
        @Test
    public void _0027_badAPE_BAD8F() {
        bad_APE_BAD8FTest.test(scopeId);
    }
        @Test
    public void _0028_badAPE_BAD8G() {
        bad_APE_BAD8GTest.test(scopeId);
    }
        @Test
    public void _0029_badAPE_BAD8H() {
        bad_APE_BAD8HTest.test(scopeId);
    }
        @Test
    public void _0030_badAPE_BAD9() {
        bad_APE_BAD9Test.test(scopeId);
    }
        @Test
    public void _0031_badAPE_BAD10() {
        bad_APE_BAD10Test.test(scopeId);
    }
        @Test
    public void _0032_badAPE_BAD11() {
        bad_APE_BAD11Test.test(scopeId);
    }
        @Test
    public void _0033_badAPE_BAD12() {
        bad_APE_BAD12Test.test(scopeId);
    }
        @Test
    public void _0034_badAPE_BAD13() {
        bad_APE_BAD13Test.test(scopeId);
    }
        @Test
    public void _0035_badAPE_BAD14() {
        bad_APE_BAD14Test.test(scopeId);
    }
        @Test
    public void _0036_badAPE_BAD15() {
        bad_APE_BAD15Test.test(scopeId);
    }
        @Test
    public void _0037_badAPE_BAD16A() {
        bad_APE_BAD16ATest.test(scopeId);
    }
        @Test
    public void _0038_badAPE_BAD16B() {
        bad_APE_BAD16BTest.test(scopeId);
    }
        @Test
    public void _0039_badAPE_BAD16C() {
        bad_APE_BAD16CTest.test(scopeId);
    }
        @Test
    public void _0040_badAPE_BAD17A() {
        bad_APE_BAD17ATest.test(scopeId);
    }
        @Test
    public void _0041_badAPE_BAD17B() {
        bad_APE_BAD17BTest.test(scopeId);
    }
        @Test
    public void _0042_badAPE_BAD17C() {
        bad_APE_BAD17CTest.test(scopeId);
    }
        @Test
    public void _0043_badAPE_BAD18A() {
        bad_APE_BAD18ATest.test(scopeId);
    }
        @Test
    public void _0044_badAPE_BAD18B() {
        bad_APE_BAD18BTest.test(scopeId);
    }
        @Test
    public void _0045_badAPE_BAD18C() {
        bad_APE_BAD18CTest.test(scopeId);
    }
        @Test
    public void _0046_badAPE_BAD19A() {
        bad_APE_BAD19ATest.test(scopeId);
    }
        @Test
    public void _0047_badAPE_BAD19B() {
        bad_APE_BAD19BTest.test(scopeId);
    }
        @Test
    public void _0048_badAPE_BAD19C() {
        bad_APE_BAD19CTest.test(scopeId);
    }
        @Test
    public void _0049_badAPE_BAD19D() {
        bad_APE_BAD19DTest.test(scopeId);
    }
        @Test
    public void _0050_badAPE_BAD19E() {
        bad_APE_BAD19ETest.test(scopeId);
    }
        @Test
    public void _0051_badAPE_BAD19F() {
        bad_APE_BAD19FTest.test(scopeId);
    }
    }
