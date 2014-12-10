package eu.factorx.awac.buisness.bad.event;

import com.fasterxml.jackson.databind.JsonNode;
import eu.factorx.awac.controllers.SecuredController;
import eu.factorx.awac.dto.myrmex.get.ProductDTO;
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
    private BAD_AEV_BAD1ATest bad_AEV_BAD1ATest;
    
    @Autowired
    private BAD_AEV_BAD1BTest bad_AEV_BAD1BTest;
    
    @Autowired
    private BAD_AEV_BAD1CTest bad_AEV_BAD1CTest;
    
    @Autowired
    private BAD_AEV_BAD1DTest bad_AEV_BAD1DTest;
    
    @Autowired
    private BAD_AEV_BAD1ETest bad_AEV_BAD1ETest;
    
    @Autowired
    private BAD_AEV_BAD1FTest bad_AEV_BAD1FTest;
    
    @Autowired
    private BAD_AEV_BAD2ATest bad_AEV_BAD2ATest;
    
    @Autowired
    private BAD_AEV_BAD3BTest bad_AEV_BAD3BTest;
    
    @Autowired
    private BAD_AEV_BAD4ATest bad_AEV_BAD4ATest;
    
    @Autowired
    private BAD_AEV_BAD4BTest bad_AEV_BAD4BTest;
    
    @Autowired
    private BAD_AEV_BAD4CTest bad_AEV_BAD4CTest;
    
    @Autowired
    private BAD_AEV_BAD4DTest bad_AEV_BAD4DTest;
    
    @Autowired
    private BAD_AEV_BAD4ETest bad_AEV_BAD4ETest;
    
    @Autowired
    private BAD_AEV_BAD4FTest bad_AEV_BAD4FTest;
    
    @Autowired
    private BAD_AEV_BAD4GTest bad_AEV_BAD4GTest;
    
    @Autowired
    private BAD_AEV_BAD5Test bad_AEV_BAD5Test;
    
    @Autowired
    private BAD_AEV_BAD6Test bad_AEV_BAD6Test;
    
    @Autowired
    private BAD_AEV_BAD7ATest bad_AEV_BAD7ATest;
    
    @Autowired
    private BAD_AEV_BAD7BTest bad_AEV_BAD7BTest;
    
    @Autowired
    private BAD_AEV_BAD7CTest bad_AEV_BAD7CTest;
    
    @Autowired
    private BAD_AEV_BAD7DTest bad_AEV_BAD7DTest;
    
    @Autowired
    private BAD_AEV_BAD7ETest bad_AEV_BAD7ETest;
    
    @Autowired
    private BAD_AEV_BAD8Test bad_AEV_BAD8Test;
    
    @Autowired
    private BAD_AEV_BAD9Test bad_AEV_BAD9Test;
    
    @Autowired
    private BAD_AEV_BAD10ATest bad_AEV_BAD10ATest;
    
    @Autowired
    private BAD_AEV_BAD10BTest bad_AEV_BAD10BTest;
    
    @Autowired
    private BAD_AEV_BAD10CTest bad_AEV_BAD10CTest;
    
    @Autowired
    private BAD_AEV_BAD10DTest bad_AEV_BAD10DTest;
    
    @Autowired
    private BAD_AEV_BAD10ETest bad_AEV_BAD10ETest;
    
    @Autowired
    private BAD_AEV_BAD10FTest bad_AEV_BAD10FTest;
    
    @Autowired
    private BAD_AEV_BAD11ATest bad_AEV_BAD11ATest;
    
    @Autowired
    private BAD_AEV_BAD11BTest bad_AEV_BAD11BTest;
    
    @Autowired
    private BAD_AEV_BAD11CTest bad_AEV_BAD11CTest;
    
    @Autowired
    private BAD_AEV_BAD11DTest bad_AEV_BAD11DTest;
    
    @Autowired
    private BAD_AEV_BAD12Test bad_AEV_BAD12Test;
    
    @Autowired
    private BAD_AEV_BAD13ATest bad_AEV_BAD13ATest;
    
    @Autowired
    private BAD_AEV_BAD13BTest bad_AEV_BAD13BTest;
    
    @Autowired
    private BAD_AEV_BAD13CTest bad_AEV_BAD13CTest;
    
    @Test
    public void _000_initialize() {


    // ConnectionFormDTO
    ConnectionFormDTO cfDto = new ConnectionFormDTO("user40", "password", InterfaceTypeCode.EVENT.getKey(), "");

    /*
    start with a percentage too big => except an error

    */
    ProductDTO dto = new ProductDTO();

    dto.setName("productname");

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
        eu.factorx.awac.controllers.routes.ref.ProductController.create(),
        saveFakeRequest
    ); // callAction

    //analyse result
    // expecting an HTTP 200 return code
    assertEquals(200, status(result));


    //analyse result
    ProductDTO resultDTO = getDTO(result, ProductDTO.class);

    assertNotNull(resultDTO.getId());

    scopeId = resultDTO.getId();

    }
        @Test
    public void _001_badAEV_BAD1A() {
        bad_AEV_BAD1ATest.test(scopeId);
    }
        @Test
    public void _002_badAEV_BAD1B() {
        bad_AEV_BAD1BTest.test(scopeId);
    }
        @Test
    public void _003_badAEV_BAD1C() {
        bad_AEV_BAD1CTest.test(scopeId);
    }
        @Test
    public void _004_badAEV_BAD1D() {
        bad_AEV_BAD1DTest.test(scopeId);
    }
        @Test
    public void _005_badAEV_BAD1E() {
        bad_AEV_BAD1ETest.test(scopeId);
    }
        @Test
    public void _006_badAEV_BAD1F() {
        bad_AEV_BAD1FTest.test(scopeId);
    }
        @Test
    public void _007_badAEV_BAD2A() {
        bad_AEV_BAD2ATest.test(scopeId);
    }
        @Test
    public void _008_badAEV_BAD3B() {
        bad_AEV_BAD3BTest.test(scopeId);
    }
        @Test
    public void _009_badAEV_BAD4A() {
        bad_AEV_BAD4ATest.test(scopeId);
    }
        @Test
    public void _0010_badAEV_BAD4B() {
        bad_AEV_BAD4BTest.test(scopeId);
    }
        @Test
    public void _0011_badAEV_BAD4C() {
        bad_AEV_BAD4CTest.test(scopeId);
    }
        @Test
    public void _0012_badAEV_BAD4D() {
        bad_AEV_BAD4DTest.test(scopeId);
    }
        @Test
    public void _0013_badAEV_BAD4E() {
        bad_AEV_BAD4ETest.test(scopeId);
    }
        @Test
    public void _0014_badAEV_BAD4F() {
        bad_AEV_BAD4FTest.test(scopeId);
    }
        @Test
    public void _0015_badAEV_BAD4G() {
        bad_AEV_BAD4GTest.test(scopeId);
    }
        @Test
    public void _0016_badAEV_BAD5() {
        bad_AEV_BAD5Test.test(scopeId);
    }
        @Test
    public void _0017_badAEV_BAD6() {
        bad_AEV_BAD6Test.test(scopeId);
    }
        @Test
    public void _0018_badAEV_BAD7A() {
        bad_AEV_BAD7ATest.test(scopeId);
    }
        @Test
    public void _0019_badAEV_BAD7B() {
        bad_AEV_BAD7BTest.test(scopeId);
    }
        @Test
    public void _0020_badAEV_BAD7C() {
        bad_AEV_BAD7CTest.test(scopeId);
    }
        @Test
    public void _0021_badAEV_BAD7D() {
        bad_AEV_BAD7DTest.test(scopeId);
    }
        @Test
    public void _0022_badAEV_BAD7E() {
        bad_AEV_BAD7ETest.test(scopeId);
    }
        @Test
    public void _0023_badAEV_BAD8() {
        bad_AEV_BAD8Test.test(scopeId);
    }
        @Test
    public void _0024_badAEV_BAD9() {
        bad_AEV_BAD9Test.test(scopeId);
    }
        @Test
    public void _0025_badAEV_BAD10A() {
        bad_AEV_BAD10ATest.test(scopeId);
    }
        @Test
    public void _0026_badAEV_BAD10B() {
        bad_AEV_BAD10BTest.test(scopeId);
    }
        @Test
    public void _0027_badAEV_BAD10C() {
        bad_AEV_BAD10CTest.test(scopeId);
    }
        @Test
    public void _0028_badAEV_BAD10D() {
        bad_AEV_BAD10DTest.test(scopeId);
    }
        @Test
    public void _0029_badAEV_BAD10E() {
        bad_AEV_BAD10ETest.test(scopeId);
    }
        @Test
    public void _0030_badAEV_BAD10F() {
        bad_AEV_BAD10FTest.test(scopeId);
    }
        @Test
    public void _0031_badAEV_BAD11A() {
        bad_AEV_BAD11ATest.test(scopeId);
    }
        @Test
    public void _0032_badAEV_BAD11B() {
        bad_AEV_BAD11BTest.test(scopeId);
    }
        @Test
    public void _0033_badAEV_BAD11C() {
        bad_AEV_BAD11CTest.test(scopeId);
    }
        @Test
    public void _0034_badAEV_BAD11D() {
        bad_AEV_BAD11DTest.test(scopeId);
    }
        @Test
    public void _0035_badAEV_BAD12() {
        bad_AEV_BAD12Test.test(scopeId);
    }
        @Test
    public void _0036_badAEV_BAD13A() {
        bad_AEV_BAD13ATest.test(scopeId);
    }
        @Test
    public void _0037_badAEV_BAD13B() {
        bad_AEV_BAD13BTest.test(scopeId);
    }
        @Test
    public void _0038_badAEV_BAD13C() {
        bad_AEV_BAD13CTest.test(scopeId);
    }
    }
