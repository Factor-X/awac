package eu.factorx.awac.service;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.data.answer.type.StringAnswerValue;
import eu.factorx.awac.models.data.question.Question;
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
	private QuestionService questionService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private ScopeService scopeService;
	@Autowired
	private PeriodService periodService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private PersonService personService;
	@Autowired
	private QuestionAnswerService questionAnswerService;
	
//	@Test
	public void removeData() {
		
	}
	
	@Test
	public void _001_testDeleteByScopeAndPeriod() {

        em.getTransaction().begin();

		Organization org = organizationService.saveOrUpdate(new Organization("TestOrg"));
		Scope scope = scopeService.saveOrUpdate(new Scope(org));
		Period period = periodService.findByCode(PeriodCode.P2013);
		
		QuestionSet qsAC9 = questionSetService.findByCode(QuestionCode.AC9);
		QuestionSet qsAC10 = questionSetService.findByCode(QuestionCode.AC10);
		Question q11 = questionService.findByCode(QuestionCode.AC11);

		QuestionSetAnswer qsaAC9 = questionSetAnswerService.saveOrUpdate(new QuestionSetAnswer(scope, period, qsAC9, 0, null));
		QuestionSetAnswer qsaAC10_1 = questionSetAnswerService.saveOrUpdate(new QuestionSetAnswer(scope, period, qsAC10, 1, qsaAC9));
		QuestionSetAnswer qsaAC10_2 = questionSetAnswerService.saveOrUpdate(new QuestionSetAnswer(scope, period, qsAC10, 2, qsaAC9));
		
		Person person = personService.saveOrUpdate(new Person("lastname", "firstname", "j.j@j.j"));
		Account account = accountService.saveOrUpdate(new Account(org, person, "test_user", "pwd", InterfaceTypeCode.MUNICIPALITY));
		
		QuestionAnswer qa11_1 = questionAnswerService.saveOrUpdate(new QuestionAnswer(account, null, qsaAC10_1, q11));
		Long qa11_1_id = qa11_1.getId();
		qa11_1.getAnswerValues().add(new StringAnswerValue(qa11_1, "answer1"));
		questionAnswerService.saveOrUpdate(qa11_1);
		
		QuestionAnswer qa11_2 = questionAnswerService.saveOrUpdate(new QuestionAnswer(account, null, qsaAC10_2, q11));
		Long qa11_2_id = qa11_2.getId();
		qa11_2.getAnswerValues().add(new StringAnswerValue(qa11_2, "answer1"));
		questionAnswerService.saveOrUpdate(qa11_2);

		Assert.assertNotNull(questionAnswerService.findById(qa11_1_id));
		Assert.assertNotNull(questionAnswerService.findById(qa11_2_id));
		
		questionSetAnswerService.deleteByScopeAndPeriod(scope, period);

//		Assert.assertNull(questionAnswerService.findById(qa11_1_id));
//		Assert.assertNull(questionAnswerService.findById(qa11_2_id));

        em.getTransaction().commit();
}

}
