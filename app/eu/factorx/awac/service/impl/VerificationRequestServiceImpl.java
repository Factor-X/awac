package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.forms.VerificationRequest;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.VerificationRequestService;
import org.springframework.stereotype.Repository;
import play.Logger;
import play.db.jpa.JPA;

import java.util.List;

/**
 * Created by florian on 26/09/14.
 */
@Repository
public class VerificationRequestServiceImpl extends AbstractJPAPersistenceServiceImpl<VerificationRequest> implements VerificationRequestService {

    @Override
    public VerificationRequest findByKey(String verificationRequestKey) {
        List<VerificationRequest> resultList = JPA.em().createNamedQuery(VerificationRequest.FIND_BY_KEY, VerificationRequest.class)
                .setParameter("verificationRequestKey", verificationRequestKey).getResultList();

        if (resultList.size() > 1) {
            String errorMsg = "More than one account with identifier = '" + verificationRequestKey + "'";
            Logger.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        if (resultList.size() == 0) {
            return null;
        }
        return resultList.get(0);

    }


    @Override
    public VerificationRequest findByVerifierAndScopeAndPeriod(Account verifier, Scope scope, Period period) {
        List<VerificationRequest> resultList = JPA.em().createNamedQuery(VerificationRequest.FIND_BY_SCOPE_AND_PERIOD, VerificationRequest.class)
                .setParameter("scope", scope)
                .setParameter("period", period)
                .getResultList();

        if (resultList.size() > 1) {
            String errorMsg = "More than one account for verifier = '" + verifier + " and scope = " + scope.getId() + " and period = " + period.getLabel();
            Logger.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        if (resultList.size() == 0) {
            return null;
        }
        if (resultList.get(0).getVerifierList().contains(verifier)) {
            return resultList.get(0);
        }
        return null;
    }

    public VerificationRequest findByOrganizationVerifierAndScopeAndPeriod(Organization organizationVerifier, Scope scope, Period period) {
        List<VerificationRequest> resultList = JPA.em().createNamedQuery(VerificationRequest.FIND_BY_ORGANIZATION_VERIFIER_AND_SCOPE_AND_PERIOD, VerificationRequest.class)
                .setParameter("organizationVerifier", organizationVerifier)
                .setParameter("scope", scope)
                .setParameter("period", period)
                .getResultList();

        if (resultList.size() > 1) {
            String errorMsg = "More than one account for organizationVerifier = '" + organizationVerifier + " and scope = " + scope.getId() + " and period = " + period.getLabel();
            Logger.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        if (resultList.size() == 0) {
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public List<VerificationRequest> findByOrganizationVerifier(Organization organizationVerifier) {
        return JPA.em().createNamedQuery(VerificationRequest.FIND_BY_ORGANIZATION_VERIFIER, VerificationRequest.class)
                .setParameter("organizationVerifier", organizationVerifier).getResultList();

    }


}
