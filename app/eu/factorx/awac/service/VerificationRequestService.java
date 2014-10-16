package eu.factorx.awac.service;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.forms.VerificationRequest;
import eu.factorx.awac.models.knowledge.Period;

import java.util.List;

/**
 * Created by florian on 9/10/14.
 */
public interface VerificationRequestService extends PersistenceService<VerificationRequest> {

    public VerificationRequest findByKey(String verificationRequestKey);

    public List<VerificationRequest> findByOrganizationVerifier(Organization organizationVerifier);

    public VerificationRequest findByVerifierAndScopeAndPeriod(Account verifier, Scope scope, Period period);

    public VerificationRequest findByOrganizationVerifierAndScopeAndPeriod(Organization organizationVerifier, Scope scope,Period period);

    public List<VerificationRequest> findByOrganizationCustomerAndOrganizationVerifier(Organization organizationCustomer, Organization organizationVerifier);
}
