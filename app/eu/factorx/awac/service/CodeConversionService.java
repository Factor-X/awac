package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.conversion.CodesEquivalence;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;

public interface CodeConversionService {

	ActivitySourceCode toActivitySourceCode(Code code);

	ActivityTypeCode toActivityTypeCode(Code code);

	/**
	 * Returns all "sublist" data (without primary key), i.e. all CodeEquivalence entities where codeKey = referencedCodeKey, ordered by id (insertion order)
	 */
	List<CodesEquivalence> findAllSublistsData();

}
