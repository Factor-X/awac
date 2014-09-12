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
 * Test for bad AE_BAD26A
*/
@Component
public class BAD_AE_BAD26ATest{

    @Autowired
    private QuestionSetAnswerService questionSetAnswerService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private ScopeService scopeService;

    @Autowired
    private PeriodService periodService;

    @Autowired
    private BaseActivityDataAE_BAD26A baseActivityDataAE_BAD26A;

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
                answerLineDTOList.addAll(buildAnswerA204());
                answerLineDTOList.addAll(buildAnswerA501());
                answerLineDTOList.addAll(buildAnswerA202());
        
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
        List<BaseActivityData> bads = baseActivityDataAE_BAD26A.getBaseActivityData(questionSetAnswers);

        //control size
        assertEquals("The number of bad generated is not correct. expected : 1, generated : "+bads.size(),1 ,bads.size());

        //control content
                controlBad1(bads.get((1 - 1)));
            }

        /**
     * build the AnswerLineDTO
     * question : A204
     */
    private List<AnswerLineDTO> buildAnswerA204(){

        List<AnswerLineDTO> list = new ArrayList<>();

                 //add repetition
        Map<String, Integer> mapRepetition1 = new HashMap<>();
                list.add(new AnswerLineDTO("A204","AT_58", identifier, mapRepetition1 ));
        
        return list;
    }
        /**
     * build the AnswerLineDTO
     * question : A501
     */
    private List<AnswerLineDTO> buildAnswerA501(){

        List<AnswerLineDTO> list = new ArrayList<>();

                 //add repetition
        Map<String, Integer> mapRepetition1 = new HashMap<>();
                list.add(new AnswerLineDTO("A501","1", identifier, mapRepetition1 ));
        
        return list;
    }
        /**
     * build the AnswerLineDTO
     * question : A202
     */
    private List<AnswerLineDTO> buildAnswerA202(){

        List<AnswerLineDTO> list = new ArrayList<>();

                 //add repetition
        Map<String, Integer> mapRepetition1 = new HashMap<>();
                list.add(new AnswerLineDTO("A202",23450.0, identifier, mapRepetition1  , UnitCode.U5133.getKey()  ));
        
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
        assertTrue("Value error : expected : 23.45, founded : "+bad.getValue(),bad.getValue().equals(23.45));
    }
    
    /**
     * control all except value
     * @param bad
     */
    private void controlGlobalBad(BaseActivityData bad){
        /*
        assertTrue("BaseActivityDataCode error. Expected : ActivityCategoryCode.AC_7, founded : "+bad.getKey(),bad.getKey().equals(BaseActivityDataCode.ActivityCategoryCode.AC_7));
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
