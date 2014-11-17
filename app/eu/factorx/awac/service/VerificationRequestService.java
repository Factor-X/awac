package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.VerificationRequestStatus;
import eu.factorx.awac.models.forms.VerificationRequest;
import eu.factorx.awac.models.knowledge.Period;

/**
 * Created by florian on 9/10/14.
 */
public interface VerificationRequestService extends PersistenceService<VerificationRequest> {

    public VerificationRequest findByKey(String verificationRequestKey);

    public List<VerificationRequest> findByOrganizationVerifier(Organization organizationVerifier);

    public VerificationRequest findByVerifierAndScopeAndPeriod(Account verifier, Scope scope, Period period);

    public VerificationRequest findByOrganizationVerifierAndScopeAndPeriod(Organization organizationVerifier, Scope scope,Period period);

    public List<VerificationRequest> findByOrganizationCustomerAndOrganizationVerifier(Organization organizationCustomer, Organization organizationVerifier);

	public List<VerificationRequest> findByOrganizationVerifierAndVerificationRequestStatus(Organization organization, VerificationRequestStatus verificationRequestStatus);

	public boolean hasVerificationAccessToScope(Account verifier, Scope scope);

}
