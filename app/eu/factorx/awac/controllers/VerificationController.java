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
import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.data.answer.Verification;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.email.EmailVerificationContent;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.forms.AwacCalculatorInstance;
import eu.factorx.awac.models.forms.VerificationRequest;
import eu.factorx.awac.models.forms.VerificationRequestCanceled;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * verification controller
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
    private ConversionService conversionService;
    @Autowired
    private VerificationRequestService verificationRequestService;
    @Autowired
    private VerificationService verificationService;
    @Autowired
    private QuestionSetService questionSetService;
    @Autowired
    private QuestionSetAnswerService quesstionSetAnswerService;
    @Autowired
    private StoredFileService storedFileService;
    @Autowired
    private VerificationRequestCanceledService verificationRequestCanceledService;

    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    @SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
    public Result getArchivedRequests() {

        //control interface
        if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {
            return unauthorized(new ExceptionsDTO("You are not a verifier"));
        }

        List<VerificationRequest> verificationRequestList = verificationRequestService.findByOrganizationVerifierAndVerificationRequestStatus(securedController.getCurrentUser().getOrganization(), VerificationRequestStatus.VERIFIED);

        List<VerificationRequestCanceled> verificationRequestCanceledList = verificationRequestCanceledService.findByOrganizationVerifier(securedController.getCurrentUser().getOrganization());

        ListDTO<VerificationRequestDTO> dto = new ListDTO<>();

        for (VerificationRequest request : verificationRequestList) {
            dto.add(conversionService.convert(request.getAwacCalculatorInstance(), VerificationRequestDTO.class));
        }


        for (VerificationRequestCanceled request : verificationRequestCanceledList) {
            dto.add(conversionService.convert(request, VerificationRequestDTO.class));
        }

        return ok(dto);
    }

    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    public Result  getAllMyVerificationRequests(){

        //load awacCalculatorInstance
        awacCalculatorInstanceService.findByPeriodAndOrganization()



    }


    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    @SecurityAnnotation(isAdmin = true, isSystemAdmin = false)
    public Result createRequest() {

        CreateVerificationRequestDTO dto = extractDTOFromRequest(CreateVerificationRequestDTO.class);

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

            Logger.info("awacCalculatorInstance == null");

            awacCalculatorInstance = new AwacCalculatorInstance();
            awacCalculatorInstance.setAwacCalculator(awacCalculator);
            awacCalculatorInstance.setPeriod(period);
            awacCalculatorInstance.setScope(scope);

            Logger.info("awacCalculatorInstance == " + awacCalculatorInstance);
            awacCalculatorInstanceService.saveOrUpdate(awacCalculatorInstance);

        } else {
            Logger.info("awacCalculatorInstance == " + awacCalculatorInstance);
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
        String emailTemplate;
        String title;

        Map<String, Object> values = new HashMap<>();
        if (organizationVerification != null) {
            emailTemplate = "verification/toWaitVerifierConfirmation.vm";
            title = "Demande de vérification de bilan GES";
        } else {
            emailTemplate = "verification/toWaitVerifierRegistration.vm";
            title = "Invitation comme vérificateur de bilan GES sur l'outil AWAC";

            String awacInterfaceTypeFragment = Configuration.root().getString("awac.verificationfragment");

            String awacHostname = Configuration.root().getString("awac.hostname");
            String awacRegistrationUrl = Configuration.root().getString("awac.verificationRegistrationFragment");


            String link = awacHostname + awacInterfaceTypeFragment + awacRegistrationUrl + verificationRequest.getKey();

            values.put("link", link);
        }


        values.put("request", verificationRequest);
        values.put("user", securedController.getCurrentUser());

        String velocityContent = velocityGeneratorService.generate(emailTemplate, values);

        // send email for invitation
        EmailMessage email = new EmailMessage(dto.getEmail(), title, velocityContent);
        emailService.send(email);

        return ok();
    }


    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    public Result getRequestsToManage() {

        //control interface
        if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {
            return unauthorized(new ExceptionsDTO("You are not a verifier"));
        }

        List<VerificationRequest> verificationRequestList = verificationRequestService.findByOrganizationVerifier(securedController.getCurrentUser().getOrganization());

        ListDTO<VerificationRequestDTO> dto = new ListDTO<>();

        for (VerificationRequest request : verificationRequestList) {
            if (!request.getVerificationRequestStatus().equals(VerificationRequestStatus.VERIFIED)) {
                dto.add(conversionService.convert(request.getAwacCalculatorInstance(), VerificationRequestDTO.class));
            }
        }

        return ok(dto);
    }

    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    public Result setStatus() {

        VerificationRequestChangeStatusDTO dto = extractDTOFromRequest(VerificationRequestChangeStatusDTO.class);

        //load period
        Period period = periodService.findByCode(new PeriodCode(dto.getPeriodKey()));
        //load scope
        Scope scope = scopeService.findById(dto.getScopeId());


        if (scope == null || period == null) {
            return unauthorized(new ExceptionsDTO("cannot load calculator instance for period " + dto.getPeriodKey() + ", scope:" + dto.getScopeId()));
        }

        //control scope
        AwacCalculatorInstance calculatorInstance = awacCalculatorInstanceService.findByPeriodAndScope(period, scope);

        if (calculatorInstance == null ||
                calculatorInstance.getVerificationRequest() == null ||
                calculatorInstance.getVerificationRequest().getOrganizationVerifier() == null) {
            return unauthorized(new ExceptionsDTO("cannot load calculator instance"));
        }

        //control organization
        if (!calculatorInstance.getScope().getOrganization().equals(securedController.getCurrentUser().getOrganization()) &&
                (calculatorInstance.getVerificationRequest().getOrganizationVerifier() == null ||
                        !calculatorInstance.getVerificationRequest().getOrganizationVerifier().equals(securedController.getCurrentUser().getOrganization()))) {
            return unauthorized(new ExceptionsDTO("this is not your organization"));
        }


        //load statuses
        VerificationRequestStatus newStatus = new VerificationRequestStatus(dto.getNewStatus());
        VerificationRequestStatus oldStatus = calculatorInstance.getVerificationRequest().getVerificationRequestStatus();

        Logger.info("newStatus : " + newStatus);
        Logger.info("oldStatus : " + oldStatus);
        String emailToSend = null;
        List<String> emailTargets = new ArrayList<>();
        String emailTitle = null;

        //control status
        if (newStatus.equals(VerificationRequestStatus.WAIT_CUSTOMER_CONFIRMATION) &&
                oldStatus.equals(VerificationRequestStatus.WAIT_VERIFIER_CONFIRMATION) &&
                securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION) &&
                securedController.getCurrentUser().getIsAdmin()) {

            //change status
            calculatorInstance.getVerificationRequest().setVerificationRequestStatus(newStatus);

            //email
            emailToSend = "waitVerifierConfirmationToWaitCustomerConfirmation.vm";
            emailTargets = getAdmins(calculatorInstance.getScope().getOrganization());
            emailTitle = "Confirmation de vérification de votre bilan GES";

        } else if (newStatus.equals(VerificationRequestStatus.REJECTED) &&
                oldStatus.equals(VerificationRequestStatus.WAIT_VERIFIER_CONFIRMATION) &&
                securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION) &&
                securedController.getCurrentUser().getIsAdmin()) {


            deleteVerificationRequest(calculatorInstance.getVerificationRequest());

            //email
            emailToSend = "waitVerifierConfirmationToRejeced.vm";
            emailTargets = getAdmins(calculatorInstance.getScope().getOrganization());
            emailTitle = "Refus de vérification de votre bilan GES";

        } else if (newStatus.equals(VerificationRequestStatus.VERIFICATION) &&
                (oldStatus.equals(VerificationRequestStatus.WAIT_ASSIGNATION) || oldStatus.equals(VerificationRequestStatus.VERIFICATION)) &&
                securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION) &&
                securedController.getCurrentUser().getIsAdmin()) {

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

            //email
            emailToSend = "waitAssignationToVerification.vm";
            emailTargets = getAssignedAccountEmail(calculatorInstance.getVerificationRequest());
            emailTitle = "Tâche de vérification de bilan GES";

        } else if (newStatus.equals(VerificationRequestStatus.WAIT_ASSIGNATION) &&
                oldStatus.equals(VerificationRequestStatus.WAIT_CUSTOMER_CONFIRMATION) &&
                !securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION) &&
                accountService.controlPassword(dto.getPassword(), securedController.getCurrentUser()) &&
                securedController.getCurrentUser().getIsAdmin()) {


            calculatorInstance.getVerificationRequest().setVerificationRequestStatus(newStatus);

            //email
            emailToSend = "waitCustomerConfirmationToWaitAssignation.vm";
            emailTargets = getAdmins(calculatorInstance.getVerificationRequest().getOrganizationVerifier());
            emailTitle = "Vérfication de bilan GES";

        } else if (newStatus.equals(VerificationRequestStatus.REJECTED) &&
                !securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION) &&
                accountService.controlPassword(dto.getPassword(), securedController.getCurrentUser()) &&
                securedController.getCurrentUser().getIsAdmin()) {


            //the request is canceled !!
            deleteVerificationRequest(calculatorInstance.getVerificationRequest());

            //email
            emailToSend = "reject.vm";
            emailTargets = getAdmins(calculatorInstance.getVerificationRequest().getOrganizationVerifier());
            emailTitle = "Annulation de la vérification de bilan GES";

        } else if (newStatus.equals(VerificationRequestStatus.WAIT_VERIFICATION_CONFIRMATION_REJECT) &&
                oldStatus.equals(VerificationRequestStatus.VERIFICATION) &&
                answerController.testCloseableValidation(period.getPeriodCode().getKey(), scope.getId()).isFinalized() &&
                securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {


            calculatorInstance.getVerificationRequest().setVerificationRequestStatus(newStatus);
            calculatorInstance.getVerificationRequest().setVerificationRejectedComment(dto.getVerificationRejectedComment());

            //email
            emailToSend = "verificationToWaitVerificationConfirmationReject.vm";
            emailTargets = getMainVerifierAdmins(calculatorInstance.getVerificationRequest().getOrganizationVerifier());
            emailTitle = "Bilan GES vérifié à retourner au client";

        } else if (newStatus.equals(VerificationRequestStatus.WAIT_VERIFICATION_CONFIRMATION_SUCCESS) &&
                oldStatus.equals(VerificationRequestStatus.VERIFICATION) &&
                answerController.testCloseableValidation(period.getPeriodCode().getKey(), scope.getId()).isFinalized() &&
                securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {


            //control file
            StoredFile storedFile = storedFileService.findById(dto.getVerificationFinalizationFileId());
            if (storedFile == null || !storedFile.getAccount().equals(securedController.getCurrentUser())) {
                return notFound(new ExceptionsDTO("the verification document was not found"));
            }

            //add the customer
            storedFile.addOrganization(calculatorInstance.getScope().getOrganization());

            calculatorInstance.getVerificationRequest().setVerificationRequestStatus(newStatus);
            calculatorInstance.getVerificationRequest().setVerificationResultDocument(storedFile);

            //email
            emailToSend = "verificationToWaitVerificationConfirmationSuccess.vm";
            emailTargets = getMainVerifierAdmins(calculatorInstance.getVerificationRequest().getOrganizationVerifier());
            emailTitle = "Bilan GES vérifié à retourner au client";

        } else if (newStatus.equals(VerificationRequestStatus.WAIT_CUSTOMER_VERIFIED_CONFIRMATION) &&
                oldStatus.equals(VerificationRequestStatus.WAIT_VERIFICATION_CONFIRMATION_SUCCESS) &&
                securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION) &&
                (securedController.getCurrentUser().getIsAdmin() ||
                        securedController.getCurrentUser().getIsMainVerifier())) {
            calculatorInstance.getVerificationRequest().setVerificationRequestStatus(newStatus);

            //email
            emailToSend = "waitVerificationConfirmationSucessToWaitCustomerVerifiedConfirmation.vm";
            emailTargets = getAdmins(calculatorInstance.getScope().getOrganization());
            emailTitle = "Retour de vérification de votre bilan GES";

        } else if (newStatus.equals(VerificationRequestStatus.CORRECTION) &&
                oldStatus.equals(VerificationRequestStatus.WAIT_VERIFICATION_CONFIRMATION_REJECT) &&
                securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION) &&
                (securedController.getCurrentUser().getIsAdmin() ||
                        securedController.getCurrentUser().getIsMainVerifier())) {
            calculatorInstance.getVerificationRequest().setVerificationRequestStatus(newStatus);

            //dis valid
            for (Verification verification : calculatorInstance.getVerificationRequest().getVerificationList()) {
                if (verification.getVerificationStatus().equals(VerificationStatus.REJECTED)) {
                    verification.getQuestionSetAnswer().getAuditInfo().setDataVerifier(null);
                }
            }

            //email
            emailToSend = "waitVerificationConfirmationRejectToCorrection.vm";
            emailTargets = getAdmins(calculatorInstance.getScope().getOrganization());
            emailTitle = "Retour de vérification de votre bilan GES";

        } else if (newStatus.equals(VerificationRequestStatus.VERIFICATION) &&
                (oldStatus.equals(VerificationRequestStatus.WAIT_VERIFICATION_CONFIRMATION_REJECT) || oldStatus.equals(VerificationRequestStatus.WAIT_VERIFICATION_CONFIRMATION_SUCCESS)) &&
                securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION) &&
                (securedController.getCurrentUser().getIsAdmin() ||
                        securedController.getCurrentUser().getIsMainVerifier())) {

            calculatorInstance.getVerificationRequest().setVerificationRequestStatus(newStatus);

            //email
            emailToSend = "waitVerificationConfirmationRejectToVeriification.vm";
            emailTargets = getAssignedAccountEmail(calculatorInstance.getVerificationRequest());
            emailTitle = "Travail complémentaire pour vérification de bilan GES";

        } else if (newStatus.equals(VerificationRequestStatus.VERIFICATION) &&
                oldStatus.equals(VerificationRequestStatus.CORRECTION) &&
                !securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION) &&
                answerController.testCloseable(dto.getPeriodKey(), dto.getScopeId()) &&
                accountService.controlPassword(dto.getPassword(), securedController.getCurrentUser()) &&
                securedController.getCurrentUser().getIsAdmin()) {
            calculatorInstance.getVerificationRequest().setVerificationRequestStatus(newStatus);

            //email
            emailToSend = "correctionToVeriification.vm";
            emailTargets = getAdmins(calculatorInstance.getVerificationRequest().getOrganizationVerifier());
            emailTargets.addAll(getAssignedAccountEmail(calculatorInstance.getVerificationRequest()));
            emailTitle = "Données corrigées de bilan GES";

        } else if (newStatus.equals(VerificationRequestStatus.VERIFIED) &&
                oldStatus.equals(VerificationRequestStatus.WAIT_CUSTOMER_VERIFIED_CONFIRMATION) &&
                !securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION) &&
                accountService.controlPassword(dto.getPassword(), securedController.getCurrentUser()) &&
                securedController.getCurrentUser().getIsAdmin()) {

            calculatorInstance.getVerificationRequest().setVerificationRequestStatus(newStatus);
        } else if (newStatus.equals(VerificationRequestStatus.VERIFICATION) &&
                oldStatus.equals(VerificationRequestStatus.WAIT_CUSTOMER_VERIFIED_CONFIRMATION) &&
                !securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION) &&
                accountService.controlPassword(dto.getPassword(), securedController.getCurrentUser()) &&
                (securedController.getCurrentUser().getIsAdmin() ||
                        securedController.getCurrentUser().getIsMainVerifier())) {


            calculatorInstance.getVerificationRequest().setVerificationRequestStatus(newStatus);

            //email
            emailToSend = "waitCustomerVerifiedConfirmationToVerification.vm";
            emailTargets = getAdmins(calculatorInstance.getVerificationRequest().getOrganizationVerifier());
            emailTargets.addAll(getAssignedAccountEmail(calculatorInstance.getVerificationRequest()));
            emailTitle = "Refus d'un rapport de vérification de bilan GES";
        } else {
            return unauthorized(new ExceptionsDTO("cannot do this action"));
        }

        //send email
        if (emailToSend != null) {
            Map<String, Object> values = new HashMap<>();
            values.put("request", calculatorInstance.getVerificationRequest());
            values.put("user", securedController.getCurrentUser());

            String velocityContent = velocityGeneratorService.generate("verification/"+emailToSend, values);

            EmailMessage email = new EmailMessage(emailTargets, emailTitle, velocityContent);
            emailService.send(email);
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
            if (request.getVerificationRequestStatus().equals(VerificationRequestStatus.VERIFICATION)) {
                list.add(conversionService.convert(request.getAwacCalculatorInstance(), VerificationRequestDTO.class));
            }
        }

        return ok(list);
    }

    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    @SecurityAnnotation(isAdmin = true, isMainVerifier = true)
    public Result getVerificationRequestsVerifiedToConfirm() {

        //control interface
        if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {
            return unauthorized(new ExceptionsDTO("You are not a verifier"));
        }

        ListDTO<VerificationRequestDTO> list = new ListDTO<>();

        //load by account
        for (VerificationRequest request : securedController.getCurrentUser().getVerificationRequestList()) {
            if (request.getVerificationRequestStatus().equals(VerificationRequestStatus.WAIT_VERIFICATION_CONFIRMATION_REJECT) ||
                    request.getVerificationRequestStatus().equals(VerificationRequestStatus.WAIT_VERIFICATION_CONFIRMATION_SUCCESS)) {
                list.add(conversionService.convert(request.getAwacCalculatorInstance(), VerificationRequestDTO.class));
            }
        }

        return ok(list);
    }

    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    public Result verify() {
        VerifyDTO dto = extractDTOFromRequest(VerifyDTO.class);

        //control interface
        if (!securedController.getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION)) {
            return unauthorized(new ExceptionsDTO("You are not a verifier"));
        }

        //load scope and period
        Scope scope = scopeService.findById(dto.getScopeId());
        Period period = periodService.findByCode(new PeriodCode(dto.getPeriodKey()));
        QuestionSet questionSet = questionSetService.findByCode(new QuestionCode(dto.getQuestionSetKey()));
        VerificationStatus verificationStatus = new VerificationStatus(dto.getVerification().getStatus());

        if (scope == null || period == null || questionSet == null) {
            return unauthorized(new ExceptionsDTO("You are not a verifier"));
        }

        List<QuestionSetAnswer> questionSetAnswerList = quesstionSetAnswerService.findByScopeAndPeriodAndQuestionSet(scope, period, questionSet);

        if (questionSetAnswerList.size() != 1) {
            return unauthorized(new ExceptionsDTO("You are not a verifier"));
        }

        //control verifier
        VerificationRequest verificationRequest = verificationRequestService.findByVerifierAndScopeAndPeriod(securedController.getCurrentUser(), scope, period);

        if (verificationRequest == null ||
                !verificationRequest.getVerificationRequestStatus().equals(VerificationRequestStatus.VERIFICATION) ||
                !verificationRequest.getVerifierList().contains(securedController.getCurrentUser())) {
            return unauthorized(new ExceptionsDTO("You are not a verifier"));
        }

        //try to load
        Verification verification = verificationService.findByVerificationRequestAndQuestionSet(verificationRequest, questionSet);

        //edit
        if (verification == null) {
            verification = new Verification();

            verification.setQuestionSetAnswer(questionSetAnswerList.get(0));
            verification.setVerificationRequest(verificationRequest);

            verification.setComment(dto.getVerification().getComment());
            verification.setVerificationStatus(verificationStatus);
            verification.setVerifier(securedController.getCurrentUser());
        } else {
            verification.setComment(dto.getVerification().getComment());
            verification.setVerificationStatus(verificationStatus);
            verification.setVerifier(securedController.getCurrentUser());
        }
        verificationService.saveOrUpdate(verification);


        //return
        return ok(conversionService.convert(verification, VerificationDTO.class));
    }

    private List<String> getMainVerifierAdmins(Organization organization) {

        List<String> emails = new ArrayList<>();
        for (Account account : organization.getAccounts()) {
            if (account.getIsMainVerifier() && account.getIsAdmin()) {
                emails.add(account.getPerson().getEmail());
            }
        }
        return emails;

    }

    private List<String> getAssignedAccountEmail(VerificationRequest verificationRequest) {
        List<String> emails = new ArrayList<>();
        for (Account account : verificationRequest.getVerifierList()) {
            emails.add(account.getPerson().getEmail());
        }
        return emails;
    }

    private List<String> getAdmins(Organization organization) {
        List<String> emails = new ArrayList<>();
        for (Account account : organization.getAccounts()) {
            if (account.getIsAdmin()) {
                emails.add(account.getPerson().getEmail());
            }
        }
        return emails;
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

        return verificationRequest;
    }


    private void deleteVerificationRequest(VerificationRequest verificationRequest) {

        if (verificationRequest.getOrganizationVerifier() != null) {
            VerificationRequestCanceled verificationRequestCanceled = new VerificationRequestCanceled(
                    verificationRequest.getAwacCalculatorInstance(),
                    verificationRequest.getOrganizationVerifier(),
                    verificationRequest.getContact(),
                    verificationRequest.getEmailVerificationContent());

            verificationRequestCanceledService.saveOrUpdate(verificationRequestCanceled);
        }

        verificationRequestService.remove(verificationRequest);
    }

}
