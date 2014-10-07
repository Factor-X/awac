package eu.factorx.awac.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import eu.factorx.awac.dto.awac.get.PeriodDTO;
import eu.factorx.awac.dto.awac.get.SiteDTO;
import eu.factorx.awac.dto.awac.post.AssignPeriodToSiteDTO;
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.SiteService;
import eu.factorx.awac.util.MyrmexRuntimeException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;

import static org.junit.Assert.*;
import static play.test.Helpers.callAction;
import static play.test.Helpers.status;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SiteTest extends AbstractBaseModelTest {

    // constant for test 001
    private static final String SITE_NAME = "site name";
    private static final String SITE_DESC = "je suis une description plutôt longue qui fait plus de 255 caractères : Lorem ipsum dolor sit amet, eu mea harum mollis cotidieque, id has mundi interesset. Explicari urbanitas sed ne. In agam nulla pertinax eum. In dictas lobortis est, ad nonumy prompta aliquam vel.Ei verear adipiscing has. Ea pri alterum fastidii, mediocritatem conclusionemque vituperatoribus ne eum. An usu tollit definitionem. Qui ea falli iusto patrioque. Veniam indoctum ex has, hinc ludus graeci has id, per ea lorem temporibus. Pri admodum pericula sapientem ei, sit errem imperdiet adversarium at, ne adhuc pertinacia pri. Eum modus denique et, sea detraxit salutatus ea, mel at nullam aliquam";
    private static final String SITE_NACE = "code nace";
    private static final String SITE_ORG_STRUCUTRE = "organizaiton structure";
    private static final String SITE_ECO_INTEREST = "economic interest";
    private static final String SITE_OPE_POLICY = "operating policy";
    private static final String SITE_ACCOUNTING = "acounting treatment";
    private static final Double SITE_PERCENTAGE_OWNED = 85.98;
    private static final Double SITE_PERCENTAGE_OWNED_WRONG = 185.0;
    private static Long idCreatedSite = null;

    //constant for test 002
    private static final String SITE_NAME2 = "site name 2";
    private static final String SITE_NACE2 = "code nace 2";
    private static final String SITE_DESC2 = "2 je suis une description plutôt longue qui fait plus de 255 caractères : Lorem ipsum dolor sit amet, eu mea harum mollis cotidieque, id has mundi interesset. Explicari urbanitas sed ne. In agam nulla pertinax eum. In dictas lobortis est, ad nonumy prompta aliquam vel.Ei verear adipiscing has. Ea pri alterum fastidii, mediocritatem conclusionemque vituperatoribus ne eum. An usu tollit definitionem. Qui ea falli iusto patrioque. Veniam indoctum ex has, hinc ludus graeci has id, per ea lorem temporibus. Pri admodum pericula sapientem ei, sit errem imperdiet adversarium at, ne adhuc pertinacia pri. Eum modus denique et, sea detraxit salutatus ea, mel at nullam aliquam";
    private static final String SITE_ORG_STRUCUTRE2 = "organizaiton structure 2";
    private static final String SITE_ECO_INTEREST2 = "economic interest 2";
    private static final String SITE_OPE_POLICY2 = "operating policy 2";
    private static final String SITE_ACCOUNTING2 = "acounting treatment 2";
    private static final Double SITE_PERCENTAGE_OWNED2 = 55.98;

    //constant for test 003
    private static final String PERIOD_CODE_KEY = "2002";

    @Autowired
    private SiteService siteService;

    //create a new site
    @Test
    public void _001_createSite() {


        // ConnectionFormDTO
        ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

		/*
        start with a percentage too big => except an error

		 */
        SiteDTO dto = new SiteDTO();

        dto.setName(SITE_NAME);
        dto.setDescription(SITE_DESC);
        dto.setNaceCode(SITE_NACE);

        dto.setOrganizationalStructure(SITE_ORG_STRUCUTRE);
        dto.setPercentOwned(SITE_PERCENTAGE_OWNED_WRONG);

        //Json node
        JsonNode node = Json.toJson(dto);

        // perform save
        // Fake request
        FakeRequest saveFakeRequest = new FakeRequest();
        saveFakeRequest.withHeader("Content-type", "application/json");
        saveFakeRequest.withJsonBody(node);
        saveFakeRequest.withSession(SecuredController.SESSION_IDENTIFIER_STORE, cfDto.getLogin());

        // Call controller action
        boolean error = false;
        try {
            callAction(
                    eu.factorx.awac.controllers.routes.ref.SiteController.create(),
                    saveFakeRequest
            ); // callAction
        } catch (MyrmexRuntimeException e) {
            error = true;
        }

        if (!error) {
            //analyse result
            // expecting an HTTP 200 return code
            assertTrue("DTO converted with wrong values (percentOwed bigger than 100", false);
        }


		/*
		Change the percentage and retry => excepted a success
		 */
        dto.setPercentOwned(SITE_PERCENTAGE_OWNED);

        //Json node
        node = Json.toJson(dto);

        // perform save
        // Fake request
        saveFakeRequest = new FakeRequest();
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

        assertEquals(resultDTO.getName(), SITE_NAME);
        assertEquals(resultDTO.getDescription(), SITE_DESC);
        assertEquals(resultDTO.getNaceCode(), SITE_NACE);

        assertEquals(resultDTO.getOrganizationalStructure(), SITE_ORG_STRUCUTRE);
        assertEquals(resultDTO.getPercentOwned(), SITE_PERCENTAGE_OWNED);

        //save id
        idCreatedSite = resultDTO.getId();
        play.Logger.info("idCreatedSite :" + idCreatedSite + " " + resultDTO.getId());

    } // end of authenticateSuccess test

    @Test
    public void _002_editSite() {

        // ConnectionFormDTO
        ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

        //test if the id exists ( = if the test1 is ok)
        play.Logger.info("idCreatedSite :" + idCreatedSite);
        assertNotNull("The id of the created site is null", idCreatedSite);

        //edit
        SiteDTO dto = new SiteDTO();

        dto.setId(idCreatedSite);
        dto.setName(SITE_NAME2);
        dto.setDescription(SITE_DESC2);
        dto.setNaceCode(SITE_NACE2);

        dto.setOrganizationalStructure(SITE_ORG_STRUCUTRE2);
        dto.setPercentOwned(SITE_PERCENTAGE_OWNED2);

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
                eu.factorx.awac.controllers.routes.ref.SiteController.edit(),
                saveFakeRequest
        ); // callAction

        //analyse result
        // expecting an HTTP 200 return code
        assertEquals(200, status(result));


        //analyse result
        SiteDTO resultDTO = getDTO(result, SiteDTO.class);

        assertEquals(resultDTO.getName(), SITE_NAME2);
        assertEquals(resultDTO.getDescription(), SITE_DESC2);
        assertEquals(resultDTO.getNaceCode(), SITE_NACE2);

        assertEquals(resultDTO.getOrganizationalStructure(), SITE_ORG_STRUCUTRE2);
        assertEquals(resultDTO.getPercentOwned(), SITE_PERCENTAGE_OWNED2);
    }

    @Test
    public void _003_loadSite() {

        // ConnectionFormDTO
        ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

        //load site by id
        // Fake request
        FakeRequest saveFakeRequest = new FakeRequest();
        saveFakeRequest.withHeader("Content-type", "application/json");
        saveFakeRequest.withSession(SecuredController.SESSION_IDENTIFIER_STORE, cfDto.getLogin());

        // Call controller action
        Result result = callAction(
                eu.factorx.awac.controllers.routes.ref.SiteController.getSite(idCreatedSite),
                saveFakeRequest
        ); // callAction

        //analyse result
        // expecting an HTTP 200 return code
        assertEquals(200, status(result));

        //analyse result
        SiteDTO resultDTO = getDTO(result, SiteDTO.class);

        //control result
        assertEquals(resultDTO.getName(), SITE_NAME2);
        assertEquals(resultDTO.getDescription(), SITE_DESC2);
        assertEquals(resultDTO.getNaceCode(), SITE_NACE2);

        assertEquals(resultDTO.getOrganizationalStructure(), SITE_ORG_STRUCUTRE2);
        assertEquals(resultDTO.getPercentOwned(), SITE_PERCENTAGE_OWNED2);
    }

    @Test
    public void _004_addPeriod() {

        // ConnectionFormDTO
        ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

        AssignPeriodToSiteDTO dto = new AssignPeriodToSiteDTO();

        dto.setAssign(true);
        dto.setPeriodKeyCode(PERIOD_CODE_KEY);
        dto.setSiteId(idCreatedSite);

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
                eu.factorx.awac.controllers.routes.ref.SiteController.assignPeriodToSite(),
                saveFakeRequest
        ); // callAction

        //analyse result
        // expecting an HTTP 200 return code
        assertEquals(200, status(result));


        //load site by id
        // Fake request
        saveFakeRequest = new FakeRequest();
        saveFakeRequest.withHeader("Content-type", "application/json");
        saveFakeRequest.withSession(SecuredController.SESSION_IDENTIFIER_STORE, cfDto.getLogin());

        // Call controller action
        result = callAction(
                eu.factorx.awac.controllers.routes.ref.SiteController.getSite(idCreatedSite),
                saveFakeRequest
        ); // callAction

        //analyse result
        //already tested in test 003

        //analyse result
        SiteDTO resultDTO = getDTO(result, SiteDTO.class);
        assertNotNull("nothing period into site", resultDTO.getListPeriodAvailable());

        boolean founded = false;
        for (PeriodDTO period : resultDTO.getListPeriodAvailable()) {
            if (period.getKey().equals(PERIOD_CODE_KEY)) {
                founded = true;
                break;
            }
        }

        assertTrue("period not founded into the site", founded);


    }


    @Test
    public void _005_removePeriod() {

        // ConnectionFormDTO
        ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

        AssignPeriodToSiteDTO dto = new AssignPeriodToSiteDTO();

        dto.setAssign(false);
        dto.setPeriodKeyCode(PERIOD_CODE_KEY);
        dto.setSiteId(idCreatedSite);

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
                eu.factorx.awac.controllers.routes.ref.SiteController.assignPeriodToSite(),
                saveFakeRequest
        ); // callAction

        //analyse result
        // expecting an HTTP 200 return code
        assertEquals(200, status(result));


        //load site by id
        // Fake request
        saveFakeRequest = new FakeRequest();
        saveFakeRequest.withHeader("Content-type", "application/json");
        saveFakeRequest.withSession(SecuredController.SESSION_IDENTIFIER_STORE, cfDto.getLogin());

        // Call controller action
        result = callAction(
                eu.factorx.awac.controllers.routes.ref.SiteController.getSite(idCreatedSite),
                saveFakeRequest
        ); // callAction

        //analyse result
        //already tested in test 003

        //analyse result
        SiteDTO resultDTO = getDTO(result, SiteDTO.class);
        if (resultDTO.getListPeriodAvailable() != null) {
            for (PeriodDTO period : resultDTO.getListPeriodAvailable()) {
                assertFalse("period not founded into the site", period.getKey().equals(PERIOD_CODE_KEY));
            }

        }


    }

}
