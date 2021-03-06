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
import eu.factorx.awac.models.association.AccountProductAssociation;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.code.type.ScopeTypeCode;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.AccountProductAssociationService;
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
    @Autowired
    private AccountProductAssociationService accountProductAssociationService;

    @Override
    public String getUsername(Context ctx) {

        Logger.warn("---- getUsername =>" + ctx.request().cookies().get("PLAY_SESSION").value() + " <=> " + ctx.session().entrySet());

        return ctx.session().get(SESSION_IDENTIFIER_STORE);
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return unauthorized(new ExceptionsDTO(BusinessErrorType.NOT_CONNECTED));
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


    public boolean isMainVerifier() {
        return ((Account) getCurrentUser()).getIsMainVerifier();
    }

    public boolean isSystemAdministrator() {
        return (((Account) getCurrentUser()) instanceof SystemAdministrator);
    }

    public void storeIdentifier(Account account) {

        //if the login and the password are ok, refresh the session
        Context.current().session().clear();
        Context.current().session().put(SecuredController.SESSION_IDENTIFIER_STORE, account.getIdentifier());
        Context.current().session().put(SecuredController.SESSION_DEFAULT_LANGUAGE_STORE, account.getDefaultLanguage().getKey());
    }

    public void logout() {
        Logger.warn("logout !! ");
        try {
            throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Context.current().session().clear();
    }

    public LanguageCode getDefaultLanguage() {
        return new LanguageCode(Context.current().session().get(SESSION_DEFAULT_LANGUAGE_STORE));
    }

    public void controlDataAccess(Form form, Period period, Scope scope) {//},boolean returnErrorMessage) {

        if (form == null) {
            throw new MyrmexRuntimeException(BusinessErrorType.WRONG_RIGHT);
        }

        controlDataAccess(period, scope);
    }

    @Autowired
    private VerificationRequestService verificationRequestService;

    public void controlDataAccess(Period period, Scope scope) {

        if (period == null || scope == null) {
            throw new MyrmexRuntimeException(BusinessErrorType.NOT_AUTHORIZATION_SCOPE_PERIOD);
        }

        //control my scope
        try {
            controlMyInstance(scope, period);
        } catch (Exception e) {
            if (!verificationRequestService.hasVerificationAccessToScope(getCurrentUser(), scope)) {
                throw new MyrmexRuntimeException(BusinessErrorType.NOT_AUTHORIZATION_SCOPE_PERIOD, scope.getName(), period.getLabel());
            }
        }
    }

    private void controlMyInstance(Scope scope, Period period) {

        //test scope
        if (!scope.getOrganization().equals(getCurrentUser().getOrganization())) {
            throw new MyrmexRuntimeException(BusinessErrorType.NOT_YOUR_SCOPE_LITTLE);

        }

        //for sites, test association between user and site, and site and period
        if (getCurrentUser().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.SITE)) {
            boolean founded = false;
            for (AccountSiteAssociation accountSiteAssociation : accountSiteAssociationService.findByAccount(this.getCurrentUser())) {
                if (accountSiteAssociation.getSite().getId().equals(scope.getId())) {
                    founded = true;
                    break;
                }
            }
            if (!founded) {
                throw new MyrmexRuntimeException(BusinessErrorType.NOT_YOUR_SCOPE, scope.getName());
            }

            //test period
            boolean foundedPeriod = false;
            for (Period periodToFind : ((Site) scope).getListPeriodAvailable()) {
                if (periodToFind.equals(period)) {
                    foundedPeriod = true;
                }
            }
            if (!foundedPeriod) {
                throw new MyrmexRuntimeException(BusinessErrorType.NOT_YOUR_PERIOD, period.getLabel());
            }
        }
        //for product, test association between user and site, and site and period
        else if (getCurrentUser().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.PRODUCT)) {
            boolean founded = false;
            for (AccountProductAssociation accountProductAssociation : accountProductAssociationService.findByAccount(this.getCurrentUser())) {
                if (accountProductAssociation.getProduct().getId().equals(scope.getId())) {
                    founded = true;
                    break;
                }
            }
            if (!founded) {
                throw new MyrmexRuntimeException(BusinessErrorType.NOT_YOUR_SCOPE, scope.getName());
            }

            //test period
            boolean foundedPeriod = false;
            for (Period periodToFind : ((Product) scope).getListPeriodAvailable()) {
                if (periodToFind.equals(period)) {
                    foundedPeriod = true;
                }
            }
            if (!foundedPeriod) {
                throw new MyrmexRuntimeException(BusinessErrorType.NOT_YOUR_PERIOD, period.getLabel());
            }
        }

    }

    public List<Scope> getAuthorizedScopes(Account account) {
        List<Scope> res = new ArrayList<>();
        // add organization
        res.add(account.getOrganization());
        // add authorized sites
        for (AccountSiteAssociation accountSiteAssociation : accountSiteAssociationService.findByAccount(account)) {
            res.add(accountSiteAssociation.getSite());
        }
        return res;
    }


    public List<Scope> getAuthorizedScopes(Account account, Period period) {
        List<Scope> res = new ArrayList<>();

        if (getCurrentUser().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.SITE)) {
            // add authorized sites
            for (AccountSiteAssociation accountSiteAssociation : accountSiteAssociationService.findByAccount(account)) {
                if (accountSiteAssociation.getSite().getListPeriodAvailable().contains(period)) {
                    res.add(accountSiteAssociation.getSite());
                }
            }
        } else if (getCurrentUser().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.ORG)) {
            // add organization
            res.add(account.getOrganization());
        } else if (getCurrentUser().getOrganization().getInterfaceCode().getScopeTypeCode().equals(ScopeTypeCode.PRODUCT)) {
            // add authorized product
            for (AccountProductAssociation accountProductAssociation : accountProductAssociationService.findByAccount(account)) {
                if (accountProductAssociation.getProduct().getListPeriodAvailable().contains(period)) {
                    res.add(accountProductAssociation.getProduct());
                }
            }
        }
        return res;
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

