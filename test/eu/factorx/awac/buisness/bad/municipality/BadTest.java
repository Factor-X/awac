package eu.factorx.awac.buisness.bad.municipality;

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
    private BAD_AC_BAD1Test bad_AC_BAD1Test;
    
    @Autowired
    private BAD_AC_BAD26Test bad_AC_BAD26Test;
    
    @Autowired
    private BAD_AC_BAD27Test bad_AC_BAD27Test;
    
    @Autowired
    private BAD_AC_BAD2ATest bad_AC_BAD2ATest;
    
    @Autowired
    private BAD_AC_BAD2BTest bad_AC_BAD2BTest;
    
    @Autowired
    private BAD_AC_BAD3Test bad_AC_BAD3Test;
    
    @Autowired
    private BAD_AC_BAD4Test bad_AC_BAD4Test;
    
    @Autowired
    private BAD_AC_BAD5Test bad_AC_BAD5Test;
    
    @Autowired
    private BAD_AC_BAD6ATest bad_AC_BAD6ATest;
    
    @Autowired
    private BAD_AC_BAD6BTest bad_AC_BAD6BTest;
    
    @Autowired
    private BAD_AC_BAD7ATest bad_AC_BAD7ATest;
    
    @Autowired
    private BAD_AC_BAD7BTest bad_AC_BAD7BTest;
    
    @Autowired
    private BAD_AC_BAD17ATest bad_AC_BAD17ATest;
    
    @Autowired
    private BAD_AC_BAD17BTest bad_AC_BAD17BTest;
    
    @Autowired
    private BAD_AC_BAD17CTest bad_AC_BAD17CTest;
    
    @Autowired
    private BAD_AC_BAD18Test bad_AC_BAD18Test;
    
    @Autowired
    private BAD_AC_BAD19Test bad_AC_BAD19Test;
    
    @Autowired
    private BAD_AC_BAD20ATest bad_AC_BAD20ATest;
    
    @Autowired
    private BAD_AC_BAD20BTest bad_AC_BAD20BTest;
    
    @Autowired
    private BAD_AC_BAD20CTest bad_AC_BAD20CTest;
    
    @Autowired
    private BAD_AC_BAD21Test bad_AC_BAD21Test;
    
    @Autowired
    private BAD_AC_BAD22Test bad_AC_BAD22Test;
    
    @Autowired
    private BAD_AC_BAD23ATest bad_AC_BAD23ATest;
    
    @Autowired
    private BAD_AC_BAD23BTest bad_AC_BAD23BTest;
    
    @Autowired
    private BAD_AC_BAD23CTest bad_AC_BAD23CTest;
    
    @Autowired
    private BAD_AC_BAD24Test bad_AC_BAD24Test;
    
    @Autowired
    private BAD_AC_BAD25Test bad_AC_BAD25Test;
    
    @Autowired
    private BAD_AC_BAD11ATest bad_AC_BAD11ATest;
    
    @Autowired
    private BAD_AC_BAD11BTest bad_AC_BAD11BTest;
    
    @Autowired
    private BAD_AC_BAD11CTest bad_AC_BAD11CTest;
    
    @Autowired
    private BAD_AC_BAD11DTest bad_AC_BAD11DTest;
    
    @Autowired
    private BAD_AC_BAD12ATest bad_AC_BAD12ATest;
    
    @Autowired
    private BAD_AC_BAD12BTest bad_AC_BAD12BTest;
    
    @Autowired
    private BAD_AC_BAD12CTest bad_AC_BAD12CTest;
    
    @Autowired
    private BAD_AC_BAD12DTest bad_AC_BAD12DTest;
    
    @Autowired
    private BAD_AC_BAD12ETest bad_AC_BAD12ETest;
    
    @Autowired
    private BAD_AC_BAD12FTest bad_AC_BAD12FTest;
    
    @Autowired
    private BAD_AC_BAD12GTest bad_AC_BAD12GTest;
    
    @Autowired
    private BAD_AC_BAD13Test bad_AC_BAD13Test;
    
    @Autowired
    private BAD_AC_BAD14CTest bad_AC_BAD14CTest;
    
    @Autowired
    private BAD_AC_BAD14DTest bad_AC_BAD14DTest;
    
    @Autowired
    private BAD_AC_BAD14KTest bad_AC_BAD14KTest;
    
    @Autowired
    private BAD_AC_BAD15ATest bad_AC_BAD15ATest;
    
    @Autowired
    private BAD_AC_BAD15BTest bad_AC_BAD15BTest;
    
    @Autowired
    private BAD_AC_BAD15CTest bad_AC_BAD15CTest;
    
    @Autowired
    private BAD_AC_BAD16ATest bad_AC_BAD16ATest;
    
    @Autowired
    private BAD_AC_BAD16BTest bad_AC_BAD16BTest;
    
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
    public void _001_badAC_BAD1() {
        bad_AC_BAD1Test.test(scopeId);
    }
        @Test
    public void _002_badAC_BAD26() {
        bad_AC_BAD26Test.test(scopeId);
    }
        @Test
    public void _003_badAC_BAD27() {
        bad_AC_BAD27Test.test(scopeId);
    }
        @Test
    public void _004_badAC_BAD2A() {
        bad_AC_BAD2ATest.test(scopeId);
    }
        @Test
    public void _005_badAC_BAD2B() {
        bad_AC_BAD2BTest.test(scopeId);
    }
        @Test
    public void _006_badAC_BAD3() {
        bad_AC_BAD3Test.test(scopeId);
    }
        @Test
    public void _007_badAC_BAD4() {
        bad_AC_BAD4Test.test(scopeId);
    }
        @Test
    public void _008_badAC_BAD5() {
        bad_AC_BAD5Test.test(scopeId);
    }
        @Test
    public void _009_badAC_BAD6A() {
        bad_AC_BAD6ATest.test(scopeId);
    }
        @Test
    public void _0010_badAC_BAD6B() {
        bad_AC_BAD6BTest.test(scopeId);
    }
        @Test
    public void _0011_badAC_BAD7A() {
        bad_AC_BAD7ATest.test(scopeId);
    }
        @Test
    public void _0012_badAC_BAD7B() {
        bad_AC_BAD7BTest.test(scopeId);
    }
        @Test
    public void _0013_badAC_BAD17A() {
        bad_AC_BAD17ATest.test(scopeId);
    }
        @Test
    public void _0014_badAC_BAD17B() {
        bad_AC_BAD17BTest.test(scopeId);
    }
        @Test
    public void _0015_badAC_BAD17C() {
        bad_AC_BAD17CTest.test(scopeId);
    }
        @Test
    public void _0016_badAC_BAD18() {
        bad_AC_BAD18Test.test(scopeId);
    }
        @Test
    public void _0017_badAC_BAD19() {
        bad_AC_BAD19Test.test(scopeId);
    }
        @Test
    public void _0018_badAC_BAD20A() {
        bad_AC_BAD20ATest.test(scopeId);
    }
        @Test
    public void _0019_badAC_BAD20B() {
        bad_AC_BAD20BTest.test(scopeId);
    }
        @Test
    public void _0020_badAC_BAD20C() {
        bad_AC_BAD20CTest.test(scopeId);
    }
        @Test
    public void _0021_badAC_BAD21() {
        bad_AC_BAD21Test.test(scopeId);
    }
        @Test
    public void _0022_badAC_BAD22() {
        bad_AC_BAD22Test.test(scopeId);
    }
        @Test
    public void _0023_badAC_BAD23A() {
        bad_AC_BAD23ATest.test(scopeId);
    }
        @Test
    public void _0024_badAC_BAD23B() {
        bad_AC_BAD23BTest.test(scopeId);
    }
        @Test
    public void _0025_badAC_BAD23C() {
        bad_AC_BAD23CTest.test(scopeId);
    }
        @Test
    public void _0026_badAC_BAD24() {
        bad_AC_BAD24Test.test(scopeId);
    }
        @Test
    public void _0027_badAC_BAD25() {
        bad_AC_BAD25Test.test(scopeId);
    }
        @Test
    public void _0028_badAC_BAD11A() {
        bad_AC_BAD11ATest.test(scopeId);
    }
        @Test
    public void _0029_badAC_BAD11B() {
        bad_AC_BAD11BTest.test(scopeId);
    }
        @Test
    public void _0030_badAC_BAD11C() {
        bad_AC_BAD11CTest.test(scopeId);
    }
        @Test
    public void _0031_badAC_BAD11D() {
        bad_AC_BAD11DTest.test(scopeId);
    }
        @Test
    public void _0032_badAC_BAD12A() {
        bad_AC_BAD12ATest.test(scopeId);
    }
        @Test
    public void _0033_badAC_BAD12B() {
        bad_AC_BAD12BTest.test(scopeId);
    }
        @Test
    public void _0034_badAC_BAD12C() {
        bad_AC_BAD12CTest.test(scopeId);
    }
        @Test
    public void _0035_badAC_BAD12D() {
        bad_AC_BAD12DTest.test(scopeId);
    }
        @Test
    public void _0036_badAC_BAD12E() {
        bad_AC_BAD12ETest.test(scopeId);
    }
        @Test
    public void _0037_badAC_BAD12F() {
        bad_AC_BAD12FTest.test(scopeId);
    }
        @Test
    public void _0038_badAC_BAD12G() {
        bad_AC_BAD12GTest.test(scopeId);
    }
        @Test
    public void _0039_badAC_BAD13() {
        bad_AC_BAD13Test.test(scopeId);
    }
        @Test
    public void _0040_badAC_BAD14C() {
        bad_AC_BAD14CTest.test(scopeId);
    }
        @Test
    public void _0041_badAC_BAD14D() {
        bad_AC_BAD14DTest.test(scopeId);
    }
        @Test
    public void _0042_badAC_BAD14K() {
        bad_AC_BAD14KTest.test(scopeId);
    }
        @Test
    public void _0043_badAC_BAD15A() {
        bad_AC_BAD15ATest.test(scopeId);
    }
        @Test
    public void _0044_badAC_BAD15B() {
        bad_AC_BAD15BTest.test(scopeId);
    }
        @Test
    public void _0045_badAC_BAD15C() {
        bad_AC_BAD15CTest.test(scopeId);
    }
        @Test
    public void _0046_badAC_BAD16A() {
        bad_AC_BAD16ATest.test(scopeId);
    }
        @Test
    public void _0047_badAC_BAD16B() {
        bad_AC_BAD16BTest.test(scopeId);
    }
    }
