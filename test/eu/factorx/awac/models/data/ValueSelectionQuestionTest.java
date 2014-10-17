package eu.factorx.awac.models.data;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.type.ValueSelectionQuestion;
import eu.factorx.awac.service.QuestionService;

@ContextConfiguration(locations = { "classpath:/components-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ValueSelectionQuestionTest extends AbstractBaseModelTest {

	private static final Map<QuestionCode, CodeList> KNOWN_VALUE_SELECTION_QUESTIONS = new HashMap<QuestionCode, CodeList>();

	// the (51) 'multiple choice' questions of calculator survey (dump of DB awac-accept on 2014-08-27 ; git tag v0.4)
	static {
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A3, CodeList.SECTEURPRINCIPAL);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A4, CodeList.SECTEURPRIMAIRE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A5, CodeList.SECTEURSECONDAIRE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A6, CodeList.SECTEURTERTIAIRE);
		//KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A8, CodeList.SECTEURTYPE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A16, CodeList.COMBUSTIBLE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A26, CodeList.ENERGIEVAPEUR);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A35, CodeList.GES);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A43, CodeList.FRIGORIGENE);
        /*
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A70, CodeList.MOTIFDEPLACEMENT);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A71, CodeList.CARBURANT);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A72, CodeList.TYPEVEHICULE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A81, CodeList.MOTIFDEPLACEMENT);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A83, CodeList.CARBURANT);
		*/
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A117, CodeList.TYPEVOL);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A118, CodeList.CATEGORIEVOL);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A137, CodeList.FRIGORIGENEBASE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A159, CodeList.PROVENANCESIMPLIFIEE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A167, CodeList.COMBUSTIBLE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A171, CodeList.FRIGORIGENE);
        /*
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A177, CodeList.TYPEDECHET);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A178, CodeList.TRAITEMENTDECHET);
		*/
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A195, CodeList.TRAITEUREAU);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A198, CodeList.ORIGINEEAUUSEE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A200, CodeList.TRAITEMENTEAU);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A501, CodeList.TRAITEUREAU);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A204, CodeList.TRAITEMENTEAU);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A211, CodeList.TYPEACHAT);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A212, CodeList.ACHATMETAL);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A213, CodeList.ACHATPLASTIQUE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A214, CodeList.ACHATPAPIER);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A215, CodeList.ACHATVERRE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A216, CodeList.ACHATCHIMIQUE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A217, CodeList.ACHATROUTE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A218, CodeList.ACHATAGRO);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A219, CodeList.ACHATSERVICE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A233, CodeList.INFRASTRUCTURE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A248, CodeList.TYPEPRODUIT);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A268, CodeList.PROVENANCESIMPLIFIEE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A276, CodeList.COMBUSTIBLE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A280, CodeList.FRIGORIGENE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A285, CodeList.COMBUSTIBLE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A289, CodeList.FRIGORIGENE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A298, CodeList.GESSIMPLIFIE);
        /*
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A306, CodeList.TYPEDECHET);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A307, CodeList.TRAITEMENTDECHET);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A308, CodeList.POURCENTSIMPLIFIE);
		*/
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A314, CodeList.COMBUSTIBLE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A318, CodeList.FRIGORIGENE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A326, CodeList.COMBUSTIBLE);
		KNOWN_VALUE_SELECTION_QUESTIONS.put(QuestionCode.A330, CodeList.FRIGORIGENE);
	}

	@Autowired
	private QuestionService questionService;

	@Test
	public void _001_testVerifyCodeList() {
		for (QuestionCode questionCode : KNOWN_VALUE_SELECTION_QUESTIONS.keySet()) {
			CodeList expectedCodeList = KNOWN_VALUE_SELECTION_QUESTIONS.get(questionCode);

			Question question = questionService.findByCode(questionCode);
			Assert.assertNotNull("Question '" + questionCode + "' cannot be found!", question);
			if (!(question instanceof ValueSelectionQuestion)) {
				Assert.fail("Question '" + questionCode + "' is not of type ValueSelectionQuestion!");
			}

			CodeList codeList = ((ValueSelectionQuestion) question).getCodeList();
			Assert.assertEquals("ValueSelectionQuestion '" + questionCode + "' is not linked to the expected CodeList!",
					expectedCodeList, codeList);
		}
	}

}
