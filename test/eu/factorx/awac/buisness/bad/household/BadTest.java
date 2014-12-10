package eu.factorx.awac.buisness.bad.household;

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
    private BAD_AM_BAD3DTest bad_AM_BAD3DTest;
    
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
        ConnectionFormDTO cfDto = new ConnectionFormDTO("user30", "password", InterfaceTypeCode.HOUSEHOLD.getKey(), "");

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
    public void _AAB_badAM_BAD1A() {
        bad_AM_BAD1ATest.test(scopeId);
    }
        @Test
    public void _AAC_badAM_BAD1B() {
        bad_AM_BAD1BTest.test(scopeId);
    }
        @Test
    public void _AAD_badAM_BAD1C() {
        bad_AM_BAD1CTest.test(scopeId);
    }
        @Test
    public void _AAE_badAM_BAD1D() {
        bad_AM_BAD1DTest.test(scopeId);
    }
        @Test
    public void _AAF_badAM_BAD1E() {
        bad_AM_BAD1ETest.test(scopeId);
    }
        @Test
    public void _AAG_badAM_BAD1F() {
        bad_AM_BAD1FTest.test(scopeId);
    }
        @Test
    public void _AAH_badAM_BAD1G() {
        bad_AM_BAD1GTest.test(scopeId);
    }
        @Test
    public void _AAI_badAM_BAD1H() {
        bad_AM_BAD1HTest.test(scopeId);
    }
        @Test
    public void _AAJ_badAM_BAD1I() {
        bad_AM_BAD1ITest.test(scopeId);
    }
        @Test
    public void _AAK_badAM_BAD1J() {
        bad_AM_BAD1JTest.test(scopeId);
    }
        @Test
    public void _AAL_badAM_BAD2A() {
        bad_AM_BAD2ATest.test(scopeId);
    }
        @Test
    public void _AAU_badAM_BAD3D() {
        bad_AM_BAD3DTest.test(scopeId);
    }
        @Test
    public void _AAV_badAM_BAD4() {
        bad_AM_BAD4Test.test(scopeId);
    }
        @Test
    public void _AAW_badAM_BAD5() {
        bad_AM_BAD5Test.test(scopeId);
    }
        @Test
    public void _AAX_badAM_BAD6() {
        bad_AM_BAD6Test.test(scopeId);
    }
        @Test
    public void _AAY_badAM_BAD7() {
        bad_AM_BAD7Test.test(scopeId);
    }
        @Test
    public void _ABA_badAM_BAD8() {
        bad_AM_BAD8Test.test(scopeId);
    }
        @Test
    public void _ABB_badAM_BAD9() {
        bad_AM_BAD9Test.test(scopeId);
    }
        @Test
    public void _ABC_badAM_BAD10() {
        bad_AM_BAD10Test.test(scopeId);
    }
        @Test
    public void _ABD_badAM_BAD11() {
        bad_AM_BAD11Test.test(scopeId);
    }
        @Test
    public void _ABE_badAM_BAD12A() {
        bad_AM_BAD12ATest.test(scopeId);
    }
        @Test
    public void _ABF_badAM_BAD12B() {
        bad_AM_BAD12BTest.test(scopeId);
    }
        @Test
    public void _ABG_badAM_BAD12C() {
        bad_AM_BAD12CTest.test(scopeId);
    }
        @Test
    public void _ABH_badAM_BAD12D() {
        bad_AM_BAD12DTest.test(scopeId);
    }
        @Test
    public void _ABI_badAM_BAD12E() {
        bad_AM_BAD12ETest.test(scopeId);
    }
        @Test
    public void _ABJ_badAM_BAD12F() {
        bad_AM_BAD12FTest.test(scopeId);
    }
        @Test
    public void _ABK_badAM_BAD13A() {
        bad_AM_BAD13ATest.test(scopeId);
    }
        @Test
    public void _ABL_badAM_BAD13B() {
        bad_AM_BAD13BTest.test(scopeId);
    }
        @Test
    public void _ABM_badAM_BAD13C() {
        bad_AM_BAD13CTest.test(scopeId);
    }
        @Test
    public void _ABN_badAM_BAD14A() {
        bad_AM_BAD14ATest.test(scopeId);
    }
        @Test
    public void _ABO_badAM_BAD14B() {
        bad_AM_BAD14BTest.test(scopeId);
    }
        @Test
    public void _ABP_badAM_BAD14C() {
        bad_AM_BAD14CTest.test(scopeId);
    }
    }
