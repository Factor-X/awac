package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.service.AwacCalculatorService;

@Component
public class AwacCalculatorServiceImpl extends AbstractJPAPersistenceServiceImpl<AwacCalculator> implements AwacCalculatorService {

	@Override
	public AwacCalculator findByCode(InterfaceTypeCode interfaceTypeCode) {

        List<AwacCalculator> resultList = JPA.em().createNamedQuery(AwacCalculator.FIND_BY_CODE, AwacCalculator.class)
                .setParameter("interfaceTypeCode", interfaceTypeCode).getResultList();

        if (resultList.size() > 1) {
            String errorMsg = "More than one account with interfaceTypeCode = '" + interfaceTypeCode + "'";
            Logger.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        if (resultList.size() == 0) {
            return null;
        }
        return resultList.get(0);
	}

}
