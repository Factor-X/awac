package eu.factorx.awac.service;

import com.fasterxml.jackson.databind.JsonNode;
import eu.factorx.awac.controllers.SecuredController;
import eu.factorx.awac.dto.awac.post.AnswerLineDTO;
import eu.factorx.awac.dto.awac.post.QuestionAnswersDTO;
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.service.knowledge.activity.contributor.impl.BaseActivityDataAE_BAD1;
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

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.callAction;
import static play.test.Helpers.status;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BadTest  extends AbstractBaseModelTest {

    @Autowired
    private QuestionSetAnswerService  questionSetAnswerService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private ScopeService scopeService;

    @Autowired
    private PeriodService periodService;

    @Autowired
    private BaseActivityDataAE_BAD1 baseActivityDataAE_bad1;

    private final static Long FORM_ID = 1L;
    private final static Long PERIOD_ID = 1L;
    private String identifier = "user1";
    private String identifierPassword = "password";

    /**
     * build the AnswerLineDTO
     * Value : 2
     * mapRepetition : {}
     */
    private List<AnswerLineDTO> buildAnswerA3(){

        List<AnswerLineDTO> list = new ArrayList<>();

        list.add(new AnswerLineDTO("A3",2, identifier));

        return list;
    }

    /**
     * build the AnswerLineDTO
     * Value : 2
     * mapRepetition : {}
     */
    private List<AnswerLineDTO> buildAnswerA5(){

        List<AnswerLineDTO> list = new ArrayList<>();

        list.add(new AnswerLineDTO("A5",1, identifier));

        return list;
    }

    /**
     * build the AnswerLineDTO
     * Value : 2
     * mapRepetition : {}
     */
    private List<AnswerLineDTO> buildAnswerA9(){

        List<AnswerLineDTO> list = new ArrayList<>();

        list.add(new AnswerLineDTO("A9",400, identifier,13));

        return list;
    }


    /**
     * build the AnswerLineDTO
     * Value : 2
     * mapRepetition : {}
     */
    private List<AnswerLineDTO> buildAnswerA10(){

        List<AnswerLineDTO> list = new ArrayList<>();

        list.add(new AnswerLineDTO("A10",200, identifier,13));

        return list;
    }

    /**
     * build the AnswerLineDTO
     * Value : 2
     * mapRepetition : {}
     */
    private List<AnswerLineDTO> buildAnswerA12(){


        List<AnswerLineDTO> list = new ArrayList<>();

        list.add(new AnswerLineDTO("A12",24, identifier));

        return list;
    }


    /**
     * build the AnswerLineDTO
     * Value : 2
     * mapRepetition : {}
     */
    private List<AnswerLineDTO> buildAnswerA16(){

        List<AnswerLineDTO> list = new ArrayList<>();

        //repetition A15:1
        Map<String, Integer> mapRepetition1 = new HashMap<>();
        mapRepetition1.put("A15",1);
        list.add(new AnswerLineDTO("A16","AS_1", identifier, mapRepetition1));

        //repetition A15:2
        Map<String, Integer> mapRepetition2 = new HashMap<>();
        mapRepetition2.put("A15",2);
        list.add(new AnswerLineDTO("A16","AS_22", identifier, mapRepetition2));

        return list;
    }

    /**
     * build the AnswerLineDTO
     * Value : 2
     * mapRepetition : {}
     */
    private List<AnswerLineDTO> buildAnswerA17(){

        List<AnswerLineDTO> list = new ArrayList<>();

        //repetition A15:1
        Map<String, Integer> mapRepetition1 = new HashMap<>();
        mapRepetition1.put("A15",1);
        list.add(new AnswerLineDTO("A17",6.5, identifier, 53,mapRepetition1));

        //repetition A15:2
        Map<String, Integer> mapRepetition2 = new HashMap<>();
        mapRepetition2.put("A15",2);
        list.add(new AnswerLineDTO("A17",456, identifier, 53,mapRepetition2));

        return list;
    }

    /**
     * control all accept value
     * @param bad
     */
    private void controlGlobalBad(BaseActivityData bad){

        assertTrue("BaseActivityDataCode error. Expected : {}, founded : "+bad.getKey(),bad.getKey().equals(BaseActivityDataCode.AE_BAD1));
        assertTrue("Rank error : Expected : {}, founded : "+bad.getRank(),bad.getRank().equals(1));
        assertTrue("SpecificPurpose error : Expected : {}, founded : "+bad.getSpecificPurpose(),bad.getSpecificPurpose() == null);
        assertTrue("ActivityCategory error : Expected : {}, founded : "+bad.getActivityCategory(),bad.getActivityCategory().equals(ActivityCategoryCode.AC_1));
        assertTrue("ActivitySubCategory error : Expected : {}, founded : "+bad.getActivitySubCategory(),bad.getActivitySubCategory().equals(ActivitySubCategoryCode.ASC_1));
        assertTrue("ActivityType error : Expected : {}, founded : "+bad.getActivityType(),bad.getActivityType().equals(ActivityTypeCode.AT_1));
        assertTrue("ActivitySource error : Expected : {}, founded : "+bad.getActivitySource(),bad.getActivitySource().equals(new ActivitySourceCode("AS_1")));
        assertTrue("ActivityOwnership error : Expected : {}, founded : "+bad.getActivityOwnership(),bad.getActivityOwnership().equals(true));
        assertTrue("Unit error : Expected : {}, founded : "+bad.getUnit().getSymbol(),bad.getUnit().getUnitCode().equals(UnitCode.U5321));

    }

    private void controlBad1(BaseActivityData bad){

        //control global
        controlGlobalBad(bad);

        //control value
        assertTrue("Value error : expected : 6.5, founded : "+bad.getValue(),bad.getValue().equals(6.5));
    }


    private void controlBad2(BaseActivityData bad){

        //control global
        controlGlobalBad(bad);

        //control value
        assertTrue("Value error : expected : 1641.6, founded : "+bad.getValue(),bad.getValue().equals(1641.6));
    }


    public void test(long scopeId){


        Site site = siteService.findById(scopeId);
        Period period = periodService.findById(PERIOD_ID);
        Scope scope = scopeService.findBySite(site);

        //
        // 1) build data
        //
        QuestionAnswersDTO questionAnswersDTO = new QuestionAnswersDTO();
        questionAnswersDTO.setFormId(FORM_ID);
        questionAnswersDTO.setPeriodId(PERIOD_ID);
        questionAnswersDTO.setScopeId(scope.getId());
        questionAnswersDTO.setLastUpdateDate(new Date().toString());

        List<AnswerLineDTO> answerLineDTOList = new ArrayList<>();

        //add answers
        answerLineDTOList.addAll(buildAnswerA3());
        answerLineDTOList.addAll(buildAnswerA5());
        answerLineDTOList.addAll(buildAnswerA9());
        answerLineDTOList.addAll(buildAnswerA10());
        answerLineDTOList.addAll(buildAnswerA12());
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
        saveFakeRequest.withSession(SecuredController.SESSION_IDENTIFIER_STORE,cfDto.getLogin());

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
        Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers = questionSetAnswerService.getAllQuestionSetAnswers(scope, period);
        List<BaseActivityData> bads = baseActivityDataAE_bad1.getBaseActivityData(questionSetAnswers);

        //control size
        assertEquals("The number of bad generated is not correct. expected : 2, generated : "+bads.size(),2 ,bads.size());

        //control content
        controlBad1(bads.get(0));
        controlBad2(bads.get(1));


    }


}
