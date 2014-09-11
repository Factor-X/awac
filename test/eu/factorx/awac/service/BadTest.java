package eu.factorx.awac.service;

import eu.factorx.awac.dto.awac.post.AnswerLineDTO;
import eu.factorx.awac.dto.awac.post.QuestionAnswersDTO;
import eu.factorx.awac.models.AbstractBaseModelTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BadTest  extends AbstractBaseModelTest {

    private final static Long FORM_ID = 1L;
    private final static Long PERIOD_ID = 1L;
    private final static Long SCOPE_ID = 1L;


    @Test
    public void _001_BAD1(){

        //1) load scope and period

        //1) insert data
        QuestionAnswersDTO questionAnswersDTO = new QuestionAnswersDTO();
        questionAnswersDTO.setFormId(FORM_ID);
        questionAnswersDTO.setPeriodId(PERIOD_ID);
        questionAnswersDTO.setScopeId(SCOPE_ID);
        questionAnswersDTO.setLastUpdateDate(new Date().toString());

        //build answer
        List<AnswerLineDTO> answerLineDTOList = new ArrayList<>();

        questionAnswersDTO.setListAnswers(answerLineDTOList);

        //2) compute bad

    }


}
