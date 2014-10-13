package eu.factorx.awac.controllers;

import eu.factorx.awac.common.actions.SecurityAnnotation;
import eu.factorx.awac.dto.awac.get.ResultsDTO;
import eu.factorx.awac.dto.awac.get.VerificationDTO;
import eu.factorx.awac.dto.awac.shared.ListDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.verification.get.VerificationRequestDTO;
import eu.factorx.awac.dto.verification.post.CreateVerificationRequestDTO;
import eu.factorx.awac.dto.verification.post.VerificationRequestChangeStatusDTO;
import eu.factorx.awac.dto.verification.post.VerificationRequestKeyDTO;
import eu.factorx.awac.dto.verification.post.VerifyDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.data.answer.Verification;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.email.EmailVerificationContent;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.forms.AwacCalculatorInstance;
import eu.factorx.awac.models.forms.VerificationRequest;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.*;
import eu.factorx.awac.util.KeyGenerator;
import eu.factorx.awac.util.MyrmexRuntimeException;
import eu.factorx.awac.util.email.messages.EmailMessage;
import eu.factorx.awac.util.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Configuration;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by florian on 6/10/14.
 */
@org.springframework.stereotype.Controller
public class VerificationController extends AbstractController {

    @Autowired
    private PeriodService periodService;
    @Autowired
    private ScopeService scopeService;
    @Autowired
    private AwacCalculatorService awacCalculatorService;
    @Autowired
    private AwacCalculatorInstanceService awacCalculatorInstanceService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private SecuredController securedController;
    @Autowired
    private AnswerController answerController;
    @Autowired
    private EmailService emailService;
    @Autowired
    private VelocityGeneratorService velocityGeneratorService;
    @Autowired
    private CodeLabelService codeLabelService;
    @Autowired
    private ConversionService conversionService;
    @Autowired
    private VerificationRequestService verificationRequestService;
    @Autowired
    private VerificationService verificationService;
    @Autowired
    private QuestionSetService questionSetService;
    @Autowired
    private QuestionSetAnswerService quesstionSetAnswerService;


    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    @SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
    public Result createRequest() {

        CreateVerificationRequestDTO dto = this.extractDTOFromRequest(CreateVerificationRequestDTO.class);

        //control password
        if (!accountService.controlPassword(dto.getPassword(), securedController.getCurrentUser())) {
            //use the same message for both login and password error
            return unauthorized(new ExceptionsDTO("The couple login / password was not found"));
        }

        //recover elements
        AwacCalculator awacCalculator = awacCalculatorService.findByCode(securedController.getCurrentUser().getOrganization().getInterfaceCode());
        Period period = periodService.findByCode(new PeriodCode(dto.getPeriodKey()));
        Scope scope = scopeService.findById(dto.getScopeId());

        //control verificationRequest
        AwacCalculatorInstance awacCalculatorInstance = awacCalculatorInstanceService.findByCalculatorAndPeriodAndScope(awacCalculator, period, scope);
        if (awacCalculatorInstance != null &&
                awacCalculatorInstance.getVerificationRequest() != null &&
                (awacCalculatorInstance.getVerificationRequest().getOrganizationVerifier() != null ||
                        awacCalculatorInstance.getVerificationRequest().getKey() != null)) {
            return unauthorized(new ExceptionsDTO("This forms are already sended to verification to " + awacCalculatorInstance.getVerificationRequest().getEmailVerificationContent().getEmail()));
        }

        //control closed form
        if (!answerController.testCloseable(dto.getPeriodKey(), dto.getScopeId())) {
            return unauthorized(new ExceptionsDTO("Sections are not all closed. Please closed them after ask a verification"));
        }

        //search email
        Organization organizationVerification = null;
        for (Account account : accountService.findByEmail(dto.getEmail())) {
            if (account.getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {
                //founded !!
                organizationVerification = account.getOrganization();
                break;
            }
        }

        //create awacCalculator
        if (awacCalculatorInstance == null) {

            awacCalculatorInstance = new AwacCalculatorInstance();
            awacCalculatorInstance.setAwacCalculator(awacCalculator);
            awacCalculatorInstance.setPeriod(period);
            awacCalculatorInstance.setScope(scope);

            awacCalculatorService.saveOrUpdate(awacCalculator);

        }

        //build emailContent
        EmailVerificationContent emailVerificationContent = new EmailVerificationContent();
        emailVerificationContent.setContent(dto.getComment());
        emailVerificationContent.setEmail(dto.getEmail());
        emailVerificationContent.setPhoneNumber(dto.getPhoneNumber());

        //create verificationRequest
        VerificationRequest verificationRequest = new VerificationRequest();
        verificationRequest.setContact(securedController.getCurrentUser());
        verificationRequest.setEmailVerificationContent(emailVerificationContent);
        if (organizationVerification != null) {
            verificationRequest.setOrganizationVerifier(organizationVerification);
            verificationRequest.setVerificationRequestStatus(VerificationRequestStatus.WAIT_VERIFIER_CONFIRMATION);
        } else {
            //generate key
            while (verificationRequest.getKey() == null) {
                String key = KeyGenerator.generateRandomKey(30);

                //control key
                if (verificationRequestService.findByKey(key) == null) {
                    verificationRequest.setKey(key);
                }
            }
            verificationRequest.setVerificationRequestStatus(VerificationRequestStatus.WAIT_VERIFIER_REGISTRATION);
        }
        verificationRequest.setAwacCalculatorInstance(awacCalculatorInstance);
        verificationRequestService.saveOrUpdate(verificationRequest);


        //build email
        HashMap<String, CodeLabel> traductions = codeLabelService.findCodeLabelsByList(CodeList.TRANSLATIONS_EMAIL_MESSAGE);
        String title = "Demande de vérification";

        Map values = new HashMap<String, Object>();
        values.put("title", title);
        values.put("CUSTOMER_ORGANIZATION", securedController.getCurrentUser().getOrganization().getName());
        values.put("CUSTOMER_INTERFACE_TYPE", securedController.getCurrentUser().getOrganization().getInterfaceCode().getKey());

        values.put("REQUEST_PERIOD", period.getPeriodCode().getKey());

        if (securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.ENTERPRISE)) {
            values.put("REQUEST_SITE", scope.getName());
        }

        if (dto.getPhoneNumber() != null) {
            values.put("CUSTOMER_PHONE_NUMBER", dto.getPhoneNumber());
        }

        if (dto.getComment() != null) {
            values.put("CUSTOMER_COMMENT", dto.getComment());
        }

        String velocityContent = null;

        //send email
        if (organizationVerification != null) {
            velocityContent = velocityGeneratorService.generate("createVerificationRequest.vm", values);

        } else {

            values.put("REQUEST_KEY", verificationRequest.getKey());

            String awacInterfaceTypeFragment = Configuration.root().getString("awac.verificationfragment");

            String awacHostname = Configuration.root().getString("awac.hostname");
            String awacRegistrationUrl = Configuration.root().getString("awac.verificationRegistrationFragment");

            String link = awacHostname + awacInterfaceTypeFragment + awacRegistrationUrl + verificationRequest.getKey();

            values.put("REGISTRATION_LINK", link);

            velocityContent = velocityGeneratorService.generate("createVerificationRequestNoVerificationOrganization.vm", values);
        }


        // send email for invitation
        EmailMessage email = new EmailMessage(dto.getEmail(), title, velocityContent);
        emailService.send(email);

        return ok();
    }


    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    public Result getRequests() {

        //control interface
        if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {
            return unauthorized(new ExceptionsDTO("You are not a verifier"));
        }

        List<VerificationRequest> verificationRequestList = verificationRequestService.findByOrganizationVerifier(securedController.getCurrentUser().getOrganization());

        ListDTO<VerificationRequestDTO> dto = new ListDTO<>();

        for (VerificationRequest request : verificationRequestList) {
            dto.add(conversionService.convert(request.getAwacCalculatorInstance(), VerificationRequestDTO.class));
        }

        return ok(dto);
    }

    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    public Result setStatus() {


        VerificationRequestChangeStatusDTO dto = this.extractDTOFromRequest(VerificationRequestChangeStatusDTO.class);

        //load period
        Period period = periodService.findByCode(new PeriodCode(dto.getPeriodKey()));
        //load scope
        Scope scope = scopeService.findById(dto.getScopeId());


        if (scope == null || period == null) {
            return unauthorized(new ExceptionsDTO("cannot load calculator instance"));
        }

        //control scope
        AwacCalculatorInstance calculatorInstance = awacCalculatorInstanceService.findByPeriodAndScope(period, scope);

        if (calculatorInstance == null ||
                calculatorInstance.getVerificationRequest() == null ||
                calculatorInstance.getVerificationRequest().getOrganizationVerifier() == null ||
                !calculatorInstance.getVerificationRequest().getOrganizationVerifier().equals(securedController.getCurrentUser().getOrganization())) {
            return unauthorized(new ExceptionsDTO("cannot load calculator instance"));
        }
        //load statuses
        VerificationRequestStatus newStatus = new VerificationRequestStatus(dto.getNewStatus());
        VerificationRequestStatus oldStatus = calculatorInstance.getVerificationRequest().getVerificationRequestStatus();

        Logger.info("newStatus : " + newStatus);
        Logger.info("oldStatus : " + oldStatus);

        //control status
        if (newStatus.equals(VerificationRequestStatus.WAIT_CUSTOMER_CONFIRMATION) &&
                oldStatus.equals(VerificationRequestStatus.WAIT_VERIFIER_CONFIRMATION) &&
                securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {
            calculatorInstance.getVerificationRequest().setVerificationRequestStatus(newStatus);
            //TODO send email ?
        } else if (newStatus.equals(VerificationRequestStatus.REJECTED) &&
                oldStatus.equals(VerificationRequestStatus.WAIT_VERIFIER_CONFIRMATION) &&
                securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {
            verificationRequestService.remove(calculatorInstance.getVerificationRequest());
            //TODO send email !
        } else if (newStatus.equals(VerificationRequestStatus.VERIFICATION) &&
                (oldStatus.equals(VerificationRequestStatus.WAIT_ASSIGNATION) || oldStatus.equals(VerificationRequestStatus.VERIFICATION)) &&
                securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {
            calculatorInstance.getVerificationRequest().setVerificationRequestStatus(newStatus);

            //load verifier
            for (String identifier : dto.getVerifierIdentifier()) {
                Account verifier = accountService.findByIdentifier(identifier);

                //control
                if (verifier == null || !verifier.getOrganization().equals(securedController.getCurrentUser().getOrganization())) {
                    return unauthorized(new ExceptionsDTO("verifier " + dto.getVerifierIdentifier() + " is not a user from your organization"));
                }
                calculatorInstance.getVerificationRequest().addVerifier(verifier);
            }
            //TODO send email ?
        } else if (newStatus.equals(VerificationRequestStatus.WAIT_ASSIGNATION) &&
                oldStatus.equals(VerificationRequestStatus.WAIT_CUSTOMER_CONFIRMATION) &&
                !securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {

            calculatorInstance.getVerificationRequest().setVerificationRequestStatus(newStatus);
            //TODO send email
        }else if (newStatus.equals(VerificationRequestStatus.REJECTED) &&
                !securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {
            //the request is canceled !!
            verificationRequestService.remove(calculatorInstance.getVerificationRequest());
            //TODO send email
            //TODO archive ?
        }
        //TODO
        else {
            return unauthorized(new ExceptionsDTO("cannot do this action"));
        }

        awacCalculatorInstanceService.saveOrUpdate(calculatorInstance);

        return ok(new ResultsDTO());
    }

    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    public Result addRequestByKey() {

        //control interface
        if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {
            return unauthorized(new ExceptionsDTO("You are not a verifier"));
        }

        VerificationRequestKeyDTO dto = extractDTOFromRequest(VerificationRequestKeyDTO.class);

        return ok(conversionService.convert(addRequestByKey(dto.getKey()).getAwacCalculatorInstance(), VerificationRequestDTO.class));
    }

    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    public Result getMyVerificationRequests() {

        //control interface
        if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {
            return unauthorized(new ExceptionsDTO("You are not a verifier"));
        }

        ListDTO<VerificationRequestDTO> list = new ListDTO<>();

        //load by account
        for (VerificationRequest request : securedController.getCurrentUser().getVerificationRequestList()) {
            list.add(conversionService.convert(request.getAwacCalculatorInstance(), VerificationRequestDTO.class));
        }

        return ok(list);
    }

    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    public Result verify(){
        VerifyDTO dto = this.extractDTOFromRequest(VerifyDTO.class);

        //control interface
        if(!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)){
            return unauthorized(new ExceptionsDTO("You are not a verifier"));
        }

        //load scope and period
        Scope scope = scopeService.findById(dto.getScopeId());
        Period period = periodService.findByCode(new PeriodCode(dto.getPeriodKey()));
        QuestionSet questionSet = questionSetService.findByCode(new QuestionCode(dto.getQuestionSetKey()));
        VerificationStatus verificationStatus = new VerificationStatus(dto.getVerification().getStatus());

        if(scope ==null || period==null || verificationStatus == null || questionSet == null){
            return unauthorized(new ExceptionsDTO("You are not a verifier"));
        }

        List<QuestionSetAnswer> questionSetAnswerList = quesstionSetAnswerService.findByScopeAndPeriodAndQuestionSet(scope,period,questionSet);

        if(questionSetAnswerList.size()!=1){
            return unauthorized(new ExceptionsDTO("You are not a verifier"));
        }

        //control verifier
        VerificationRequest verificationRequest = verificationRequestService.findByVerifierAndScopeAndPeriod(securedController.getCurrentUser(), scope,period);

        if(verificationRequest == null){
            return unauthorized(new ExceptionsDTO("You are not a verifier"));
        }

        //try to load
        Verification  verification = verificationService.findByVerificationRequestAndQuestionSet(verificationRequest, questionSet);

        //edit
        if(verification==null){
            verification=new Verification();

            verification.setQuestionSetAnswer(questionSetAnswerList.get(0));
            verification.setVerificationRequest(verificationRequest);

            verification.setComment(dto.getVerification().getComment());
            verification.setVerificationStatus(verificationStatus);
            verification.setVerifier(securedController.getCurrentUser());
        }
        else{
            verification.setComment(dto.getVerification().getComment());
            verification.setVerificationStatus(verificationStatus);
            verification.setVerifier(securedController.getCurrentUser());
        }
        verificationService.saveOrUpdate(verification);


        //return
        return ok(conversionService.convert(verification,VerificationDTO.class));
    }


    public VerificationRequest addRequestByKey(String key) {
        //control key
        VerificationRequest verificationRequest = verificationRequestService.findByKey(key);
        if (verificationRequest.getOrganizationVerifier() != null) {
            throw new MyrmexRuntimeException("the validation request must be canceled by the customer");
        }

        //
        //link the request to the organization
        //
        verificationRequest.setOrganizationVerifier(securedController.getCurrentUser().getOrganization());
        //remove the key
        verificationRequest.setKey(null);
        //change status
        verificationRequest.setVerificationRequestStatus(VerificationRequestStatus.WAIT_VERIFIER_CONFIRMATION);
        //save
        verificationRequestService.saveOrUpdate(verificationRequest);

        //TODO send email ?

        return verificationRequest;
    }


}
