package eu.factorx.awac.service;

import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.business.Organization;

@ContextConfiguration(locations = { "classpath:/components-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReportResultServiceTest extends AbstractBaseModelTest {

//	@Autowired
//	private ReportResultService reportResultService;
//	
//	@Autowired
//	private QuestionAnswerService questionAnswerService;
//	
//	@Autowired
//	private QuestionSetAnswerService questionSetAnswerService;
//
//	@Autowired
//	private SiteService siteService;
//	
//	@Autowired
//	private PeriodService periodService;
//	
//	@Autowired
//	private AwacCalculatorService awacCalculatorService;
//
//	public void test() {
//		Organization org = new Organization("test-org");
//		
//		reportResultService.getReportResults(awacCalculator, scopes, period)
//	}
}
