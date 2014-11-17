package eu.factorx.awac.controllers;

import eu.factorx.awac.business.ComputeAverage;
import eu.factorx.awac.dto.awac.get.CodeListDTO;
import eu.factorx.awac.dto.awac.get.ResultsDTO;
import eu.factorx.awac.dto.awac.post.ComputeAverageDTO;
import eu.factorx.awac.dto.awac.shared.ListDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.data.answer.type.*;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.*;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexRuntimeException;
import eu.factorx.awac.util.batch.messages.ComputeAverageMessage;
import eu.factorx.awac.util.batch.service.ComputeAverageService;
import jxl.write.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import play.Logger;
import play.api.Play;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import java.io.*;
import java.lang.Boolean;
import java.util.*;

import static play.libs.F.Promise.promise;
import play.libs.F.*;
import play.mvc.*;
import scala.concurrent.duration.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import static play.libs.F.Promise.promise;

/**
 * Created by florian on 5/11/14.
 */
@org.springframework.stereotype.Controller
//@EnableAsync
public class AverageController extends AbstractController {

	@Autowired
	private AwacCalculatorInstanceService awacCalculatorInstanceService;
	@Autowired
	private AwacCalculatorService awacCalculatorService;
	@Autowired
	private QuestionSetAnswerService questionSetAnswerService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private PeriodService periodService;
	@Autowired
	private QuestionSetService questionSetService;
    @Autowired
    private ComputeAverage computeAverageService;

    @Autowired
    ComputeAverageService batchComputeAverageService;

    public static Future<Boolean> result;

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result getNaceCodeList() throws IOException, WriteException {

		if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.ADMIN)) {
			throw new MyrmexRuntimeException(BusinessErrorType.WRONG_INTERFACE_FOR_USER);
		}

		ListDTO<CodeListDTO> list = new ListDTO<>();

		list.add(toCodeListDTO(CodeList.SECTEURPRIMAIRE, securedController.getCurrentUser().getPerson().getDefaultLanguage()));
		list.add(toCodeListDTO(CodeList.SECTEURSECONDAIRE, securedController.getCurrentUser().getPerson().getDefaultLanguage()));
		list.add(toCodeListDTO(CodeList.SECTEURTERTIAIRE, securedController.getCurrentUser().getPerson().getDefaultLanguage()));

		return ok(list);
	}
