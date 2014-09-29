package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.forms.AwacCalculatorClosed;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.AwacCalculatorClosedService;
import org.springframework.stereotype.Repository;
import play.Logger;
import play.db.jpa.JPA;

import java.util.List;

/**
 * Created by florian on 26/09/14.
 */
@Repository
public class AwacCalculatorClosedServiceImpl extends AbstractJPAPersistenceServiceImpl<AwacCalculatorClosed> implements AwacCalculatorClosedService {

    @Override
    public AwacCalculatorClosed findByCalculatorAndPeriodAndScope(AwacCalculator awacCalculator, Period period, Scope scope) {

        List<AwacCalculatorClosed> resultList = JPA.em().createNamedQuery(AwacCalculatorClosed.FIND_BY_CALCULATOR_AND_PERIOD_AND_SCOPE, AwacCalculatorClosed.class)
                .setParameter("calculator", awacCalculator).setParameter("period", period).setParameter("scope", scope).getResultList();
        if (resultList.size() > 1) {
            String errorMsg = "More than one account with calculator = '" + awacCalculator.getInterfaceTypeCode().getKey() + ", period : "+period.getLabel()+", scope :"+scope.getId();
            Logger.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        if (resultList.size() == 0) {
            return null;
        }
        return resultList.get(0);
        /*
        return JPA.em().createNamedQuery(AwacCalculatorClosed.FIND_BY_CALCULATOR_AND_PERIOD_AND_SCOPE, AwacCalculatorClosed.class)
                .setParameter("calculator", awacCalculator).setParameter("period", period).setParameter("scope", scope).getSingleResult();
                */
    }
}
