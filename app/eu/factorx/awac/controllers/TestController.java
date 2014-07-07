package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.awac.get.TestDTO;
import eu.factorx.awac.service.QuestionAnswerService;
import org.springframework.stereotype.Component;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

@Component
public class TestController extends Controller {

    public static final String QUERY = "select f, fq from Form f INNER JOIN f.questions fq";
    public static final String QUERY2 = "select qs from QuestionSet qs";
    public static final String QUERY3 = "select q from Question q";

    @Transactional
    public static Result index() {

        // QuestionAnswerService qas =

        List resultList = JPA.em().createQuery(QUERY).getResultList();

/*
        CriteriaBuilder builder =  JPA.em().getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery();
        Root<Person> men = query.from( Person.class );
        Root<Person> women = query.from( Person.class );
        Predicate menRestriction = builder.and(
                    builder.equal( men.get( Person_.gender ), Gender.MALE ),
                    builder.equal( men.get( Person_.relationshipStatus ),
                RelationshipStatus.SINGLE ));
        Predicate womenRestriction = builder.and(
                    builder.equal( women.get( Person_.gender ), Gender.FEMALE ),
                    builder.equal( women.get( Person_.relationshipStatus ),
                RelationshipStatus.SINGLE ));
        query.where( builder.and( menRestriction, womenRestriction ) );
*/


        TestDTO dto = new TestDTO();

        return ok(new TestDTO(resultList));
    }

}
