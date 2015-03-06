package eu.factorx.awac.service.impl;

import eu.factorx.awac.GlobalVariables;
import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.FactorValue;
import eu.factorx.awac.service.FactorValueService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class FactorValueServiceImpl extends AbstractJPAPersistenceServiceImpl<FactorValue> implements FactorValueService {

	@Override
	public FactorValue findByFactorAndYear(Factor factor, int year) {
		/*

		PERFORMANCE ISSUE:

		List<FactorValue> candidate = JPA.em().createQuery("select fv from FactorValue fv where fv.factor = :f and fv.dateIn <= :year and fv.dateOut is not null and fv.dateOut >= :year ", FactorValue.class)
			.setParameter("f", factor)
			.setParameter("year", year)
			.getResultList();

		if (candidate.size() > 0) {
			return candidate.get(0);
		}

		return JPA.em().createQuery("select fv from FactorValue fv where fv.factor = :f and fv.dateIn <= :year and fv.dateOut is null ", FactorValue.class)
			.setParameter("f", factor)
			.setParameter("year", year)
			.getSingleResult();

		2 QUERIES. WE SIMPLIFY IT BY AN "ORDER BY". FIRST THE NOT NULLS ARE GIVE THEN THE NULLS. SO WE ALWAYS RETURN THE FIRST RESULT.
		*/

		if (GlobalVariables.factorValues == null) {
			GlobalVariables.factorValues = this.findAll();
			Collections.sort(GlobalVariables.factorValues, new Comparator<FactorValue>() {
				@Override
				public int compare(FactorValue o1, FactorValue o2) {
					Integer out1 = o1.getDateOut();
					Integer out2 = o2.getDateOut();
					if (out1 == null) out1 = Integer.MAX_VALUE;
					if (out2 == null) out2 = Integer.MAX_VALUE;
					return out1.compareTo(out2);
				}
			});
		}

		List<FactorValue> resultList = new ArrayList<>();

		for (FactorValue factorValue : GlobalVariables.factorValues) {
			if (factorValue.getFactor().getId().equals(factor.getId()) && factorValue.getDateIn() <= year) {
				resultList.add(factorValue);
			}
		}

		for (FactorValue factorValue : resultList) {
			Integer dateOut = factorValue.getDateOut();
			if (dateOut == null) {
				return factorValue;
			} else {
				if (dateOut >= year) {
					return factorValue;
				}
			}
		}

		return null;
	}

}
