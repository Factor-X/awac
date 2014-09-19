package eu.factorx.awac.buisness.bad.municipality;

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
import eu.factorx.awac.service.knowledge.activity.contributor.municipality.*;
import org.junit.runner.RunWith;
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
 * Test for bad AC_BAD9G
*/
@Component
public class BAD_AC_BAD9GTest{

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
    private BaseActivityDataAC_BAD9G baseActivityDataAC_BAD9G;

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
                answerLineDTOList.addAll(buildAnswerAC74());
                answerLineDTOList.addAll(buildAnswerAC76());
                answerLineDTOList.addAll(buildAnswerAC77());
                answerLineDTOList.addAll(buildAnswerAC83());
                answerLineDTOList.addAll(buildAnswerAC81());
                answerLineDTOList.addAll(buildAnswerAC75());
                answerLineDTOList.addAll(buildAnswerAC78());
        
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
        //TODO temporary
        JPA.em().clear();
        JPA.em().flush();
        Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers = questionSetAnswerService.getAllQuestionSetAnswers(site, period);
        List<BaseActivityData> bads = baseActivityDataAC_BAD9G.getBaseActivityData(questionSetAnswers);

        //control content
        //map mapResult
        Map<Double, Boolean> mapResult = new HashMap<>();
                mapResult.put(810.0, false);
        
        String valueGenerated = "";

        for(BaseActivityData bad : bads){
            valueGenerated += String.valueOf(bad.getValue()) + ",";
            for(Map.Entry<Double, Boolean> entry : mapResult.entrySet()){
                if(around(entry.getKey(),bad.getValue())){
                    entry.setValue(true);
                }
            }
        }

        String valueNotFound = "";

        for(Map.Entry<Double, Boolean> entry : mapResult.entrySet()){
            if(entry.getValue().equals(false)){
                valueNotFound+=String.valueOf(entry.getKey())+", ";
            }
        }

