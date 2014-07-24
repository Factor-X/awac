package eu.factorx.awac.service.impl;

import java.util.List;

import play.db.jpa.JPA;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.conversion.CodeToActivitySourceCodeEquivalence;
import eu.factorx.awac.models.code.conversion.CodeToActivityTypeCodeEquivalence;
import eu.factorx.awac.models.code.conversion.CodesEquivalence;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;
import eu.factorx.awac.service.CodeConversionService;

public class CodeConversionServiceImpl extends AbstractJPAPersistenceServiceImpl<CodesEquivalence> implements CodeConversionService {

	@Override
	public ActivitySourceCode toActivitySourceCode(Code code) {
		return findByCode(code, CodeToActivitySourceCodeEquivalence.FIND_BY_CODE, ActivitySourceCode.class);
	}

	@Override
	public ActivityTypeCode toActivityTypeCode(Code code) {
		return findByCode(code, CodeToActivityTypeCodeEquivalence.FIND_BY_CODE, ActivityTypeCode.class);
	}

	private <T> T findByCode(Code code, String namedQuery, Class<T> resultType) {
		List<T> resultList = JPA.em().createNamedQuery(namedQuery, resultType)
				.setParameter("codeList", code.getCodeList())
				.setParameter("codeKey", code.getKey())
				.getResultList();
		if (resultList.size() > 1) {
			throw new RuntimeException("More than one equivalence found for given parameters");
		}
		if (resultList.size() == 0) {
			return null;
		}

		return resultList.get(0);
	}

}
