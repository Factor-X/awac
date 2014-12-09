package eu.factorx.awac.buisness.bad.household;

import com.fasterxml.jackson.databind.JsonNode;
import eu.factorx.awac.controllers.SecuredController;
import eu.factorx.awac.dto.awac.get.SiteDTO;
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
    private BAD_AM_BAD1ATest bad_AM_BAD1ATest;
    
    @Autowired
    private BAD_AM_BAD1BTest bad_AM_BAD1BTest;
    
    @Autowired
    private BAD_AM_BAD1CTest bad_AM_BAD1CTest;
    
    @Autowired
    private BAD_AM_BAD1DTest bad_AM_BAD1DTest;
    
    @Autowired
    private BAD_AM_BAD1ETest bad_AM_BAD1ETest;
    
    @Autowired
    private BAD_AM_BAD1FTest bad_AM_BAD1FTest;
    
    @Autowired
    private BAD_AM_BAD1GTest bad_AM_BAD1GTest;
    
    @Autowired
    private BAD_AM_BAD1HTest bad_AM_BAD1HTest;
    
    @Autowired
    private BAD_AM_BAD1ITest bad_AM_BAD1ITest;
    
    @Autowired
    private BAD_AM_BAD1JTest bad_AM_BAD1JTest;
    
    @Autowired
    private BAD_AM_BAD2ATest bad_AM_BAD2ATest;
    
    @Autowired
    private BAD_AM_BAD2BTest bad_AM_BAD2BTest;
    
    @Autowired
    private BAD_AM_BAD2CTest bad_AM_BAD2CTest;
    
    @Autowired
    private BAD_AM_BAD2DTest bad_AM_BAD2DTest;
    
    @Autowired
    private BAD_AM_BAD2ETest bad_AM_BAD2ETest;
    
    @Autowired
    private BAD_AM_BAD2FTest bad_AM_BAD2FTest;
    
    @Autowired
    private BAD_AM_BAD3BTest bad_AM_BAD3BTest;
    
    @Autowired
    private BAD_AM_BAD4Test bad_AM_BAD4Test;
    
    @Autowired
    private BAD_AM_BAD5Test bad_AM_BAD5Test;
    
    @Autowired
    private BAD_AM_BAD6Test bad_AM_BAD6Test;
    
    @Autowired
    private BAD_AM_BAD7Test bad_AM_BAD7Test;
    
    @Autowired
    private BAD_AM_BAD8Test bad_AM_BAD8Test;
    
    @Autowired
    private BAD_AM_BAD9Test bad_AM_BAD9Test;
    
    @Autowired
    private BAD_AM_BAD10Test bad_AM_BAD10Test;
    
    @Autowired
    private BAD_AM_BAD11Test bad_AM_BAD11Test;
    
    @Autowired
    private BAD_AM_BAD12ATest bad_AM_BAD12ATest;
    
    @Autowired
    private BAD_AM_BAD12BTest bad_AM_BAD12BTest;
    
    @Autowired
    private BAD_AM_BAD12CTest bad_AM_BAD12CTest;
    
    @Autowired
    private BAD_AM_BAD12DTest bad_AM_BAD12DTest;
    
    @Autowired
    private BAD_AM_BAD12ETest bad_AM_BAD12ETest;
    
    @Autowired
    private BAD_AM_BAD12FTest bad_AM_BAD12FTest;
    
    @Autowired
    private BAD_AM_BAD13ATest bad_AM_BAD13ATest;
    
    @Autowired
    private BAD_AM_BAD13BTest bad_AM_BAD13BTest;
    
    @Autowired
    private BAD_AM_BAD13CTest bad_AM_BAD13CTest;
    
    @Autowired
    private BAD_AM_BAD14ATest bad_AM_BAD14ATest;
    
    @Autowired
    private BAD_AM_BAD14BTest bad_AM_BAD14BTest;
    
    @Autowired
    private BAD_AM_BAD14CTest bad_AM_BAD14CTest;
    
    @Test
    public void _000_initialize() {


        // ConnectionFormDTO
        ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

        /*
        start with a percentage too big => except an error

        */
        SiteDTO dto = new SiteDTO();

        dto.setName("sitename");
        dto.setPercentOwned(100.0);

        //Json node
        JsonNode node = Json.toJson(dto);

        // perform save
        // Fake request
        FakeRequest saveFakeRequest = new FakeRequest();
        saveFakeRequest.withHeader("Content-type", "application/json");
        saveFakeRequest.withJsonBody(node);
        saveFakeRequest.withSession(SecuredController.SESSION_IDENTIFIER_STORE, cfDto.getLogin());

        // Call controller action
        Result result = callAction(
        eu.factorx.awac.controllers.routes.ref.SiteController.create(),
        saveFakeRequest
        ); // callAction

        //analyse result
        // expecting an HTTP 200 return code
        assertEquals(200, status(result));


        //analyse result
        SiteDTO resultDTO = getDTO(result, SiteDTO.class);

        assertNotNull(resultDTO.getId());

        scopeId = resultDTO.getId();

    }
        @Test
    public void _001_badAM_BAD1A() {
        bad_AM_BAD1ATest.test(scopeId);
    }
        @Test
    public void _002_badAM_BAD1B() {
        bad_AM_BAD1BTest.test(scopeId);
    }
        @Test
    public void _003_badAM_BAD1C() {
        bad_AM_BAD1CTest.test(scopeId);
    }
        @Test
    public void _004_badAM_BAD1D() {
        bad_AM_BAD1DTest.test(scopeId);
    }
        @Test
    public void _005_badAM_BAD1E() {
        bad_AM_BAD1ETest.test(scopeId);
    }
        @Test
    public void _006_badAM_BAD1F() {
        bad_AM_BAD1FTest.test(scopeId);
    }
        @Test
    public void _007_badAM_BAD1G() {
        bad_AM_BAD1GTest.test(scopeId);
    }
        @Test
    public void _008_badAM_BAD1H() {
        bad_AM_BAD1HTest.test(scopeId);
    }
        @Test
    public void _009_badAM_BAD1I() {
        bad_AM_BAD1ITest.test(scopeId);
    }
        @Test
    public void _0010_badAM_BAD1J() {
        bad_AM_BAD1JTest.test(scopeId);
    }
        @Test
    public void _0011_badAM_BAD2A() {
        bad_AM_BAD2ATest.test(scopeId);
    }
        @Test
    public void _0012_badAM_BAD2B() {
        bad_AM_BAD2BTest.test(scopeId);
    }
        @Test
    public void _0013_badAM_BAD2C() {
        bad_AM_BAD2CTest.test(scopeId);
    }
        @Test
    public void _0014_badAM_BAD2D() {
        bad_AM_BAD2DTest.test(scopeId);
    }
        @Test
    public void _0015_badAM_BAD2E() {
        bad_AM_BAD2ETest.test(scopeId);
    }
        @Test
    public void _0016_badAM_BAD2F() {
        bad_AM_BAD2FTest.test(scopeId);
    }
        @Test
    public void _0017_badAM_BAD3B() {
        bad_AM_BAD3BTest.test(scopeId);
    }
        @Test
    public void _0018_badAM_BAD4() {
        bad_AM_BAD4Test.test(scopeId);
    }
        @Test
    public void _0019_badAM_BAD5() {
        bad_AM_BAD5Test.test(scopeId);
    }
        @Test
    public void _0020_badAM_BAD6() {
        bad_AM_BAD6Test.test(scopeId);
    }
        @Test
    public void _0021_badAM_BAD7() {
        bad_AM_BAD7Test.test(scopeId);
    }
        @Test
    public void _0022_badAM_BAD8() {
        bad_AM_BAD8Test.test(scopeId);
    }
        @Test
    public void _0023_badAM_BAD9() {
        bad_AM_BAD9Test.test(scopeId);
    }
        @Test
    public void _0024_badAM_BAD10() {
        bad_AM_BAD10Test.test(scopeId);
    }
        @Test
    public void _0025_badAM_BAD11() {
        bad_AM_BAD11Test.test(scopeId);
    }
        @Test
    public void _0026_badAM_BAD12A() {
        bad_AM_BAD12ATest.test(scopeId);
    }
        @Test
    public void _0027_badAM_BAD12B() {
        bad_AM_BAD12BTest.test(scopeId);
    }
        @Test
    public void _0028_badAM_BAD12C() {
        bad_AM_BAD12CTest.test(scopeId);
    }
        @Test
    public void _0029_badAM_BAD12D() {
        bad_AM_BAD12DTest.test(scopeId);
    }
        @Test
    public void _0030_badAM_BAD12E() {
        bad_AM_BAD12ETest.test(scopeId);
    }
        @Test
    public void _0031_badAM_BAD12F() {
        bad_AM_BAD12FTest.test(scopeId);
    }
        @Test
    public void _0032_badAM_BAD13A() {
        bad_AM_BAD13ATest.test(scopeId);
    }
        @Test
    public void _0033_badAM_BAD13B() {
        bad_AM_BAD13BTest.test(scopeId);
    }
        @Test
    public void _0034_badAM_BAD13C() {
        bad_AM_BAD13CTest.test(scopeId);
    }
        @Test
    public void _0035_badAM_BAD14A() {
        bad_AM_BAD14ATest.test(scopeId);
    }
        @Test
    public void _0036_badAM_BAD14B() {
        bad_AM_BAD14BTest.test(scopeId);
    }
        @Test
    public void _0037_badAM_BAD14C() {
        bad_AM_BAD14CTest.test(scopeId);
    }
    }