/* TODO temp
	public Result computeAverage() {
		return async(
				promise(new Function0<Integer>() {
					public Integer apply() {
						return 2;
					}
				}).map(new Function<Integer,Result>() {
					public Result apply(Integer i) {
						//return ok("Got " + i);
						return ok(new ResultsDTO());
					}
				})
		);
	}
*/
	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result computeAverage() throws IOException, WriteException {



		if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.ADMIN)) {
			throw new MyrmexRuntimeException(BusinessErrorType.WRONG_INTERFACE_FOR_USER);
		}

		ComputeAverageDTO dto = extractDTOFromRequest(ComputeAverageDTO.class);


        // refactor to new service

		//convert data
		//TODO
		final AwacCalculator awacCalculator = awacCalculatorService.findByCode(new InterfaceTypeCode(dto.getInterfaceName()));
		final Period period = periodService.findByCode(new PeriodCode(dto.getPeriodKey()));


		//compute scopeAndPEriod list
		final List<ScopeAndPeriod> scopeAndPeriodList = new ArrayList<>();

		// load all organization
		List<Organization> organizationList = organizationService.findByInterfaceTypeCode(awacCalculator.getInterfaceTypeCode());

		Integer organizationComputed = 0;
		Integer scopeComputed = 0;

		//looking for scope
		for (Organization organization : organizationList) {

			//control organization
			if (organization.getStatisticsAllowed() != null && organization.getStatisticsAllowed()) {
				organizationComputed++;

				if (awacCalculator.getInterfaceTypeCode().equals(InterfaceTypeCode.ENTERPRISE)) {
					for (Site site : organization.getSites()) {
						for (Period periodToTest : site.getListPeriodAvailable()) {

							//control period
							if (periodToTest.equals(period)) {

								//for organization : found awaccalcultor and check if there is closed
								if (awacCalculatorInstanceService.findByCalculatorAndPeriodAndScope(awacCalculator, period, site) != null) {

									//control NACE code
									if (computeNace(dto.getNaceCodeListKey(), dto.getNaceCodeKey(), site, period)) {
										scopeComputed++;
										scopeAndPeriodList.add(new ScopeAndPeriod(site, periodToTest));
									}
								}
							}
						}
					}
				} else if (awacCalculator.getInterfaceTypeCode().equals(InterfaceTypeCode.MUNICIPALITY)) {

					for (Period periodToTest : periodService.findAll()) {

						//control period
						if (periodToTest.equals(period)) {

							//for municipality : found awaccalcultor and check if there is closed
							if (awacCalculatorInstanceService.findByCalculatorAndPeriodAndScope(awacCalculator, period, organization) != null) {

								//control
								scopeAndPeriodList.add(new ScopeAndPeriod(organization, periodToTest));
							}
						}
					}
				} else if (awacCalculator.getInterfaceTypeCode().equals(InterfaceTypeCode.EVENT)) {
					for (Product product : organization.getProducts()) {
						for (Period periodToTest : product.getListPeriodAvailable()) {

							scopeComputed++;

							//control period
							if (periodToTest.equals(period)) {

								//control
								scopeAndPeriodList.add(new ScopeAndPeriod(product, periodToTest));
							}
						}
					}
				} else {
					//for house and little
					for (Period periodToTest : periodService.findAll()) {

						//control period
						if (periodToTest.equals(period)) {

							//control
							scopeAndPeriodList.add(new ScopeAndPeriod(organization, periodToTest));
						}
					}
				}
			}
		}

		if(scopeAndPeriodList.size()==0){
			//TODO
			return ok(new ExceptionsDTO("Il n'y a aucun questionnaire qui correspond aux critères demandées"));
		}
		else {

// call classical Spring service
            try {

                    computeAverageService.computeAverage(
                            securedController.getCurrentUser(),
                            awacCalculator,
                            scopeAndPeriodList,
                            period,
                            organizationComputed,
                            scopeComputed
                            );
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }


//            if ((result==null) || result.isDone ()) {
//                // call asynchronous pring service
//                Logger.info("ASYNCH Service called");
//                try {
//
//                    result = computeAverageService.computeAverageAsync(
//                            securedController.getCurrentUser(),
//                            awacCalculator,
//                            scopeAndPeriodList,
//                            period,
//                            organizationComputed,
//                            scopeComputed
//                    );
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (WriteException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                Logger.info("ASYNCH Service called already in processing");
//                return ok(new ExceptionsDTO("Les moyennes sont en cours de calcul. Vous recevrez le résultat sur votre adresse e-mail dans quelques minutes"));
//            }




// call batch processing


//             ComputeAverageMessage msg = new ComputeAverageMessage(
//                     securedController.getCurrentUser(),
//                        awacCalculator,
//                        scopeAndPeriodList,
//                        period,
//                        organizationComputed,
//                        scopeComputed
//             );
//
//             batchComputeAverageService.send(msg);


//            BatchMessage batchMessage = new BatchMessage ("AverageController",
//                    awacCalculator,
//                    scopeAndPeriodList,
//                    period,
//                    organizationComputed,
//                    scopeComputed);
//
//            batchService.run(batchMessage);




//            Akka.system().scheduler().scheduleOnce(
//                    Duration.create(10, TimeUnit.MILLISECONDS),
//                    new Runnable() {
//                        public void run() {
//
//                            Logger.info("job scheduled");
//                            try {
//                                computeAverage(awacCalculator, scopeAndPeriodList, period, 1, 1);//TODO,organizationComputed,scopeComputed);
//                            } catch (IOException e) {
//								e.printStackTrace();
//							} catch (WriteException e) {
//								e.printStackTrace();
//							}
//                        }
//                    },
//                    Akka.system().dispatcher()
//            );

//			return async(
//					promise(new Function0<Integer>() {
//						public Integer apply() {
//
//							try {
//								computeAverage(awacCalculator,scopeAndPeriodList,period,1,1);//TODO,organizationComputed,scopeComputed);
//							} catch (IOException e) {
//								e.printStackTrace();
//							} catch (WriteException e) {
//								e.printStackTrace();
//							}
//
//							return 2;
//						}
//					}).map(new Function<Integer, Result>() {
//						public Result apply(Integer i) {
//							//TODO
//							return ok(new ExceptionsDTO("Les moyennes sont en cours de calcul. Vous recevrez le résultat sur votre adresse e-mail dans quelques minutes"));
//						}
//					})
//			);
		} // else

        return ok(new ResultsDTO());
	}

    /************************************/

	private boolean computeNace(String naceCodeList, String naceCode, Scope site, Period period) {
		if (naceCodeList != null) {
			//load questionSet
			QuestionSet questionSeta1 = questionSetService.findByCode(new QuestionCode("A1"));
			if (CodeList.SECTEURPRIMAIRE.toString().equals(naceCodeList)) {

				//load code
				if (naceCode != null) {
					Code code = new Code(CodeList.SECTEURPRIMAIRE, naceCode);

					for (QuestionSetAnswer questionSetAnswer : questionSetAnswerService.findByScopeAndPeriodAndQuestionSet(site, period, questionSeta1)) {
						for (QuestionAnswer questionAnswer : questionSetAnswer.getQuestionAnswers()) {
							if (questionAnswer.getQuestion().getCode().getKey().equals("A4")) {
								for (AnswerValue answerValue : questionAnswer.getAnswerValues()) {
									if (((CodeAnswerValue) answerValue).getValue().equals(code)) {
										return true;
									}
								}


							}
						}
					}
				} else {
					Code code = new Code(CodeList.SECTEURPRINCIPAL, "1");
					for (QuestionSetAnswer questionSetAnswer : questionSetAnswerService.findByScopeAndPeriodAndQuestionSet(site, period, questionSeta1)) {
						for (QuestionAnswer questionAnswer : questionSetAnswer.getQuestionAnswers()) {
							if (questionAnswer.getQuestion().getCode().getKey().equals("A3")) {
								for (AnswerValue answerValue : questionAnswer.getAnswerValues()) {
									if (((CodeAnswerValue) answerValue).getValue().equals(code)) {
										return true;
									}
								}


							}
						}
					}
				}
			} else if (CodeList.SECTEURSECONDAIRE.toString().equals(naceCodeList)) {

				//load code
				if (naceCode != null) {
					Code code = new Code(CodeList.SECTEURSECONDAIRE, naceCode);

					for (QuestionSetAnswer questionSetAnswer : questionSetAnswerService.findByScopeAndPeriodAndQuestionSet(site, period, questionSeta1)) {
						for (QuestionAnswer questionAnswer : questionSetAnswer.getQuestionAnswers()) {
							if (questionAnswer.getQuestion().getCode().getKey().equals("A5")) {
								for (AnswerValue answerValue : questionAnswer.getAnswerValues()) {
									if (((CodeAnswerValue) answerValue).getValue().equals(code)) {
										return true;
									}
								}


							}
						}
					}
				} else {
					Code code = new Code(CodeList.SECTEURPRINCIPAL, "2");
					Code code2 = new Code(CodeList.SECTEURPRINCIPAL, "3");

					for (QuestionSetAnswer questionSetAnswer : questionSetAnswerService.findByScopeAndPeriodAndQuestionSet(site, period, questionSeta1)) {
						for (QuestionAnswer questionAnswer : questionSetAnswer.getQuestionAnswers()) {
							if (questionAnswer.getQuestion().getCode().getKey().equals("A3")) {
								for (AnswerValue answerValue : questionAnswer.getAnswerValues()) {
									if (((CodeAnswerValue) answerValue).getValue().equals(code) ||
											((CodeAnswerValue) answerValue).getValue().equals(code2)) {
										return true;
									}
								}


							}
						}
					}
				}
			} else if (CodeList.SECTEURTERTIAIRE.toString().equals(naceCodeList)) {
				//load code

				if (naceCode != null) {
					Code code = new Code(CodeList.SECTEURTERTIAIRE, naceCode);

					for (QuestionSetAnswer questionSetAnswer : questionSetAnswerService.findByScopeAndPeriodAndQuestionSet(site, period, questionSeta1)) {
						for (QuestionAnswer questionAnswer : questionSetAnswer.getQuestionAnswers()) {
							if (questionAnswer.getQuestion().getCode().getKey().equals("A6")) {
								for (AnswerValue answerValue : questionAnswer.getAnswerValues()) {
									if (((CodeAnswerValue) answerValue).getValue().equals(code)) {
										return true;
									}
								}


							}
						}
					}
				} else {
					Code code = new Code(CodeList.SECTEURPRINCIPAL, "4");

					for (QuestionSetAnswer questionSetAnswer : questionSetAnswerService.findByScopeAndPeriodAndQuestionSet(site, period, questionSeta1)) {
						for (QuestionAnswer questionAnswer : questionSetAnswer.getQuestionAnswers()) {
							if (questionAnswer.getQuestion().getCode().getKey().equals("A3")) {
								for (AnswerValue answerValue : questionAnswer.getAnswerValues()) {
									if (((CodeAnswerValue) answerValue).getValue().equals(code)) {
										return true;
									}
								}


							}
						}
					}
				}
			}
		} else

		{
			return true;
		}

		return false;
	}

    /*******************************************/

	public static class ScopeAndPeriod {

		private final Scope scope;

		private final Period period;

		private ScopeAndPeriod(Scope scope, Period period) {
			this.scope = scope;
			this.period = period;
		}

		public Scope getScope() {
			return scope;
		}

		public Period getPeriod() {
			return period;
		}

		@Override
		public String toString() {
			return "ScopeAndPeriod{" +
					"scope=" + scope +
					", period=" + period +
					'}';
		}
	}
} // end of class
