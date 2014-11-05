package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.forms.AwacCalculatorInstance;
import eu.factorx.awac.models.knowledge.Period;

/**
 * Created by florian on 26/09/14.
 */
public interface AwacCalculatorInstanceService extends PersistenceService<AwacCalculatorInstance> {

    public AwacCalculatorInstance findByCalculatorAndPeriodAndScope(AwacCalculator awacCalculator, Period period, Scope scope);

    public AwacCalculatorInstance findByPeriodAndScope(Period period, Scope scope);

    public List<AwacCalculatorInstance> findByPeriodAndScopes(Period period, List<Scope> scopeList);

    public List<AwacCalculatorInstance> findByScope(Scope scope);
}
