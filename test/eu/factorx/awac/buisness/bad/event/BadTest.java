package eu.factorx.awac.buisness.bad.event;

import com.fasterxml.jackson.databind.JsonNode;
import eu.factorx.awac.controllers.SecuredController;
import eu.factorx.awac.dto.awac.get.OrganizationDTO;
import eu.factorx.awac.dto.awac.post.AnswerLineDTO;
import eu.factorx.awac.dto.awac.post.QuestionAnswersDTO;
import eu.factorx.awac.dto.myrmex.get.ProductDTO;
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
    private BAD_AEV_BAD1ATest bad_AEV_BAD1ATest;
    
    @Autowired
    private BAD_AEV_BAD1BTest bad_AEV_BAD1BTest;
    
    @Autowired
    private BAD_AEV_BAD1CTest bad_AEV_BAD1CTest;
    
    @Autowired
    private BAD_AEV_BAD1DTest bad_AEV_BAD1DTest;
    
    @Autowired
    private BAD_AEV_BAD1ETest bad_AEV_BAD1ETest;
    
    @Autowired
    private BAD_AEV_BAD1FTest bad_AEV_BAD1FTest;
    
    @Autowired
    private BAD_AEV_BAD2ATest bad_AEV_BAD2ATest;
    
    @Autowired
    private BAD_AEV_BAD3BTest bad_AEV_BAD3BTest;
    
    @Autowired
    private BAD_AEV_BAD4ATest bad_AEV_BAD4ATest;
    
    @Autowired
    private BAD_AEV_BAD4BTest bad_AEV_BAD4BTest;
    
    @Autowired
    private BAD_AEV_BAD4CTest bad_AEV_BAD4CTest;
    
    @Autowired
    private BAD_AEV_BAD4DTest bad_AEV_BAD4DTest;
    
    @Autowired
    private BAD_AEV_BAD4ETest bad_AEV_BAD4ETest;
    
    @Autowired
    private BAD_AEV_BAD4FTest bad_AEV_BAD4FTest;
    
    @Autowired
    private BAD_AEV_BAD4GTest bad_AEV_BAD4GTest;
    
    @Autowired
    private BAD_AEV_BAD5Test bad_AEV_BAD5Test;
    
    @Autowired
    private BAD_AEV_BAD6Test bad_AEV_BAD6Test;
    
    @Autowired
    private BAD_AEV_BAD7ATest bad_AEV_BAD7ATest;
    
    @Autowired
    private BAD_AEV_BAD7BTest bad_AEV_BAD7BTest;
    
    @Autowired
    private BAD_AEV_BAD7CTest bad_AEV_BAD7CTest;
    
    @Autowired
    private BAD_AEV_BAD7DTest bad_AEV_BAD7DTest;
    
    @Autowired
    private BAD_AEV_BAD7ETest bad_AEV_BAD7ETest;
    
    @Autowired
    private BAD_AEV_BAD8Test bad_AEV_BAD8Test;
    
    @Autowired
    private BAD_AEV_BAD9Test bad_AEV_BAD9Test;
    
    @Autowired
    private BAD_AEV_BAD10ATest bad_AEV_BAD10ATest;
    
    @Autowired
    private BAD_AEV_BAD10BTest bad_AEV_BAD10BTest;
    
    @Autowired
    private BAD_AEV_BAD10CTest bad_AEV_BAD10CTest;
    
    @Autowired
    private BAD_AEV_BAD10DTest bad_AEV_BAD10DTest;
    
    @Autowired
    private BAD_AEV_BAD10ETest bad_AEV_BAD10ETest;
    
    @Autowired
    private BAD_AEV_BAD10FTest bad_AEV_BAD10FTest;
    
    @Autowired
    private BAD_AEV_BAD11ATest bad_AEV_BAD11ATest;
    
    @Autowired
    private BAD_AEV_BAD11BTest bad_AEV_BAD11BTest;
    
    @Autowired
    private BAD_AEV_BAD11CTest bad_AEV_BAD11CTest;
    
    @Autowired
    private BAD_AEV_BAD11DTest bad_AEV_BAD11DTest;
    
    @Autowired
    private BAD_AEV_BAD12Test bad_AEV_BAD12Test;
    
    @Autowired
    private BAD_AEV_BAD13ATest bad_AEV_BAD13ATest;
    
    @Autowired
    private BAD_AEV_BAD13BTest bad_AEV_BAD13BTest;
    
    @Autowired
    private BAD_AEV_BAD13CTest bad_AEV_BAD13CTest;
    
    @Test
    public void _000_initialize() {


    // ConnectionFormDTO
    ConnectionFormDTO cfDto = new ConnectionFormDTO("user40", "password", InterfaceTypeCode.EVENT.getKey(), "");

    /*
    start with a percentage too big => except an error

    */
    ProductDTO dto = new ProductDTO();

    dto.setName("productname");

    //Json node
    JsonNode nodeForProduct = Json.toJson(dto);

    // perform save
    // Fake request
    FakeRequest saveFakeRequest = new FakeRequest();
    saveFakeRequest.withHeader("Content-type", "application/json");
    saveFakeRequest.withJsonBody(nodeForProduct);
    saveFakeRequest.withSession(SecuredController.SESSION_IDENTIFIER_STORE, cfDto.getLogin());

    // Call controller action
    Result result = callAction(
        eu.factorx.awac.controllers.routes.ref.ProductController.create(),
        saveFakeRequest
    ); // callAction

    //analyse result
    // expecting an HTTP 200 return code
    assertEquals(200, status(result));


    //analyse result
    ProductDTO resultDTO = getDTO(result, ProductDTO.class);

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
            answerLineDTOList.addAll(buildAnswerAEV14());
            answerLineDTOList.addAll(buildAnswerAEV16());
            answerLineDTOList.addAll(buildAnswerAEV17());
            answerLineDTOList.addAll(buildAnswerAEV18());
            answerLineDTOList.addAll(buildAnswerAEV19());
            answerLineDTOList.addAll(buildAnswerAEV20());
            answerLineDTOList.addAll(buildAnswerAEV21());
            answerLineDTOList.addAll(buildAnswerAEV22());
            answerLineDTOList.addAll(buildAnswerAEV30());
            answerLineDTOList.addAll(buildAnswerAEV29());
            answerLineDTOList.addAll(buildAnswerAEV36());
            answerLineDTOList.addAll(buildAnswerAEV40());
            answerLineDTOList.addAll(buildAnswerAEV41());
            answerLineDTOList.addAll(buildAnswerAEV43());
            answerLineDTOList.addAll(buildAnswerAEV44());
            answerLineDTOList.addAll(buildAnswerAEV45());
            answerLineDTOList.addAll(buildAnswerAEV46());
            answerLineDTOList.addAll(buildAnswerAEV48());
            answerLineDTOList.addAll(buildAnswerAEV49());
            answerLineDTOList.addAll(buildAnswerAEV50());
            answerLineDTOList.addAll(buildAnswerAEV51());
            answerLineDTOList.addAll(buildAnswerAEV52());
            answerLineDTOList.addAll(buildAnswerAEV53());
            answerLineDTOList.addAll(buildAnswerAEV59());
            answerLineDTOList.addAll(buildAnswerAEV61());
            answerLineDTOList.addAll(buildAnswerAEV60());
            answerLineDTOList.addAll(buildAnswerAEV63());
            answerLineDTOList.addAll(buildAnswerAEV62());
            answerLineDTOList.addAll(buildAnswerAEV68());
            answerLineDTOList.addAll(buildAnswerAEV76());
            answerLineDTOList.addAll(buildAnswerAEV77());
            answerLineDTOList.addAll(buildAnswerAEV74());
            answerLineDTOList.addAll(buildAnswerAEV79());
            answerLineDTOList.addAll(buildAnswerAEV80());
            answerLineDTOList.addAll(buildAnswerAEV81());
            answerLineDTOList.addAll(buildAnswerAEV82());
            answerLineDTOList.addAll(buildAnswerAEV83());
            answerLineDTOList.addAll(buildAnswerAEV86());
            answerLineDTOList.addAll(buildAnswerAEV88());
            answerLineDTOList.addAll(buildAnswerAEV87());
            answerLineDTOList.addAll(buildAnswerAEV90());
            answerLineDTOList.addAll(buildAnswerAEV89());
            answerLineDTOList.addAll(buildAnswerAEV99());
            answerLineDTOList.addAll(buildAnswerAEV102());
            answerLineDTOList.addAll(buildAnswerAEV105());
            answerLineDTOList.addAll(buildAnswerAEV103());
            answerLineDTOList.addAll(buildAnswerAEV104());
            answerLineDTOList.addAll(buildAnswerAEV101());
            answerLineDTOList.addAll(buildAnswerAEV110());
            answerLineDTOList.addAll(buildAnswerAEV113());
            answerLineDTOList.addAll(buildAnswerAEV114());
            answerLineDTOList.addAll(buildAnswerAEV115());
            answerLineDTOList.addAll(buildAnswerAEV117());
            answerLineDTOList.addAll(buildAnswerAEV122());
            answerLineDTOList.addAll(buildAnswerAEV129());
            answerLineDTOList.addAll(buildAnswerAEV128());
            answerLineDTOList.addAll(buildAnswerAEV131());
            answerLineDTOList.addAll(buildAnswerAEV134());
            answerLineDTOList.addAll(buildAnswerAEV135());
            answerLineDTOList.addAll(buildAnswerAEV137());
    
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
    public void _AAB_badAEV_BAD1A() {
        bad_AEV_BAD1ATest.test(scopeId);
    }
        @Test
    public void _AAC_badAEV_BAD1B() {
        bad_AEV_BAD1BTest.test(scopeId);
    }
        @Test
    public void _AAD_badAEV_BAD1C() {
        bad_AEV_BAD1CTest.test(scopeId);
    }
        @Test
    public void _AAE_badAEV_BAD1D() {
        bad_AEV_BAD1DTest.test(scopeId);
    }
        @Test
    public void _AAF_badAEV_BAD1E() {
        bad_AEV_BAD1ETest.test(scopeId);
    }
        @Test
    public void _AAG_badAEV_BAD1F() {
        bad_AEV_BAD1FTest.test(scopeId);
    }
        @Test
    public void _AAH_badAEV_BAD2A() {
        bad_AEV_BAD2ATest.test(scopeId);
    }
        @Test
    public void _AAK_badAEV_BAD3B() {
        bad_AEV_BAD3BTest.test(scopeId);
    }
        @Test
    public void _AAL_badAEV_BAD4A() {
        bad_AEV_BAD4ATest.test(scopeId);
    }
        @Test
    public void _AAM_badAEV_BAD4B() {
        bad_AEV_BAD4BTest.test(scopeId);
    }
        @Test
    public void _AAN_badAEV_BAD4C() {
        bad_AEV_BAD4CTest.test(scopeId);
    }
        @Test
    public void _AAO_badAEV_BAD4D() {
        bad_AEV_BAD4DTest.test(scopeId);
    }
        @Test
    public void _AAP_badAEV_BAD4E() {
        bad_AEV_BAD4ETest.test(scopeId);
    }
        @Test
    public void _AAQ_badAEV_BAD4F() {
        bad_AEV_BAD4FTest.test(scopeId);
    }
        @Test
    public void _AAR_badAEV_BAD4G() {
        bad_AEV_BAD4GTest.test(scopeId);
    }
        @Test
    public void _AAS_badAEV_BAD5() {
        bad_AEV_BAD5Test.test(scopeId);
    }
        @Test
    public void _AAT_badAEV_BAD6() {
        bad_AEV_BAD6Test.test(scopeId);
    }
        @Test
    public void _AAU_badAEV_BAD7A() {
        bad_AEV_BAD7ATest.test(scopeId);
    }
        @Test
    public void _AAV_badAEV_BAD7B() {
        bad_AEV_BAD7BTest.test(scopeId);
    }
        @Test
    public void _AAW_badAEV_BAD7C() {
        bad_AEV_BAD7CTest.test(scopeId);
    }
        @Test
    public void _AAX_badAEV_BAD7D() {
        bad_AEV_BAD7DTest.test(scopeId);
    }
        @Test
    public void _AAY_badAEV_BAD7E() {
        bad_AEV_BAD7ETest.test(scopeId);
    }
        @Test
    public void _ABA_badAEV_BAD8() {
        bad_AEV_BAD8Test.test(scopeId);
    }
        @Test
    public void _ABB_badAEV_BAD9() {
        bad_AEV_BAD9Test.test(scopeId);
    }
        @Test
    public void _ABC_badAEV_BAD10A() {
        bad_AEV_BAD10ATest.test(scopeId);
    }
        @Test
    public void _ABD_badAEV_BAD10B() {
        bad_AEV_BAD10BTest.test(scopeId);
    }
        @Test
    public void _ABE_badAEV_BAD10C() {
        bad_AEV_BAD10CTest.test(scopeId);
    }
        @Test
    public void _ABF_badAEV_BAD10D() {
        bad_AEV_BAD10DTest.test(scopeId);
    }
        @Test
    public void _ABG_badAEV_BAD10E() {
        bad_AEV_BAD10ETest.test(scopeId);
    }
        @Test
    public void _ABH_badAEV_BAD10F() {
        bad_AEV_BAD10FTest.test(scopeId);
    }
        @Test
    public void _ABI_badAEV_BAD11A() {
        bad_AEV_BAD11ATest.test(scopeId);
    }
        @Test
    public void _ABJ_badAEV_BAD11B() {
        bad_AEV_BAD11BTest.test(scopeId);
    }
        @Test
    public void _ABK_badAEV_BAD11C() {
        bad_AEV_BAD11CTest.test(scopeId);
    }
        @Test
    public void _ABL_badAEV_BAD11D() {
        bad_AEV_BAD11DTest.test(scopeId);
    }
        @Test
    public void _ABM_badAEV_BAD12() {
        bad_AEV_BAD12Test.test(scopeId);
    }
        @Test
    public void _ABN_badAEV_BAD13A() {
        bad_AEV_BAD13ATest.test(scopeId);
    }
        @Test
    public void _ABO_badAEV_BAD13B() {
        bad_AEV_BAD13BTest.test(scopeId);
    }
        @Test
    public void _ABP_badAEV_BAD13C() {
        bad_AEV_BAD13CTest.test(scopeId);
    }
    

        /**
    * build the AnswerLineDTO
    * question : AEV14
    */
    private List<AnswerLineDTO> buildAnswerAEV14(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV13",1);
                        list.add(new AnswerLineDTO("AEV14","AS_1",  mapRepetition1 ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AEV13",2);
                        list.add(new AnswerLineDTO("AEV14","AS_10",  mapRepetition2 ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AEV13",3);
                        list.add(new AnswerLineDTO("AEV14","AS_1",  mapRepetition3 ));
                    //add repetition
            Map<String, Integer> mapRepetition4 = new HashMap<>();
                            mapRepetition4.put("AEV13",4);
                        list.add(new AnswerLineDTO("AEV14","AS_3",  mapRepetition4 ));
                    //add repetition
            Map<String, Integer> mapRepetition5 = new HashMap<>();
                            mapRepetition5.put("AEV13",5);
                        list.add(new AnswerLineDTO("AEV14","AS_11",  mapRepetition5 ));
                    //add repetition
            Map<String, Integer> mapRepetition6 = new HashMap<>();
                            mapRepetition6.put("AEV13",6);
                        list.add(new AnswerLineDTO("AEV14","AS_10",  mapRepetition6 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV16
    */
    private List<AnswerLineDTO> buildAnswerAEV16(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV13",1);
                        list.add(new AnswerLineDTO("AEV16",4000.0,  mapRepetition1  , UnitCode.U5126.getKey()  ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV17
    */
    private List<AnswerLineDTO> buildAnswerAEV17(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV13",2);
                        list.add(new AnswerLineDTO("AEV17",3000.0,  mapRepetition1  , UnitCode.U5133.getKey()  ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV18
    */
    private List<AnswerLineDTO> buildAnswerAEV18(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV13",3);
                        list.add(new AnswerLineDTO("AEV18",10000.0,  mapRepetition1  , UnitCode.U5170.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AEV13",4);
                        list.add(new AnswerLineDTO("AEV18",10000.0,  mapRepetition2  , UnitCode.U5170.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AEV13",5);
                        list.add(new AnswerLineDTO("AEV18",10000.0,  mapRepetition3  , UnitCode.U5170.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition4 = new HashMap<>();
                            mapRepetition4.put("AEV13",6);
                        list.add(new AnswerLineDTO("AEV18",10000.0,  mapRepetition4  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV19
    */
    private List<AnswerLineDTO> buildAnswerAEV19(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV13",3);
                        list.add(new AnswerLineDTO("AEV19",1.0,  mapRepetition1  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV20
    */
    private List<AnswerLineDTO> buildAnswerAEV20(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV13",4);
                        list.add(new AnswerLineDTO("AEV20",20.0,  mapRepetition1  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV21
    */
    private List<AnswerLineDTO> buildAnswerAEV21(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV13",5);
                        list.add(new AnswerLineDTO("AEV21",2.0,  mapRepetition1  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV22
    */
    private List<AnswerLineDTO> buildAnswerAEV22(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV13",6);
                        list.add(new AnswerLineDTO("AEV22",5.0,  mapRepetition1  , UnitCode.U5170.getKey()  ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV30
    */
    private List<AnswerLineDTO> buildAnswerAEV30(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV30",4000.0,  mapRepetition1  , UnitCode.U5156.getKey()  ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV29
    */
    private List<AnswerLineDTO> buildAnswerAEV29(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV29","AT_3",  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV36
    */
    private List<AnswerLineDTO> buildAnswerAEV36(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV36",10.0,  mapRepetition1  , UnitCode.U5324.getKey()  ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV40
    */
    private List<AnswerLineDTO> buildAnswerAEV40(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV40",1000.0,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV41
    */
    private List<AnswerLineDTO> buildAnswerAEV41(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV41",40.0,  mapRepetition1  , UnitCode.U5106.getKey()  ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV43
    */
    private List<AnswerLineDTO> buildAnswerAEV43(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV43",0.2,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV44
    */
    private List<AnswerLineDTO> buildAnswerAEV44(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV44",6.0,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV45
    */
    private List<AnswerLineDTO> buildAnswerAEV45(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV45",0.7,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV46
    */
    private List<AnswerLineDTO> buildAnswerAEV46(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV46",0.3,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV48
    */
    private List<AnswerLineDTO> buildAnswerAEV48(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV48",0.2,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV49
    */
    private List<AnswerLineDTO> buildAnswerAEV49(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV49",12.0,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV50
    */
    private List<AnswerLineDTO> buildAnswerAEV50(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV50",0.1,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV51
    */
    private List<AnswerLineDTO> buildAnswerAEV51(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV51",0.1,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV52
    */
    private List<AnswerLineDTO> buildAnswerAEV52(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV52",0.05,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV53
    */
    private List<AnswerLineDTO> buildAnswerAEV53(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV53",0.05,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV59
    */
    private List<AnswerLineDTO> buildAnswerAEV59(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV58",1);
                        list.add(new AnswerLineDTO("AEV59","Vol",  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV61
    */
    private List<AnswerLineDTO> buildAnswerAEV61(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV58",1);
                        list.add(new AnswerLineDTO("AEV61","AT_18",  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV60
    */
    private List<AnswerLineDTO> buildAnswerAEV60(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV58",1);
                        list.add(new AnswerLineDTO("AEV60","AS_173",  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV63
    */
    private List<AnswerLineDTO> buildAnswerAEV63(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV58",1);
                        list.add(new AnswerLineDTO("AEV63",2000.0,  mapRepetition1  , UnitCode.U5106.getKey()  ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV62
    */
    private List<AnswerLineDTO> buildAnswerAEV62(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV58",1);
                        list.add(new AnswerLineDTO("AEV62",5.0,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV68
    */
    private List<AnswerLineDTO> buildAnswerAEV68(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV67",1);
                        list.add(new AnswerLineDTO("AEV68","Toto",  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV76
    */
    private List<AnswerLineDTO> buildAnswerAEV76(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV67",1);
                        list.add(new AnswerLineDTO("AEV76","AS_162",  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV77
    */
    private List<AnswerLineDTO> buildAnswerAEV77(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV67",1);
                        list.add(new AnswerLineDTO("AEV77",8000.0,  mapRepetition1  , UnitCode.U5106.getKey()  ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV74
    */
    private List<AnswerLineDTO> buildAnswerAEV74(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV67",1);
                        list.add(new AnswerLineDTO("AEV74",8.0,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV79
    */
    private List<AnswerLineDTO> buildAnswerAEV79(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV79",2000.0,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV80
    */
    private List<AnswerLineDTO> buildAnswerAEV80(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV80",3000.0,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV81
    */
    private List<AnswerLineDTO> buildAnswerAEV81(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV81",4000.0,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV82
    */
    private List<AnswerLineDTO> buildAnswerAEV82(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV82",5000.0,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV83
    */
    private List<AnswerLineDTO> buildAnswerAEV83(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV83",6000.0,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV86
    */
    private List<AnswerLineDTO> buildAnswerAEV86(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV85",1);
                        list.add(new AnswerLineDTO("AEV86","Vol",  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV88
    */
    private List<AnswerLineDTO> buildAnswerAEV88(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV85",1);
                        list.add(new AnswerLineDTO("AEV88","AT_18",  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV87
    */
    private List<AnswerLineDTO> buildAnswerAEV87(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV85",1);
                        list.add(new AnswerLineDTO("AEV87","AS_173",  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV90
    */
    private List<AnswerLineDTO> buildAnswerAEV90(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV85",1);
                        list.add(new AnswerLineDTO("AEV90",2000.0,  mapRepetition1  , UnitCode.U5106.getKey()  ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV89
    */
    private List<AnswerLineDTO> buildAnswerAEV89(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV85",1);
                        list.add(new AnswerLineDTO("AEV89",5.0,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV99
    */
    private List<AnswerLineDTO> buildAnswerAEV99(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV98",1);
                        list.add(new AnswerLineDTO("AEV99","test",  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV102
    */
    private List<AnswerLineDTO> buildAnswerAEV102(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV98",1);
                        list.add(new AnswerLineDTO("AEV102","AS_162",  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV105
    */
    private List<AnswerLineDTO> buildAnswerAEV105(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV98",1);
                        list.add(new AnswerLineDTO("AEV105","1",  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV103
    */
    private List<AnswerLineDTO> buildAnswerAEV103(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV98",1);
                        list.add(new AnswerLineDTO("AEV103",10.0,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV104
    */
    private List<AnswerLineDTO> buildAnswerAEV104(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV98",1);
                        list.add(new AnswerLineDTO("AEV104",100.0,  mapRepetition1  , UnitCode.U5106.getKey()  ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV101
    */
    private List<AnswerLineDTO> buildAnswerAEV101(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV98",1);
                        list.add(new AnswerLineDTO("AEV101",5.0,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV110
    */
    private List<AnswerLineDTO> buildAnswerAEV110(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV109",1);
                        list.add(new AnswerLineDTO("AEV110","1",  mapRepetition1 ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AEV109",2);
                        list.add(new AnswerLineDTO("AEV110","5",  mapRepetition2 ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AEV109",3);
                        list.add(new AnswerLineDTO("AEV110","10",  mapRepetition3 ));
                    //add repetition
            Map<String, Integer> mapRepetition4 = new HashMap<>();
                            mapRepetition4.put("AEV109",4);
                        list.add(new AnswerLineDTO("AEV110","17",  mapRepetition4 ));
                    //add repetition
            Map<String, Integer> mapRepetition5 = new HashMap<>();
                            mapRepetition5.put("AEV109",5);
                        list.add(new AnswerLineDTO("AEV110","20",  mapRepetition5 ));
                    //add repetition
            Map<String, Integer> mapRepetition6 = new HashMap<>();
                            mapRepetition6.put("AEV109",6);
                        list.add(new AnswerLineDTO("AEV110","13",  mapRepetition6 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV113
    */
    private List<AnswerLineDTO> buildAnswerAEV113(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV109",1);
                        list.add(new AnswerLineDTO("AEV113",1.0,  mapRepetition1 ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AEV109",2);
                        list.add(new AnswerLineDTO("AEV113",2.0,  mapRepetition2 ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AEV109",3);
                        list.add(new AnswerLineDTO("AEV113",3.0,  mapRepetition3 ));
                    //add repetition
            Map<String, Integer> mapRepetition4 = new HashMap<>();
                            mapRepetition4.put("AEV109",4);
                        list.add(new AnswerLineDTO("AEV113",4.0,  mapRepetition4 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV114
    */
    private List<AnswerLineDTO> buildAnswerAEV114(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV109",1);
                        list.add(new AnswerLineDTO("AEV114",50.0,  mapRepetition1  , UnitCode.U5126.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AEV109",2);
                        list.add(new AnswerLineDTO("AEV114",50.0,  mapRepetition2  , UnitCode.U5126.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AEV109",3);
                        list.add(new AnswerLineDTO("AEV114",50.0,  mapRepetition3  , UnitCode.U5126.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition4 = new HashMap<>();
                            mapRepetition4.put("AEV109",4);
                        list.add(new AnswerLineDTO("AEV114",50.0,  mapRepetition4  , UnitCode.U5126.getKey()  ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV115
    */
    private List<AnswerLineDTO> buildAnswerAEV115(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV109",1);
                        list.add(new AnswerLineDTO("AEV115","1",  mapRepetition1 ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AEV109",2);
                        list.add(new AnswerLineDTO("AEV115","2",  mapRepetition2 ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AEV109",3);
                        list.add(new AnswerLineDTO("AEV115","3",  mapRepetition3 ));
                    //add repetition
            Map<String, Integer> mapRepetition4 = new HashMap<>();
                            mapRepetition4.put("AEV109",4);
                        list.add(new AnswerLineDTO("AEV115","4",  mapRepetition4 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV117
    */
    private List<AnswerLineDTO> buildAnswerAEV117(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV109",5);
                        list.add(new AnswerLineDTO("AEV117",80.0,  mapRepetition1  , UnitCode.U5133.getKey()  ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV122
    */
    private List<AnswerLineDTO> buildAnswerAEV122(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV109",6);
                        list.add(new AnswerLineDTO("AEV122",40.0,  mapRepetition1  , UnitCode.U5133.getKey()  ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV129
    */
    private List<AnswerLineDTO> buildAnswerAEV129(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV127",1);
                        list.add(new AnswerLineDTO("AEV129",1.0,  mapRepetition1  , UnitCode.U5133.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AEV127",2);
                        list.add(new AnswerLineDTO("AEV129",2.0,  mapRepetition2  , UnitCode.U5133.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AEV127",3);
                        list.add(new AnswerLineDTO("AEV129",3.0,  mapRepetition3  , UnitCode.U5133.getKey()  ));
                    //add repetition
            Map<String, Integer> mapRepetition4 = new HashMap<>();
                            mapRepetition4.put("AEV127",4);
                        list.add(new AnswerLineDTO("AEV129",4.0,  mapRepetition4  , UnitCode.U5133.getKey()  ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV128
    */
    private List<AnswerLineDTO> buildAnswerAEV128(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                            mapRepetition1.put("AEV127",1);
                        list.add(new AnswerLineDTO("AEV128","1",  mapRepetition1 ));
                    //add repetition
            Map<String, Integer> mapRepetition2 = new HashMap<>();
                            mapRepetition2.put("AEV127",2);
                        list.add(new AnswerLineDTO("AEV128","2",  mapRepetition2 ));
                    //add repetition
            Map<String, Integer> mapRepetition3 = new HashMap<>();
                            mapRepetition3.put("AEV127",3);
                        list.add(new AnswerLineDTO("AEV128","3",  mapRepetition3 ));
                    //add repetition
            Map<String, Integer> mapRepetition4 = new HashMap<>();
                            mapRepetition4.put("AEV127",4);
                        list.add(new AnswerLineDTO("AEV128","4",  mapRepetition4 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV131
    */
    private List<AnswerLineDTO> buildAnswerAEV131(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV131",26.0,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV134
    */
    private List<AnswerLineDTO> buildAnswerAEV134(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV134",280.0,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV135
    */
    private List<AnswerLineDTO> buildAnswerAEV135(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV135",158.0,  mapRepetition1 ));
        
        return list;
        }
        /**
    * build the AnswerLineDTO
    * question : AEV137
    */
    private List<AnswerLineDTO> buildAnswerAEV137(){

        List<AnswerLineDTO> list = new ArrayList<>();

                    //add repetition
            Map<String, Integer> mapRepetition1 = new HashMap<>();
                        list.add(new AnswerLineDTO("AEV137",520.0,  mapRepetition1 ));
        
        return list;
        }
    }
