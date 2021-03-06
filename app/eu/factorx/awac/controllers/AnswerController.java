package eu.factorx.awac.controllers;

import eu.factorx.awac.common.actions.SecurityAnnotation;
import eu.factorx.awac.converter.QuestionAnswerToAnswerLineConverter;
import eu.factorx.awac.dto.awac.get.*;
import eu.factorx.awac.dto.awac.get.ListPeriodsDTO;
import eu.factorx.awac.dto.awac.post.*;
import eu.factorx.awac.dto.awac.shared.ResultMessageDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.dto.verification.get.VerificationRequestDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.FormProgress;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.AuditInfo;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.data.answer.type.*;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.data.question.type.*;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.forms.AwacCalculatorInstance;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;
import eu.factorx.awac.service.*;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexFatalException;
import eu.factorx.awac.util.MyrmexRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.util.*;

@org.springframework.stereotype.Controller
public class AnswerController extends AbstractController {

    private static final String ERROR_ANSWER_UNIT_NOT_AUTHORIZED = "The question identified by key '%s' does not accept unit, since a unit (id = %s) is present in client answer";
    private static final String ERROR_ANSWER_UNIT_REQUIRED = "The question identified by key '%s' requires a unit of the category '%s', but no unit is present in client answer";
    private static final String ERROR_ANSWER_UNIT_INVALID = "The question identified by key '%s' requires a unit of the category '%s', since the unit of the client answer is '%s' (part of the category '%s')";

    private static final boolean DISPLAY_LOG = false;

