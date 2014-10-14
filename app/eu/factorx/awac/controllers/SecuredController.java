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

import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.SystemAdministrator;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.AccountService;
import eu.factorx.awac.service.AccountSiteAssociationService;
import eu.factorx.awac.service.VerificationRequestService;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class SecuredController extends Security.Authenticator {

    public static final String SESSION_IDENTIFIER_STORE = "identifier";
    public static final String SESSION_DEFAULT_LANGUAGE_STORE = "defaultLanguage";

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountSiteAssociationService accountSiteAssociationService;

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get(SESSION_IDENTIFIER_STORE);
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return unauthorized(new ExceptionsDTO("Not connected"));
    }

    public boolean isAuthenticated() {
        return (Context.current().session().get(SESSION_IDENTIFIER_STORE) == null) ? false : true;
    }

    @Transactional(readOnly = true)
    public Account getCurrentUser() {

        return accountService.findByIdentifier(Context.current().session().get(SESSION_IDENTIFIER_STORE));
    }

    public boolean isAdministrator() {
        return ((Account) getCurrentUser()).getIsAdmin();
    }

    public boolean isSystemAdministrator() {
        return (((Account) getCurrentUser()) instanceof SystemAdministrator);
    }

    public void storeIdentifier(Account account) {

        //if the login and the password are ok, refresh the session
        Context.current().session().clear();
        Context.current().session().put(SecuredController.SESSION_IDENTIFIER_STORE, account.getIdentifier());
        Context.current().session().put(SecuredController.SESSION_DEFAULT_LANGUAGE_STORE, account.getPerson().getDefaultLanguage().getKey());
    }

    public LanguageCode getDefaultLanguage() {
        return new LanguageCode(Context.current().session().get(SESSION_DEFAULT_LANGUAGE_STORE));
    }

    public void controlDataAccess(Form form, Period period, Scope scope) {

        if (form == null) {
            throw new RuntimeException("You doesn't have the required authorization for the form ");
        }

        controlDataAccess(period, scope);
    }

    @Autowired
    private VerificationRequestService verificationRequestService;

    public void controlDataAccess(Period period, Scope scope) {

        if (period == null || scope == null) {
            throw new RuntimeException("You doesn't have the required authorization for the site " + scope.getName() + "/ period : " + period.getLabel());
        }
        //control my scope
        try {
            controlMyInstance(scope, period);
        } catch (Exception e) {
            if (!(getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.VERIFICATION) &&
                    verificationRequestService.findByOrganizationVerifierAndScopeAndPeriod(getCurrentUser().getOrganization(), scope, period) != null)) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    private void controlMyInstance(Scope scope, Period period) throws Exception {
        //test scope
        if (!scope.getOrganization().equals(getCurrentUser().getOrganization())) {
            throw new Exception("This is not your scope");

        }

        //for organization, test association between user and site, and site and period
        if (getCurrentUser().getOrganization().getInterfaceCode().equals(InterfaceTypeCode.ENTERPRISE)) {
            boolean founded = false;
            for (AccountSiteAssociation accountSiteAssociation : accountSiteAssociationService.findByAccount(this.getCurrentUser())) {
                if (accountSiteAssociation.getSite().getId().equals(scope.getId())) {
                    founded = true;
                    break;
                }
            }
            if (!founded) {
                throw new Exception("You doesn't have the required authorization for the site " + scope.getName());
            }

            //test period
            boolean foundedPeriod = false;
            for (Period periodToFind : ((Site) scope).getListPeriodAvailable()) {
                if (periodToFind.equals(period)) {
                    foundedPeriod = true;
                }
            }
            if (!foundedPeriod) {
                throw new Exception("You doesn't have the required authorization for the period : " + period.getLabel());
            }
        }

    }

    public boolean isUnlock(QuestionSet questionSet, Scope scope, Period period) {
        cleanOlderLock();

        for (int i = lockQuestionSetList.size() - 1; i >= 0; i--) {
            LockQuestionSet lockQuestionSet = lockQuestionSetList.get(i);

            if (lockQuestionSet.isLocked(questionSet, scope, period, getCurrentUser())) {
                return false;
            }
        }
        return true;
    }

    public void lockForm(QuestionSet questionSet, Scope scope, Period period) {
        cleanOlderLock();
        if (!isUnlock(questionSet, scope, period)) {
            throw new MyrmexRuntimeException(BusinessErrorType.FORM_ALREADY_USED);
        }
        lockQuestionSetList.add(new LockQuestionSet(questionSet, scope, period, getCurrentUser()));
    }

    public void unlockForm(QuestionSet questionSet, Scope scope, Period period) {
        cleanOlderLock();
        for (int i = lockQuestionSetList.size() - 1; i >= 0; i--) {

            LockQuestionSet lockQuestionSet = lockQuestionSetList.get(i);
            if (lockQuestionSet.isLockedByMyself(questionSet, scope, period, getCurrentUser())) {
                lockQuestionSetList.remove(i);
                break;
            }
        }
    }

    public void refreshLock(QuestionSet questionSet, Scope scope, Period period) {
        cleanOlderLock();
        for (LockQuestionSet lockQuestionSet : lockQuestionSetList) {

            if (lockQuestionSet.isLockedByMyself(questionSet, scope, period, getCurrentUser())) {
                lockQuestionSet.refreshLock();
            }
        }
    }

    private void cleanOlderLock() {
        for (int i = lockQuestionSetList.size() - 1; i >= 0; i--) {
            LockQuestionSet lockQuestionSet = lockQuestionSetList.get(i);
            if (lockQuestionSet.getLastLock().getTime() < (new Date().getTime() - SESSION_TIME_MAX)) {
                lockQuestionSetList.remove(i);
            }
        }
    }

    private final static long SESSION_TIME_MAX = 30L * 60L * 1000L;

    private static List<LockQuestionSet> lockQuestionSetList = new ArrayList<>();


    private static class LockQuestionSet {

        private QuestionSet questionSet;

        private Scope scope;

        private Period period;

        private Account account;

        private Date lastLock;

        private LockQuestionSet(QuestionSet questionSet, Scope scope, Period period, Account account) {
            this.questionSet = questionSet;
            this.scope = scope;
            this.period = period;
            this.account = account;
            lastLock = new Date();
        }

        public Date getLastLock() {
            return lastLock;
        }

        public void setLastLock(Date lastLock) {
            this.lastLock = lastLock;
        }

        public QuestionSet getQuestionSet() {
            return questionSet;
        }

        public void setQuestionSet(QuestionSet questionSet) {
            this.questionSet = questionSet;
        }

        public Scope getScope() {
            return scope;
        }

        public void setScope(Scope scope) {
            this.scope = scope;
        }

        public Period getPeriod() {
            return period;
        }

        public void setPeriod(Period period) {
            this.period = period;
        }

        public Account getAccount() {
            return account;
        }

        public void setAccount(Account account) {
            this.account = account;
        }


        public void refreshLock() {
            this.lastLock = new Date();
        }

        public boolean isLocked(QuestionSet questionSet, Scope scope, Period period, Account account) {
            if (this.questionSet.equals(questionSet) && this.scope.equals(scope) && this.period.equals(period) && !this.account.equals(account)) {
                return true;
            }
            return false;
        }

        public boolean isLockedByMyself(QuestionSet questionSet, Scope scope, Period period, Account account) {
            if (this.questionSet.equals(questionSet) && this.scope.equals(scope) && this.period.equals(period) && this.account.equals(account)) {
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return "LockQuestionSet{" +
                    "questionSet=" + questionSet.getCode() +
                    ", scope=" + scope.getId() +
                    ", period=" + period.getLabel() +
                    ", account=" + account.getIdentifier() +
                    ", lastLock=" + lastLock +
                    '}';
        }
    }

}

