package eu.factorx.awac.service;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.knowledge.Period;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ResultPdfGeneratorService {

	byte[] generate(AwacCalculator awacCalculator, LanguageCode lang, List<Scope> scopes, Period period, Period comparedPeriod, InterfaceTypeCode interfaceCode, Map<String, Double> typicalResultValues, Map<String, Double> idealResultValues) throws WriteException, IOException, BiffException;
}
