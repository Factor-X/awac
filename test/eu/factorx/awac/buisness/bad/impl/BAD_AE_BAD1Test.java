package eu.factorx.awac.buisness.bad.impl;

import com.fasterxml.jackson.databind.JsonNode;
import eu.factorx.awac.controllers.SecuredController;
import eu.factorx.awac.dto.awac.post.AnswerLineDTO;
import eu.factorx.awac.dto.awac.post.QuestionAnswersDTO;
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.code.type.UnitCode;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.service.PeriodService;
import eu.factorx.awac.service.QuestionSetAnswerService;
import eu.factorx.awac.service.ScopeService;
import eu.factorx.awac.service.SiteService;
import eu.factorx.awac.service.knowledge.activity.contributor.impl.BaseActivityDataAE_BAD1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import play.db.jpa.JPA;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.callAction;
import static play.test.Helpers.status;

/*
 * Test for bad AE_BAD1
*/
@Component
public class BAD_AE_BAD1Test {

    private static final Double ERROR_MARGE = 0.0001;

    @Autowired
    private QuestionSetAnswerService questionSetAnswerService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private ScopeService scopeService;

    @Autowired
    private PeriodService periodService;

    @Autowired
    private BaseActivityDataAE_BAD1 baseActivityDataAE_BAD1;

    private final static Long FORM_ID = 2L;
    private final static Long PERIOD_ID = 1L;
    private String identifier = "user1";
    private String identifierPassword = "password";

    /**
     * run test
     * need an id of a scope (site)
     *
     * @param siteId
     */
    public void test(long siteId) {

        Site site = siteService.findById(siteId);
        Period period = periodService.findById(PERIOD_ID);

        //
        // 1) build data
        //
        QuestionAnswersDTO questionAnswersDTO = new QuestionAnswersDTO();
        questionAnswersDTO.setFormId(FORM_ID);
        questionAnswersDTO.setPeriodId(PERIOD_ID);
        questionAnswersDTO.setScopeId(site.getId());
        questionAnswersDTO.setLastUpdateDate(new Date().toString());

        List<AnswerLineDTO> answerLineDTOList = new ArrayList<>();

        //add answers
        answerLineDTOList.addAll(buildAnswerA16());
        answerLineDTOList.addAll(buildAnswerA17());

        questionAnswersDTO.setListAnswers(answerLineDTOList);

        //
        // 2) send request
        //
        //Json node
        JsonNode node = Json.toJson(questionAnswersDTO);
        FakeRequest saveFakeRequest = new FakeRequest();
        saveFakeRequest.withHeader("Content-type", "application/json");
        saveFakeRequest.withJsonBody(node);

        //connection
        ConnectionFormDTO cfDto = new ConnectionFormDTO(identifier, identifierPassword, InterfaceTypeCode.ENTERPRISE.getKey(), "");
        saveFakeRequest.withSession(SecuredController.SESSION_IDENTIFIER_STORE, cfDto.getLogin());

        // Call controller action
        Result result = callAction(
                eu.factorx.awac.controllers.routes.ref.AnswerController.save(),
                saveFakeRequest
        );

        // control result
        assertEquals(200, status(result));

        //
        // 3) control BAD
        //
        //TODO temporary
        JPA.em().clear();
        JPA.em().flush();
        Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers = questionSetAnswerService.getAllQuestionSetAnswers(site, period);
        List<BaseActivityData> bads = baseActivityDataAE_BAD1.getBaseActivityData(questionSetAnswers);

        //control content
        //map mapResult
        Map<Double, Boolean> mapResult = new HashMap<>();
        mapResult.put(6.5, false);
        mapResult.put(1.6416, false);

        String valueGenerated = "";

        for (BaseActivityData bad : bads) {
            valueGenerated += String.valueOf(bad.getValue()) + ",";
            for (Map.Entry<Double, Boolean> entry : mapResult.entrySet()) {
                if (around(entry.getKey(), bad.getValue())) {
                    entry.setValue(true);
                }
            }
        }

        String valueNotFound = "";

        for (Map.Entry<Double, Boolean> entry : mapResult.entrySet()) {
            if (entry.getValue().equals(false)) {
                valueNotFound += String.valueOf(entry.getKey()) + ", ";
            }
        }

        //create errorMessage
        assertTrue("Value expected but not found : " + valueNotFound + ". Value generated : " + valueGenerated, valueNotFound.length() == 0);

    }

    /**
     * build the AnswerLineDTO
     * question : A16
     */
    private List<AnswerLineDTO> buildAnswerA16() {

        List<AnswerLineDTO> list = new ArrayList<>();

        //add repetition
        Map<String, Integer> mapRepetition1 = new HashMap<>();
        mapRepetition1.put("A15", 1);
        list.add(new AnswerLineDTO("A16", "AS_1", identifier, mapRepetition1));
        //add repetition
        Map<String, Integer> mapRepetition2 = new HashMap<>();
        mapRepetition2.put("A15", 2);
        list.add(new AnswerLineDTO("A16", "AS_22", identifier, mapRepetition2));

        return list;
    }

    /**
     * build the AnswerLineDTO
     * question : A17
     */
    private List<AnswerLineDTO> buildAnswerA17() {

        List<AnswerLineDTO> list = new ArrayList<>();

        //add repetition
        Map<String, Integer> mapRepetition1 = new HashMap<>();
        mapRepetition1.put("A15", 1);
        list.add(new AnswerLineDTO("A17", 6.5, identifier, mapRepetition1, UnitCode.U5321.getKey()));
        //add repetition
        Map<String, Integer> mapRepetition2 = new HashMap<>();
        mapRepetition2.put("A15", 2);
        list.add(new AnswerLineDTO("A17", 456.0, identifier, mapRepetition2, UnitCode.U5156.getKey()));

        return list;
    }


    /**
     * control all except value
     *
     * @param bad
     */
    private void controlGlobalBad(BaseActivityData bad) {
        /*
        assertTrue("BaseActivityDataCode error. Expected : ActivityCategoryCode.AC_1, founded : "+bad.getKey(),bad.getKey().equals(BaseActivityDataCode.ActivityCategoryCode.AC_1));
        assertTrue("Rank error : Expected : 1, founded : "+bad.getRank(),bad.getRank().equals(1));
        assertTrue("SpecificPurpose error : Expected : {}, founded : "+bad.getSpecificPurpose(),bad.getSpecificPurpose() == null);
        assertTrue("ActivityCategory error : Expected : {}, founded : "+bad.getActivityCategory(),bad.getActivityCategory().equals(ActivityCategoryCode.AC_1));
        assertTrue("ActivitySubCategory error : Expected : {}, founded : "+bad.getActivitySubCategory(),bad.getActivitySubCategory().equals(ActivitySubCategoryCode.ASC_1));
        assertTrue("ActivityType error : Expected : {}, founded : "+bad.getActivityType(),bad.getActivityType().equals(ActivityTypeCode.AT_1));
        assertTrue("ActivitySource error : Expected : {}, founded : "+bad.getActivitySource(),bad.getActivitySource().equals(new ActivitySourceCode("AS_1")));
        assertTrue("ActivityOwnership error : Expected : {}, founded : "+bad.getActivityOwnership(),bad.getActivityOwnership().equals(true));
        assertTrue("Unit error : Expected : {}, founded : "+bad.getUnit().getSymbol(),bad.getUnit().getUnitCode().equals(UnitCode.U5321));
        */
    }

    private boolean around(Double value1, Double value2) {
        if (value1 >= value2 * (1 - ERROR_MARGE) && value1 <= value2 * (1 + ERROR_MARGE)) {
            return true;
        }
        return false;
    }
}