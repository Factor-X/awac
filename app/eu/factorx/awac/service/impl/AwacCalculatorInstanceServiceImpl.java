package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.forms.AwacCalculatorInstance;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.AwacCalculatorInstanceService;

/**
 * Created by florian on 26/09/14.
 */
@Repository
public class AwacCalculatorInstanceServiceImpl extends AbstractJPAPersistenceServiceImpl<AwacCalculatorInstance> implements AwacCalculatorInstanceService {


    @Override
    public AwacCalculatorInstance findByCalculatorAndPeriodAndScope(AwacCalculator awacCalculator, Period period, Scope scope) {

        if(awacCalculator==null || period == null || scope == null){
            throw new RuntimeException("AwacCalculatorInstance findByCalculatorAndPeriodAndScope : one argument is null");
        }

        List<AwacCalculatorInstance> resultList = JPA.em().createNamedQuery(AwacCalculatorInstance.FIND_BY_CALCULATOR_AND_PERIOD_AND_SCOPE, AwacCalculatorInstance.class)
                .setParameter("calculator", awacCalculator).setParameter("period", period).setParameter("scope", scope).getResultList();
        if (resultList.size() > 1) {
            String errorMsg = "More than one account with calculator = '" + awacCalculator.getInterfaceTypeCode().getKey() + ", period : " + period.getLabel() + ", scope :" + scope.getId();
            Logger.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        if (resultList.size() == 0) {
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public AwacCalculatorInstance findByPeriodAndScope(Period period, Scope scope) {

        List<AwacCalculatorInstance> resultList = JPA.em().createNamedQuery(AwacCalculatorInstance.FIND_BY_PERIOD_AND_SCOPE, AwacCalculatorInstance.class)
                .setParameter("period", period).setParameter("scope", scope).getResultList();
        if (resultList.size() > 1) {
            String errorMsg = "More than one account with period : " + period.getLabel() + ", scope :" + scope.getId();
            Logger.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        if (resultList.size() == 0) {
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public List<AwacCalculatorInstance> findByScope(Scope scope) {
        return JPA.em().createNamedQuery(AwacCalculatorInstance.FIND_BY_SCOPE, AwacCalculatorInstance.class)
                .setParameter("scope", scope)
                .getResultList();
    }

    @Override
    public List<AwacCalculatorInstance> findByCalculator(AwacCalculator awacCalculator) {


        return JPA.em().createNamedQuery(AwacCalculatorInstance.FIND_BY_CALCULATOR, AwacCalculatorInstance.class)
                .setParameter("calculator", awacCalculator)
                .getResultList();
    }

    @Override
    public List<AwacCalculatorInstance> findByPeriodAndScopes(Period period, List<Scope> scopeList) {

        return JPA.em().createNamedQuery(AwacCalculatorInstance.FIND_BY_PERIOD_AND_SCOPES, AwacCalculatorInstance.class)
                .setParameter("period", period)
                .setParameter("scopes", scopeList)
                .getResultList();
    }


}
