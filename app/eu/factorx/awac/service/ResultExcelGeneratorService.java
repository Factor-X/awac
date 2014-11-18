package eu.factorx.awac.service;

import java.io.IOException;
import java.util.List;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.knowledge.Period;

public interface ResultExcelGeneratorService {

	byte[] generateExcelInStream(LanguageCode lang, List<Scope> scopes, Period period, InterfaceTypeCode interfaceCode)
		throws IOException, WriteException, BiffException;

	byte[] generateComparedExcelInStream(LanguageCode lang, List<Scope> scopes, Period period, Period comparedPeriod, InterfaceTypeCode interfaceCode)
		throws IOException, WriteException, BiffException;
}
