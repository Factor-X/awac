package eu.factorx.awac.service;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.forms.AwacCalculatorInstance;
import eu.factorx.awac.models.knowledge.Period;

import java.util.List;

/**
 * Created by florian on 26/09/14.
 */
public interface AwacCalculatorInstanceService extends PersistenceService<AwacCalculatorInstance> {

    public AwacCalculatorInstance findByCalculatorAndPeriodAndScope(AwacCalculator awacCalculator, Period period, Scope scope);

    public AwacCalculatorInstance findByPeriodAndScope(Period period, Scope scope);

    public List<AwacCalculatorInstance> findByPeriodAndOrganization(Period period, Organization organization);
}