    @Autowired
    private PeriodService periodService;
    @Autowired
    private ScopeService scopeService;
    @Autowired
    private QuestionSetAnswerService questionSetAnswerService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionAnswerService questionAnswerService;
    @Autowired
    private UnitCategoryService unitCategoryService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private FormService formService;
    @Autowired
    private CodeLabelService codeLabelService;
    @Autowired
    private ConversionService conversionService;
    @Autowired
    private StoredFileService storedFileService;
    @Autowired
    private FormProgressService formProgressService;
    @Autowired
    private QuestionSetService questionSetService;
    @Autowired
    private AwacCalculatorService awacCalculatorService;
    @Autowired
    private AwacCalculatorInstanceService awacCalculatorInstanceService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private FilesController filesController;
    @Autowired
    private VerificationRequestService verificationRequestService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private DriverValueService driverValueService;


    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    public Result testClosing(String periodKey, Long scopeId) {

        FormsClosingDTO formsClosingDTO = new FormsClosingDTO();
        formsClosingDTO.setCloseable(testCloseable(periodKey, scopeId));

        Period period = periodService.findByCode(new PeriodCode(periodKey));

        Scope scope = scopeService.findById(scopeId);

        securedController.controlDataAccess(period, scope);

        AwacCalculator awacCalculator = awacCalculatorService.findByCode(securedController.getCurrentUser().getOrganization().getInterfaceCode());

        AwacCalculatorInstance awacCalculatorInstance = awacCalculatorInstanceService.findByCalculatorAndPeriodAndScope(awacCalculator, period, scope);
        if (awacCalculatorInstance == null || awacCalculatorInstance.isClosed() == false) {
            formsClosingDTO.setClosed(false);
        } else {
            formsClosingDTO.setClosed(true);
        }

        //add verificationStatus
        if (awacCalculatorInstance != null && awacCalculatorInstance.getVerificationRequest() != null) {
            formsClosingDTO.setVerificationRequest(conversionService.convert(awacCalculatorInstance, VerificationRequestDTO.class));
        }

        return ok(formsClosingDTO);
    }

    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    public Result testClosingValidation(String periodKey, Long scopeId) {

        FormsClosingDTO formsClosingDTO = new FormsClosingDTO();

        Scope scope = scopeService.findById(scopeId);

        Period period = periodService.findByCode(new PeriodCode(periodKey));

        securedController.controlDataAccess(period, scope);

        return ok(testCloseableVerification(period, scope));
    }

    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    @SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
    public Result closeForm() {

        FormsCloseDTO formsCloseDTO = this.extractDTOFromRequest(FormsCloseDTO.class);

        //control password
        if (!accountService.controlPassword(formsCloseDTO.getPassword(), securedController.getCurrentUser())) {
            //use the same message for both login and password error
            return unauthorized(new ExceptionsDTO(BusinessErrorType.LOGIN_PASSWORD_PAIR_NOT_FOUND));
        }

        Period period = periodService.findByCode(new PeriodCode(formsCloseDTO.getPeriodKey()));

        Scope scope = scopeService.findById(formsCloseDTO.getScopeId());

        securedController.controlDataAccess(period, scope);

        AwacCalculator awacCalculator = awacCalculatorService.findByCode(securedController.getCurrentUser().getOrganization().getInterfaceCode());

        AwacCalculatorInstance awacCalculatorInstance = awacCalculatorInstanceService.findByCalculatorAndPeriodAndScope(awacCalculator, period, scope);

        if (awacCalculatorInstance == null) {
            awacCalculatorInstance = new AwacCalculatorInstance();
            awacCalculatorInstance.setAwacCalculator(awacCalculator);
            awacCalculatorInstance.setPeriod(period);
            awacCalculatorInstance.setScope(scope);
            awacCalculatorInstance.setClosed(formsCloseDTO.getClose());

            awacCalculatorInstanceService.saveOrUpdate(awacCalculatorInstance);
        }
        if (!formsCloseDTO.getClose() && awacCalculatorInstance.isClosed()) {
            awacCalculatorInstance.setClosed(false);

            //is there is a veriifciation, remove them
            if (awacCalculatorInstance.getVerificationRequest() != null) {
                verificationRequestService.remove(awacCalculatorInstance.getVerificationRequest());
            }

            awacCalculatorInstanceService.saveOrUpdate(awacCalculatorInstance);

        } else if (formsCloseDTO.getClose() && awacCalculatorInstance.isClosed() == false) {
            awacCalculatorInstance.setClosed(true);
            awacCalculatorInstanceService.saveOrUpdate(awacCalculatorInstance);
        }

        return ok(new ResultMessageDTO());
    }


    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    public Result getByForm(String formIdentifier, String periodKey, Long scopeId) {

        Form form = formService.findByIdentifier(formIdentifier);
        Period period = periodService.findByCode(new PeriodCode(periodKey));
        Scope scope = scopeService.findById(scopeId);

        //test if the form is aviable for the scope / period
        securedController.controlDataAccess(form, period, scope);

        // TODO The user language should be saved in a session attribute, and it should be possible to update it with a request parameter (change language request).
        // Without connection, the user language should be obtained from a cookie or from browser "accepted languages" request header.
        LanguageCode lang = LanguageCode.ENGLISH;

        Map<Long, UnitCategoryDTO> unitCategoryDTOs = getAllUnitCategories();

        Map<String, CodeListDTO> codeListDTOs = getNecessaryCodeLists(form.getAllQuestionSets(), lang);

        //control if the form is locked or not
        if (!securedController.isUnlock(form.getAllQuestionSets().get(0), scope, period) && !securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {
            throw new MyrmexRuntimeException(BusinessErrorType.FORM_ALREADY_USED);
        }
        //lock
        if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {
            securedController.lockForm(form.getAllQuestionSets().get(0), scope, period);
        }


        //lock / validate / vertification : load all questionSetAnswer from basic questionSet
        HashMap<QuestionCode, QuestionSetAnswer> questionSetAnswers = new HashMap<>();

        for (QuestionSet questionSet : form.getAllQuestionSets()) {
            List<QuestionSetAnswer> questionSetAnswerList = questionSetAnswerService.findByScopeAndPeriodAndQuestionSet(scope, period, questionSet);

            if (questionSetAnswerList.size() == 1) {
                questionSetAnswers.put(questionSet.getCode(), questionSetAnswerList.get(0));
            }
        }

        Set<QuestionSet> questionSets = form.getQuestionSets();
        List<QuestionSetDTO> questionSetDTOs = toQuestionSetDTOs(new ArrayList<>(questionSets), questionSetAnswers, period);

        List<QuestionAnswer> questionAnswers = questionAnswerService.findByParameters(new QuestionAnswerSearchParameter().appendForm(form).appendPeriod(period).appendScope(scope));
        List<AnswerLineDTO> answerLineDTOs = toAnswerLineDTOs(questionAnswers);

        if (DISPLAY_LOG) {
            Logger.info("GET '{}' DataCell:", form.getIdentifier());
            for (AnswerLineDTO answerLineDTO : answerLineDTOs) {
                Logger.info("\t" + answerLineDTO);
            }
        }

        QuestionAnswersDTO questionAnswersDTO = new QuestionAnswersDTO(form.getId(), scopeId, period.getId(), getMaxLastUpdateDate(questionAnswers), answerLineDTOs);

        return ok(new FormDTO(unitCategoryDTOs, codeListDTOs, questionSetDTOs, questionAnswersDTO));
    }

    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    public Result lockQuestionSet() {

        LockQuestionSetDTO lockQuestionSetDTO = this.extractDTOFromRequest(LockQuestionSetDTO.class);

        //load questionSet
        QuestionSet questionSet = questionSetService.findByCode(new QuestionCode(lockQuestionSetDTO.getQuestionSetKey()));

        if (questionSet == null || questionSet.getParent() != null) {
            new MyrmexFatalException("cannot use this questionSet : " + lockQuestionSetDTO.getQuestionSetKey());
        }

        //load scope
        Scope scope = scopeService.findById(lockQuestionSetDTO.getScopeId());

        //load period
        Period period = periodService.findByCode(new PeriodCode(lockQuestionSetDTO.getPeriodCode()));

        //control authorization
        securedController.controlDataAccess(period, scope);

        //load questionSetAnswer
        List<QuestionSetAnswer> questionSetAnswerList = questionSetAnswerService.findByScopeAndPeriodAndQuestionSet(scope, period, questionSet);

        //it can be only one questionSetAnswer because the questionSet doesn't have any parent
        if (questionSetAnswerList.size() > 1) {
            new MyrmexFatalException("Fatal error : more than one questionSetAnswer for : period:" + lockQuestionSetDTO.getPeriodCode() + "/scope:" + lockQuestionSetDTO.getScopeId() + "/questionSetKey:" + lockQuestionSetDTO.getQuestionSetKey());
        }

        //recover the questionSetAnswer
        QuestionSetAnswer questionSetAnswer;

        if (questionSetAnswerList.size() == 0) {
            questionSetAnswer = new QuestionSetAnswer();
            questionSetAnswer.setQuestionSet(questionSet);
            questionSetAnswer.setScope(scope);
            questionSetAnswer.setPeriod(period);
        } else {
            questionSetAnswer = questionSetAnswerList.get(0);
        }

        //recover / control the auditInfo
        if (questionSetAnswer.getAuditInfo() != null && questionSetAnswer.getAuditInfo().getDataLocker() != null) {
            if (!questionSetAnswer.getAuditInfo().getDataLocker().equals(securedController.getCurrentUser()) && securedController.getCurrentUser().getIsAdmin() == false) {
                new MyrmexRuntimeException(BusinessErrorType.ANSWER_CONTROLLER_QSA_ALREAD_LOCKED, questionSetAnswer.getQuestionSet().getCode().getKey(), questionSetAnswer.getAuditInfo().getDataLocker().getFirstname() + " " + questionSetAnswer.getAuditInfo().getDataLocker().getLastname());
            }
            if (questionSetAnswer.getAuditInfo().getDataValidator() != null) {
                new MyrmexRuntimeException(BusinessErrorType.ANSWER_CONTROLLER_QSA_ALREAD_VALIDATE, questionSetAnswer.getQuestionSet().getCode().getKey(), questionSetAnswer.getAuditInfo().getDataLocker().getFirstname() + " " + questionSetAnswer.getAuditInfo().getDataLocker().getLastname());
            }
        }

        //update
        if (questionSetAnswer.getAuditInfo() == null) {
            questionSetAnswer.setAuditInfo(new AuditInfo());
        }

        if (lockQuestionSetDTO.getLock()) {
            questionSetAnswer.getAuditInfo().setDataLocker(securedController.getCurrentUser());
        } else {
            questionSetAnswer.getAuditInfo().setDataLocker(null);
        }

        //save
        questionSetAnswerService.saveOrUpdate(questionSetAnswer);

        return ok(new ResultMessageDTO());
    }

    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    public Result unlockForm(String formIdentifier, String periodKey, Long scopeId) {

        Form form = formService.findByIdentifier(formIdentifier);
        Period period = periodService.findByCode(new PeriodCode(periodKey));
        Scope scope = scopeService.findById(scopeId);

        //test if the form is aviable for the scope / period
        securedController.controlDataAccess(form, period, scope);

        //unlock
        securedController.unlockForm(form.getAllQuestionSets().get(0), scope, period);

        return ok(new ResultMessageDTO());

    }

    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    public Result validateQuestionSet() {

        LockQuestionSetDTO lockQuestionSetDTO = this.extractDTOFromRequest(LockQuestionSetDTO.class);

        //load questionSet
        QuestionSet questionSet = questionSetService.findByCode(new QuestionCode(lockQuestionSetDTO.getQuestionSetKey()));

        if (questionSet == null || questionSet.getParent() != null) {
            //technical message
            throw new MyrmexFatalException("Fatal error : cannot use this questionSet : " + lockQuestionSetDTO.getQuestionSetKey());
        }

        //load scope
        Scope scope = scopeService.findById(lockQuestionSetDTO.getScopeId());

        //load period
        Period period = periodService.findByCode(new PeriodCode(lockQuestionSetDTO.getPeriodCode()));

        //control authorization
        securedController.controlDataAccess(period, scope);

        //control verification
        AwacCalculator awacCalculator = awacCalculatorService.findByCode(securedController.getCurrentUser().getOrganization().getInterfaceCode());
        AwacCalculatorInstance awacCalculatorInstance = awacCalculatorInstanceService.findByCalculatorAndPeriodAndScope(awacCalculator, period, scope);

        if (awacCalculatorInstance != null &&
                awacCalculatorInstance.getVerificationRequest() != null &&
                !awacCalculatorInstance.getVerificationRequest().getVerificationRequestStatus().equals(VerificationRequestStatus.CORRECTION)) {
            //technical message
            throw new MyrmexRuntimeException("Fatal error : cannot use this questionSet : " + lockQuestionSetDTO.getQuestionSetKey());
        }


        //load questionSetAnswer
        List<QuestionSetAnswer> questionSetAnswerList = questionSetAnswerService.findByScopeAndPeriodAndQuestionSet(scope, period, questionSet);

        //it can be only one questionSetAnswer because the questionSet doesn't have any parent
        if (questionSetAnswerList.size() > 1) {
            //technical message
            throw new MyrmexFatalException("Fatal error : more than one questionSetAnswer for : period:" + lockQuestionSetDTO.getPeriodCode() + "/scope:" + lockQuestionSetDTO.getScopeId() + "/questionSetKey:" + lockQuestionSetDTO.getQuestionSetKey());
        }

        //recover the questionSetAnswer
        QuestionSetAnswer questionSetAnswer;

        if (questionSetAnswerList.size() == 0) {
            questionSetAnswer = new QuestionSetAnswer();
            questionSetAnswer.setQuestionSet(questionSet);
            questionSetAnswer.setScope(scope);
            questionSetAnswer.setPeriod(period);
        } else {
            questionSetAnswer = questionSetAnswerList.get(0);
        }

        //recover / control the auditInfo
        if (questionSetAnswer.getAuditInfo() != null && questionSetAnswer.getAuditInfo().getDataValidator() != null) {
            if (!questionSetAnswer.getAuditInfo().getDataValidator().equals(securedController.getCurrentUser()) && securedController.getCurrentUser().getIsAdmin() == false) {
                throw new MyrmexRuntimeException(BusinessErrorType.ANSWER_CONTROLLER_QSA_ALREAD_LOCKED, questionSetAnswer.getQuestionSet().getCode().getKey(), questionSetAnswer.getAuditInfo().getDataValidator().getFirstname() + " " + questionSetAnswer.getAuditInfo().getDataValidator().getLastname());
            }
        }

        //update
        if (questionSetAnswer.getAuditInfo() == null) {
            questionSetAnswer.setAuditInfo(new AuditInfo());
        }

        if (lockQuestionSetDTO.getLock()) {
            questionSetAnswer.getAuditInfo().setDataValidator(securedController.getCurrentUser());
        } else {
            questionSetAnswer.getAuditInfo().setDataValidator(null);
        }

        //save
        questionSetAnswerService.saveOrUpdate(questionSetAnswer);

        return ok(new ResultMessageDTO());
    }

    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    public Result getPeriodsForComparison(Long scopeId) {

        if (securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {
            return getPeriodsForComparisonByOrganization(scopeId);
        }
        Scope scope = scopeService.findById(scopeId);

        List<PeriodDTO> periodDTOs = new ArrayList<>();
        List<Period> periods = questionSetAnswerService.getAllQuestionSetAnswersPeriodsByScope(scopeId);
        for (Period period : periods) {

            //for calculator : restriction by site
            if (securedController.getCurrentUser().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.SITE)) {
                //restrict by period by site
                for (Period periodToTest : ((Site) scope).getListPeriodAvailable()) {
                    if (periodToTest.equals(period)) {
                        periodDTOs.add(conversionService.convert(period, PeriodDTO.class));
                        break;
                    }
                }
            } else if (securedController.getCurrentUser().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.ORG)) {
                periodDTOs.add(conversionService.convert(period, PeriodDTO.class));

            } else if (securedController.getCurrentUser().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.PRODUCT)) {
                //restrict by period by site
                for (Period periodToTest : ((Product) scope).getListPeriodAvailable()) {
                    if (periodToTest.equals(period)) {
                        periodDTOs.add(conversionService.convert(period, PeriodDTO.class));
                        break;
                    }
                }
            }
        }

