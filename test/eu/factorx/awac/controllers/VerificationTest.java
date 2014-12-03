package eu.factorx.awac.controllers;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.callAction;
import static play.test.Helpers.status;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.VelocityGeneratorService;
import eu.factorx.awac.util.email.messages.EmailMessage;
import eu.factorx.awac.util.email.service.EmailService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import play.mvc.Result;
import play.test.FakeRequest;
import eu.factorx.awac.dto.awac.post.LockQuestionSetDTO;
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;

import java.util.HashMap;
import java.util.Map;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VerificationTest extends AbstractBaseModelTest {

    private final static String PERIOD_KEY = "2013";
    private final static Long SCOPE_ID = 1L;
    private final static String[] QUESTION_SET_ARRAY = {"A1", "A13", "A20", "A31", "A37", "A50", "A128", "A173", "A205", "A208", "A229", "A243", "A309", "A320", "A332"};

    @Autowired
    CodeLabelService codeLabelService;

    @Autowired
    EmailService emailService;

    @Autowired
    private VelocityGeneratorService velocityGeneratorService;

    //create a new site
    @Test
    public void _001_validAllQuestionSet() {


        //validateQuestionSet() {
        //

        // ConnectionFormDTO
        ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

        for (String questionSetKey : QUESTION_SET_ARRAY) {
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
                eu.factorx.awac.controllers.routes.ref.AnswerController.testClosing(PERIOD_KEY, SCOPE_ID),
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

        for (String questionSetKey : QUESTION_SET_ARRAY) {
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
                eu.factorx.awac.controllers.routes.ref.AnswerController.testClosing(PERIOD_KEY, SCOPE_ID),
                saveFakeRequest);

        assertEquals(200, status(result));
        //public Result testClosing(String periodKey, Long scopeId) {


    }

    //create a new site
    @Test
    public void _100_verificationEmails() {


        HashMap<String, CodeLabel> traductions = codeLabelService.findCodeLabelsByList(CodeList.TRANSLATIONS_EMAIL_MESSAGE);
        Map<String, Object> values = new HashMap<>();

        String emailTemplate = "verification/toWaitVerifierConfirmation.vm";
        //title = "Demande de vérification de bilan GES";
        String subject = traductions.get("VERIFICATION_toWaitVerifierConfirmation_SUBJECT").getLabel(LanguageCode.FRENCH);
        String content = traductions.get("VERIFICATION_toWaitVerifierConfirmation_CONTENT").getLabel(LanguageCode.FRENCH);



        content = content.replace("${user.person.firstname}","Hollands");
        content = content.replace("${user.person.lastname}","Gaston");
        content = content.replace("${request.awacCalculatorInstance.scope.organization.name}","Organization Name");
        content = content.replace("${request.emailVerificationContent.content}","Content");
        content = content.replace("${request.emailVerificationContent.phoneNumber}","00324445566");
        content = content.replace("${request.contact.person.email}","gaston.hollands@factorx.eu");
        content = content.replace("${request.key}","Keyyyyyy");
        content = content.replace("${request.awacCalculatorInstance.period.label}","2014");
        content = content.replace("${request.awacCalculatorInstance.scope.name}","scope name");


        values.put("subject", subject);
        values.put("content", content);

        String velocityContent = velocityGeneratorService.generate(emailTemplate, values);

        EmailMessage message = new EmailMessage ("gaston.hollands@factorx.eu",subject, velocityContent);
        emailService.send(message);

        try {
            Thread.sleep(10000);
        } catch (Exception e) {

        }

    }
}
