package eu.factorx.awac.service;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;

public interface CodeConversionService {

	ActivitySourceCode toActivitySourceCode(Code code);

	ActivityTypeCode toActivityTypeCode(Code code);

}
