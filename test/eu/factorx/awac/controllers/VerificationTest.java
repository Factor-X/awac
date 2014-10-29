package eu.factorx.awac.controllers;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.callAction;
import static play.test.Helpers.status;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import play.mvc.Result;
import play.test.FakeRequest;
import eu.factorx.awac.dto.awac.post.LockQuestionSetDTO;
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VerificationTest extends AbstractBaseModelTest {

    private final static String PERIOD_KEY="2013";
    private final static Long SCOPE_ID= 1L;
    private final static String[] QUESTION_SET_ARRAY = {"A1","A13","A20","A31","A37","A50","A128","A173","A205","A208","A229","A243","A309","A320","A332"};


    //create a new site
    @Test
    public void _001_validAllQuestionSet() {


        //validateQuestionSet() {
        //

        // ConnectionFormDTO
        ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

        for(String questionSetKey : QUESTION_SET_ARRAY) {
            //valid all questionSet for enterprise
            LockQuestionSetDTO dto = new LockQuestionSetDTO();
            dto.setLock(true);
            dto.setPeriodCode(PERIOD_KEY);
            dto.setScopeId(SCOPE_ID);
            dto.setQuestionSetKey(questionSetKey);

            // Fake request
            FakeRequest saveFakeRequest = new FakeRequest();
            saveFakeRequest.withHeader("Content-type", "application/json");
            saveFakeRequest.withJsonBody(play.libs.Json.toJson(dto));
            saveFakeRequest.withSession(SecuredController.SESSION_IDENTIFIER_STORE, cfDto.getLogin());

            // Call controller action
            Result result = callAction(
                    eu.factorx.awac.controllers.routes.ref.AnswerController.validateQuestionSet(),
                    saveFakeRequest);

            assertEquals(200, status(result));
        }

        //test result
        // Fake request
        FakeRequest saveFakeRequest = new FakeRequest();
        saveFakeRequest.withHeader("Content-type", "application/json");
        saveFakeRequest.withSession(SecuredController.SESSION_IDENTIFIER_STORE, cfDto.getLogin());

        // Call controller action
        Result result = callAction(
                eu.factorx.awac.controllers.routes.ref.AnswerController.testClosing(PERIOD_KEY,SCOPE_ID),
                saveFakeRequest);

        assertEquals(200, status(result));
       //public Result testClosing(String periodKey, Long scopeId) {


    }


    //create a new site
    @Test
    public void _099_clean() {


        //validateQuestionSet() {
        //

        // ConnectionFormDTO
        ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

        for(String questionSetKey : QUESTION_SET_ARRAY) {
            //valid all questionSet for enterprise
            LockQuestionSetDTO dto = new LockQuestionSetDTO();
            dto.setLock(false);
            dto.setPeriodCode(PERIOD_KEY);
            dto.setScopeId(SCOPE_ID);
            dto.setQuestionSetKey(questionSetKey);

            // Fake request
            FakeRequest saveFakeRequest = new FakeRequest();
            saveFakeRequest.withHeader("Content-type", "application/json");
            saveFakeRequest.withJsonBody(play.libs.Json.toJson(dto));
            saveFakeRequest.withSession(SecuredController.SESSION_IDENTIFIER_STORE, cfDto.getLogin());

            // Call controller action
            Result result = callAction(
                    eu.factorx.awac.controllers.routes.ref.AnswerController.validateQuestionSet(),
                    saveFakeRequest);

            assertEquals(200, status(result));
        }

        //test result
        // Fake request
        FakeRequest saveFakeRequest = new FakeRequest();
        saveFakeRequest.withHeader("Content-type", "application/json");
        saveFakeRequest.withSession(SecuredController.SESSION_IDENTIFIER_STORE, cfDto.getLogin());

        // Call controller action
        Result result = callAction(
                eu.factorx.awac.controllers.routes.ref.AnswerController.testClosing(PERIOD_KEY,SCOPE_ID),
                saveFakeRequest);

        assertEquals(200, status(result));
        //public Result testClosing(String periodKey, Long scopeId) {


    }

}
