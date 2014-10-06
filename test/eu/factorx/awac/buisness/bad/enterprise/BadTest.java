package eu.factorx.awac.buisness.bad.enterprise;

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
    private BAD_AE_BAD1Test bad_AE_BAD1Test;
    
    @Autowired
    private BAD_AE_BAD50Test bad_AE_BAD50Test;
    
    @Autowired
    private BAD_AE_BAD51Test bad_AE_BAD51Test;
    
    @Autowired
    private BAD_AE_BAD2ATest bad_AE_BAD2ATest;
    
    @Autowired
    private BAD_AE_BAD2BTest bad_AE_BAD2BTest;
    
    @Autowired
    private BAD_AE_BAD3Test bad_AE_BAD3Test;
    
    @Autowired
    private BAD_AE_BAD4Test bad_AE_BAD4Test;
    
    @Autowired
    private BAD_AE_BAD5Test bad_AE_BAD5Test;
    
    @Autowired
    private BAD_AE_BAD40Test bad_AE_BAD40Test;
    
    @Autowired
    private BAD_AE_BAD6ATest bad_AE_BAD6ATest;
    
    @Autowired
    private BAD_AE_BAD6BTest bad_AE_BAD6BTest;
    
    @Autowired
    private BAD_AE_BAD41ATest bad_AE_BAD41ATest;
    
    @Autowired
    private BAD_AE_BAD41BTest bad_AE_BAD41BTest;
    
    @Autowired
    private BAD_AE_BAD41CTest bad_AE_BAD41CTest;
    
    @Autowired
    private BAD_AE_BAD42Test bad_AE_BAD42Test;
    
    @Autowired
    private BAD_AE_BAD43Test bad_AE_BAD43Test;
    
    @Autowired
    private BAD_AE_BAD44ATest bad_AE_BAD44ATest;
    
    @Autowired
    private BAD_AE_BAD44BTest bad_AE_BAD44BTest;
    
    @Autowired
    private BAD_AE_BAD44CTest bad_AE_BAD44CTest;
    
    @Autowired
    private BAD_AE_BAD45Test bad_AE_BAD45Test;
    
    @Autowired
    private BAD_AE_BAD46Test bad_AE_BAD46Test;
    
    @Autowired
    private BAD_AE_BAD47ATest bad_AE_BAD47ATest;
    
    @Autowired
    private BAD_AE_BAD47BTest bad_AE_BAD47BTest;
    
    @Autowired
    private BAD_AE_BAD47CTest bad_AE_BAD47CTest;
    
    @Autowired
    private BAD_AE_BAD48Test bad_AE_BAD48Test;
    
    @Autowired
    private BAD_AE_BAD49Test bad_AE_BAD49Test;
    
    @Autowired
    private BAD_AE_BAD12ATest bad_AE_BAD12ATest;
    
    @Autowired
    private BAD_AE_BAD12BTest bad_AE_BAD12BTest;
    
    @Autowired
    private BAD_AE_BAD12CTest bad_AE_BAD12CTest;
    
    @Autowired
    private BAD_AE_BAD12DTest bad_AE_BAD12DTest;
    
    @Autowired
    private BAD_AE_BAD12ETest bad_AE_BAD12ETest;
    
    @Autowired
    private BAD_AE_BAD12FTest bad_AE_BAD12FTest;
    
    @Autowired
    private BAD_AE_BAD12GTest bad_AE_BAD12GTest;
    
    @Autowired
    private BAD_AE_BAD12HTest bad_AE_BAD12HTest;
    
    @Autowired
    private BAD_AE_BAD12ITest bad_AE_BAD12ITest;
    
    @Autowired
    private BAD_AE_BAD12JTest bad_AE_BAD12JTest;
    
    @Autowired
    private BAD_AE_BAD12KTest bad_AE_BAD12KTest;
    
    @Autowired
    private BAD_AE_BAD12LTest bad_AE_BAD12LTest;
    
    @Autowired
    private BAD_AE_BAD12MTest bad_AE_BAD12MTest;
    
    @Autowired
    private BAD_AE_BAD12NTest bad_AE_BAD12NTest;
    
    @Autowired
    private BAD_AE_BAD13CTest bad_AE_BAD13CTest;
    
    @Autowired
    private BAD_AE_BAD14Test bad_AE_BAD14Test;
    
    @Autowired
    private BAD_AE_BAD15CTest bad_AE_BAD15CTest;
    
    @Autowired
    private BAD_AE_BAD27ATest bad_AE_BAD27ATest;
    
    @Autowired
    private BAD_AE_BAD27BTest bad_AE_BAD27BTest;
    
    @Autowired
    private BAD_AE_BAD27LTest bad_AE_BAD27LTest;
    
    @Autowired
    private BAD_AE_BAD28Test bad_AE_BAD28Test;
    
    @Autowired
    private BAD_AE_BAD16ATest bad_AE_BAD16ATest;
    
    @Autowired
    private BAD_AE_BAD16BTest bad_AE_BAD16BTest;
    
    @Autowired
    private BAD_AE_BAD16CTest bad_AE_BAD16CTest;
    
    @Autowired
    private BAD_AE_BAD16ETest bad_AE_BAD16ETest;
    
    @Autowired
    private BAD_AE_BAD17ATest bad_AE_BAD17ATest;
    
    @Autowired
    private BAD_AE_BAD17BTest bad_AE_BAD17BTest;
    
    @Autowired
    private BAD_AE_BAD17CTest bad_AE_BAD17CTest;
    
    @Autowired
    private BAD_AE_BAD17DTest bad_AE_BAD17DTest;
    
    @Autowired
    private BAD_AE_BAD17ETest bad_AE_BAD17ETest;
    
    @Autowired
    private BAD_AE_BAD17FTest bad_AE_BAD17FTest;
    
    @Autowired
    private BAD_AE_BAD17GTest bad_AE_BAD17GTest;
    
    @Autowired
    private BAD_AE_BAD17HTest bad_AE_BAD17HTest;
    
    @Autowired
    private BAD_AE_BAD17ITest bad_AE_BAD17ITest;
    
    @Autowired
    private BAD_AE_BAD18BTest bad_AE_BAD18BTest;
    
    @Autowired
    private BAD_AE_BAD18ETest bad_AE_BAD18ETest;
    
    @Autowired
    private BAD_AE_BAD19ATest bad_AE_BAD19ATest;
    
    @Autowired
    private BAD_AE_BAD19BTest bad_AE_BAD19BTest;
    
    @Autowired
    private BAD_AE_BAD19CTest bad_AE_BAD19CTest;
    
    @Autowired
    private BAD_AE_BAD19DTest bad_AE_BAD19DTest;
    
    @Autowired
    private BAD_AE_BAD19ETest bad_AE_BAD19ETest;
    
    @Autowired
    private BAD_AE_BAD20Test bad_AE_BAD20Test;
    
    @Autowired
    private BAD_AE_BAD21Test bad_AE_BAD21Test;
    
    @Autowired
    private BAD_AE_BAD22Test bad_AE_BAD22Test;
    
    @Autowired
    private BAD_AE_BAD23Test bad_AE_BAD23Test;
    
    @Autowired
    private BAD_AE_BAD24Test bad_AE_BAD24Test;
    
    @Autowired
    private BAD_AE_BAD25Test bad_AE_BAD25Test;
    
    @Autowired
    private BAD_AE_BAD26ATest bad_AE_BAD26ATest;
    
    @Autowired
    private BAD_AE_BAD26BTest bad_AE_BAD26BTest;
    
    @Autowired
    private BAD_AE_BAD29ATest bad_AE_BAD29ATest;
    
    @Autowired
    private BAD_AE_BAD29BTest bad_AE_BAD29BTest;
    
    @Autowired
    private BAD_AE_BAD29CTest bad_AE_BAD29CTest;
    
    @Autowired
    private BAD_AE_BAD30Test bad_AE_BAD30Test;
    
    @Autowired
    private BAD_AE_BAD37ATest bad_AE_BAD37ATest;
    
    @Autowired
    private BAD_AE_BAD37BTest bad_AE_BAD37BTest;
    
    @Autowired
    private BAD_AE_BAD37CTest bad_AE_BAD37CTest;
    
    @Autowired
    private BAD_AE_BAD37DTest bad_AE_BAD37DTest;
    
    @Autowired
    private BAD_AE_BAD37ETest bad_AE_BAD37ETest;
    
    @Autowired
    private BAD_AE_BAD38ATest bad_AE_BAD38ATest;
    
    @Autowired
    private BAD_AE_BAD38BTest bad_AE_BAD38BTest;
    
    @Autowired
    private BAD_AE_BAD38CTest bad_AE_BAD38CTest;
    
    @Autowired
    private BAD_AE_BAD38DTest bad_AE_BAD38DTest;
    
    @Autowired
    private BAD_AE_BAD38ETest bad_AE_BAD38ETest;
    
    @Autowired
    private BAD_AE_BAD39ATest bad_AE_BAD39ATest;
    
    @Autowired
    private BAD_AE_BAD39BTest bad_AE_BAD39BTest;
    
    @Autowired
    private BAD_AE_BAD31ATest bad_AE_BAD31ATest;
    
    @Autowired
    private BAD_AE_BAD31BTest bad_AE_BAD31BTest;
    
    @Autowired
    private BAD_AE_BAD31CTest bad_AE_BAD31CTest;
    
    @Autowired
    private BAD_AE_BAD31DTest bad_AE_BAD31DTest;
    
    @Autowired
    private BAD_AE_BAD31ETest bad_AE_BAD31ETest;
    
    @Autowired
    private BAD_AE_BAD31FTest bad_AE_BAD31FTest;
    
    @Autowired
    private BAD_AE_BAD31GTest bad_AE_BAD31GTest;
    
    @Autowired
    private BAD_AE_BAD31HTest bad_AE_BAD31HTest;
    
    @Autowired
    private BAD_AE_BAD31ITest bad_AE_BAD31ITest;
    
    @Autowired
    private BAD_AE_BAD32ATest bad_AE_BAD32ATest;
    
    @Autowired
    private BAD_AE_BAD32CTest bad_AE_BAD32CTest;
    
    @Autowired
    private BAD_AE_BAD32DTest bad_AE_BAD32DTest;
    
    @Autowired
    private BAD_AE_BAD32FTest bad_AE_BAD32FTest;
    
    @Autowired
    private BAD_AE_BAD33ATest bad_AE_BAD33ATest;
    
    @Autowired
    private BAD_AE_BAD33BTest bad_AE_BAD33BTest;
    
    @Autowired
    private BAD_AE_BAD33CTest bad_AE_BAD33CTest;
    
    @Autowired
    private BAD_AE_BAD33DTest bad_AE_BAD33DTest;
    
    @Autowired
    private BAD_AE_BAD33ETest bad_AE_BAD33ETest;
    
    @Autowired
    private BAD_AE_BAD34ATest bad_AE_BAD34ATest;
    
    @Autowired
    private BAD_AE_BAD34BTest bad_AE_BAD34BTest;
    
    @Autowired
    private BAD_AE_BAD34CTest bad_AE_BAD34CTest;
    
    @Autowired
    private BAD_AE_BAD34DTest bad_AE_BAD34DTest;
    
    @Autowired
    private BAD_AE_BAD34ETest bad_AE_BAD34ETest;
    
    @Autowired
    private BAD_AE_BAD35ATest bad_AE_BAD35ATest;
    
    @Autowired
    private BAD_AE_BAD35BTest bad_AE_BAD35BTest;
    
    @Autowired
    private BAD_AE_BAD35CTest bad_AE_BAD35CTest;
    
    @Autowired
    private BAD_AE_BAD36Test bad_AE_BAD36Test;
    
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
    public void _001_badAE_BAD1() {
        bad_AE_BAD1Test.test(scopeId);
    }
        @Test
    public void _002_badAE_BAD50() {
        bad_AE_BAD50Test.test(scopeId);
    }
        @Test
    public void _003_badAE_BAD51() {
        bad_AE_BAD51Test.test(scopeId);
    }
        @Test
    public void _004_badAE_BAD2A() {
        bad_AE_BAD2ATest.test(scopeId);
    }
        @Test
    public void _005_badAE_BAD2B() {
        bad_AE_BAD2BTest.test(scopeId);
    }
        @Test
    public void _006_badAE_BAD3() {
        bad_AE_BAD3Test.test(scopeId);
    }
        @Test
    public void _007_badAE_BAD4() {
        bad_AE_BAD4Test.test(scopeId);
    }
        @Test
    public void _008_badAE_BAD5() {
        bad_AE_BAD5Test.test(scopeId);
    }
        @Test
    public void _009_badAE_BAD40() {
        bad_AE_BAD40Test.test(scopeId);
    }
        @Test
    public void _0010_badAE_BAD6A() {
        bad_AE_BAD6ATest.test(scopeId);
    }
        @Test
    public void _0011_badAE_BAD6B() {
        bad_AE_BAD6BTest.test(scopeId);
    }
        @Test
    public void _0012_badAE_BAD41A() {
        bad_AE_BAD41ATest.test(scopeId);
    }
        @Test
    public void _0013_badAE_BAD41B() {
        bad_AE_BAD41BTest.test(scopeId);
    }
        @Test
    public void _0014_badAE_BAD41C() {
        bad_AE_BAD41CTest.test(scopeId);
    }
        @Test
    public void _0015_badAE_BAD42() {
        bad_AE_BAD42Test.test(scopeId);
    }
        @Test
    public void _0016_badAE_BAD43() {
        bad_AE_BAD43Test.test(scopeId);
    }
        @Test
    public void _0017_badAE_BAD44A() {
        bad_AE_BAD44ATest.test(scopeId);
    }
        @Test
    public void _0018_badAE_BAD44B() {
        bad_AE_BAD44BTest.test(scopeId);
    }
        @Test
    public void _0019_badAE_BAD44C() {
        bad_AE_BAD44CTest.test(scopeId);
    }
        @Test
    public void _0020_badAE_BAD45() {
        bad_AE_BAD45Test.test(scopeId);
    }
        @Test
    public void _0021_badAE_BAD46() {
        bad_AE_BAD46Test.test(scopeId);
    }
        @Test
    public void _0022_badAE_BAD47A() {
        bad_AE_BAD47ATest.test(scopeId);
    }
        @Test
    public void _0023_badAE_BAD47B() {
        bad_AE_BAD47BTest.test(scopeId);
    }
        @Test
    public void _0024_badAE_BAD47C() {
        bad_AE_BAD47CTest.test(scopeId);
    }
        @Test
    public void _0025_badAE_BAD48() {
        bad_AE_BAD48Test.test(scopeId);
    }
        @Test
    public void _0026_badAE_BAD49() {
        bad_AE_BAD49Test.test(scopeId);
    }
        @Test
    public void _0027_badAE_BAD12A() {
        bad_AE_BAD12ATest.test(scopeId);
    }
        @Test
    public void _0028_badAE_BAD12B() {
        bad_AE_BAD12BTest.test(scopeId);
    }
        @Test
    public void _0029_badAE_BAD12C() {
        bad_AE_BAD12CTest.test(scopeId);
    }
        @Test
    public void _0030_badAE_BAD12D() {
        bad_AE_BAD12DTest.test(scopeId);
    }
        @Test
    public void _0031_badAE_BAD12E() {
        bad_AE_BAD12ETest.test(scopeId);
    }
        @Test
    public void _0032_badAE_BAD12F() {
        bad_AE_BAD12FTest.test(scopeId);
    }
        @Test
    public void _0033_badAE_BAD12G() {
        bad_AE_BAD12GTest.test(scopeId);
    }
        @Test
    public void _0034_badAE_BAD12H() {
        bad_AE_BAD12HTest.test(scopeId);
    }
        @Test
    public void _0035_badAE_BAD12I() {
        bad_AE_BAD12ITest.test(scopeId);
    }
        @Test
    public void _0036_badAE_BAD12J() {
        bad_AE_BAD12JTest.test(scopeId);
    }
        @Test
    public void _0037_badAE_BAD12K() {
        bad_AE_BAD12KTest.test(scopeId);
    }
        @Test
    public void _0038_badAE_BAD12L() {
        bad_AE_BAD12LTest.test(scopeId);
    }
        @Test
    public void _0039_badAE_BAD12M() {
        bad_AE_BAD12MTest.test(scopeId);
    }
        @Test
    public void _0040_badAE_BAD12N() {
        bad_AE_BAD12NTest.test(scopeId);
    }
        @Test
    public void _0041_badAE_BAD13C() {
        bad_AE_BAD13CTest.test(scopeId);
    }
        @Test
    public void _0042_badAE_BAD14() {
        bad_AE_BAD14Test.test(scopeId);
    }
        @Test
    public void _0043_badAE_BAD15C() {
        bad_AE_BAD15CTest.test(scopeId);
    }
        @Test
    public void _0044_badAE_BAD27A() {
        bad_AE_BAD27ATest.test(scopeId);
    }
        @Test
    public void _0045_badAE_BAD27B() {
        bad_AE_BAD27BTest.test(scopeId);
    }
        @Test
    public void _0046_badAE_BAD27L() {
        bad_AE_BAD27LTest.test(scopeId);
    }
        @Test
    public void _0047_badAE_BAD28() {
        bad_AE_BAD28Test.test(scopeId);
    }
        @Test
    public void _0048_badAE_BAD16A() {
        bad_AE_BAD16ATest.test(scopeId);
    }
        @Test
    public void _0049_badAE_BAD16B() {
        bad_AE_BAD16BTest.test(scopeId);
    }
        @Test
    public void _0050_badAE_BAD16C() {
        bad_AE_BAD16CTest.test(scopeId);
    }
        @Test
    public void _0051_badAE_BAD16E() {
        bad_AE_BAD16ETest.test(scopeId);
    }
        @Test
    public void _0052_badAE_BAD17A() {
        bad_AE_BAD17ATest.test(scopeId);
    }
        @Test
    public void _0053_badAE_BAD17B() {
        bad_AE_BAD17BTest.test(scopeId);
    }
        @Test
    public void _0054_badAE_BAD17C() {
        bad_AE_BAD17CTest.test(scopeId);
    }
        @Test
    public void _0055_badAE_BAD17D() {
        bad_AE_BAD17DTest.test(scopeId);
    }
        @Test
    public void _0056_badAE_BAD17E() {
        bad_AE_BAD17ETest.test(scopeId);
    }
        @Test
    public void _0057_badAE_BAD17F() {
        bad_AE_BAD17FTest.test(scopeId);
    }
        @Test
    public void _0058_badAE_BAD17G() {
        bad_AE_BAD17GTest.test(scopeId);
    }
        @Test
    public void _0059_badAE_BAD17H() {
        bad_AE_BAD17HTest.test(scopeId);
    }
        @Test
    public void _0060_badAE_BAD17I() {
        bad_AE_BAD17ITest.test(scopeId);
    }
        @Test
    public void _0061_badAE_BAD18B() {
        bad_AE_BAD18BTest.test(scopeId);
    }
        @Test
    public void _0062_badAE_BAD18E() {
        bad_AE_BAD18ETest.test(scopeId);
    }
        @Test
    public void _0063_badAE_BAD19A() {
        bad_AE_BAD19ATest.test(scopeId);
    }
        @Test
    public void _0064_badAE_BAD19B() {
        bad_AE_BAD19BTest.test(scopeId);
    }
        @Test
    public void _0065_badAE_BAD19C() {
        bad_AE_BAD19CTest.test(scopeId);
    }
        @Test
    public void _0066_badAE_BAD19D() {
        bad_AE_BAD19DTest.test(scopeId);
    }
        @Test
    public void _0067_badAE_BAD19E() {
        bad_AE_BAD19ETest.test(scopeId);
    }
        @Test
    public void _0068_badAE_BAD20() {
        bad_AE_BAD20Test.test(scopeId);
    }
        @Test
    public void _0069_badAE_BAD21() {
        bad_AE_BAD21Test.test(scopeId);
    }
        @Test
    public void _0070_badAE_BAD22() {
        bad_AE_BAD22Test.test(scopeId);
    }
        @Test
    public void _0071_badAE_BAD23() {
        bad_AE_BAD23Test.test(scopeId);
    }
        @Test
    public void _0072_badAE_BAD24() {
        bad_AE_BAD24Test.test(scopeId);
    }
        @Test
    public void _0073_badAE_BAD25() {
        bad_AE_BAD25Test.test(scopeId);
    }
        @Test
    public void _0074_badAE_BAD26A() {
        bad_AE_BAD26ATest.test(scopeId);
    }
        @Test
    public void _0075_badAE_BAD26B() {
        bad_AE_BAD26BTest.test(scopeId);
    }
        @Test
    public void _0076_badAE_BAD29A() {
        bad_AE_BAD29ATest.test(scopeId);
    }
        @Test
    public void _0077_badAE_BAD29B() {
        bad_AE_BAD29BTest.test(scopeId);
    }
        @Test
    public void _0078_badAE_BAD29C() {
        bad_AE_BAD29CTest.test(scopeId);
    }
        @Test
    public void _0079_badAE_BAD30() {
        bad_AE_BAD30Test.test(scopeId);
    }
        @Test
    public void _0080_badAE_BAD37A() {
        bad_AE_BAD37ATest.test(scopeId);
    }
        @Test
    public void _0081_badAE_BAD37B() {
        bad_AE_BAD37BTest.test(scopeId);
    }
        @Test
    public void _0082_badAE_BAD37C() {
        bad_AE_BAD37CTest.test(scopeId);
    }
        @Test
    public void _0083_badAE_BAD37D() {
        bad_AE_BAD37DTest.test(scopeId);
    }
        @Test
    public void _0084_badAE_BAD37E() {
        bad_AE_BAD37ETest.test(scopeId);
    }
        @Test
    public void _0085_badAE_BAD38A() {
        bad_AE_BAD38ATest.test(scopeId);
    }
        @Test
    public void _0086_badAE_BAD38B() {
        bad_AE_BAD38BTest.test(scopeId);
    }
        @Test
    public void _0087_badAE_BAD38C() {
        bad_AE_BAD38CTest.test(scopeId);
    }
        @Test
    public void _0088_badAE_BAD38D() {
        bad_AE_BAD38DTest.test(scopeId);
    }
        @Test
    public void _0089_badAE_BAD38E() {
        bad_AE_BAD38ETest.test(scopeId);
    }
        @Test
    public void _0090_badAE_BAD39A() {
        bad_AE_BAD39ATest.test(scopeId);
    }
        @Test
    public void _0091_badAE_BAD39B() {
        bad_AE_BAD39BTest.test(scopeId);
    }
        @Test
    public void _0092_badAE_BAD31A() {
        bad_AE_BAD31ATest.test(scopeId);
    }
        @Test
    public void _0093_badAE_BAD31B() {
        bad_AE_BAD31BTest.test(scopeId);
    }
        @Test
    public void _0094_badAE_BAD31C() {
        bad_AE_BAD31CTest.test(scopeId);
    }
        @Test
    public void _0095_badAE_BAD31D() {
        bad_AE_BAD31DTest.test(scopeId);
    }
        @Test
    public void _0096_badAE_BAD31E() {
        bad_AE_BAD31ETest.test(scopeId);
    }
        @Test
    public void _0097_badAE_BAD31F() {
        bad_AE_BAD31FTest.test(scopeId);
    }
        @Test
    public void _0098_badAE_BAD31G() {
        bad_AE_BAD31GTest.test(scopeId);
    }
        @Test
    public void _0099_badAE_BAD31H() {
        bad_AE_BAD31HTest.test(scopeId);
    }
        @Test
    public void _00100_badAE_BAD31I() {
        bad_AE_BAD31ITest.test(scopeId);
    }
        @Test
    public void _00101_badAE_BAD32A() {
        bad_AE_BAD32ATest.test(scopeId);
    }
        @Test
    public void _00102_badAE_BAD32C() {
        bad_AE_BAD32CTest.test(scopeId);
    }
        @Test
    public void _00103_badAE_BAD32D() {
        bad_AE_BAD32DTest.test(scopeId);
    }
        @Test
    public void _00104_badAE_BAD32F() {
        bad_AE_BAD32FTest.test(scopeId);
    }
        @Test
    public void _00105_badAE_BAD33A() {
        bad_AE_BAD33ATest.test(scopeId);
    }
        @Test
    public void _00106_badAE_BAD33B() {
        bad_AE_BAD33BTest.test(scopeId);
    }
        @Test
    public void _00107_badAE_BAD33C() {
        bad_AE_BAD33CTest.test(scopeId);
    }
        @Test
    public void _00108_badAE_BAD33D() {
        bad_AE_BAD33DTest.test(scopeId);
    }
        @Test
    public void _00109_badAE_BAD33E() {
        bad_AE_BAD33ETest.test(scopeId);
    }
        @Test
    public void _00110_badAE_BAD34A() {
        bad_AE_BAD34ATest.test(scopeId);
    }
        @Test
    public void _00111_badAE_BAD34B() {
        bad_AE_BAD34BTest.test(scopeId);
    }
        @Test
    public void _00112_badAE_BAD34C() {
        bad_AE_BAD34CTest.test(scopeId);
    }
        @Test
    public void _00113_badAE_BAD34D() {
        bad_AE_BAD34DTest.test(scopeId);
    }
        @Test
    public void _00114_badAE_BAD34E() {
        bad_AE_BAD34ETest.test(scopeId);
    }
        @Test
    public void _00115_badAE_BAD35A() {
        bad_AE_BAD35ATest.test(scopeId);
    }
        @Test
    public void _00116_badAE_BAD35B() {
        bad_AE_BAD35BTest.test(scopeId);
    }
        @Test
    public void _00117_badAE_BAD35C() {
        bad_AE_BAD35CTest.test(scopeId);
    }
        @Test
    public void _00118_badAE_BAD36() {
        bad_AE_BAD36Test.test(scopeId);
    }
    }
