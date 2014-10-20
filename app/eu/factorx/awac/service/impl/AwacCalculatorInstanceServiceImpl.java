package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.forms.AwacCalculatorInstance;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.AwacCalculatorInstanceService;
import eu.factorx.awac.service.OrganizationService;
import eu.factorx.awac.service.ScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import play.Logger;
import play.db.jpa.JPA;

import java.util.List;

/**
 * Created by florian on 26/09/14.
 */
@Repository
public class AwacCalculatorInstanceServiceImpl extends AbstractJPAPersistenceServiceImpl<AwacCalculatorInstance> implements AwacCalculatorInstanceService {


    @Override
    public AwacCalculatorInstance findByCalculatorAndPeriodAndScope(AwacCalculator awacCalculator, Period period, Scope scope) {

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
    public List<AwacCalculatorInstance> findByPeriodAndOrganization(Period period, Organization organization) {

        List<Scope> scopeList =organization.getScopes();

        return JPA.em().createNamedQuery(AwacCalculatorInstance.FIND_BY_PERIOD_AND_ORGANIZATION, AwacCalculatorInstance.class)
                .setParameter("period", period)
                .setParameter("organization", organization)
                .getResultList();
    }

}
