package eu.factorx.awac.service;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.knowledge.Period;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import java.io.IOException;
import java.util.List;

public interface ResultPdfGeneratorService {

	byte[] generate(LanguageCode lang, List<Scope> scopes, Period period, InterfaceTypeCode interfaceCode) throws WriteException, IOException, BiffException;
}
