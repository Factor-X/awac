package eu.factorx.awac.service;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QuestionSetAnswerServiceTest extends AbstractBaseModelTest {
	
	@Autowired
	private QuestionSetAnswerService questionSetAnswerService;
	@Autowired
	private FormService formService;
	@Autowired
	private QuestionSetService questionSetService;
	@Autowired
	private ScopeService scopeService;
	@Autowired
	private PeriodService periodService;
	
	@Test
	public void _001_testDeleteByScopeAndPeriod() {

		Scope scope = scopeService.saveOrUpdate(new Scope(new Organization("TestOrg")));
		Period period = periodService.findByCode(PeriodCode.P2014);
		Form form = formService.findByIdentifier("TAB_C2");
//		List<QuestionSet> questionSets = questionSetService.findByForm(form);
//		
//		new QuestionSetAnswer(scope, period, questionSet, repetitionIndex, parent);
	}

}
