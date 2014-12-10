package eu.factorx.awac.buisness.bad.household;

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
    private BAD_AM_BAD1ATest bad_AM_BAD1ATest;
    
    @Autowired
    private BAD_AM_BAD1BTest bad_AM_BAD1BTest;
    
    @Autowired
    private BAD_AM_BAD1CTest bad_AM_BAD1CTest;
    
    @Autowired
    private BAD_AM_BAD1DTest bad_AM_BAD1DTest;
    
    @Autowired
    private BAD_AM_BAD1ETest bad_AM_BAD1ETest;
    
    @Autowired
    private BAD_AM_BAD1FTest bad_AM_BAD1FTest;
    
    @Autowired
    private BAD_AM_BAD1GTest bad_AM_BAD1GTest;
    
    @Autowired
    private BAD_AM_BAD1HTest bad_AM_BAD1HTest;
    
    @Autowired
    private BAD_AM_BAD1ITest bad_AM_BAD1ITest;
    
    @Autowired
    private BAD_AM_BAD1JTest bad_AM_BAD1JTest;
    
    @Autowired
    private BAD_AM_BAD2ATest bad_AM_BAD2ATest;
    
    @Autowired
    private BAD_AM_BAD3DTest bad_AM_BAD3DTest;
    
    @Autowired
    private BAD_AM_BAD4Test bad_AM_BAD4Test;
    
    @Autowired
    private BAD_AM_BAD5Test bad_AM_BAD5Test;
    
    @Autowired
    private BAD_AM_BAD6Test bad_AM_BAD6Test;
    
    @Autowired
    private BAD_AM_BAD7Test bad_AM_BAD7Test;
    
    @Autowired
    private BAD_AM_BAD8Test bad_AM_BAD8Test;
    
    @Autowired
    private BAD_AM_BAD9Test bad_AM_BAD9Test;
    
    @Autowired
    private BAD_AM_BAD10Test bad_AM_BAD10Test;
    
    @Autowired
    private BAD_AM_BAD11Test bad_AM_BAD11Test;
    
    @Autowired
    private BAD_AM_BAD12ATest bad_AM_BAD12ATest;
    
    @Autowired
    private BAD_AM_BAD12BTest bad_AM_BAD12BTest;
    
    @Autowired
    private BAD_AM_BAD12CTest bad_AM_BAD12CTest;
    
    @Autowired
    private BAD_AM_BAD12DTest bad_AM_BAD12DTest;
    
    @Autowired
    private BAD_AM_BAD12ETest bad_AM_BAD12ETest;
    
    @Autowired
    private BAD_AM_BAD12FTest bad_AM_BAD12FTest;
    
    @Autowired
    private BAD_AM_BAD13ATest bad_AM_BAD13ATest;
    
    @Autowired
    private BAD_AM_BAD13BTest bad_AM_BAD13BTest;
    
    @Autowired
    private BAD_AM_BAD13CTest bad_AM_BAD13CTest;
    
    @Autowired
    private BAD_AM_BAD14ATest bad_AM_BAD14ATest;
    
    @Autowired
    private BAD_AM_BAD14BTest bad_AM_BAD14BTest;
    
    @Autowired
    private BAD_AM_BAD14CTest bad_AM_BAD14CTest;
    
    @Test
    public void _000_initialize() {
        // ConnectionFormDTO
        ConnectionFormDTO cfDto = new ConnectionFormDTO("user30", "password", InterfaceTypeCode.HOUSEHOLD.getKey(), "");

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
                    answerLineDTOList.addAll(buildAnswerAM27());
                    answerLineDTOList.addAll(buildAnswerAM30());
                    answerLineDTOList.addAll(buildAnswerAM31());
                    answerLineDTOList.addAll(buildAnswerAM32());
                    answerLineDTOList.addAll(buildAnswerAM33());
                    answerLineDTOList.addAll(buildAnswerAM34());
                    answerLineDTOList.addAll(buildAnswerAM35());
                    answerLineDTOList.addAll(buildAnswerAM36());
                    answerLineDTOList.addAll(buildAnswerAM37());
                    answerLineDTOList.addAll(buildAnswerAM38());
                    answerLineDTOList.addAll(buildAnswerAM39());
                    answerLineDTOList.addAll(buildAnswerAM40());
                    answerLineDTOList.addAll(buildAnswerAM49());
                    answerLineDTOList.addAll(buildAnswerAM52());
                    answerLineDTOList.addAll(buildAnswerAM66());
                    answerLineDTOList.addAll(buildAnswerAM68());
                    answerLineDTOList.addAll(buildAnswerAM69());
                    answerLineDTOList.addAll(buildAnswerAM112());
                    answerLineDTOList.addAll(buildAnswerAM113());
                    answerLineDTOList.addAll(buildAnswerAM114());
                    answerLineDTOList.addAll(buildAnswerAM115());
                    answerLineDTOList.addAll(buildAnswerAM119());
                    answerLineDTOList.addAll(buildAnswerAM120());
                    answerLineDTOList.addAll(buildAnswerAM121());
                    answerLineDTOList.addAll(buildAnswerAM123());
                    answerLineDTOList.addAll(buildAnswerAM124());
                    answerLineDTOList.addAll(buildAnswerAM125());
                    answerLineDTOList.addAll(buildAnswerAM127());
                    answerLineDTOList.addAll(buildAnswerAM128());
                    answerLineDTOList.addAll(buildAnswerAM129());
                    answerLineDTOList.addAll(buildAnswerAM131());
                    answerLineDTOList.addAll(buildAnswerAM132());
                    answerLineDTOList.addAll(buildAnswerAM133());
                    answerLineDTOList.addAll(buildAnswerAM135());
                    answerLineDTOList.addAll(buildAnswerAM137());
                    answerLineDTOList.addAll(buildAnswerAM136());
                    answerLineDTOList.addAll(buildAnswerAM138());
                    answerLineDTOList.addAll(buildAnswerAM139());
                    answerLineDTOList.addAll(buildAnswerAM141());
                    answerLineDTOList.addAll(buildAnswerAM142());
                    answerLineDTOList.addAll(buildAnswerAM154());
                    answerLineDTOList.addAll(buildAnswerAM157());
                    answerLineDTOList.addAll(buildAnswerAM158());
                    answerLineDTOList.addAll(buildAnswerAM159());
                    answerLineDTOList.addAll(buildAnswerAM161());
                    answerLineDTOList.addAll(buildAnswerAM166());
                    answerLineDTOList.addAll(buildAnswerAM172());
                    answerLineDTOList.addAll(buildAnswerAM173());
                    answerLineDTOList.addAll(buildAnswerAM174());
                    answerLineDTOList.addAll(buildAnswerAM177());
                    answerLineDTOList.addAll(buildAnswerAM178());
                    answerLineDTOList.addAll(buildAnswerAM179());
        
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
    public void _AAB_badAM_BAD1A() {
        bad_AM_BAD1ATest.test(scopeId);
    }
        @Test
    public void _AAC_badAM_BAD1B() {
        bad_AM_BAD1BTest.test(scopeId);
    }
        @Test
    public void _AAD_badAM_BAD1C() {
        bad_AM_BAD1CTest.test(scopeId);
    }
        @Test
    public void _AAE_badAM_BAD1D() {
        bad_AM_BAD1DTest.test(scopeId);
    }
        @Test
    public void _AAF_badAM_BAD1E() {
        bad_AM_BAD1ETest.test(scopeId);
    }
        @Test
    public void _AAG_badAM_BAD1F() {
        bad_AM_BAD1FTest.test(scopeId);
    }
        @Test
    public void _AAH_badAM_BAD1G() {
        bad_AM_BAD1GTest.test(scopeId);
    }
        @Test
    public void _AAI_badAM_BAD1H() {
        bad_AM_BAD1HTest.test(scopeId);
    }
        @Test
    public void _AAJ_badAM_BAD1I() {
        bad_AM_BAD1ITest.test(scopeId);
    }
        @Test
    public void _AAK_badAM_BAD1J() {
        bad_AM_BAD1JTest.test(scopeId);
    }
        @Test
    public void _AAL_badAM_BAD2A() {
        bad_AM_BAD2ATest.test(scopeId);
    }
        @Test
    public void _AAU_badAM_BAD3D() {
        bad_AM_BAD3DTest.test(scopeId);
    }
        @Test
    public void _AAV_badAM_BAD4() {
        bad_AM_BAD4Test.test(scopeId);
    }
        @Test
    public void _AAW_badAM_BAD5() {
        bad_AM_BAD5Test.test(scopeId);
    }
        @Test
    public void _AAX_badAM_BAD6() {
        bad_AM_BAD6Test.test(scopeId);
    }
        @Test
    public void _AAY_badAM_BAD7() {
        bad_AM_BAD7Test.test(scopeId);
    }
        @Test
    public void _ABA_badAM_BAD8() {
        bad_AM_BAD8Test.test(scopeId);
    }
        @Test
    public void _ABB_badAM_BAD9() {
        bad_AM_BAD9Test.test(scopeId);
    }
        @Test
    public void _ABC_badAM_BAD10() {
        bad_AM_BAD10Test.test(scopeId);
    }
        @Test
    public void _ABD_badAM_BAD11() {
        bad_AM_BAD11Test.test(scopeId);
    }
        @Test
    public void _ABE_badAM_BAD12A() {
        bad_AM_BAD12ATest.test(scopeId);
    }
        @Test
    public void _ABF_badAM_BAD12B() {
        bad_AM_BAD12BTest.test(scopeId);
    }
        @Test
    public void _ABG_badAM_BAD12C() {
        bad_AM_BAD12CTest.test(scopeId);
    }
        @Test
    public void _ABH_badAM_BAD12D() {
        bad_AM_BAD12DTest.test(scopeId);
    }
        @Test
    public void _ABI_badAM_BAD12E() {
        bad_AM_BAD12ETest.test(scopeId);
    }
        @Test
    public void _ABJ_badAM_BAD12F() {
        bad_AM_BAD12FTest.test(scopeId);
    }
        @Test
    public void _ABK_badAM_BAD13A() {
        bad_AM_BAD13ATest.test(scopeId);
    }
        @Test
    public void _ABL_badAM_BAD13B() {
        bad_AM_BAD13BTest.test(scopeId);
    }
        @Test
    public void _ABM_badAM_BAD13C() {
        bad_AM_BAD13CTest.test(scopeId);
    }
        @Test
    public void _ABN_badAM_BAD14A() {
        bad_AM_BAD14ATest.test(scopeId);
    }
        @Test
    public void _ABO_badAM_BAD14B() {
        bad_AM_BAD14BTest.test(scopeId);
    }
        @Test
    public void _ABP_badAM_BAD14C() {
        bad_AM_BAD14CTest.test(scopeId);
    }
    
            /**
        * build the AnswerLineDTO
        * question : AM27
        */
        private List<AnswerLineDTO> buildAnswerAM27(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM26",1);
                        list.add(new AnswerLineDTO("AM27","AS_1",  mapRepetition1 ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AM26",2);
                        list.add(new AnswerLineDTO("AM27","AS_10",  mapRepetition2 ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AM26",3);
                        list.add(new AnswerLineDTO("AM27","AS_42",  mapRepetition3 ));
                    //add repetition
            Map<String, Integer> mapRepetition4 = new HashMap<>();
                            mapRepetition4.put("AM26",4);
                        list.add(new AnswerLineDTO("AM27","AS_1",  mapRepetition4 ));
                    //add repetition
            Map<String, Integer> mapRepetition5 = new HashMap<>();
                            mapRepetition5.put("AM26",5);
                        list.add(new AnswerLineDTO("AM27","AS_376",  mapRepetition5 ));
                    //add repetition
            Map<String, Integer> mapRepetition6 = new HashMap<>();
                            mapRepetition6.put("AM26",6);
                        list.add(new AnswerLineDTO("AM27","AS_15",  mapRepetition6 ));
                    //add repetition
            Map<String, Integer> mapRepetition7 = new HashMap<>();
                            mapRepetition7.put("AM26",7);
                        list.add(new AnswerLineDTO("AM27","AS_16",  mapRepetition7 ));
                    //add repetition
            Map<String, Integer> mapRepetition8 = new HashMap<>();
                            mapRepetition8.put("AM26",8);
                        list.add(new AnswerLineDTO("AM27","AS_11",  mapRepetition8 ));
                    //add repetition
            Map<String, Integer> mapRepetition9 = new HashMap<>();
                            mapRepetition9.put("AM26",9);
                        list.add(new AnswerLineDTO("AM27","AS_10",  mapRepetition9 ));
                    //add repetition
            Map<String, Integer> mapRepetition10 = new HashMap<>();
                            mapRepetition10.put("AM26",10);
                        list.add(new AnswerLineDTO("AM27","AS_42",  mapRepetition10 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM30
        */
        private List<AnswerLineDTO> buildAnswerAM30(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM26",1);
                        list.add(new AnswerLineDTO("AM30",2000.0,  mapRepetition1  , UnitCode.U5126.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM31
        */
        private List<AnswerLineDTO> buildAnswerAM31(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM26",2);
                        list.add(new AnswerLineDTO("AM31",3000.0,  mapRepetition1  , UnitCode.U5133.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM32
        */
        private List<AnswerLineDTO> buildAnswerAM32(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM26",3);
                        list.add(new AnswerLineDTO("AM32",4000.0,  mapRepetition1  , UnitCode.U5156.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM33
        */
        private List<AnswerLineDTO> buildAnswerAM33(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM26",4);
                        list.add(new AnswerLineDTO("AM33",1000.0,  mapRepetition1  , UnitCode.U5170.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AM26",5);
                        list.add(new AnswerLineDTO("AM33",1000.0,  mapRepetition2  , UnitCode.U5170.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AM26",6);
                        list.add(new AnswerLineDTO("AM33",1000.0,  mapRepetition3  , UnitCode.U5170.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition4 = new HashMap<>();
                            mapRepetition4.put("AM26",7);
                        list.add(new AnswerLineDTO("AM33",1000.0,  mapRepetition4  , UnitCode.U5170.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition5 = new HashMap<>();
                            mapRepetition5.put("AM26",8);
                        list.add(new AnswerLineDTO("AM33",1000.0,  mapRepetition5  , UnitCode.U5170.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition6 = new HashMap<>();
                            mapRepetition6.put("AM26",9);
                        list.add(new AnswerLineDTO("AM33",1000.0,  mapRepetition6  , UnitCode.U5170.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition7 = new HashMap<>();
                            mapRepetition7.put("AM26",10);
                        list.add(new AnswerLineDTO("AM33",1000.0,  mapRepetition7  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM34
        */
        private List<AnswerLineDTO> buildAnswerAM34(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM26",4);
                        list.add(new AnswerLineDTO("AM34",0.6,  mapRepetition1  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM35
        */
        private List<AnswerLineDTO> buildAnswerAM35(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM26",5);
                        list.add(new AnswerLineDTO("AM35",3.0,  mapRepetition1  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM36
        */
        private List<AnswerLineDTO> buildAnswerAM36(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM26",6);
                        list.add(new AnswerLineDTO("AM36",3.5,  mapRepetition1  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM37
        */
        private List<AnswerLineDTO> buildAnswerAM37(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM26",7);
                        list.add(new AnswerLineDTO("AM37",3.8,  mapRepetition1  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM38
        */
        private List<AnswerLineDTO> buildAnswerAM38(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM26",8);
                        list.add(new AnswerLineDTO("AM38",1.0,  mapRepetition1  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM39
        */
        private List<AnswerLineDTO> buildAnswerAM39(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM26",9);
                        list.add(new AnswerLineDTO("AM39",0.8,  mapRepetition1  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM40
        */
        private List<AnswerLineDTO> buildAnswerAM40(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM26",10);
                        list.add(new AnswerLineDTO("AM40",0.3,  mapRepetition1  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM49
        */
        private List<AnswerLineDTO> buildAnswerAM49(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM49","AS_3",  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM52
        */
        private List<AnswerLineDTO> buildAnswerAM52(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM52",2.5,  mapRepetition1  , UnitCode.U5122.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM66
        */
        private List<AnswerLineDTO> buildAnswerAM66(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM66","2",  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM68
        */
        private List<AnswerLineDTO> buildAnswerAM68(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM68",3000.0,  mapRepetition1  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM69
        */
        private List<AnswerLineDTO> buildAnswerAM69(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM69",3.0,  mapRepetition1  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM112
        */
        private List<AnswerLineDTO> buildAnswerAM112(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM111",1);
                        list.add(new AnswerLineDTO("AM112","1",  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM113
        */
        private List<AnswerLineDTO> buildAnswerAM113(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM111",1);
                        list.add(new AnswerLineDTO("AM113","AS_6",  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM114
        */
        private List<AnswerLineDTO> buildAnswerAM114(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM111",1);
                        list.add(new AnswerLineDTO("AM114",20000.0,  mapRepetition1  , UnitCode.U5106.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM115
        */
        private List<AnswerLineDTO> buildAnswerAM115(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM111",1);
                        list.add(new AnswerLineDTO("AM115",5.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM119
        */
        private List<AnswerLineDTO> buildAnswerAM119(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM119",1.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM120
        */
        private List<AnswerLineDTO> buildAnswerAM120(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM120",220.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM121
        */
        private List<AnswerLineDTO> buildAnswerAM121(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM121",10.0,  mapRepetition1  , UnitCode.U5106.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM123
        */
        private List<AnswerLineDTO> buildAnswerAM123(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM123",1.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM124
        */
        private List<AnswerLineDTO> buildAnswerAM124(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM124",220.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM125
        */
        private List<AnswerLineDTO> buildAnswerAM125(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM125",10.0,  mapRepetition1  , UnitCode.U5106.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM127
        */
        private List<AnswerLineDTO> buildAnswerAM127(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM127",1.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM128
        */
        private List<AnswerLineDTO> buildAnswerAM128(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM128",220.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM129
        */
        private List<AnswerLineDTO> buildAnswerAM129(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM129",10.0,  mapRepetition1  , UnitCode.U5106.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM131
        */
        private List<AnswerLineDTO> buildAnswerAM131(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM131",1.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM132
        */
        private List<AnswerLineDTO> buildAnswerAM132(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM132",220.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM133
        */
        private List<AnswerLineDTO> buildAnswerAM133(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM133",10.0,  mapRepetition1  , UnitCode.U5106.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM135
        */
        private List<AnswerLineDTO> buildAnswerAM135(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM134",1);
                        list.add(new AnswerLineDTO("AM135","Vacances",  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM137
        */
        private List<AnswerLineDTO> buildAnswerAM137(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM134",1);
                        list.add(new AnswerLineDTO("AM137","AT_18",  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM136
        */
        private List<AnswerLineDTO> buildAnswerAM136(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM134",1);
                        list.add(new AnswerLineDTO("AM136","AS_174",  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM138
        */
        private List<AnswerLineDTO> buildAnswerAM138(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM134",1);
                        list.add(new AnswerLineDTO("AM138",3.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM139
        */
        private List<AnswerLineDTO> buildAnswerAM139(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM134",1);
                        list.add(new AnswerLineDTO("AM139",700.0,  mapRepetition1  , UnitCode.U5106.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM141
        */
        private List<AnswerLineDTO> buildAnswerAM141(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM141",5.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM142
        */
        private List<AnswerLineDTO> buildAnswerAM142(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM142",300.0,  mapRepetition1  , UnitCode.U5106.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM154
        */
        private List<AnswerLineDTO> buildAnswerAM154(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM153",1);
                        list.add(new AnswerLineDTO("AM154","1",  mapRepetition1 ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AM153",2);
                        list.add(new AnswerLineDTO("AM154","5",  mapRepetition2 ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AM153",3);
                        list.add(new AnswerLineDTO("AM154","10",  mapRepetition3 ));
                    //add repetition
            Map<String, Integer> mapRepetition4 = new HashMap<>();
                            mapRepetition4.put("AM153",4);
                        list.add(new AnswerLineDTO("AM154","17",  mapRepetition4 ));
                    //add repetition
            Map<String, Integer> mapRepetition5 = new HashMap<>();
                            mapRepetition5.put("AM153",5);
                        list.add(new AnswerLineDTO("AM154","20",  mapRepetition5 ));
                    //add repetition
            Map<String, Integer> mapRepetition6 = new HashMap<>();
                            mapRepetition6.put("AM153",6);
                        list.add(new AnswerLineDTO("AM154","13",  mapRepetition6 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM157
        */
        private List<AnswerLineDTO> buildAnswerAM157(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM153",1);
                        list.add(new AnswerLineDTO("AM157",1.0,  mapRepetition1 ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AM153",2);
                        list.add(new AnswerLineDTO("AM157",2.0,  mapRepetition2 ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AM153",3);
                        list.add(new AnswerLineDTO("AM157",3.0,  mapRepetition3 ));
                    //add repetition
            Map<String, Integer> mapRepetition4 = new HashMap<>();
                            mapRepetition4.put("AM153",4);
                        list.add(new AnswerLineDTO("AM157",4.0,  mapRepetition4 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM158
        */
        private List<AnswerLineDTO> buildAnswerAM158(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM153",1);
                        list.add(new AnswerLineDTO("AM158",50.0,  mapRepetition1  , UnitCode.U5126.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AM153",2);
                        list.add(new AnswerLineDTO("AM158",50.0,  mapRepetition2  , UnitCode.U5126.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AM153",3);
                        list.add(new AnswerLineDTO("AM158",50.0,  mapRepetition3  , UnitCode.U5126.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition4 = new HashMap<>();
                            mapRepetition4.put("AM153",4);
                        list.add(new AnswerLineDTO("AM158",50.0,  mapRepetition4  , UnitCode.U5126.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM159
        */
        private List<AnswerLineDTO> buildAnswerAM159(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM153",1);
                        list.add(new AnswerLineDTO("AM159","1",  mapRepetition1 ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AM153",2);
                        list.add(new AnswerLineDTO("AM159","2",  mapRepetition2 ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AM153",3);
                        list.add(new AnswerLineDTO("AM159","3",  mapRepetition3 ));
                    //add repetition
            Map<String, Integer> mapRepetition4 = new HashMap<>();
                            mapRepetition4.put("AM153",4);
                        list.add(new AnswerLineDTO("AM159","4",  mapRepetition4 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM161
        */
        private List<AnswerLineDTO> buildAnswerAM161(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM153",5);
                        list.add(new AnswerLineDTO("AM161",80.0,  mapRepetition1  , UnitCode.U5133.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM166
        */
        private List<AnswerLineDTO> buildAnswerAM166(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AM153",6);
                        list.add(new AnswerLineDTO("AM166",40.0,  mapRepetition1  , UnitCode.U5133.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM172
        */
        private List<AnswerLineDTO> buildAnswerAM172(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM172",5.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM173
        */
        private List<AnswerLineDTO> buildAnswerAM173(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM173",3.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM174
        */
        private List<AnswerLineDTO> buildAnswerAM174(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM174",10.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM177
        */
        private List<AnswerLineDTO> buildAnswerAM177(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM177",26.0,  mapRepetition1 ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM178
        */
        private List<AnswerLineDTO> buildAnswerAM178(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM178",2000.0,  mapRepetition1  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
            /**
        * build the AnswerLineDTO
        * question : AM179
        */
        private List<AnswerLineDTO> buildAnswerAM179(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AM179",1000000.0,  mapRepetition1 ));
        
        return list;
        }
    }
