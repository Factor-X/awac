package eu.factorx.awac.service;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.forms.AwacCalculatorClosed;
import eu.factorx.awac.models.knowledge.Period;

/**
 * Created by florian on 26/09/14.
 */
public interface AwacCalculatorClosedService extends PersistenceService<AwacCalculatorClosed> {

    public AwacCalculatorClosed findByCalculatorAndPeriodAndScope(AwacCalculator awacCalculator, Period period, Scope scope);

}
