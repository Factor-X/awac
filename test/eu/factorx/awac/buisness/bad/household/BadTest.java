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
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
    @Autowired
    private BAD_${bad.getBaseActivityDataCode()}Test bad_${bad.getBaseActivityDataCode()}Test;
    
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
    public void _001_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _002_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _003_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _004_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _005_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _006_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _007_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _008_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _009_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0010_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0011_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0012_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0013_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0014_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0015_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0016_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0017_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0018_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0019_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0020_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0021_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0022_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0023_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0024_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0025_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0026_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0027_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0028_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0029_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0030_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0031_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0032_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0033_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0034_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0035_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0036_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
        @Test
    public void _0037_bad${bad.getBaseActivityDataCode()}() {
        bad_${bad.getBaseActivityDataCode()}Test.test(scopeId);
    }
    }