        return ok(new ListPeriodsDTO(periodDTOs));
    }

    private Result getPeriodsForComparisonByOrganization(long scopeId) {

        //load scope
        Scope scope = scopeService.findById(scopeId);

        //load organization customer
        Organization organizationCustomer = scope.getOrganization();

        //control organization
        if (verificationRequestService.findByOrganizationCustomerAndOrganizationVerifier(organizationCustomer, securedController.getCurrentUser().getOrganization()).size() == 0) {
            new MyrmexRuntimeException((BusinessErrorType.WRONG_RIGHT));
        }

        List<PeriodDTO> periodDTOs = new ArrayList<>();
        List<Period> periods = questionSetAnswerService.getAllQuestionSetAnswersPeriodsByScope(scopeId);
        for (Period period : periods) {

            //for calculator : restriction by site
            if (organizationCustomer.getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.SITE)) {
                //restrict by period by site
                for (Period periodToTest : ((Site) scope).getListPeriodAvailable()) {
                    if (periodToTest.equals(period)) {
                        periodDTOs.add(conversionService.convert(period, PeriodDTO.class));
                        break;
                    }
                }
            } else if (organizationCustomer.getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.ORG)) {
                periodDTOs.add(conversionService.convert(period, PeriodDTO.class));
            } else if (organizationCustomer.getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.PRODUCT)) {
                //TODO
            }
        }

        return ok(new ListPeriodsDTO(periodDTOs));
    }

    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    public Result save() {

        Account currentUser = securedController.getCurrentUser();
        QuestionAnswersDTO answersDTO = extractDTOFromRequest(QuestionAnswersDTO.class);

        // validate context
        Form form = formService.findById(answersDTO.getFormId());
        Period period = periodService.findById(answersDTO.getPeriodId());
        Scope scope = scopeService.findById(answersDTO.getScopeId());
        AwacCalculator awacCalculator = awacCalculatorService.findByCode(securedController.getCurrentUser().getOrganization().getInterfaceCode());

        //test if the form is aviable for the scope / period
        securedController.controlDataAccess(form, period, scope);


        AwacCalculatorInstance awacCalculatorInstance = awacCalculatorInstanceService.findByCalculatorAndPeriodAndScope(awacCalculator, period, scope);

        if (awacCalculatorInstance != null &&
                awacCalculatorInstance.getVerificationRequest() != null &&
                !awacCalculatorInstance.getVerificationRequest().getVerificationRequestStatus().equals(VerificationRequestStatus.CORRECTION)) {
            new MyrmexRuntimeException(BusinessErrorType.ANSWER_CONTROLLER_ACI_IN_VALIDATION);
        }

        // validate user organization
        validateUserRightsForScope(currentUser, scope);

        // log posted data
        if (DISPLAY_LOG) {
            Logger.info("POST '{}' DataCell:", form.getIdentifier());
            for (AnswerLineDTO answerLine : answersDTO.getListAnswers()) {
                Logger.info("\t" + answerLine);
            }
        }

        // create, update or delete QuestionAnswers and QuestionSetAnswers
        saveAnswsersDTO(currentUser, form, period, scope, answersDTO.getListAnswers());

        //refresh lock
        securedController.refreshLock(form.getAllQuestionSets().get(0), scope, period);

        return ok(new SaveAnswersResultDTO());
    }

    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    public Result getFormProgress(String periodKey, Long scopeId) {

        // 1. control the owner of the scope
        Scope scope = scopeService.findById(scopeId);

        // 2. load period
        Period period = periodService.findByCode(new PeriodCode(periodKey));

        //test if the form is aviable for the scope / period
        securedController.controlDataAccess(period, scope);

        // 3. load formProgress
        List<FormProgress> formProgressList = formProgressService.findByPeriodAndByScope(period, scope);

        return ok(conversionService.convert(formProgressList, FormProgressListDTO.class));

    }

    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    public Result setFormProgress() {

        FormProgressDTO formProgressDTO = extractDTOFromRequest(FormProgressDTO.class);

        Period period = periodService.findByCode(new PeriodCode(formProgressDTO.getPeriod()));
        Scope scope = scopeService.findById(formProgressDTO.getScope());
        Form form = formService.findByIdentifier(formProgressDTO.getForm());

        // control percentage
        Integer percentage = formProgressDTO.getPercentage();
        if (percentage < 0 || percentage > 100) {
            throw new MyrmexRuntimeException(BusinessErrorType.WRONG_PERCENTAGE);
        }

        // control scope
        if (!scope.getOrganization().equals(securedController.getCurrentUser().getOrganization())) {
            throw new MyrmexFatalException("This scope doesn't owned by your organization. Scope asked : " + scope.toString());
        }

        // try to load
        FormProgress formProgress = formProgressService.findByPeriodAndByScopeAndForm(period, scope, form);

        // update...
        if (formProgress != null) {
            formProgress.setPercentage(percentage);
        }
        // ...or create a new formProgress
        else {
            formProgress = new FormProgress(period, form, scope, percentage);
        }

        formProgressService.saveOrUpdate(formProgress);

        return ok();
    }

    /* ***********
    PRIVATE
    *************** */

    private List<AnswerLineDTO> toAnswerLineDTOs(List<QuestionAnswer> allQuestionAnswers) {
        List<AnswerLineDTO> answerLineDTOs = new ArrayList<>();
        for (QuestionAnswer questionAnswer : allQuestionAnswers) {
            answerLineDTOs.add(conversionService.convert(questionAnswer, AnswerLineDTO.class));
        }
        return answerLineDTOs;
    }

    private List<QuestionSetDTO> toQuestionSetDTOs(List<QuestionSet> questionSets, HashMap<QuestionCode, QuestionSetAnswer> questionSetAnswers, Period period) {
        List<QuestionSetDTO> questionSetDTOs = new ArrayList<>();
        for (QuestionSet questionSet : questionSets) {

            QuestionSetDTO questionSetDTO = convertQuestionSet(questionSet, period);

            //complete DTO
            if (questionSetAnswers.containsKey(questionSet.getCode())) {
                //add locker
                QuestionSetAnswer questionSetAnswer = questionSetAnswers.get(questionSet.getCode());
                if (questionSetAnswer.getAuditInfo() != null) {
                    if (questionSetAnswer.getAuditInfo().getDataLocker() != null) {
                        questionSetDTO.setDatalocker(conversionService.convert(questionSetAnswer.getAuditInfo().getDataLocker(), PersonDTO.class));
                    }
                    //add validator
                    if (questionSetAnswer.getAuditInfo().getDataValidator() != null) {
                        questionSetDTO.setDataValidator(conversionService.convert(questionSetAnswer.getAuditInfo().getDataValidator(), PersonDTO.class));
                    }
                }
                //add verifier
                if (questionSetAnswer.getVerification() != null) {
                    questionSetDTO.setVerification(conversionService.convert(questionSetAnswer.getVerification(), VerificationDTO.class));
                }

            }

            questionSetDTOs.add(questionSetDTO);


        }
        return questionSetDTOs;
    }

    private QuestionSetDTO convertQuestionSet(QuestionSet questionSet, Period period) {

        String code = questionSet.getCode().getKey();
        Boolean repetitionAllowed = questionSet.getRepetitionAllowed();
        List<QuestionDTO> questions = new ArrayList<>();
        for (Question question : questionSet.getQuestions()) {
            questions.add(convertQuestion(question, period));
        }
        List<QuestionSetDTO> children = new ArrayList<>();
        for (QuestionSet childQuestionSet : questionSet.getChildren()) {
            children.add(convertQuestionSet(childQuestionSet, period));
        }

        return new QuestionSetDTO(code, repetitionAllowed, children, questions);
    }

    private QuestionDTO convertQuestion(Question question, Period period) {

        Long unitCategoryId = null;
        String codeListName = null;
        Object defaultValue = null;
        UnitDTO defaultUnit = null;

        if (question instanceof NumericQuestion) {
            UnitCategory unitCategory = ((NumericQuestion) question).getUnitCategory();
            if (unitCategory != null) {
                unitCategoryId = unitCategory.getId();
            }

        } else if (question instanceof ValueSelectionQuestion) {
            codeListName = ((ValueSelectionQuestion) question).getCodeList().name();
        }

        //add driver
        if (question instanceof NumericQuestion && ((NumericQuestion) question).getDriver() != null) {
            defaultValue = driverValueService.findLastedValid(((NumericQuestion) question), period).getDefaultValue();
        }


        //add default unit
        if (question instanceof NumericQuestion && ((NumericQuestion) question).getDefaultUnit() != null) {
            defaultUnit = new UnitDTO(((NumericQuestion) question).getDefaultUnit().getUnitCode().getKey(), ((NumericQuestion) question).getDefaultUnit().getSymbol());
        }

        return new QuestionDTO(question.getCode().getKey(), question.getAnswerType(), codeListName, unitCategoryId, defaultValue, defaultUnit);
    }

    private Map<String, CodeListDTO> getNecessaryCodeLists(List<QuestionSet> questionSets, LanguageCode lang) {
        Map<String, CodeListDTO> codeLists = new HashMap<>();
        for (QuestionSet questionSet : questionSets) {
            for (Question question : questionSet.getQuestions()) {
                if (question instanceof ValueSelectionQuestion) {
                    CodeList codeList = ((ValueSelectionQuestion) question).getCodeList();
                    String codeListName = codeList.name();
                    if (!codeLists.containsKey(codeListName)) {
                        codeLists.put(codeListName, toCodeListDTO(codeList, lang));
                    }
                }
            }
        }
        return codeLists;
    }

    protected CodeListDTO toCodeListDTO(CodeList codeList, LanguageCode lang) {
        List<CodeLabel> codeLabels = new ArrayList<CodeLabel>(codeLabelService.findCodeLabelsByList(codeList).values());
        List<CodeLabelDTO> codeLabelDTOs = new ArrayList<>();
        for (CodeLabel codeLabel : codeLabels) {
            codeLabelDTOs.add(new CodeLabelDTO(codeLabel.getKey(), codeLabel.getLabel(lang)));
        }
        return new CodeListDTO(codeList.name(), codeLabelDTOs);
    }

    private Map<Long, UnitCategoryDTO> getAllUnitCategories() {
        Map<Long, UnitCategoryDTO> res = new HashMap<>();
        for (UnitCategory unitCategory : unitCategoryService.findAll()) {
            UnitCategoryDTO unitCategoryDTO = new UnitCategoryDTO(unitCategory.getId());
            unitCategoryDTO.setMainUnitCode((unitCategory.getMainUnit().getUnitCode().getKey()));
            for (Unit unit : unitCategory.getUnits()) {
                unitCategoryDTO.addUnit(new UnitDTO(unit.getUnitCode().getKey(), unit.getSymbol()));
            }
            res.put(unitCategoryDTO.getId(), unitCategoryDTO);
        }
        return res;
    }

    private void saveAnswsersDTO(Account currentUser, Form form, Period period, Scope scope, List<AnswerLineDTO> newAnswersInfos) {

        // get current form data
        List<QuestionSetAnswer> questionSetAnswersList = questionSetAnswerService.findByParameters(new QuestionSetAnswerSearchParameter(false).appendForm(form).appendPeriod(period)
                .appendScope(scope));

        Map<Map<QuestionCode, Integer>, QuestionSetAnswer> questionSetAnswersMap = byRepetitionMap(questionSetAnswersList);

        Map<String, Map<Map<String, Integer>, QuestionAnswer>> questionAnswersMap = asQuestionAnswersMap(questionSetAnswersList);

        if (DISPLAY_LOG) {
            Logger.info("saveAnswsersDTO() - (1) - Update or delete existing QuestionAnswers...");
        }
        updateOrDeleteQuestionAnswers(newAnswersInfos, questionAnswersMap);

        if (DISPLAY_LOG) {
            Logger.info("saveAnswsersDTO() - (2) - Save new QuestionAnswers...");
        }
        createQuestionAnswers(newAnswersInfos, currentUser, period, scope, questionSetAnswersMap);

        if (DISPLAY_LOG) {
            Logger.info("saveAnswsersDTO() - (3) - Find and delete empty QuestionSetAnswers...");
        }
        questionSetAnswerService.deleteEmptyQuestionSetAnswers(scope, period, form);
    }

    private static Map<Map<QuestionCode, Integer>, QuestionSetAnswer> byRepetitionMap(List<QuestionSetAnswer> questionSetAnswers) {
        Map<Map<QuestionCode, Integer>, QuestionSetAnswer> res = new HashMap<>();
        for (QuestionSetAnswer questionSetAnswer : questionSetAnswers) {
            res.put(getNormalizedRepetitionMap(questionSetAnswer), questionSetAnswer);
            res.putAll(byRepetitionMap(questionSetAnswer.getChildren()));
        }
        return res;
    }

    /**
     * For each new answer info in <code>newAnswersInfos</code> parameter, check if there is an already registered answer (ie a matching entry in given <code>questionAnswersMap</code>).
     * If found, this answer is updated or deleted, belong the new answer info (and this 'new answer info' is removed in <code>newAnswersInfos</code> list).
     *
     * @param newAnswersInfos
     * @param questionAnswersMap
     */
    private void updateOrDeleteQuestionAnswers(List<AnswerLineDTO> newAnswersInfos, Map<String, Map<Map<String, Integer>, QuestionAnswer>> questionAnswersMap) {
        for (int i = newAnswersInfos.size() - 1; i >= 0; i--) {
            AnswerLineDTO newAnswerInfo = newAnswersInfos.get(i);
            if (newAnswerInfo.getMapRepetition() == null) {
                newAnswerInfo.setMapRepetition(new HashMap<String, Integer>());
            }
            QuestionAnswer questionAnswer = findExistingQuestionAnswer(newAnswerInfo, questionAnswersMap);
            if (questionAnswer != null) {
                updateOrDeleteQuestionAnswer(questionAnswer, newAnswerInfo);
                newAnswersInfos.remove(i);
            }
        }
    }

    /**
     * Update or delete the given {@link QuestionAnswer questionAnswer} according to the value of the given {@link AnswerLineDTO answerLineDTO}.
     *
     * @param questionAnswer
     * @param answerLineDTO
     */
    private void updateOrDeleteQuestionAnswer(QuestionAnswer questionAnswer, AnswerLineDTO answerLineDTO) {
        Object value = answerLineDTO.getValue();
        QuestionSetAnswer questionSetAnswer = questionAnswer.getQuestionSetAnswer();
        if ((value == null) || (StringUtils.trimToNull(value.toString()) == null)) {
            if (DISPLAY_LOG) {
                Logger.info("DELETING {}", questionAnswer);
            }
            questionSetAnswer.getQuestionAnswers().remove(questionAnswer);
            questionSetAnswerService.saveOrUpdate(questionSetAnswer);
        } else {
            List<AnswerValue> oldAnswerValues = questionAnswer.getAnswerValues();
            List<AnswerValue> newAnswerValues = getAnswerValues(answerLineDTO, questionAnswer);
            if (!oldAnswerValues.equals(newAnswerValues)) {
                questionAnswer.updateAnswerValues(newAnswerValues);
                questionSetAnswerService.saveOrUpdate(questionSetAnswer);
                if (DISPLAY_LOG) {
                    Logger.info("UPDATED {}", questionAnswer);
                }
            } else {
                if (DISPLAY_LOG) {
                    Logger.warn("Cannot update {} from answer line {}: values are identical!", questionAnswer, answerLineDTO);
                }
            }
        }
    }

    private void createQuestionAnswers(List<AnswerLineDTO> newAnswersInfos, Account currentUser, Period period, Scope scope, Map<Map<QuestionCode, Integer>, QuestionSetAnswer> existingQuestionSetAnswers) {
        for (AnswerLineDTO answerLineDTO : newAnswersInfos) {
            createQuestionAnswer(currentUser, period, scope, answerLineDTO, existingQuestionSetAnswers);
        }
    }

    private void createQuestionAnswer(Account currentUser, Period period, Scope scope, AnswerLineDTO answerLineDTO, Map<Map<QuestionCode, Integer>, QuestionSetAnswer> existingQuestionSetAnswers) {
        Object answerValue = answerLineDTO.getValue();
        if ((answerValue == null) || StringUtils.isBlank(answerValue.toString())) {
            Logger.warn("Cannot create a new QuestionAnswer from answer line {}: value is null", answerLineDTO);
            return;
        }

        Question question = getAndVerifyQuestion(answerLineDTO);
        QuestionSet questionSet = question.getQuestionSet();

        // get QuestionSetAnswer
        QuestionSetAnswer questionSetAnswer = findOrCreateQuestionSetAnswer(period, scope, questionSet, answerLineDTO, existingQuestionSetAnswers);

        //find the questionSetAnswerParent
        QuestionSetAnswer questionSetAnswerFirstParent = getFirstParent(questionSetAnswer);

        //if the firstParent is block or validate, stop save
        if (questionSetAnswerFirstParent.getAuditInfo() != null &&
                ((questionSetAnswerFirstParent.getAuditInfo().getDataLocker() != null && !questionSetAnswerFirstParent.getAuditInfo().getDataLocker().equals(securedController.getCurrentUser())) ||
                        questionSetAnswerFirstParent.getAuditInfo().getDataValidator() != null)) {
            return;
        }

        // create and save QuestionAnswer
        QuestionAnswer questionAnswer = new QuestionAnswer(currentUser, questionSetAnswer, question);
        List<AnswerValue> answerValues = getAnswerValues(answerLineDTO, questionAnswer);
        if (answerValues == null) {
            return;
        }
        questionAnswer.getAnswerValues().addAll(answerValues);
        questionSetAnswer.getQuestionAnswers().add(questionAnswer);
        questionAnswerService.saveOrUpdate(questionAnswer);
        questionSetAnswerService.saveOrUpdate(questionSetAnswer);
        if (DISPLAY_LOG) {
            Logger.info("--> CREATED {}", questionAnswer);
        }
    }

    private QuestionSetAnswer findOrCreateQuestionSetAnswer(Period period, Scope scope, QuestionSet questionSet, AnswerLineDTO answerLineDTO, Map<Map<QuestionCode, Integer>, QuestionSetAnswer> existingQuestionSetAnswers) {
        Map<QuestionCode, Integer> repetitionMap = getNormalizedRepetitionMap(questionSet, answerLineDTO.getMapRepetition());

        // Check if QuestionSetAnswer already exists
        QuestionSetAnswer questionSetAnswer = existingQuestionSetAnswers.get(repetitionMap);

        if (questionSetAnswer != null) {
            return questionSetAnswer;
        }

        // If not, create a new QuestionSetAnswer...
        // if questionSet has a parent, find or create related parent QuestionSetAnswer
        QuestionSetAnswer parentQuestionSetAnswer = null;
        if (questionSet.getParent() != null) {
            parentQuestionSetAnswer = findOrCreateQuestionSetAnswer(period, scope, questionSet.getParent(), answerLineDTO, existingQuestionSetAnswers);
        }
        // create QuestionSetAnswer
        Integer repetitionIndex = repetitionMap.get(questionSet.getCode());
        questionSetAnswer = questionSetAnswerService.saveOrUpdate(new QuestionSetAnswer(scope, period, questionSet, repetitionIndex, parentQuestionSetAnswer));
        // link new QuestionSetAnswer to his parent (if existing)
        if (parentQuestionSetAnswer != null) {
            parentQuestionSetAnswer.getChildren().add(questionSetAnswer);
            questionSetAnswerService.saveOrUpdate(parentQuestionSetAnswer);
        }
        // add new QuestionSetAnswer to existingQuestionSetAnswers map
        existingQuestionSetAnswers.put(repetitionMap, questionSetAnswer);
        if (DISPLAY_LOG) {
            Logger.info("--> CREATED {}", questionSetAnswer);
        }
        return questionSetAnswer;
    }

    /**
     * Extract the 'normalized' repetition Map from a given 'raw' repetition Map, and for a given QuestionSet: entries linked to children of this question set won't be included in result.
     *
     * @param questionSet
     * @param rawRepetitionMap
     * @return
     */
    private static Map<QuestionCode, Integer> getNormalizedRepetitionMap(QuestionSet questionSet, Map<String, Integer> rawRepetitionMap) {
        Map<QuestionCode, Integer> res = new HashMap<>();

        QuestionCode questionSetCode = questionSet.getCode();
        Integer repetitionIndex = rawRepetitionMap.get(questionSetCode.getKey());

        if (repetitionIndex == null) {
            if (questionSet.getRepetitionAllowed()) {
                throw new MyrmexFatalException("Invalid repetition map (" + rawRepetitionMap + "): does not contain entry for QuestionSet '" + questionSetCode
                        + "' (while repetion is allowed in this QuestionSet)");
            } else {
                res.put(questionSetCode, 0);
            }
        } else {
            res.put(questionSetCode, repetitionIndex);
        }

        if (questionSet.getParent() != null) {
            res.putAll(getNormalizedRepetitionMap(questionSet.getParent(), rawRepetitionMap));
        }
        return res;
    }

    /**
     * Extract the 'normalized' repetition map from a given questionSetAnswer
     *
     * @param questionSetAnswer
     * @return
     */
    private static Map<QuestionCode, Integer> getNormalizedRepetitionMap(QuestionSetAnswer questionSetAnswer) {
        Map<QuestionCode, Integer> res = new HashMap<>();

        QuestionSet questionSet = questionSetAnswer.getQuestionSet();
        if (questionSet.getRepetitionAllowed()) {
            res.put(questionSet.getCode(), questionSetAnswer.getRepetitionIndex());
        } else {
            res.put(questionSet.getCode(), 0);
        }
        if (questionSetAnswer.getParent() != null) {
            res.putAll(getNormalizedRepetitionMap(questionSetAnswer.getParent()));
        }
        return res;
    }

    private List<AnswerValue> getAnswerValues(AnswerLineDTO answerLine, QuestionAnswer questionAnswer) {
        if (answerLine.getValue() == null) {
            return null;
        }
        Object rawAnswerValue = answerLine.getValue();
        List<AnswerValue> answerValue = new ArrayList<>();

        Question question = questionAnswer.getQuestion();
        switch (question.getAnswerType()) {
            case BOOLEAN:

                // build the answer value
                String strValue = StringUtils.trim(rawAnswerValue.toString());
                Boolean booleanValue = null;
                if ("1".equals(strValue)) {
                    booleanValue = Boolean.TRUE;
                } else if ("0".equals(strValue)) {
                    booleanValue = Boolean.FALSE;
                }

                BooleanAnswerValue booleanAnswerValue = new BooleanAnswerValue(questionAnswer, booleanValue);

                // test if the value is not null
                if (booleanAnswerValue.getValue() == null) {
                    throw new MyrmexFatalException("the answer of the question " + question.getCode() + " cannot be saved : " + rawAnswerValue.toString());
                }

                // add to the list
                answerValue.add(booleanAnswerValue);

                break;
            case STRING:

                // build the answerValue
                StringAnswerValue stringAnswerValue = new StringAnswerValue(questionAnswer, rawAnswerValue.toString());

                // test if the value is not null
                if (stringAnswerValue.getValue() == null) {
                    throw new MyrmexFatalException("the answer of the question " + question.getCode() + " cannot be saved : " + rawAnswerValue.toString());
                }

                // add to the list
                answerValue.add(stringAnswerValue);
                break;
            case INTEGER:

                // build the answerValue
                UnitCategory unitCategoryInt = ((IntegerQuestion) question).getUnitCategory();
                Unit unitInt = getAndVerifyUnit(answerLine, unitCategoryInt, question.getCode().getKey());
                IntegerAnswerValue integerAnswerValue = new IntegerAnswerValue(questionAnswer, Double.valueOf(rawAnswerValue.toString()), unitInt);

                // test if the value is not null
                if (integerAnswerValue.getValue() == null) {
                    throw new MyrmexFatalException("the answer of the question " + question.getCode() + " cannot be saved : " + rawAnswerValue.toString());
                }

                // add to the list
                answerValue.add(integerAnswerValue);
                break;
            case DOUBLE:

                // build the answerValue
                UnitCategory unitCategoryDbl = ((DoubleQuestion) question).getUnitCategory();
                Unit unitDbl = getAndVerifyUnit(answerLine, unitCategoryDbl, question.getCode().getKey());

                DoubleAnswerValue doubleAnswerValue = new DoubleAnswerValue(questionAnswer, Double.valueOf(rawAnswerValue.toString()), unitDbl);

                // test if the value is not null
                if (doubleAnswerValue.getValue() == null) {
                    throw new MyrmexFatalException("the answer of the question " + question.getCode() + " cannot be saved : " + rawAnswerValue.toString());
                }

                // add to the list
                answerValue.add(doubleAnswerValue);
                break;
            case PERCENTAGE:

                DoubleAnswerValue percentageAnswerValue = new DoubleAnswerValue(questionAnswer, Double.valueOf(rawAnswerValue.toString()), null);

                // test if the value is not null
                if (percentageAnswerValue.getValue() == null) {
                    throw new MyrmexFatalException("the answer of the question " + question.getCode() + " cannot be saved : " + rawAnswerValue.toString());
                }

                // add to the list
                answerValue.add(percentageAnswerValue);
                break;
            case VALUE_SELECTION:

                // build the answerValue
                CodeList codeList = ((ValueSelectionQuestion) question).getCodeList();

                CodeAnswerValue codeAnswerValue = new CodeAnswerValue(questionAnswer, new Code(codeList, rawAnswerValue.toString()));

                // test if the value is not null
                if (codeAnswerValue.getValue() == null) {
                    throw new MyrmexFatalException("the answer of the question " + question.getCode() + " cannot be saved : " + rawAnswerValue.toString());
                }

                // add to the list
                answerValue.add(codeAnswerValue);
                break;
            case ENTITY_SELECTION:

                // build the answerValue
                String entityName = ((EntitySelectionQuestion) question).getEntityName();

                EntityAnswerValue entityAnswerValue = new EntityAnswerValue(questionAnswer, entityName, Long.valueOf(rawAnswerValue.toString()));

                // test if the value is not null
                if (entityAnswerValue.getEntityId() == null) {
                    throw new MyrmexFatalException("the answer of the question " + question.getCode() + " cannot be saved : " + rawAnswerValue.toString());
                }

                // add to the list
                answerValue.add(entityAnswerValue);
                break;
            case DOCUMENT:
                if (DISPLAY_LOG) {
                    Logger.info(rawAnswerValue.toString());
                    Logger.info(rawAnswerValue.getClass().toString());
                }

                for (Map.Entry<String, String> entry : ((LinkedHashMap<String, String>) rawAnswerValue).entrySet()) {
                    StoredFile storedFile = storedFileService.findById(Long.parseLong(entry.getKey()));
                    if (storedFile == null) {
                        throw new MyrmexFatalException("the answer of the question " + question.getCode() + " cannot be saved : " + rawAnswerValue.toString());
                    }
                    answerValue.add(new DocumentAnswerValue(questionAnswer, storedFile));
                }

                break;
            case DATE_TIME:
                // build the answer value
                strValue = StringUtils.trimToNull(rawAnswerValue.toString());
                Long longValue = Long.valueOf(strValue);
                DateTime dateTime = null;
                if (longValue != null) {
                    dateTime = new DateTime(longValue);
                    answerValue.add(new DateTimeAnswerValue(questionAnswer, dateTime));
                }
                if (longValue == null || dateTime == null) {
                    throw new MyrmexFatalException("the answer of the question " + question.getCode() + " cannot be saved : " + rawAnswerValue.toString());
                }
                break;
        }

        //add comment into each answerValue
        if (answerLine.getComment() != null) {
            for (AnswerValue answerValue1 : answerValue) {
                answerValue1.setComment(answerLine.getComment());
            }
        }

        return answerValue;
    }

    private Question getAndVerifyQuestion(AnswerLineDTO answerLine) {
        String questionKey = StringUtils.trimToNull(answerLine.getQuestionKey());
        if (questionKey == null) {
            throw new MyrmexFatalException("The answer [" + answerLine + "] is not valid : question key is null.");
        }
        Question question = questionService.findByCode(new QuestionCode(questionKey));
        if (question == null) {
            throw new MyrmexFatalException("The question key [" + questionKey + "] is not valid.");
        }
        return question;
    }

    private Unit getAndVerifyUnit(AnswerLineDTO answerLine, UnitCategory questionUnitCategory, String questionKey) {
        String answerUnitCode = answerLine.getUnitCode();

        // no unit category linked to the question => return null, or throw an Exception if client provided a unit
        if (questionUnitCategory == null) {
            if (answerUnitCode != null) {
                // TODO this event should not throw a RuntimeException => to improve
                throw new MyrmexFatalException(String.format(ERROR_ANSWER_UNIT_NOT_AUTHORIZED, questionKey, answerUnitCode));
            } else {
                return null;
            }
        }

        // the question is linked to a unit category => get unit from client answer, or throw an Exception if client provided no unit
        if (answerUnitCode == null) {
            throw new MyrmexFatalException(String.format(ERROR_ANSWER_UNIT_REQUIRED, questionKey, questionUnitCategory.getName()));
        }
        Unit answerUnit = unitService.findByCode(new UnitCode(answerUnitCode));

        // check unit category => throw an Exception if client provided an invalid unit (not part of the question's unit category)
        UnitCategory answerUnitCategory = answerUnit.getCategory();
        if (!questionUnitCategory.equals(answerUnitCategory)) {
            throw new MyrmexFatalException(String.format(ERROR_ANSWER_UNIT_INVALID, questionKey, questionUnitCategory.getName(), answerUnit.getName(), answerUnitCategory.getName()));
        }
        return answerUnit;
    }

    private static Map<String, Map<Map<String, Integer>, QuestionAnswer>> asQuestionAnswersMap(List<QuestionSetAnswer> currentQuestionSetAnswers) {
        Map<String, Map<Map<String, Integer>, QuestionAnswer>> res = new HashMap<>();
        for (QuestionSetAnswer questionSetAnswer : currentQuestionSetAnswers) {
            for (QuestionAnswer questionAnswer : questionSetAnswer.getQuestionAnswers()) {
                String questionKey = questionAnswer.getQuestion().getCode().getKey();
                if (!res.containsKey(questionKey)) {
                    res.put(questionKey, new HashMap<Map<String, Integer>, QuestionAnswer>());
                }
                res.get(questionKey).put(QuestionAnswerToAnswerLineConverter.buildRepetitionMap(questionSetAnswer), questionAnswer);
            }
            Map<String, Map<Map<String, Integer>, QuestionAnswer>> childrenQuestionAnswers = asQuestionAnswersMap(questionSetAnswer.getChildren());
            for (String questionKey : childrenQuestionAnswers.keySet()) {
                if (!res.containsKey(questionKey)) {
                    res.put(questionKey, new HashMap<Map<String, Integer>, QuestionAnswer>());
                }
                res.get(questionKey).putAll(childrenQuestionAnswers.get(questionKey));
            }
        }
        return res;
    }

    private static QuestionAnswer findExistingQuestionAnswer(AnswerLineDTO answerLineDTO, Map<String, Map<Map<String, Integer>, QuestionAnswer>> questionAnswersMap) {
        String questionKey = answerLineDTO.getQuestionKey();
        Map<String, Integer> mapRepetition = answerLineDTO.getMapRepetition();

        Map<Map<String, Integer>, QuestionAnswer> questionAnswers = questionAnswersMap.get(questionKey);
        if (questionAnswers != null) {
            return questionAnswers.get(mapRepetition);
        }
        return null;

    }

    private static DateTime getMaxLastUpdateDate(List<QuestionAnswer> allQuestionAnswers) {
        if ((allQuestionAnswers == null) || allQuestionAnswers.isEmpty()) {
            return null;
        }
        DateTime maxLastUpdateDate = allQuestionAnswers.get(0).getTechnicalSegment().getLastUpdateDate();
        for (int i = 1; i < allQuestionAnswers.size(); i++) {
            DateTime lastUpdateDate = allQuestionAnswers.get(i).getTechnicalSegment().getLastUpdateDate();
            if (lastUpdateDate.isAfter(maxLastUpdateDate)) {
                maxLastUpdateDate = lastUpdateDate;
            }
        }
        return maxLastUpdateDate;
    }

    private static void validateUserRightsForScope(Account currentUser, Scope scope) {
        if (!scope.getOrganization().equals(currentUser.getOrganization())) {
            throw new MyrmexFatalException("The user '" + currentUser.getIdentifier() + "' is not allowed to update data of organization '" + scope.getOrganization() + "'");
        }
    }

    public boolean testCloseable(String periodKey, Long scopeId) {
        Scope scope = scopeService.findById(scopeId);

        Period period = periodService.findByCode(new PeriodCode(periodKey));

        securedController.controlDataAccess(period, scope);

        List<Form> forms = awacCalculatorService.findByCode(securedController.getCurrentUser().getOrganization().getInterfaceCode()).getForms();

        for (Form form : forms) {
            for (QuestionSet questionSet : form.getAllQuestionSets()) {
                if (questionSet.getParent() == null) {
                    List<QuestionSetAnswer> questionSetAnswers = questionSetAnswerService.findByScopeAndPeriodAndQuestionSet(scope, period, questionSet);

                    if (questionSetAnswers.size() != 1 || questionSetAnswers.get(0).getAuditInfo() == null || questionSetAnswers.get(0).getAuditInfo().getDataValidator() == null) {
                        Logger.info("cannot be closed because " + questionSet + " is not close");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public VerificationFinalizationDTO testCloseableVerification(Period period, Scope scope) {

        VerificationFinalizationDTO result = new VerificationFinalizationDTO();
        result.setFinalized(true);
        result.setSuccess(true);

        List<Form> forms = awacCalculatorService.findByCode(scope.getOrganization().getInterfaceCode()).getForms();

        for (Form form : forms) {
            for (QuestionSet questionSet : form.getAllQuestionSets()) {
                if (questionSet.getParent() == null) {
                    List<QuestionSetAnswer> questionSetAnswers = questionSetAnswerService.findByScopeAndPeriodAndQuestionSet(scope, period, questionSet);

                    if (questionSetAnswers.size() != 1 ||
                            questionSetAnswers.get(0).getVerification() == null ||
                            questionSetAnswers.get(0).getVerification().getVerificationStatus() == null) {

                        result.setFinalized(false);
                        result.setSuccess(false);
                    } else if (questionSetAnswers.get(0).getVerification().getVerificationStatus().equals(VerificationStatus.REJECTED)) {
                        result.setSuccess(false);
                    }
                }
            }
        }

        return result;
    }

    private QuestionSetAnswer getFirstParent(QuestionSetAnswer questionSetAnswer) {
        if (questionSetAnswer.getParent() == null) {
            return questionSetAnswer;
        } else {
            return getFirstParent(questionSetAnswer.getParent());
        }
    }

}
