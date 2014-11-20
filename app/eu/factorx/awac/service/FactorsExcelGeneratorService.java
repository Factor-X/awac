package eu.factorx.awac.service;

import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.code.type.PeriodCode;
import eu.factorx.awac.models.knowledge.Period;
import jxl.write.WriteException;

import java.io.IOException;

public interface FactorsExcelGeneratorService {
    byte[] generateExcel(LanguageCode lang) throws IOException, WriteException;
}
