package eu.factorx.awac.controllers;

import java.util.List;

import javax.persistence.NamedQuery;

import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import eu.factorx.awac.dto.awac.get.TestDTO;

@Component
@NamedQuery(name = TestController.QUERY_NAME, query = "select adm from Administrator adm")
public class TestController extends Controller {

	public static final String QUERY_NAME = "Query";

	@Transactional
	public static Result index() {
		List resultList = JPA.em().createNamedQuery(QUERY_NAME).getResultList();
		return ok(new TestDTO(resultList));
	}

}
