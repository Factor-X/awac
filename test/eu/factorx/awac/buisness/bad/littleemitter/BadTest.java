package eu.factorx.awac.buisness.bad.littleemitter;

import com.fasterxml.jackson.databind.JsonNode;
import eu.factorx.awac.controllers.SecuredController;
import eu.factorx.awac.dto.awac.get.OrganizationDTO;
import eu.factorx.awac.dto.awac.post.AnswerLineDTO;
import eu.factorx.awac.dto.awac.post.QuestionAnswersDTO;
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.UnitCode;
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
    private final static Long FORM_ID = 2L;
    private final static Long PERIOD_ID = 1L;
    private String identifier = "user20";
    private String identifierPassword = "password";

    
    @Autowired
    private BAD_APE_BAD1ATest bad_APE_BAD1ATest;
    
    @Autowired
    private BAD_APE_BAD1BTest bad_APE_BAD1BTest;
    
    @Autowired
    private BAD_APE_BAD1CTest bad_APE_BAD1CTest;
    
    @Autowired
    private BAD_APE_BAD1DTest bad_APE_BAD1DTest;
    
    @Autowired
    private BAD_APE_BAD1GTest bad_APE_BAD1GTest;
    
    @Autowired
    private BAD_APE_BAD1HTest bad_APE_BAD1HTest;
    
    @Autowired
    private BAD_APE_BAD1ITest bad_APE_BAD1ITest;
    
    @Autowired
    private BAD_APE_BAD1JTest bad_APE_BAD1JTest;
    
    @Autowired
    private BAD_APE_BAD1KTest bad_APE_BAD1KTest;
    
    @Autowired
    private BAD_APE_BAD2BTest bad_APE_BAD2BTest;
    
    @Autowired
    private BAD_APE_BAD3Test bad_APE_BAD3Test;
    
    @Autowired
    private BAD_APE_BAD4Test bad_APE_BAD4Test;
    
    @Autowired
    private BAD_APE_BAD5ATest bad_APE_BAD5ATest;
    
    @Autowired
    private BAD_APE_BAD5BTest bad_APE_BAD5BTest;
    
    @Autowired
    private BAD_APE_BAD5CTest bad_APE_BAD5CTest;
    
    @Autowired
    private BAD_APE_BAD6Test bad_APE_BAD6Test;
    
    @Autowired
    private BAD_APE_BAD7ATest bad_APE_BAD7ATest;
    
    @Autowired
    private BAD_APE_BAD7BTest bad_APE_BAD7BTest;
    
    @Autowired
    private BAD_APE_BAD7CTest bad_APE_BAD7CTest;
    
    @Autowired
    private BAD_APE_BAD7ETest bad_APE_BAD7ETest;
    
    @Autowired
    private BAD_APE_BAD7FTest bad_APE_BAD7FTest;
    
    @Autowired
    private BAD_APE_BAD7GTest bad_APE_BAD7GTest;
    
    @Autowired
    private BAD_APE_BAD7HTest bad_APE_BAD7HTest;
    
    @Autowired
    private BAD_APE_BAD8ATest bad_APE_BAD8ATest;
    
    @Autowired
    private BAD_APE_BAD8BTest bad_APE_BAD8BTest;
    
    @Autowired
    private BAD_APE_BAD8ETest bad_APE_BAD8ETest;
    
    @Autowired
    private BAD_APE_BAD8FTest bad_APE_BAD8FTest;
    
    @Autowired
    private BAD_APE_BAD8GTest bad_APE_BAD8GTest;
    
    @Autowired
    private BAD_APE_BAD8HTest bad_APE_BAD8HTest;
    
    @Autowired
    private BAD_APE_BAD9Test bad_APE_BAD9Test;
    
    @Autowired
    private BAD_APE_BAD10Test bad_APE_BAD10Test;
    
    @Autowired
    private BAD_APE_BAD11Test bad_APE_BAD11Test;
    
    @Autowired
    private BAD_APE_BAD12Test bad_APE_BAD12Test;
    
    @Autowired
    private BAD_APE_BAD13Test bad_APE_BAD13Test;
    
    @Autowired
    private BAD_APE_BAD14Test bad_APE_BAD14Test;
    
    @Autowired
    private BAD_APE_BAD15Test bad_APE_BAD15Test;
    
    @Autowired
    private BAD_APE_BAD16ATest bad_APE_BAD16ATest;
    
    @Autowired
    private BAD_APE_BAD16BTest bad_APE_BAD16BTest;
    
    @Autowired
    private BAD_APE_BAD16CTest bad_APE_BAD16CTest;
    
    @Autowired
    private BAD_APE_BAD17ATest bad_APE_BAD17ATest;
    
    @Autowired
    private BAD_APE_BAD17BTest bad_APE_BAD17BTest;
    
    @Autowired
    private BAD_APE_BAD17CTest bad_APE_BAD17CTest;
    
    @Autowired
    private BAD_APE_BAD18ATest bad_APE_BAD18ATest;
    
    @Autowired
    private BAD_APE_BAD18BTest bad_APE_BAD18BTest;
    
    @Autowired
    private BAD_APE_BAD18CTest bad_APE_BAD18CTest;
    
    @Autowired
    private BAD_APE_BAD19ATest bad_APE_BAD19ATest;
    
    @Autowired
    private BAD_APE_BAD19BTest bad_APE_BAD19BTest;
    
    @Autowired
    private BAD_APE_BAD19CTest bad_APE_BAD19CTest;
    
    @Autowired
    private BAD_APE_BAD19DTest bad_APE_BAD19DTest;
    
    @Autowired
    private BAD_APE_BAD19ETest bad_APE_BAD19ETest;
    
    @Autowired
    private BAD_APE_BAD19FTest bad_APE_BAD19FTest;
    
    @Test
    public void _000_initialize() {
        // ConnectionFormDTO
        ConnectionFormDTO cfDto = new ConnectionFormDTO("user20", "password", InterfaceTypeCode.LITTLEEMITTER.getKey(), "");

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

        //
        // 1) build data
        //
        QuestionAnswersDTO questionAnswersDTO = new QuestionAnswersDTO();
        questionAnswersDTO.setFormId(FORM_ID);
        questionAnswersDTO.setPeriodId(PERIOD_ID);
        questionAnswersDTO.setScopeId(scopeId);
        questionAnswersDTO.setLastUpdateDate(new Date().toString());

        List<AnswerLineDTO> answerLineDTOList = new ArrayList<>();

        //add answers
                    answerLineDTOList.addAll(buildAnswerAP18());
                    answerLineDTOList.addAll(buildAnswerAP21());
                    answerLineDTOList.addAll(buildAnswerAP22());
                    answerLineDTOList.addAll(buildAnswerAP23());
                    answerLineDTOList.addAll(buildAnswerAP24());
                    answerLineDTOList.addAll(buildAnswerAP26());
                    answerLineDTOList.addAll(buildAnswerAP29());
                    answerLineDTOList.addAll(buildAnswerAP30());
                    answerLineDTOList.addAll(buildAnswerAP25());
                    answerLineDTOList.addAll(buildAnswerAP11());
                    answerLineDTOList.addAll(buildAnswerAP12());
                    answerLineDTOList.addAll(buildAnswerAP500());
                    answerLineDTOList.addAll(buildAnswerAP501());
                    answerLineDTOList.addAll(buildAnswerAP43());
                    answerLineDTOList.addAll(buildAnswerAP46());
                    answerLineDTOList.addAll(buildAnswerAP47());
                    answerLineDTOList.addAll(buildAnswerAP54());
                    answerLineDTOList.addAll(buildAnswerAP55());
                    answerLineDTOList.addAll(buildAnswerAP59());
                    answerLineDTOList.addAll(buildAnswerAP60());
                    answerLineDTOList.addAll(buildAnswerAP66());
                    answerLineDTOList.addAll(buildAnswerAP67());
                    answerLineDTOList.addAll(buildAnswerAP68());
                    answerLineDTOList.addAll(buildAnswerAP71());
                    answerLineDTOList.addAll(buildAnswerAP73());
                    answerLineDTOList.addAll(buildAnswerAP76());
                    answerLineDTOList.addAll(buildAnswerAP77());
                    answerLineDTOList.addAll(buildAnswerAP82());
                    answerLineDTOList.addAll(buildAnswerAP85());
                    answerLineDTOList.addAll(buildAnswerAP86());
                    answerLineDTOList.addAll(buildAnswerAP87());
                    answerLineDTOList.addAll(buildAnswerAP90());
                    answerLineDTOList.addAll(buildAnswerAP83());
                    answerLineDTOList.addAll(buildAnswerAP7());
                    answerLineDTOList.addAll(buildAnswerAP92());
                    answerLineDTOList.addAll(buildAnswerAP94());
                    answerLineDTOList.addAll(buildAnswerAP96());
                    answerLineDTOList.addAll(buildAnswerAP98());
                    answerLineDTOList.addAll(buildAnswerAP97());
                    answerLineDTOList.addAll(buildAnswerAP100());
                    answerLineDTOList.addAll(buildAnswerAP101());
                    answerLineDTOList.addAll(buildAnswerAP99());
                    answerLineDTOList.addAll(buildAnswerAP102());
                    answerLineDTOList.addAll(buildAnswerAP110());
                    answerLineDTOList.addAll(buildAnswerAP112());
                    answerLineDTOList.addAll(buildAnswerAP117());
                    answerLineDTOList.addAll(buildAnswerAP116());
                    answerLineDTOList.addAll(buildAnswerAP118());
                    answerLineDTOList.addAll(buildAnswerAP119());
                    answerLineDTOList.addAll(buildAnswerAP120());
                    answerLineDTOList.addAll(buildAnswerAP125());
                    answerLineDTOList.addAll(buildAnswerAP127());
                    answerLineDTOList.addAll(buildAnswerAP132());
                    answerLineDTOList.addAll(buildAnswerAP131());
                    answerLineDTOList.addAll(buildAnswerAP133());
                    answerLineDTOList.addAll(buildAnswerAP135());
                    answerLineDTOList.addAll(buildAnswerAP137());
                    answerLineDTOList.addAll(buildAnswerAP136());
                    answerLineDTOList.addAll(buildAnswerAP139());
                    answerLineDTOList.addAll(buildAnswerAP138());
                    answerLineDTOList.addAll(buildAnswerAP145());
                    answerLineDTOList.addAll(buildAnswerAP146());
                    answerLineDTOList.addAll(buildAnswerAP147());
                    answerLineDTOList.addAll(buildAnswerAP149());
                    answerLineDTOList.addAll(buildAnswerAP148());
                    answerLineDTOList.addAll(buildAnswerAP150());
                    answerLineDTOList.addAll(buildAnswerAP153());
                    answerLineDTOList.addAll(buildAnswerAP154());
                    answerLineDTOList.addAll(buildAnswerAP155());
                    answerLineDTOList.addAll(buildAnswerAP157());
                    answerLineDTOList.addAll(buildAnswerAP156());
                    answerLineDTOList.addAll(buildAnswerAP158());
                    answerLineDTOList.addAll(buildAnswerAP162());
                    answerLineDTOList.addAll(buildAnswerAP164());
                    answerLineDTOList.addAll(buildAnswerAP165());
                    answerLineDTOList.addAll(buildAnswerAP166());
                    answerLineDTOList.addAll(buildAnswerAP190());
                    answerLineDTOList.addAll(buildAnswerAP191());
                    answerLineDTOList.addAll(buildAnswerAP192());
                    answerLineDTOList.addAll(buildAnswerAP193());
                    answerLineDTOList.addAll(buildAnswerAP194());
                    answerLineDTOList.addAll(buildAnswerAP195());
        
        questionAnswersDTO.setListAnswers(answerLineDTOList);

        //
        // 2) send request
        //
        //Json node
        JsonNode node = Json.toJson(questionAnswersDTO);
        FakeRequest saveFakeRequestBad = new FakeRequest();
        saveFakeRequestBad.withHeader("Content-type", "application/json");
        saveFakeRequestBad.withJsonBody(node);

        //connection
        saveFakeRequestBad.withSession(SecuredController.SESSION_IDENTIFIER_STORE,cfDto.getLogin());

        // Call controller action
        Result resultBad = callAction(
        eu.factorx.awac.controllers.routes.ref.AnswerController.save(),
        saveFakeRequestBad
        );

        // control result
        assertEquals(200, status(resultBad));

    }

        @Test
    public void _AAB_badAPE_BAD1A() {
        bad_APE_BAD1ATest.test(scopeId);
    }
        @Test
    public void _AAC_badAPE_BAD1B() {
        bad_APE_BAD1BTest.test(scopeId);
    }
        @Test
    public void _AAD_badAPE_BAD1C() {
        bad_APE_BAD1CTest.test(scopeId);
    }
        @Test
    public void _AAE_badAPE_BAD1D() {
        bad_APE_BAD1DTest.test(scopeId);
    }
        @Test
    public void _AAH_badAPE_BAD1G() {
        bad_APE_BAD1GTest.test(scopeId);
    }
        @Test
    public void _AAI_badAPE_BAD1H() {
        bad_APE_BAD1HTest.test(scopeId);
    }
        @Test
    public void _AAJ_badAPE_BAD1I() {
        bad_APE_BAD1ITest.test(scopeId);
    }
        @Test
    public void _AAK_badAPE_BAD1J() {
        bad_APE_BAD1JTest.test(scopeId);
    }
        @Test
    public void _AAL_badAPE_BAD1K() {
        bad_APE_BAD1KTest.test(scopeId);
    }
        @Test
    public void _AAN_badAPE_BAD2B() {
        bad_APE_BAD2BTest.test(scopeId);
    }
        @Test
    public void _AAS_badAPE_BAD3() {
        bad_APE_BAD3Test.test(scopeId);
    }
        @Test
    public void _AAT_badAPE_BAD4() {
        bad_APE_BAD4Test.test(scopeId);
    }
        @Test
    public void _AAU_badAPE_BAD5A() {
        bad_APE_BAD5ATest.test(scopeId);
    }
        @Test
    public void _AAV_badAPE_BAD5B() {
        bad_APE_BAD5BTest.test(scopeId);
    }
        @Test
    public void _AAW_badAPE_BAD5C() {
        bad_APE_BAD5CTest.test(scopeId);
    }
        @Test
    public void _AAX_badAPE_BAD6() {
        bad_APE_BAD6Test.test(scopeId);
    }
        @Test
    public void _AAY_badAPE_BAD7A() {
        bad_APE_BAD7ATest.test(scopeId);
    }
        @Test
    public void _ABA_badAPE_BAD7B() {
        bad_APE_BAD7BTest.test(scopeId);
    }
        @Test
    public void _ABB_badAPE_BAD7C() {
        bad_APE_BAD7CTest.test(scopeId);
    }
        @Test
    public void _ABC_badAPE_BAD7E() {
        bad_APE_BAD7ETest.test(scopeId);
    }
        @Test
    public void _ABD_badAPE_BAD7F() {
        bad_APE_BAD7FTest.test(scopeId);
    }
        @Test
    public void _ABE_badAPE_BAD7G() {
        bad_APE_BAD7GTest.test(scopeId);
    }
        @Test
    public void _ABF_badAPE_BAD7H() {
        bad_APE_BAD7HTest.test(scopeId);
    }
        @Test
    public void _ABG_badAPE_BAD8A() {
        bad_APE_BAD8ATest.test(scopeId);
    }
        @Test
    public void _ABH_badAPE_BAD8B() {
        bad_APE_BAD8BTest.test(scopeId);
    }
        @Test
    public void _ABI_badAPE_BAD8E() {
        bad_APE_BAD8ETest.test(scopeId);
    }
        @Test
    public void _ABJ_badAPE_BAD8F() {
        bad_APE_BAD8FTest.test(scopeId);
    }
        @Test
    public void _ABK_badAPE_BAD8G() {
        bad_APE_BAD8GTest.test(scopeId);
    }
        @Test
    public void _ABL_badAPE_BAD8H() {
        bad_APE_BAD8HTest.test(scopeId);
    }
        @Test
    public void _ABM_badAPE_BAD9() {
        bad_APE_BAD9Test.test(scopeId);
    }
        @Test
    public void _ABN_badAPE_BAD10() {
        bad_APE_BAD10Test.test(scopeId);
    }
        @Test
    public void _ABO_badAPE_BAD11() {
        bad_APE_BAD11Test.test(scopeId);
    }
        @Test
    public void _ABP_badAPE_BAD12() {
        bad_APE_BAD12Test.test(scopeId);
    }
        @Test
    public void _ABQ_badAPE_BAD13() {
        bad_APE_BAD13Test.test(scopeId);
    }
        @Test
    public void _ABR_badAPE_BAD14() {
        bad_APE_BAD14Test.test(scopeId);
    }
        @Test
    public void _ABS_badAPE_BAD15() {
        bad_APE_BAD15Test.test(scopeId);
    }
        @Test
    public void _ABT_badAPE_BAD16A() {
        bad_APE_BAD16ATest.test(scopeId);
    }
        @Test
    public void _ABU_badAPE_BAD16B() {
        bad_APE_BAD16BTest.test(scopeId);
    }
        @Test
    public void _ABV_badAPE_BAD16C() {
        bad_APE_BAD16CTest.test(scopeId);
    }
        @Test
    public void _ABW_badAPE_BAD17A() {
        bad_APE_BAD17ATest.test(scopeId);
    }
        @Test
    public void _ABX_badAPE_BAD17B() {
        bad_APE_BAD17BTest.test(scopeId);
    }
        @Test
    public void _ABY_badAPE_BAD17C() {
        bad_APE_BAD17CTest.test(scopeId);
    }
        @Test
    public void _ACA_badAPE_BAD18A() {
        bad_APE_BAD18ATest.test(scopeId);
    }
        @Test
    public void _ACB_badAPE_BAD18B() {
        bad_APE_BAD18BTest.test(scopeId);
    }
        @Test
    public void _ACC_badAPE_BAD18C() {
        bad_APE_BAD18CTest.test(scopeId);
    }
        @Test
    public void _ACD_badAPE_BAD19A() {
        bad_APE_BAD19ATest.test(scopeId);
    }
        @Test
    public void _ACE_badAPE_BAD19B() {
        bad_APE_BAD19BTest.test(scopeId);
    }
        @Test
    public void _ACF_badAPE_BAD19C() {
        bad_APE_BAD19CTest.test(scopeId);
    }
        @Test
    public void _ACG_badAPE_BAD19D() {
        bad_APE_BAD19DTest.test(scopeId);
    }
        @Test
    public void _ACH_badAPE_BAD19E() {
        bad_APE_BAD19ETest.test(scopeId);
    }
        @Test
    public void _ACI_badAPE_BAD19F() {
        bad_APE_BAD19FTest.test(scopeId);
    }
    
            /**
        * build the AnswerLineDTO
        * question : AP18
        */
        private List<AnswerLineDTO> buildAnswerAP18(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP17",1);
                        list.add(new AnswerLineDTO("AP18","AS_42",  mapRepetition1 ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AP17",2);
                        list.add(new AnswerLineDTO("AP18","AS_42",  mapRepetition2 ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AP17",3);
                        list.add(new AnswerLineDTO("AP18","AS_42",  mapRepetition3 ));
                    //add repetition
            Map<String, Integer> mapRepetition4 = new HashMap<>();
                            mapRepetition4.put("AP17",4);
                        list.add(new AnswerLineDTO("AP18","AS_1",  mapRepetition4 ));
                    //add repetition
            Map<String, Integer> mapRepetition5 = new HashMap<>();
                            mapRepetition5.put("AP17",5);
                        list.add(new AnswerLineDTO("AP18","AS_1",  mapRepetition5 ));
                    //add repetition
            Map<String, Integer> mapRepetition6 = new HashMap<>();
                            mapRepetition6.put("AP17",6);
                        list.add(new AnswerLineDTO("AP18","AS_1",  mapRepetition6 ));
                    //add repetition
            Map<String, Integer> mapRepetition7 = new HashMap<>();
                            mapRepetition7.put("AP17",7);
                        list.add(new AnswerLineDTO("AP18","AS_10",  mapRepetition7 ));
                    //add repetition
            Map<String, Integer> mapRepetition8 = new HashMap<>();
                            mapRepetition8.put("AP17",8);
                        list.add(new AnswerLineDTO("AP18","AS_10",  mapRepetition8 ));
                    //add repetition
            Map<String, Integer> mapRepetition9 = new HashMap<>();
                            mapRepetition9.put("AP17",9);
                        list.add(new AnswerLineDTO("AP18","AS_10",  mapRepetition9 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP21
        */
        private List<AnswerLineDTO> buildAnswerAP21(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP17",4);
                        list.add(new AnswerLineDTO("AP21",3000.0,  mapRepetition1  , UnitCode.U5126.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP22
        */
        private List<AnswerLineDTO> buildAnswerAP22(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP17",7);
                        list.add(new AnswerLineDTO("AP22",4000.0,  mapRepetition1  , UnitCode.U5133.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP23
        */
        private List<AnswerLineDTO> buildAnswerAP23(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP17",1);
                        list.add(new AnswerLineDTO("AP23",5000.0,  mapRepetition1  , UnitCode.U5156.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP24
        */
        private List<AnswerLineDTO> buildAnswerAP24(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP17",2);
                        list.add(new AnswerLineDTO("AP24",8000.0,  mapRepetition1  , UnitCode.U5170.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AP17",5);
                        list.add(new AnswerLineDTO("AP24",8000.0,  mapRepetition2  , UnitCode.U5170.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AP17",8);
                        list.add(new AnswerLineDTO("AP24",8000.0,  mapRepetition3  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP26
        */
        private List<AnswerLineDTO> buildAnswerAP26(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP17",5);
                        list.add(new AnswerLineDTO("AP26",1.3,  mapRepetition1  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP29
        */
        private List<AnswerLineDTO> buildAnswerAP29(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP17",8);
                        list.add(new AnswerLineDTO("AP29",1.1,  mapRepetition1  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP30
        */
        private List<AnswerLineDTO> buildAnswerAP30(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP17",2);
                        list.add(new AnswerLineDTO("AP30",1.0,  mapRepetition1  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP25
        */
        private List<AnswerLineDTO> buildAnswerAP25(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP17",6);
                        list.add(new AnswerLineDTO("AP25",8000.0,  mapRepetition1  , UnitCode.U5126.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP11
        */
        private List<AnswerLineDTO> buildAnswerAP11(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP11",200.0,  mapRepetition1  , UnitCode.U5115.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP12
        */
        private List<AnswerLineDTO> buildAnswerAP12(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP12",400.0,  mapRepetition1  , UnitCode.U5115.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP500
        */
        private List<AnswerLineDTO> buildAnswerAP500(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP17",9);
                        list.add(new AnswerLineDTO("AP500",10000.0,  mapRepetition1  , UnitCode.U5133.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP501
        */
        private List<AnswerLineDTO> buildAnswerAP501(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP17",3);
                        list.add(new AnswerLineDTO("AP501",15000.0,  mapRepetition1  , UnitCode.U5156.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP43
        */
        private List<AnswerLineDTO> buildAnswerAP43(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP43","AT_3",  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP46
        */
        private List<AnswerLineDTO> buildAnswerAP46(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP46",7000.0,  mapRepetition1  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP47
        */
        private List<AnswerLineDTO> buildAnswerAP47(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP47",2.0,  mapRepetition1  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP54
        */
        private List<AnswerLineDTO> buildAnswerAP54(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP53",1);
                        list.add(new AnswerLineDTO("AP54","AS_378",  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP55
        */
        private List<AnswerLineDTO> buildAnswerAP55(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP53",1);
                        list.add(new AnswerLineDTO("AP55",3.0,  mapRepetition1  , UnitCode.U5133.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP59
        */
        private List<AnswerLineDTO> buildAnswerAP59(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP58",1);
                        list.add(new AnswerLineDTO("AP59","AS_378",  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP60
        */
        private List<AnswerLineDTO> buildAnswerAP60(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP58",1);
                        list.add(new AnswerLineDTO("AP60",0.02,  mapRepetition1  , UnitCode.U5135.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP66
        */
        private List<AnswerLineDTO> buildAnswerAP66(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP66",2000.0,  mapRepetition1  , UnitCode.U5126.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP67
        */
        private List<AnswerLineDTO> buildAnswerAP67(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP67",3000.0,  mapRepetition1  , UnitCode.U5126.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP68
        */
        private List<AnswerLineDTO> buildAnswerAP68(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP68",4000.0,  mapRepetition1  , UnitCode.U5126.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP71
        */
        private List<AnswerLineDTO> buildAnswerAP71(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP70",1);
                        list.add(new AnswerLineDTO("AP71","Test",  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP73
        */
        private List<AnswerLineDTO> buildAnswerAP73(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP70",1);
                        list.add(new AnswerLineDTO("AP73","AS_6",  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP76
        */
        private List<AnswerLineDTO> buildAnswerAP76(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP70",1);
                        list.add(new AnswerLineDTO("AP76",5.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP77
        */
        private List<AnswerLineDTO> buildAnswerAP77(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP70",1);
                        list.add(new AnswerLineDTO("AP77",100000.0,  mapRepetition1  , UnitCode.U5106.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP82
        */
        private List<AnswerLineDTO> buildAnswerAP82(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP81",1);
                        list.add(new AnswerLineDTO("AP82","1",  mapRepetition1 ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AP81",2);
                        list.add(new AnswerLineDTO("AP82","2",  mapRepetition2 ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AP81",3);
                        list.add(new AnswerLineDTO("AP82","3",  mapRepetition3 ));
                    //add repetition
            Map<String, Integer> mapRepetition4 = new HashMap<>();
                            mapRepetition4.put("AP81",4);
                        list.add(new AnswerLineDTO("AP82","4",  mapRepetition4 ));
                    //add repetition
            Map<String, Integer> mapRepetition5 = new HashMap<>();
                            mapRepetition5.put("AP81",5);
                        list.add(new AnswerLineDTO("AP82","5",  mapRepetition5 ));
                    //add repetition
            Map<String, Integer> mapRepetition6 = new HashMap<>();
                            mapRepetition6.put("AP81",6);
                        list.add(new AnswerLineDTO("AP82","6",  mapRepetition6 ));
                    //add repetition
            Map<String, Integer> mapRepetition7 = new HashMap<>();
                            mapRepetition7.put("AP81",7);
                        list.add(new AnswerLineDTO("AP82","7",  mapRepetition7 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP85
        */
        private List<AnswerLineDTO> buildAnswerAP85(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP81",1);
                        list.add(new AnswerLineDTO("AP85",1.0,  mapRepetition1 ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AP81",2);
                        list.add(new AnswerLineDTO("AP85",2.0,  mapRepetition2 ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AP81",3);
                        list.add(new AnswerLineDTO("AP85",3.0,  mapRepetition3 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP86
        */
        private List<AnswerLineDTO> buildAnswerAP86(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP81",1);
                        list.add(new AnswerLineDTO("AP86",100.0,  mapRepetition1 ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AP81",2);
                        list.add(new AnswerLineDTO("AP86",200.0,  mapRepetition2 ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AP81",3);
                        list.add(new AnswerLineDTO("AP86",300.0,  mapRepetition3 ));
                    //add repetition
            Map<String, Integer> mapRepetition4 = new HashMap<>();
                            mapRepetition4.put("AP81",4);
                        list.add(new AnswerLineDTO("AP86",400.0,  mapRepetition4 ));
                    //add repetition
            Map<String, Integer> mapRepetition5 = new HashMap<>();
                            mapRepetition5.put("AP81",5);
                        list.add(new AnswerLineDTO("AP86",500.0,  mapRepetition5 ));
                    //add repetition
            Map<String, Integer> mapRepetition6 = new HashMap<>();
                            mapRepetition6.put("AP81",6);
                        list.add(new AnswerLineDTO("AP86",600.0,  mapRepetition6 ));
                    //add repetition
            Map<String, Integer> mapRepetition7 = new HashMap<>();
                            mapRepetition7.put("AP81",7);
                        list.add(new AnswerLineDTO("AP86",700.0,  mapRepetition7 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP87
        */
        private List<AnswerLineDTO> buildAnswerAP87(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP81",1);
                        list.add(new AnswerLineDTO("AP87",10.0,  mapRepetition1  , UnitCode.U5106.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AP81",2);
                        list.add(new AnswerLineDTO("AP87",20.0,  mapRepetition2  , UnitCode.U5106.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AP81",3);
                        list.add(new AnswerLineDTO("AP87",30.0,  mapRepetition3  , UnitCode.U5106.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition4 = new HashMap<>();
                            mapRepetition4.put("AP81",4);
                        list.add(new AnswerLineDTO("AP87",40.0,  mapRepetition4  , UnitCode.U5106.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition5 = new HashMap<>();
                            mapRepetition5.put("AP81",5);
                        list.add(new AnswerLineDTO("AP87",50.0,  mapRepetition5  , UnitCode.U5106.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition6 = new HashMap<>();
                            mapRepetition6.put("AP81",6);
                        list.add(new AnswerLineDTO("AP87",60.0,  mapRepetition6  , UnitCode.U5106.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition7 = new HashMap<>();
                            mapRepetition7.put("AP81",7);
                        list.add(new AnswerLineDTO("AP87",70.0,  mapRepetition7  , UnitCode.U5106.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP90
        */
        private List<AnswerLineDTO> buildAnswerAP90(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP81",1);
                        list.add(new AnswerLineDTO("AP90",1.0,  mapRepetition1 ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AP81",2);
                        list.add(new AnswerLineDTO("AP90",2.0,  mapRepetition2 ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AP81",3);
                        list.add(new AnswerLineDTO("AP90",3.0,  mapRepetition3 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP83
        */
        private List<AnswerLineDTO> buildAnswerAP83(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP81",1);
                        list.add(new AnswerLineDTO("AP83","1",  mapRepetition1 ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AP81",2);
                        list.add(new AnswerLineDTO("AP83","2",  mapRepetition2 ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AP81",3);
                        list.add(new AnswerLineDTO("AP83","3",  mapRepetition3 ));
                    //add repetition
            Map<String, Integer> mapRepetition4 = new HashMap<>();
                            mapRepetition4.put("AP81",4);
                        list.add(new AnswerLineDTO("AP83","4",  mapRepetition4 ));
                    //add repetition
            Map<String, Integer> mapRepetition5 = new HashMap<>();
                            mapRepetition5.put("AP81",5);
                        list.add(new AnswerLineDTO("AP83","5",  mapRepetition5 ));
                    //add repetition
            Map<String, Integer> mapRepetition6 = new HashMap<>();
                            mapRepetition6.put("AP81",6);
                        list.add(new AnswerLineDTO("AP83","6",  mapRepetition6 ));
                    //add repetition
            Map<String, Integer> mapRepetition7 = new HashMap<>();
                            mapRepetition7.put("AP81",7);
                        list.add(new AnswerLineDTO("AP83","7",  mapRepetition7 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP7
        */
        private List<AnswerLineDTO> buildAnswerAP7(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP7",10.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP92
        */
        private List<AnswerLineDTO> buildAnswerAP92(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP92",50.0,  mapRepetition1  , UnitCode.U5106.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP94
        */
        private List<AnswerLineDTO> buildAnswerAP94(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP94",0.2,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP96
        */
        private List<AnswerLineDTO> buildAnswerAP96(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP96",0.8,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP98
        */
        private List<AnswerLineDTO> buildAnswerAP98(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP98",6.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP97
        */
        private List<AnswerLineDTO> buildAnswerAP97(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP97",0.2,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP100
        */
        private List<AnswerLineDTO> buildAnswerAP100(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP100",0.1,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP101
        */
        private List<AnswerLineDTO> buildAnswerAP101(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP101",0.1,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP99
        */
        private List<AnswerLineDTO> buildAnswerAP99(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP99",0.1,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP102
        */
        private List<AnswerLineDTO> buildAnswerAP102(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP102",0.1,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP110
        */
        private List<AnswerLineDTO> buildAnswerAP110(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP109",1);
                        list.add(new AnswerLineDTO("AP110","Test",  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP112
        */
        private List<AnswerLineDTO> buildAnswerAP112(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP109",1);
                        list.add(new AnswerLineDTO("AP112","AS_6",  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP117
        */
        private List<AnswerLineDTO> buildAnswerAP117(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP109",1);
                        list.add(new AnswerLineDTO("AP117",200000.0,  mapRepetition1  , UnitCode.U5106.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP116
        */
        private List<AnswerLineDTO> buildAnswerAP116(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP109",1);
                        list.add(new AnswerLineDTO("AP116",10.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP118
        */
        private List<AnswerLineDTO> buildAnswerAP118(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP118",2400.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP119
        */
        private List<AnswerLineDTO> buildAnswerAP119(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP119",2400.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP120
        */
        private List<AnswerLineDTO> buildAnswerAP120(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP120",2400.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP125
        */
        private List<AnswerLineDTO> buildAnswerAP125(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP124",1);
                        list.add(new AnswerLineDTO("AP125","Test",  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP127
        */
        private List<AnswerLineDTO> buildAnswerAP127(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP124",1);
                        list.add(new AnswerLineDTO("AP127","AS_6",  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP132
        */
        private List<AnswerLineDTO> buildAnswerAP132(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP124",1);
                        list.add(new AnswerLineDTO("AP132",200000.0,  mapRepetition1  , UnitCode.U5106.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP131
        */
        private List<AnswerLineDTO> buildAnswerAP131(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP124",1);
                        list.add(new AnswerLineDTO("AP131",10.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP133
        */
        private List<AnswerLineDTO> buildAnswerAP133(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP133",2400.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP135
        */
        private List<AnswerLineDTO> buildAnswerAP135(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP134",1);
                        list.add(new AnswerLineDTO("AP135","Vol",  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP137
        */
        private List<AnswerLineDTO> buildAnswerAP137(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP134",1);
                        list.add(new AnswerLineDTO("AP137","AT_18",  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP136
        */
        private List<AnswerLineDTO> buildAnswerAP136(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP134",1);
                        list.add(new AnswerLineDTO("AP136","AS_173",  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP139
        */
        private List<AnswerLineDTO> buildAnswerAP139(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP134",1);
                        list.add(new AnswerLineDTO("AP139",2000.0,  mapRepetition1  , UnitCode.U5106.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP138
        */
        private List<AnswerLineDTO> buildAnswerAP138(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP134",1);
                        list.add(new AnswerLineDTO("AP138",5.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP145
        */
        private List<AnswerLineDTO> buildAnswerAP145(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP144",1);
                        list.add(new AnswerLineDTO("AP145","Gros",  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP146
        */
        private List<AnswerLineDTO> buildAnswerAP146(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP144",1);
                        list.add(new AnswerLineDTO("AP146",50000.0,  mapRepetition1  , UnitCode.U5133.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP147
        */
        private List<AnswerLineDTO> buildAnswerAP147(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP144",1);
                        list.add(new AnswerLineDTO("AP147",100.0,  mapRepetition1  , UnitCode.U5106.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP149
        */
        private List<AnswerLineDTO> buildAnswerAP149(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP144",1);
                        list.add(new AnswerLineDTO("AP149",0.3,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP148
        */
        private List<AnswerLineDTO> buildAnswerAP148(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP144",1);
                        list.add(new AnswerLineDTO("AP148",0.5,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP150
        */
        private List<AnswerLineDTO> buildAnswerAP150(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP144",1);
                        list.add(new AnswerLineDTO("AP150",0.2,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP153
        */
        private List<AnswerLineDTO> buildAnswerAP153(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP152",1);
                        list.add(new AnswerLineDTO("AP153","Gros",  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP154
        */
        private List<AnswerLineDTO> buildAnswerAP154(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP152",1);
                        list.add(new AnswerLineDTO("AP154",50000.0,  mapRepetition1  , UnitCode.U5133.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP155
        */
        private List<AnswerLineDTO> buildAnswerAP155(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP152",1);
                        list.add(new AnswerLineDTO("AP155",100.0,  mapRepetition1  , UnitCode.U5106.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP157
        */
        private List<AnswerLineDTO> buildAnswerAP157(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP152",1);
                        list.add(new AnswerLineDTO("AP157",0.3,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP156
        */
        private List<AnswerLineDTO> buildAnswerAP156(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP152",1);
                        list.add(new AnswerLineDTO("AP156",0.5,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP158
        */
        private List<AnswerLineDTO> buildAnswerAP158(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP152",1);
                        list.add(new AnswerLineDTO("AP158",0.2,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP162
        */
        private List<AnswerLineDTO> buildAnswerAP162(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP161",1);
                        list.add(new AnswerLineDTO("AP162","1",  mapRepetition1 ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AP161",2);
                        list.add(new AnswerLineDTO("AP162","5",  mapRepetition2 ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AP161",3);
                        list.add(new AnswerLineDTO("AP162","10",  mapRepetition3 ));
                    //add repetition
            Map<String, Integer> mapRepetition4 = new HashMap<>();
                            mapRepetition4.put("AP161",4);
                        list.add(new AnswerLineDTO("AP162","17",  mapRepetition4 ));
                    //add repetition
            Map<String, Integer> mapRepetition5 = new HashMap<>();
                            mapRepetition5.put("AP161",5);
                        list.add(new AnswerLineDTO("AP162","20",  mapRepetition5 ));
                    //add repetition
            Map<String, Integer> mapRepetition6 = new HashMap<>();
                            mapRepetition6.put("AP161",6);
                        list.add(new AnswerLineDTO("AP162","13",  mapRepetition6 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP164
        */
        private List<AnswerLineDTO> buildAnswerAP164(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP161",1);
                        list.add(new AnswerLineDTO("AP164",50.0,  mapRepetition1  , UnitCode.U5122.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AP161",2);
                        list.add(new AnswerLineDTO("AP164",50.0,  mapRepetition2  , UnitCode.U5122.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AP161",3);
                        list.add(new AnswerLineDTO("AP164",50.0,  mapRepetition3  , UnitCode.U5122.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition4 = new HashMap<>();
                            mapRepetition4.put("AP161",4);
                        list.add(new AnswerLineDTO("AP164",50.0,  mapRepetition4  , UnitCode.U5122.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP165
        */
        private List<AnswerLineDTO> buildAnswerAP165(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP161",5);
                        list.add(new AnswerLineDTO("AP165",80.0,  mapRepetition1  , UnitCode.U5135.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP166
        */
        private List<AnswerLineDTO> buildAnswerAP166(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AP161",6);
                        list.add(new AnswerLineDTO("AP166",40.0,  mapRepetition1  , UnitCode.U5135.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP190
        */
        private List<AnswerLineDTO> buildAnswerAP190(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP190",100.0,  mapRepetition1  , UnitCode.U5135.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP191
        */
        private List<AnswerLineDTO> buildAnswerAP191(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP191",200.0,  mapRepetition1  , UnitCode.U5135.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP192
        */
        private List<AnswerLineDTO> buildAnswerAP192(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP192",300.0,  mapRepetition1  , UnitCode.U5135.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP193
        */
        private List<AnswerLineDTO> buildAnswerAP193(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP193",400.0,  mapRepetition1  , UnitCode.U5135.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP194
        */
        private List<AnswerLineDTO> buildAnswerAP194(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP194",500.0,  mapRepetition1  , UnitCode.U5135.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AP195
        */
        private List<AnswerLineDTO> buildAnswerAP195(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AP195",600.0,  mapRepetition1  , UnitCode.U5135.getKey()  ));
        
        return list;
        }
    }
