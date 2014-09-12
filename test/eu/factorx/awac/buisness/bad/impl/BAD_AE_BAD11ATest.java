package eu.factorx.awac.buisness.bad.impl;

import com.fasterxml.jackson.databind.JsonNode;
import eu.factorx.awac.controllers.SecuredController;
import eu.factorx.awac.dto.awac.post.AnswerLineDTO;
import eu.factorx.awac.dto.awac.post.QuestionAnswersDTO;
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.service.PeriodService;
import eu.factorx.awac.service.QuestionSetAnswerService;
import eu.factorx.awac.service.ScopeService;
import eu.factorx.awac.service.SiteService;
import eu.factorx.awac.service.knowledge.activity.contributor.impl.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.callAction;
import static play.test.Helpers.status;

/*
 * Test for bad AE_BAD11A
*/
@Component
public class BAD_AE_BAD11ATest{

    @Autowired
    private QuestionSetAnswerService questionSetAnswerService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private ScopeService scopeService;

    @Autowired
    private PeriodService periodService;

    @Autowired
    private BaseActivityDataAE_BAD11A baseActivityDataAE_BAD11A;

    private final static Long FORM_ID = 2L;
    private final static Long PERIOD_ID = 1L;
    private String identifier = "user1";
    private String identifierPassword = "password";

    /**
     * run test
     * need an id of a scope (site)
     * @param siteId
     */
    public void test(long siteId){

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
                answerLineDTOList.addAll(buildAnswerA83());
                answerLineDTOList.addAll(buildAnswerA80());
                answerLineDTOList.addAll(buildAnswerA88());
                answerLineDTOList.addAll(buildAnswerA89());
        
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
        Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers = questionSetAnswerService.getAllQuestionSetAnswers(site, period);
        List<BaseActivityData> bads = baseActivityDataAE_BAD11A.getBaseActivityData(questionSetAnswers);

        //control size
        assertEquals("The number of bad generated is not correct. expected : 1, generated : "+bads.size(),1 ,bads.size());

        //control content
                controlBad1(bads.get((1 - 1)));
            }

        /**
     * build the AnswerLineDTO
     * question : A83
     */
    private List<AnswerLineDTO> buildAnswerA83(){

        List<AnswerLineDTO> list = new ArrayList<>();

                 //add repetition
        Map<String, Integer> mapRepetition1 = new HashMap<>();
                mapRepetition1.put("A78",1);
                list.add(new AnswerLineDTO("A83","AS_5", identifier, mapRepetition1 ));
        
        return list;
    }
        /**
     * build the AnswerLineDTO
     * question : A80
     */
    private List<AnswerLineDTO> buildAnswerA80(){

        List<AnswerLineDTO> list = new ArrayList<>();

                 //add repetition
        Map<String, Integer> mapRepetition1 = new HashMap<>();
                mapRepetition1.put("A78",1);
                list.add(new AnswerLineDTO("A80",true, identifier, mapRepetition1 ));
                //add repetition
        Map<String, Integer> mapRepetition2 = new HashMap<>();
                mapRepetition2.put("A78",2);
                list.add(new AnswerLineDTO("A80",true, identifier, mapRepetition2 ));
                //add repetition
        Map<String, Integer> mapRepetition3 = new HashMap<>();
                mapRepetition3.put("A78",3);
                list.add(new AnswerLineDTO("A80",false, identifier, mapRepetition3 ));
        
        return list;
    }
        /**
     * build the AnswerLineDTO
     * question : A88
     */
    private List<AnswerLineDTO> buildAnswerA88(){

        List<AnswerLineDTO> list = new ArrayList<>();

                 //add repetition
        Map<String, Integer> mapRepetition1 = new HashMap<>();
                mapRepetition1.put("A78",1);
                list.add(new AnswerLineDTO("A88",80000.0, identifier, mapRepetition1  , UnitCode.U5170.getKey()  ));
                //add repetition
        Map<String, Integer> mapRepetition2 = new HashMap<>();
                mapRepetition2.put("A78",2);
                list.add(new AnswerLineDTO("A88",150000.0, identifier, mapRepetition2  , UnitCode.U5170.getKey()  ));
                //add repetition
        Map<String, Integer> mapRepetition3 = new HashMap<>();
                mapRepetition3.put("A78",3);
                list.add(new AnswerLineDTO("A88",23.0, identifier, mapRepetition3  , UnitCode.U5325.getKey()  ));
        
        return list;
    }
        /**
     * build the AnswerLineDTO
     * question : A89
     */
    private List<AnswerLineDTO> buildAnswerA89(){

        List<AnswerLineDTO> list = new ArrayList<>();

                 //add repetition
        Map<String, Integer> mapRepetition1 = new HashMap<>();
                mapRepetition1.put("A78",1);
                list.add(new AnswerLineDTO("A89",1.6, identifier, mapRepetition1  , UnitCode.U5170.getKey()  ));
        
        return list;
    }
    
        /**
     * control the content and the value of the bad
     * @param bad
     */
    private void controlBad1(BaseActivityData bad){
        //control global
        controlGlobalBad(bad);

        //control value
        assertTrue("Value error : expected : 50000.0, founded : "+bad.getValue(),bad.getValue().equals(50000.0));
    }
    
    /**
     * control all except value
     * @param bad
     */
    private void controlGlobalBad(BaseActivityData bad){
        /*
        assertTrue("BaseActivityDataCode error. Expected : ActivityCategoryCode.AC_5, founded : "+bad.getKey(),bad.getKey().equals(BaseActivityDataCode.ActivityCategoryCode.AC_5));
        assertTrue("Rank error : Expected : 2, founded : "+bad.getRank(),bad.getRank().equals(2));
        assertTrue("SpecificPurpose error : Expected : {}, founded : "+bad.getSpecificPurpose(),bad.getSpecificPurpose() == null);
        assertTrue("ActivityCategory error : Expected : {}, founded : "+bad.getActivityCategory(),bad.getActivityCategory().equals(ActivityCategoryCode.AC_1));
        assertTrue("ActivitySubCategory error : Expected : {}, founded : "+bad.getActivitySubCategory(),bad.getActivitySubCategory().equals(ActivitySubCategoryCode.ASC_1));
        assertTrue("ActivityType error : Expected : {}, founded : "+bad.getActivityType(),bad.getActivityType().equals(ActivityTypeCode.AT_1));
        assertTrue("ActivitySource error : Expected : {}, founded : "+bad.getActivitySource(),bad.getActivitySource().equals(new ActivitySourceCode("AS_1")));
        assertTrue("ActivityOwnership error : Expected : {}, founded : "+bad.getActivityOwnership(),bad.getActivityOwnership().equals(true));
        assertTrue("Unit error : Expected : {}, founded : "+bad.getUnit().getSymbol(),bad.getUnit().getUnitCode().equals(UnitCode.U5321));
        */
    }
}
