package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.forms.VerificationRequest;
import eu.factorx.awac.service.AwacCalculatorService;
import org.springframework.stereotype.Component;
import play.Logger;
import play.db.jpa.JPA;

import javax.persistence.TypedQuery;
import java.util.List;

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
