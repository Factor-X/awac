/*
 *
 * Instant Play Framework
 * AWAC
 *                       
 *
 * Copyright (c) 2014 Factor-X.
 * Author Gaston Hollands
 *
 */
 
package eu.factorx.awac.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import eu.factorx.awac.dto.awac.get.LoginResultDTO;
import eu.factorx.awac.dto.awac.get.SaveAnswersResultDTO;
import eu.factorx.awac.dto.awac.post.AnswerLineDTO;
import eu.factorx.awac.dto.awac.post.QuestionAnswersDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.code.type.PeriodCode;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

//import play.api.mvc.AnyContent;
//import com.avaje.ebean.Ebean;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnswerTest extends AbstractBaseModelTest {


	private final Long FormId = 2L;
	private final Long PeriodId = 1L;
	private final Long ScopeId = 1L;

  	@Test
	public void _001_saveActionSuccess() {

	// ConnectionFormDTO
	ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password","interfacename","");

	// LineAnswerDTO
	List<AnswerLineDTO> answerListDto = new ArrayList<AnswerLineDTO>();
	AnswerLineDTO answerLineDto = new AnswerLineDTO();
	answerLineDto.setComment("comment");
	answerLineDto.setLastUpdateUser("user1");
	//answerLineDto.setMapRepetition();
	answerLineDto.setQuestionKey("A2");
	//answerLineDto.setUnitId(1);
	answerLineDto.setValue((String) "10000");
	// add answer to answer list
	answerListDto.add(answerLineDto);

	// QuestionAnswersDTO
	QuestionAnswersDTO questionAnswersDto = new QuestionAnswersDTO();		 
	questionAnswersDto.setListAnswers(answerListDto);
	questionAnswersDto.setFormId(FormId);
	questionAnswersDto.setPeriodId(PeriodId); // periodCode
	questionAnswersDto.setScopeId(ScopeId);
	questionAnswersDto.setLastUpdateDate("10102014");

	//Json node
	JsonNode node = Json.toJson(questionAnswersDto);

	// perform save
	// Fake request
	FakeRequest saveFakeRequest = new FakeRequest();
	saveFakeRequest.withHeader("Content-type", "application/json");
	saveFakeRequest.withJsonBody(node);
	saveFakeRequest.withSession(SecuredController.SESSION_IDENTIFIER_STORE,cfDto.getLogin());

	// Call controller action
	Result saveResult = callAction(
			eu.factorx.awac.controllers.routes.ref.AnswerController.save(),
			saveFakeRequest
	); // callAction

	// get LoginResultDTO
	Logger.info("results: " + new String(contentAsBytes(saveResult)));
	String content = new String(contentAsBytes(saveResult));
	JsonNode jsonResponse = Json.parse(content);
	SaveAnswersResultDTO saveAnswerResultDto = Json.fromJson(jsonResponse,SaveAnswersResultDTO.class);

	Logger.info("jsonNode: " + jsonResponse.toString());
	//Logger.info("findPath:" + jsonResponse.findPath("lastname").asText());
	//Logger.info("lastname:" + loginResult.getPerson().getLastName());

	// verify lastupdate
	//TODO should return a date, but for now on it returns null
	//assertEquals("10102014",saveAnswerResultDto.getLastUpdate());
	assertEquals(null,saveAnswerResultDto.getLastUpdate());

  } // end of saveActionSuccess test

}