        //create errorMessage
        assertTrue("Value expected but not found : "+valueNotFound+". Value generated : "+valueGenerated,valueNotFound.length() == 0);

    }

        /**
     * build the AnswerLineDTO
     * question : AC74
     */
    private List<AnswerLineDTO> buildAnswerAC74(){

        List<AnswerLineDTO> list = new ArrayList<>();

                 //add repetition
        Map<String, Integer> mapRepetition1 = new HashMap<>();
                mapRepetition1.put("AC73",1);
                list.add(new AnswerLineDTO("AC74","essence",  mapRepetition1 ));
                //add repetition
        Map<String, Integer> mapRepetition2 = new HashMap<>();
                mapRepetition2.put("AC73",2);
                list.add(new AnswerLineDTO("AC74","diesel",  mapRepetition2 ));
                //add repetition
        Map<String, Integer> mapRepetition3 = new HashMap<>();
                mapRepetition3.put("AC73",3);
                list.add(new AnswerLineDTO("AC74","essence",  mapRepetition3 ));
                //add repetition
        Map<String, Integer> mapRepetition4 = new HashMap<>();
                mapRepetition4.put("AC73",4);
                list.add(new AnswerLineDTO("AC74","diesel",  mapRepetition4 ));
                //add repetition
        Map<String, Integer> mapRepetition5 = new HashMap<>();
                mapRepetition5.put("AC73",5);
                list.add(new AnswerLineDTO("AC74","essence",  mapRepetition5 ));
                //add repetition
        Map<String, Integer> mapRepetition6 = new HashMap<>();
                mapRepetition6.put("AC73",6);
                list.add(new AnswerLineDTO("AC74","diesel",  mapRepetition6 ));
                //add repetition
        Map<String, Integer> mapRepetition7 = new HashMap<>();
                mapRepetition7.put("AC73",7);
                list.add(new AnswerLineDTO("AC74","essence",  mapRepetition7 ));
                //add repetition
        Map<String, Integer> mapRepetition8 = new HashMap<>();
                mapRepetition8.put("AC73",8);
                list.add(new AnswerLineDTO("AC74","diesel",  mapRepetition8 ));
        
        return list;
    }
        /**
     * build the AnswerLineDTO
     * question : AC76
     */
    private List<AnswerLineDTO> buildAnswerAC76(){

        List<AnswerLineDTO> list = new ArrayList<>();

                 //add repetition
        Map<String, Integer> mapRepetition1 = new HashMap<>();
                mapRepetition1.put("AC73",2);
                list.add(new AnswerLineDTO("AC76","ASC_7",  mapRepetition1 ));
                //add repetition
        Map<String, Integer> mapRepetition2 = new HashMap<>();
                mapRepetition2.put("AC73",4);
                list.add(new AnswerLineDTO("AC76","ASC_7",  mapRepetition2 ));
                //add repetition
        Map<String, Integer> mapRepetition3 = new HashMap<>();
                mapRepetition3.put("AC73",6);
                list.add(new AnswerLineDTO("AC76","ASC_7",  mapRepetition3 ));
                //add repetition
        Map<String, Integer> mapRepetition4 = new HashMap<>();
                mapRepetition4.put("AC73",8);
                list.add(new AnswerLineDTO("AC76","ASC_7",  mapRepetition4 ));
        
        return list;
    }
        /**
     * build the AnswerLineDTO
     * question : AC77
     */
    private List<AnswerLineDTO> buildAnswerAC77(){

        List<AnswerLineDTO> list = new ArrayList<>();

                 //add repetition
        Map<String, Integer> mapRepetition1 = new HashMap<>();
                mapRepetition1.put("AC73",1);
                list.add(new AnswerLineDTO("AC77","AS_5",  mapRepetition1 ));
                //add repetition
        Map<String, Integer> mapRepetition2 = new HashMap<>();
                mapRepetition2.put("AC73",2);
                list.add(new AnswerLineDTO("AC77","AS_162",  mapRepetition2 ));
                //add repetition
        Map<String, Integer> mapRepetition3 = new HashMap<>();
                mapRepetition3.put("AC73",3);
                list.add(new AnswerLineDTO("AC77","AS_5",  mapRepetition3 ));
                //add repetition
        Map<String, Integer> mapRepetition4 = new HashMap<>();
                mapRepetition4.put("AC73",4);
                list.add(new AnswerLineDTO("AC77","AS_162",  mapRepetition4 ));
                //add repetition
        Map<String, Integer> mapRepetition5 = new HashMap<>();
                mapRepetition5.put("AC73",5);
                list.add(new AnswerLineDTO("AC77","AS_5",  mapRepetition5 ));
                //add repetition
        Map<String, Integer> mapRepetition6 = new HashMap<>();
                mapRepetition6.put("AC73",6);
                list.add(new AnswerLineDTO("AC77","AS_162",  mapRepetition6 ));
                //add repetition
        Map<String, Integer> mapRepetition7 = new HashMap<>();
                mapRepetition7.put("AC73",7);
                list.add(new AnswerLineDTO("AC77","AS_5",  mapRepetition7 ));
                //add repetition
        Map<String, Integer> mapRepetition8 = new HashMap<>();
                mapRepetition8.put("AC73",8);
                list.add(new AnswerLineDTO("AC77","AS_162",  mapRepetition8 ));
        
        return list;
    }
        /**
     * build the AnswerLineDTO
     * question : AC83
     */
    private List<AnswerLineDTO> buildAnswerAC83(){

        List<AnswerLineDTO> list = new ArrayList<>();

                 //add repetition
        Map<String, Integer> mapRepetition1 = new HashMap<>();
                mapRepetition1.put("AC73",1);
                list.add(new AnswerLineDTO("AC83",60000.0,  mapRepetition1  , UnitCode.U5106.getKey()  ));
                //add repetition
        Map<String, Integer> mapRepetition2 = new HashMap<>();
                mapRepetition2.put("AC73",2);
                list.add(new AnswerLineDTO("AC83",90000.0,  mapRepetition2  , UnitCode.U5106.getKey()  ));
                //add repetition
        Map<String, Integer> mapRepetition3 = new HashMap<>();
                mapRepetition3.put("AC73",3);
                list.add(new AnswerLineDTO("AC83",60000.0,  mapRepetition3  , UnitCode.U5106.getKey()  ));
                //add repetition
        Map<String, Integer> mapRepetition4 = new HashMap<>();
                mapRepetition4.put("AC73",4);
                list.add(new AnswerLineDTO("AC83",90000.0,  mapRepetition4  , UnitCode.U5106.getKey()  ));
                //add repetition
        Map<String, Integer> mapRepetition5 = new HashMap<>();
                mapRepetition5.put("AC73",5);
                list.add(new AnswerLineDTO("AC83",60000.0,  mapRepetition5  , UnitCode.U5106.getKey()  ));
                //add repetition
        Map<String, Integer> mapRepetition6 = new HashMap<>();
                mapRepetition6.put("AC73",6);
                list.add(new AnswerLineDTO("AC83",90000.0,  mapRepetition6  , UnitCode.U5106.getKey()  ));
                //add repetition
        Map<String, Integer> mapRepetition7 = new HashMap<>();
                mapRepetition7.put("AC73",7);
                list.add(new AnswerLineDTO("AC83",60000.0,  mapRepetition7  , UnitCode.U5106.getKey()  ));
                //add repetition
        Map<String, Integer> mapRepetition8 = new HashMap<>();
                mapRepetition8.put("AC73",8);
                list.add(new AnswerLineDTO("AC83",90000.0,  mapRepetition8  , UnitCode.U5106.getKey()  ));
        
        return list;
    }
        /**
     * build the AnswerLineDTO
     * question : AC81
     */
    private List<AnswerLineDTO> buildAnswerAC81(){

        List<AnswerLineDTO> list = new ArrayList<>();

                 //add repetition
        Map<String, Integer> mapRepetition1 = new HashMap<>();
                mapRepetition1.put("AC73",5);
                list.add(new AnswerLineDTO("AC81",1.0,  mapRepetition1 ));
                //add repetition
        Map<String, Integer> mapRepetition2 = new HashMap<>();
                mapRepetition2.put("AC73",6);
                list.add(new AnswerLineDTO("AC81",0.9,  mapRepetition2 ));
        
        return list;
    }
        /**
     * build the AnswerLineDTO
     * question : AC75
     */
    private List<AnswerLineDTO> buildAnswerAC75(){

        List<AnswerLineDTO> list = new ArrayList<>();

                 //add repetition
        Map<String, Integer> mapRepetition1 = new HashMap<>();
                mapRepetition1.put("AC73",1);
                list.add(new AnswerLineDTO("AC75","1",  mapRepetition1 ));
                //add repetition
        Map<String, Integer> mapRepetition2 = new HashMap<>();
                mapRepetition2.put("AC73",2);
                list.add(new AnswerLineDTO("AC75","2",  mapRepetition2 ));
                //add repetition
        Map<String, Integer> mapRepetition3 = new HashMap<>();
                mapRepetition3.put("AC73",3);
                list.add(new AnswerLineDTO("AC75","1",  mapRepetition3 ));
                //add repetition
        Map<String, Integer> mapRepetition4 = new HashMap<>();
                mapRepetition4.put("AC73",4);
                list.add(new AnswerLineDTO("AC75","2",  mapRepetition4 ));
                //add repetition
        Map<String, Integer> mapRepetition5 = new HashMap<>();
                mapRepetition5.put("AC73",5);
                list.add(new AnswerLineDTO("AC75","1",  mapRepetition5 ));
                //add repetition
        Map<String, Integer> mapRepetition6 = new HashMap<>();
                mapRepetition6.put("AC73",6);
                list.add(new AnswerLineDTO("AC75","2",  mapRepetition6 ));
                //add repetition
        Map<String, Integer> mapRepetition7 = new HashMap<>();
                mapRepetition7.put("AC73",7);
                list.add(new AnswerLineDTO("AC75","1",  mapRepetition7 ));
                //add repetition
        Map<String, Integer> mapRepetition8 = new HashMap<>();
                mapRepetition8.put("AC73",8);
                list.add(new AnswerLineDTO("AC75","2",  mapRepetition8 ));
        
        return list;
    }
        /**
     * build the AnswerLineDTO
     * question : AC78
     */
    private List<AnswerLineDTO> buildAnswerAC78(){

        List<AnswerLineDTO> list = new ArrayList<>();

                 //add repetition
        Map<String, Integer> mapRepetition1 = new HashMap<>();
                mapRepetition1.put("AC73",1);
                list.add(new AnswerLineDTO("AC78","1",  mapRepetition1 ));
                //add repetition
        Map<String, Integer> mapRepetition2 = new HashMap<>();
                mapRepetition2.put("AC73",2);
                list.add(new AnswerLineDTO("AC78","1",  mapRepetition2 ));
                //add repetition
        Map<String, Integer> mapRepetition3 = new HashMap<>();
                mapRepetition3.put("AC73",3);
                list.add(new AnswerLineDTO("AC78","5",  mapRepetition3 ));
                //add repetition
        Map<String, Integer> mapRepetition4 = new HashMap<>();
                mapRepetition4.put("AC73",4);
                list.add(new AnswerLineDTO("AC78","5",  mapRepetition4 ));
                //add repetition
        Map<String, Integer> mapRepetition5 = new HashMap<>();
                mapRepetition5.put("AC73",5);
                list.add(new AnswerLineDTO("AC78","7",  mapRepetition5 ));
                //add repetition
        Map<String, Integer> mapRepetition6 = new HashMap<>();
                mapRepetition6.put("AC73",6);
                list.add(new AnswerLineDTO("AC78","7",  mapRepetition6 ));
                //add repetition
        Map<String, Integer> mapRepetition7 = new HashMap<>();
                mapRepetition7.put("AC73",7);
                list.add(new AnswerLineDTO("AC78","3",  mapRepetition7 ));
                //add repetition
        Map<String, Integer> mapRepetition8 = new HashMap<>();
                mapRepetition8.put("AC73",8);
                list.add(new AnswerLineDTO("AC78","3",  mapRepetition8 ));
        
        return list;
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

    private boolean around(Double value1,Double value2){
        if(value1>=value2*(1-ERROR_MARGE) && value1 <= value2*(1+ERROR_MARGE)){
            return true;
        }
       return false;
    }
}
